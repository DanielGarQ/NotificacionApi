package com.notificationapi.notificationapi.service;

import com.notificationapi.notificationapi.MessengerService.notificacion.MessageSenderNotificacion;
import com.notificationapi.notificationapi.config.notificacionQueueConfig.NotificacionQueueConfigConsultar;
import com.notificationapi.notificationapi.config.notificacionQueueConfig.NotificacionQueueConfigCrear;
import com.notificationapi.notificationapi.config.notificacionQueueConfig.NotificacionQueueConfigEliminar;
import com.notificationapi.notificationapi.crossCutting.utils.UtilDate;
import com.notificationapi.notificationapi.domain.NotificacionDomain;
import com.notificationapi.notificationapi.domain.PersonaDomain;
import com.notificationapi.notificationapi.entity.PersonaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Repository
public class NotificacionService {


    @Autowired
    private MessageSenderNotificacion messageSenderNotificacion;
    @Autowired
    private NotificacionQueueConfigConsultar notificacionQueueConfigConsultar;
    @Autowired
    private NotificacionQueueConfigCrear notificacionQueueConfigCrear;
    @Autowired
    private NotificacionQueueConfigEliminar notificacionQueueConfigEliminar;


    public List<NotificacionDomain> findAll(){
        NotificacionDomain notificacionPorDefecto = new NotificacionDomain();
        messageSenderNotificacion.execute(notificacionPorDefecto,"12312", notificacionQueueConfigConsultar.getExchangeName(),notificacionQueueConfigConsultar.getRoutingKeyName());
        return null;
    }

    public List<NotificacionDomain> getNotificacionesPorDestinatario(String correo){
       NotificacionDomain notificacionDomain = new NotificacionDomain();
       PersonaDomain autor = new PersonaDomain();
       autor.setCorreoElectronico(correo);
       notificacionDomain.setAutor(autor);
       messageSenderNotificacion.execute(notificacionDomain,"12314", notificacionQueueConfigConsultar.getExchangeName(), notificacionQueueConfigConsultar.getRoutingKeyName());
       return null;
    }

    public void saveNotificacion(NotificacionDomain notificacion){
        messageSenderNotificacion.execute(notificacion,"3423",notificacionQueueConfigCrear.getExchangeName(),notificacionQueueConfigCrear.getRoutingKeyName());
    }

    public void deleteNotificacion(UUID identificador){
        NotificacionDomain notificacion = new NotificacionDomain();
        notificacion.setIdentificador(identificador);
        messageSenderNotificacion.execute(notificacion,"235432 ", notificacionQueueConfigEliminar.getExchangeName(), notificacionQueueConfigEliminar.getRoutingKeyName());
    }

}
