package com.notificationapi.notificationapi.MessengerService.buzonNotificacion;

import com.notificationapi.notificationapi.config.buzonNotificacionQueueConfig.BuzonNotificacionQueueConfig;
import com.notificationapi.notificationapi.config.buzonNotificacionQueueConfig.BuzonNotificacionQueueConfigCrear;
import com.notificationapi.notificationapi.crossCutting.utils.MessageSender;
import com.notificationapi.notificationapi.crossCutting.utils.gson.MapperJsonObjeto;
import com.notificationapi.notificationapi.domain.BuzonNotificacionDomain;
import com.notificationapi.notificationapi.domain.NotificacionDomain;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.MessagePropertiesBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
@Component

public class MessageSenderBuzonNotificacion implements MessageSender<BuzonNotificacionDomain> {


    @Autowired
    private final RabbitTemplate rabbitTemplate;
    @Autowired
    private final MapperJsonObjeto mapperJsonObjeto;



    public MessageSenderBuzonNotificacion(RabbitTemplate rabbitTemplate, MapperJsonObjeto mapperJsonObjeto) {
        this.rabbitTemplate = rabbitTemplate;
        this.mapperJsonObjeto = mapperJsonObjeto;
    }

    public void sendMesagge(Object mensaje, Long idMensajeEmisor, String exchange, String routingKey) {

    }

    private MessageProperties generarPropiedadesMensaje(Long idMensajeEmisor ) {
        return MessagePropertiesBuilder.newInstance()
                .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                .setHeader("idMensaje", String.valueOf(idMensajeEmisor))
                .build();
    }

    private Optional<Message> obtenerCuerpoMensaje(Object mensaje, MessageProperties propiedadesMensaje) {
        Optional<String> textoMensaje = mapperJsonObjeto.ejecutarGson(mensaje);

        return textoMensaje.map(msg -> MessageBuilder
                .withBody(msg.getBytes())
                .andProperties(propiedadesMensaje)
                .build());

    }


    @Override
    public void execute(BuzonNotificacionDomain message, String idMessage, String exchange, String routingKey) {
        MessageProperties propiedadesMensaje = generarPropiedadesMensaje(Long.valueOf(idMessage));

        Optional<Message> cuerpoMensaje = obtenerCuerpoMensaje(message, propiedadesMensaje);
        if (!cuerpoMensaje.isPresent()) {
            return;
        }

        rabbitTemplate.convertAndSend(exchange, routingKey, cuerpoMensaje.get());
    }
}
