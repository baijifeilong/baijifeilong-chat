package io.github.baijifeilong.chat;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DefaultDSLContext;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;

/**
 * 应用入口
 */
@SpringBootApplication
public class App implements InitializingBean, WebMvcConfigurer {
    public static void main(String[] args) {
        new SpringApplicationBuilder(App.class).web(WebApplicationType.SERVLET).run(args);
    }

    @Bean
    public DSLContext dslContext(DataSource dataSource) {
        return new DefaultDSLContext(dataSource, SQLDialect.MYSQL_8_0);
    }

    /**
     * 全局CORS配置
     *
     * @param registry .
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }

    @Override
    public void afterPropertiesSet() {
        ((Logger) LoggerFactory.getLogger(org.jooq.Constants.class.getPackage().getName())).setLevel(Level.DEBUG);
        ((Logger) LoggerFactory.getLogger(org.jooq.Constants.class)).setLevel(Level.WARN);
        ((Logger) LoggerFactory.getLogger(App.class.getPackage().getName())).setLevel(Level.DEBUG);
    }
}
