package com.notificationapi.notificationapi.MessengerService.notificacion;


import com.notificationapi.notificationapi.MessengerService.buzonNotificacion.ReciverMessageBuzonNotificacion;
import com.notificationapi.notificationapi.crossCutting.Messages.UtilMessagesService;
import com.notificationapi.notificationapi.crossCutting.utils.gson.MapperJsonObjeto;
import com.notificationapi.notificationapi.domain.NotificacionDomain;
import com.notificationapi.notificationapi.service.NotificacionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ReciverMesssageNotificacion {

    public static final Logger logger = LoggerFactory.getLogger(ReciverMesssageNotificacion.class);

    private final NotificacionService notificacionService = new NotificacionService();

    @Autowired
    private final MapperJsonObjeto mapperJsonObjeto;

    public ReciverMesssageNotificacion(MapperJsonObjeto mapperJsonObjeto) {
        this.mapperJsonObjeto = mapperJsonObjeto;
    }


    //@RabbitListener(queues = "")
    public void receiveMessageProcessClient(String message) {
        try {
            notificacionService.saveNotificacion(obtenerObjetoDeMensaje(message).get());
        } catch (Exception exception) {
            logger.error(UtilMessagesService.ReciverMessageNotificacion.ERROR_DEFAULT, exception.getMessage(), exception);
        }
    }

    private Optional<NotificacionDomain> obtenerObjetoDeMensaje(String mensaje) {
        return mapperJsonObjeto.ejecutar(mensaje, NotificacionDomain.class);
    }
}
