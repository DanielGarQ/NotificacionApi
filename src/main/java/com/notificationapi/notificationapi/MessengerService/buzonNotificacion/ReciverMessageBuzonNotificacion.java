package com.notificationapi.notificationapi.MessengerService.buzonNotificacion;

import com.notificationapi.notificationapi.crossCutting.Messages.UtilMessagesService;
import com.notificationapi.notificationapi.crossCutting.utils.gson.MapperJsonObjeto;
import com.notificationapi.notificationapi.service.BuzonNotificacionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ReciverMessageBuzonNotificacion {

    public static final Logger logger = LoggerFactory.getLogger(ReciverMessageBuzonNotificacion.class);

    private final BuzonNotificacionService buzonNotificacionService;
    private final MapperJsonObjeto mapperJsonObjeto;

    @Autowired
    public ReciverMessageBuzonNotificacion(BuzonNotificacionService buzonNotificacionService, MapperJsonObjeto mapperJsonObjeto) {
        this.buzonNotificacionService = buzonNotificacionService;
        this.mapperJsonObjeto = mapperJsonObjeto;
    }


    @RabbitListener(queues = "cola.buzon.lista")
    public  void receiveMessageConsultaBuzon(String message) {
        var mensajeRecibido = obtenerObjetoDeMensaje(message).get();
        try {
            buzonNotificacionService.listaRecibida(mensajeRecibido);
        } catch (Exception exception) {
            logger.error(UtilMessagesService.ReciverMessageBuzonNotificacion.ERROR_DEFAULT, exception.getMessage(), exception);
        }
    }
    @RabbitListener(queues = "cola.buzon.respuesta")
    public  void receiveMessageRespuestaBuzon(String message) {
        var mensajeRecibido = obtenerObjetoDeMensajeString(message).get();
        try {
            buzonNotificacionService.setMensajeExcepcion(mensajeRecibido);
        } catch (Exception exception) {
            logger.error(UtilMessagesService.ReciverMessageBuzonNotificacion.ERROR_DEFAULT, exception.getMessage(), exception);
        }
    }
    private Optional<List> obtenerObjetoDeMensaje(String mensaje) {
        return mapperJsonObjeto.ejecutar(mensaje,List.class);
    }
    private Optional<String> obtenerObjetoDeMensajeString(String mensaje){
        return  mapperJsonObjeto.ejecutar(mensaje,String.class);
    }
}
