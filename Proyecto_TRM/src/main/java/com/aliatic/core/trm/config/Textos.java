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
        NO_SE_ENCONTRO_PROVEEDOR("No se encontró proveedor"),
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
        PARAMETRO_NO_ENCONTRADO("Parametro no encontrado"),
        PARAMETRO_ACTUALIZADO("Parametro actualizado con éxito"),
        AUTORIZACION_CONCECIDA("Autorización concedida"),
        TIPO_CAMBIO_ACTUALIZADO("Tipo de cambio actualizado con Éxito"),
        TIPO_CAMBIO_NO_ENCONTRADO("Tipo de cambio no encontrado"),
        EL_CODIGO_DE_TIPO_DE_CAMBIO_NO_VACIO("El código de tipo de cambio no puede estar vacío"),
        IMPORTE_NO_VACIO("El importe no puede estar vacío"),
        ERROR_INTERNO_LISTAR_TIPOS_TASAS("Se ha producido un error interno al listar los tipos de tasas"),
        NO_SE_ENCONTRO_EL_METODO_EXTRACCION("No se encontró el método de extracción con ID: "),
        ERROR_INTERNO_OBTENER_METODO_EXTRACCION("Se ha producido un error interno al obtener el método de extracción con ID: "),
        ERROR_INTERNO_LISTAR_METODOS_EXTRACCION("Se ha producido un error interno al listar los métodos de extracción"),
        ERROR_INTERNO_CREAR_PROVEEDOR("Se ha producido un error interno al crear proveedor"),
        ERROR_INTERNO_LISTAR_PROVEEDORES("Se ha producido un error interno al listar proveedores"),
        ERROR_INTERNO_ACTUALIZAR_METODO_EXTRACCION("Se ha producido un error interno al actualizar " +
                "el método de extracción con ID: "),
        METODO_EXTRACCION_NO_ENCONTRADO("Metodo de extracción no encontrado"),
        METODO_EXTRACCION_ACTUALIZADO_CORRECTAMENTE("Metodo de extracción actualizado correctamente"),
        NO_SE_ENCONTRO_LA_MONEDA("No se encontró la moneda con ID: "),
        ERROR_INTERNO_OBTENER_MONEDA("Se ha producido un error interno al obtener la moneda con ID: "),
        ERROR_INTERNO_LISTAR_MONEDAS("Se ha producido un error interno al listar monedas"),
        ERROR_INTERNO_CREAR_MONEDA("Se ha producido un error interno al crear la moneda"),
        ERROR_INTERNO_ACTUALIZAR_MONEDA("Se ha producido un error interno al actualizar la moneda"),
        MONEDA_ACTUALIZADA_CORRECTAMENTE("Moneda actualizada correctamente"),
        MONEDA_NO_ENCONTRADA("Moneda no encontrada"),
        CODIGO_MONEDA_VACIO("El código de la moneda no puede ser vacío"),
        CODIGO_ISO_VACIO("El código ISO de la moneda no puede ser vacío"),
        PROVEEDOR_ACTUALIZADO_CORRECTAMENTE("Proveedor actualizado correctamente"),
        PROVEEDOR_NO_ENCONTRADO("Proveedor no encontrado"),
        CODIGO_PROVEEDOR_VACIO("El código del proveedor no puede ser vacío"),
        DENOMINACION_PROVEEDOR_VACIA("La denominación del proveedor no puede ser vacía"),
        TIPO_TASA_ACTUALIZADO_CORRECTAMENTE("Tipo de Tasa actualizado correctamente"),
        TIPO_TASA_NO_ENCONTRADO("Tipo de Tasa no encontrado"),
        CODIGO_TIPO_TASA_VACIO("El código del tipo de tasa no puede ser vacío"),
        DENOMINACION_TIPO_TASA_VACIA("La denominación del tipo de tasa no puede ser vacía"),
        ERROR_INTERNO_OBTENER_DTF("Se ha producido un error interno al obtener el DTF"),
        ERROR_INTERNO_CREAR_DTF("Se ha producido un error interno al crear el DTF"),
        ERROR_INTERNO_ACTUALIZAR_DTF("Se ha producido un error interno al actualizar el DTF"),
        ;






        private final String val;

        Es(String val) {
            this.val = val;
        }
    }

}
