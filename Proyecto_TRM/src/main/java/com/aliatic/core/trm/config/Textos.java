package com.aliatic.core.trm.config;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder(toBuilder = true)
public class Textos {

    @Getter
    public enum Es {
        VERSION("Versión "),
        SE_HA_PRESENTADO_UN_ERROR("Se ha presentado un error "),
        CREDENCIALES_INVALIDAS("Credenciales inválidas"),
        SE_HA_PRODUCIDO_UN_ERROR_INTERNO_INICIAR_SESION("Se ha producido un error interno al iniciar sesión"),
        ERROR_CONSTRUYENDO_EN_JSON_DEL_LOGGER("Error construyendo el JSON del logger"),
        INICIO_TRANSFORMACION_COLA_RABBIT("Inicio transformación de información desde cola rabbit"),
        HUBO_UN_ERROR_AL_TRANSORMAR_ALGUNA_FECHA("Hubo un error al transformar alguna fecha"),
        NO_SE_ENCONTRO_PROCEDENCIA("No se encontró moneda de procedencia"),
        NO_SE_ENCONTRO_DESTINO("No se encontró moneda de destino"),
        NO_SE_ENCONTRO_METODO_EXTRACCION("No se encontró Método de extracción"),
        NO_SE_ENCONTRARON_TIPOS_TASAS("No se encontraron Tipos de tasas"),
        NO_SE_ENCONTRO_FUENTE("No se encontró fuente"),
        FIN_TRANSFORMACION_COLA_RABBIT("Fin transformación de información desde cola rabbit"),
        ERROR_AL_TRANSOFRMAR_INFO_COLA_RABBIT("Hubo un error al transformar información desde cola rabbit"),
        INICIO_ALMACENAMIENTO_DESDE_COLA_RABBIT("Inicio almacenamiento información desde cola rabbit"),
        FIN_ALMACENAMIENTO_COLA_RABBIT("Fin almacenamiento información desde cola rabbit"),
        HUBO_ERROR_ALMACENAR_COLA_RABBIT("Hubo un error al almacenar información desde cola rabbit"),
        SE_HA_PRODUCIDO_UN_ERROR_INTERNO_LISTARAMS("Se ha producido un error interno al listar los parámetros"),
        SE_HA_PRODUCIDO_UN_ERROR_INTERNO_CREATARAMS("Se ha producido un error interno al crear los parámetros"),
        EL_CODIGO_DE_PARAMETRO_NO_VACIO("El código de parámetro no puede estar vacío"),
        SISTEMA_O_COMPONENTE_NO_VACIO("El sistema o componente no puede estar vacío"),
        PARAMETRO_REGISTRADO_PREVIAMENTE("El parámetro que intenta registrar ya se encuentra en la base de datos"),
        AUTORIZACION_CONCECIDA("Autorización concedida");

        private final String val;

        Es(String val) {
            this.val = val;
        }
    }

}
