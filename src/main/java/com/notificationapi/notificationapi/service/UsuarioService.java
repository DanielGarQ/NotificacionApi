package com.notificationapi.notificationapi.service;

import com.notificationapi.notificationapi.crossCutting.Messages.UtilMessagesService;
import com.notificationapi.notificationapi.crossCutting.exception.NotificationException;
import com.notificationapi.notificationapi.crossCutting.utils.UtilEmail;
import com.notificationapi.notificationapi.crossCutting.utils.UtilText;
import com.notificationapi.notificationapi.domain.UsuarioDomain;
import com.notificationapi.notificationapi.entity.UsuarioEntity;
import com.notificationapi.notificationapi.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UsuarioService {

    public static final Logger logger = LoggerFactory.getLogger(UsuarioService.class);

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private PersonaService personaService;


    public List<UsuarioDomain> findAll(){
        return usuarioRepository.findAll().stream().map(new UsuarioService()::toDomain).toList();
    }
    public UsuarioDomain toDomain(UsuarioEntity entity){
        return new UsuarioDomain(entity.getIdentificador(),entity.getUsername(),entity.getPassword());
    }
    public UsuarioEntity toEntity(UsuarioDomain domain) {
        return new UsuarioEntity(domain.getIdentificador(), domain.getCorreoElectronico(), domain.getContrasena(), domain.getRol());
    }


    public UsuarioDomain consult(String correoElectronico){
        return toDomain(usuarioRepository.findByCorreo(correoElectronico));
    }

    public void save(UsuarioDomain usuario) throws NotificationException {
        if(!datosSonValidos(usuario)){
            throw new NotificationException();
        }
        try{
            usuarioRepository.save(toEntity(usuario));
        }catch (Exception exception){
            logger.error(UtilMessagesService.UsuarioService.ERROR_GUARDAR, exception.getMessage(), exception);
        }

    }

    public void delete(String correo) throws NotificationException {
        if(correo.equals(UtilEmail.getDefaultValueMail())){
            throw new NotificationException();
        }
        try{
            usuarioRepository.deleteById(usuarioRepository.findByCorreo(correo).getIdentificador());
            personaService.delete(correo);
        }catch (Exception exception){
            logger.error(UtilMessagesService.UsuarioService.ERROR_ELIMINAR, exception.getMessage(), exception);
        }
    }
    public void update(String correoElectronico, String contrasena) throws NotificationException {
        if(contrasena.equals(UtilText.getDefaultTextValue())){
            throw new NotificationException();
        }
        try {
            String encriptadaContrasena = passwordEncoder.encode(contrasena);
            usuarioRepository.updateByCorreoElectronico(encriptadaContrasena, correoElectronico);
        }catch (Exception exception){
            logger.error(UtilMessagesService.UsuarioService.ERROR_ACTUALIZAR, exception.getMessage(), exception);
        }
    }


    private boolean datosSonValidos(UsuarioDomain usuario){
        if(usuario.getCorreoElectronico().equals(UtilEmail.getDefaultValueMail()) || usuario.getCorreoElectronico().equals(UtilEmail.getDefaultValueMail())){
            return false;
        }
        return !usuario.getContrasena().equals(UtilText.getDefaultTextValue());
    }
}
