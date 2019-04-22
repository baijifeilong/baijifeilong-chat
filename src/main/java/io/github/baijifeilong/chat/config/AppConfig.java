package io.github.baijifeilong.chat.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by BaiJiFeiLong@gmail.com at 2019/1/26 下午3:07
 * <p>
 * 应用配置
 */
@SuppressWarnings("ALL")
@ConfigurationProperties(prefix = "app")
@Component
@Data
public class AppConfig {

    /**
     * MQTT配置
     */
    private Mqtt mqtt;

    /**
     * 万能密码
     */
    private String godPassword;

    /**
     * MQTT配置
     */
    @Data
    public static class Mqtt {
        /**
         * MQTT连接URI
         */
        private String uri;

        /**
         * MQTT超级用户
         */
        private Superuser superuser;

        /**
         * MQTT超级用户配置
         */
        @Data
        public static class Superuser {

            /**
             * 用户名
             */
            private String username;

            /**
             * 密码
             */
            private String password;
        }
    }
}
