package com.notificationapi.notificationapi.domain;

import com.notificationapi.notificationapi.crossCutting.utils.UtilDefaultObject;
import com.notificationapi.notificationapi.crossCutting.utils.UtilEmail;
import com.notificationapi.notificationapi.crossCutting.utils.UtilText;
import com.notificationapi.notificationapi.crossCutting.utils.UtilUUID;

import java.util.UUID;

public class UsuarioDomain {

    private UUID identificador;
    private String correoElectronico;
    private String contrasena;
    private Rol rol;


    public UsuarioDomain(UUID identificador, String correoElectronico, String contrasena) {
        this.identificador = identificador;
        this.correoElectronico = correoElectronico;
        this.contrasena = contrasena;
    }

    public UsuarioDomain() {
        setIdentificador(UtilUUID.getUuidDefaultValue());
        setCorreoElectronico(UtilEmail.getDefaultValueMail());
        setContrasena(UtilText.getDefaultTextValue());
    }

    public UUID getIdentificador() {
        return identificador;
    }

    public void setIdentificador(UUID identificador) {
        this.identificador = (UUID) UtilDefaultObject.defaultValue(identificador, UtilUUID.getUuidDefaultValue());
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public UsuarioDomain setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = (String) UtilDefaultObject.defaultValue(correoElectronico,UtilEmail.getDefaultValueMail());
        return null;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = (String) UtilDefaultObject.defaultValue(contrasena,UtilText.getDefaultTextValue());
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
}
