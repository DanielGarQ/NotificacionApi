package com.notificationapi.notificationapi.crossCutting.Messages;

import com.notificationapi.notificationapi.MessengerService.buzonNotificacion.ReciverMessageBuzonNotificacion;

public class UtilMessagesService {
    public static final class ReciverMessageBuzonNotificacion{
        public static final String ERROR_DEFAULT = "Se ha presentado el siguiente error: ";
    }

    public static final class ReciverMessageNotificacion{
        public static final String ERROR_DEFAULT = "Se ha presentado el siguiente error: ";
    }

    public static final class BuzonNotificacionService{
        public static final String ERROR_DEFAULT = "Se ha presentado el siguiente error: ";

        public static final String ERROR_ELIMINAR = "Se ha presentado un error al eliminar: ";
    }

    public static final class PersonaService{
        public static final String ERROR_DEFAULT = "Se ha presentado el siguiente error: ";

        public static final String ERROR_ELIMINAR = "Se ha presentado un error al eliminar: ";
        public static final String ERROR_ACTUALIZAR = "Se ha presentado un error al actualizar: ";
        public static final String ERROR_GUARDAR = "Se ha presentado un error al guardar: ";
    }
    public static final class UsuarioService{
        public static final String ERROR_DEFAULT = "Se ha presentado el siguiente error: ";

        public static final String ERROR_ELIMINAR = "Se ha presentado un error al eliminar: ";
        public static final String ERROR_ACTUALIZAR = "Se ha presentado un error al actualizar: ";
        public static final String ERROR_GUARDAR = "Se ha presentado un error al guardar: ";
    }


}
