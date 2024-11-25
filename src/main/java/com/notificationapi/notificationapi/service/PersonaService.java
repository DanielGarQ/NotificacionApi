package com.notificationapi.notificationapi.service;

import com.notificationapi.notificationapi.MessengerService.buzonNotificacion.ReciverMessageBuzonNotificacion;
import com.notificationapi.notificationapi.crossCutting.Messages.UtilMessagesService;
import com.notificationapi.notificationapi.crossCutting.utils.UtilEmail;
import com.notificationapi.notificationapi.crossCutting.utils.UtilText;
import com.notificationapi.notificationapi.crossCutting.utils.UtilUUID;
import com.notificationapi.notificationapi.crossCutting.exception.NotificationException;
import com.notificationapi.notificationapi.domain.BuzonNotificacionDomain;
import com.notificationapi.notificationapi.domain.PersonaDomain;
import com.notificationapi.notificationapi.entity.PersonaEntity;
import com.notificationapi.notificationapi.repository.PersonaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class PersonaService {

    public static final Logger logger = LoggerFactory.getLogger(PersonaService.class);

    @Autowired
    private PersonaRepository personaRepository;

    @Autowired
    private BuzonNotificacionService buzonNotificacionService;

    public List<PersonaDomain> findAll(){
        return personaRepository.findAll().stream().map(new PersonaService()::toDomain).toList();
    }

    public PersonaDomain toDomain(PersonaEntity entity){
        return new PersonaDomain(entity.getIdentificador(),entity.getPrimerNombre(),entity.getSegundoNombre(),entity.getPrimerApellido()
        ,entity.getSegundoApellido(),entity.getCorreoElectronico());
    }
    public PersonaEntity toEntity(PersonaDomain domain){
        return new PersonaEntity(domain.getIdentificador(),domain.getPrimerNombre(),domain.getSegundoNombre(),domain.getPrimerApellido()
                ,domain.getSegundoApellido(),domain.getCorreoElectronico());
    }

    public PersonaDomain consult(String correoElectronico){
        return toDomain(personaRepository.findBycorreoElectronico(correoElectronico));
    }

    public void save(PersonaDomain persona) throws NotificationException {
        if(!datosSonValidos(persona)){
            throw new NotificationException();
        }
        try {
            personaRepository.save(toEntity(persona));
        }catch (Exception exception){
            logger.error(UtilMessagesService.PersonaService.ERROR_GUARDAR, exception.getMessage(), exception);
        }
        BuzonNotificacionDomain buzonNotificacionDomain = new BuzonNotificacionDomain();
        buzonNotificacionDomain.setPropietario(persona);
        buzonNotificacionService.saveBuzonNotificacion(buzonNotificacionDomain);
    }

    public void update(PersonaDomain persona) throws NotificationException {
        if(persona.getCorreoElectronico().equals(UtilText.getDefaultTextValue()) || persona.getCorreoElectronico().equals(UtilEmail.getDefaultValueMail())){
            throw new NotificationException();
        }
        try{
            personaRepository.updateBycorreoElectronico(persona.getPrimerApellido(),persona.getPrimerNombre(),persona.getSegundoApellido(),persona.getSegundoNombre(),persona.getCorreoElectronico());
        }catch (Exception exception){
            logger.error(UtilMessagesService.PersonaService.ERROR_ACTUALIZAR, exception.getMessage(), exception);
        }
    }

    public void delete(String correo) throws NotificationException {
        if(correo.equals(UtilEmail.getDefaultValueMail())) {
            throw new NotificationException();
        }
        try {
            PersonaDomain persona = toDomain(personaRepository.findBycorreoElectronico(correo));
            personaRepository.deleteById(persona.getIdentificador());
            buzonNotificacionService.eliminar(persona);
        }catch (Exception exception){
            logger.error(UtilMessagesService.PersonaService.ERROR_ELIMINAR, exception.getMessage(), exception);
        }
    }


    private boolean datosSonValidos(PersonaDomain persona){
        return !persona.getPrimerNombre().equals(UtilText.getDefaultTextValue()) && !persona.getPrimerApellido().equals(UtilText.getDefaultTextValue());
    }
}
