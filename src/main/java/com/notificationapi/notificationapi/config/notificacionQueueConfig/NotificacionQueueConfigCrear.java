package com.notificationapi.notificationapi.config.notificacionQueueConfig;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@Setter
@ConfigurationProperties(prefix = "notificacion-crear-rabbitmq")
public class NotificacionQueueConfigCrear extends NotificacionQueueConfig{

    public String exchangeName;
    public String routingKeyName;
    public String queueName;

}
