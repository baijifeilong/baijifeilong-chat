package io.github.baijifeilong.chat.worker;

import io.github.baijifeilong.chat.config.AppConfig;
import io.github.baijifeilong.chat.domain.Group;
import io.github.baijifeilong.chat.domain.Message;
import io.github.baijifeilong.chat.domain.MessageTable;
import io.github.baijifeilong.chat.domain.User;
import io.github.baijifeilong.chat.service.GroupService;
import io.github.baijifeilong.chat.service.MessageService;
import io.github.baijifeilong.chat.service.UserService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by BaiJiFeiLong@gmail.com at 2019/1/24 下午3:03
 * <p>
 * MQTT后台任务，用于持久化MQTT消息到数据库
 */
@Component
@Slf4j
public class MqttWorker implements ApplicationListener<ApplicationReadyEvent> {

    @Resource
    private MessageService messageService;

    @Resource
    private UserService userService;

    @Resource
    private GroupService groupService;

    @Resource
    private AppConfig appConfig;

    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 以超级用户身份连接MQTT服务器，并订阅全部消息，收到聊天消息后处理入库
     */
    @SneakyThrows
    private void run() {
        AppConfig.Mqtt mqttConfig = appConfig.getMqtt();
        MqttClient mqttClient = new MqttClient(mqttConfig.getUri(), "root");
        mqttClient.connect(new MqttConnectOptions() {{
            setUserName(mqttConfig.getSuperuser().getUsername());
            setPassword(mqttConfig.getSuperuser().getPassword().toCharArray());
        }});
        mqttClient.subscribe("#");
        mqttClient.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) {
                try {
                    processMessage(topic, message);
                } catch (Throwable e) {
                    log.error("MQTT消息处理失败", e);
                }
            }

            @Override
            @SneakyThrows
            public void deliveryComplete(IMqttDeliveryToken token) {
                log.info("Message delivered: {}", token.getMessage());
            }
        });
    }

    /**
     * 处理MQTT消息
     *
     * @param topic   消息主题
     * @param message 消息
     * @throws IOException .
     */
    private void processMessage(String topic, MqttMessage message) throws IOException {
        Pattern messagePattern = Pattern.compile("^/(users|groups)/(\\d+)/messages$");
        log.info("收到MQTT消息: {} => {}", topic, message);
        Matcher matcher = messagePattern.matcher(topic);
        if (!matcher.matches()) {
            log.info("非聊天消息: {}", message);
            return;
        }
        MessageTable.Type topicType = matcher.group(1).equals("users")
                ? MessageTable.Type.CHAT : MessageTable.Type.GROUPCHAT;
        int receiverId = Integer.parseInt(matcher.group(2));
        JsonNode messageJson = objectMapper.readValue(message.toString(), JsonNode.class);

        MessageTable.Type messageType = MessageTable.Type.valueOf(messageJson.get("type").asText());
        int fromId = messageJson.get("fromId").asInt();
        int toId = messageJson.get("toId").asInt();
        String content = messageJson.get("content").asText();
        log.info("消息流向: {} => {} {}", fromId, toId, content);

        Assertions.assertThat(messageType).isEqualTo(topicType);
        Assertions.assertThat(toId).isEqualTo(receiverId);
        Assertions.assertThat(content).isNotEmpty();

        User fromUser = userService.findUserByUserIdOrThrow(fromId);
        log.info("发送方: {}", fromUser);
        if (messageType.equals(MessageTable.Type.CHAT)) {
            User toUser = userService.findUserByUserIdOrThrow(toId);
            log.info("接收方: {}", toUser);
        } else {
            Group toGroup = groupService.findGroupByGroupIdOrThrow(toId);
            log.info("接收方: {}", toGroup);
        }

        Message chatMessage = messageService.createMessage(messageType, fromId, toId, content);
        log.info("消息已入库: {}", chatMessage);
    }

    @Override
    public void onApplicationEvent(@NonNull ApplicationReadyEvent event) {
        log.info("正在启动MQTT消息监控服务");
        run();
        log.info("MQTT消息监控服务已启动");
    }
}
