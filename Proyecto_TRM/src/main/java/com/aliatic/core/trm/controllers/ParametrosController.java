package com.aliatic.core.trm.controllers;

import com.aliatic.core.trm.config.AliaticLogger;
import com.aliatic.core.trm.domain.dto.ParametrosRequestDTO;
import com.aliatic.core.trm.domain.dto.StandardResponseDTO;
import com.aliatic.core.trm.persistence.entities.ParametrosEntity;
import com.aliatic.core.trm.services.ParametrosService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

import static com.aliatic.core.trm.config.Textos.Es.*;
import static com.aliatic.core.trm.config.Constants.*;

@RestController
@RequestMapping(path = "/parametros", produces = MediaType.APPLICATION_JSON_VALUE)
public class ParametrosController {

    private String nombreClase = null;
    private ParametrosService parametrosService;

    public ParametrosController(ParametrosService parametrosService){
        this.nombreClase = this.getClass().getName();
        this.parametrosService = parametrosService;
    }

    @GetMapping
    public ResponseEntity<StandardResponseDTO> getParametros(
            @RequestParam(name = "q", required = false, defaultValue = VACIO) String q,
            @RequestParam(name = "pagina", required = false, defaultValue = PAGINA_CERO) Integer numeroPagina){

        StandardResponseDTO respuestaEstandar = new StandardResponseDTO();
        String nombreMetodo = Thread.currentThread().getStackTrace()[1].getMethodName();
        respuestaEstandar.setFechaHora(new Date());

        try {
            Page<ParametrosEntity> parametros = parametrosService.findByParametroContaining(q, numeroPagina);

            if (parametros.getTotalElements() < 1){
                respuestaEstandar.setCodigoRespuestaInterno(HttpStatus.NO_CONTENT.value());
                respuestaEstandar.setMensaje(HttpStatus.NO_CONTENT.getReasonPhrase());
                return ResponseEntity.status(HttpStatus.OK).body(respuestaEstandar);
            }

            respuestaEstandar.setCodigoRespuestaInterno(HttpStatus.OK.value());
            respuestaEstandar.setMensaje(HttpStatus.OK.getReasonPhrase());
            respuestaEstandar.setPayload(parametros);

        }catch (Exception ex){
            respuestaEstandar.setCodigoRespuestaInterno(HttpStatus.INTERNAL_SERVER_ERROR.value());
            respuestaEstandar.setMensaje(SE_HA_PRODUCIDO_UN_ERROR_INTERNO_LISTARAMS.getVal());
            AliaticLogger.error(SE_HA_PRODUCIDO_UN_ERROR_INTERNO_LISTARAMS.getVal(), nombreClase,
                    nombreMetodo, ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuestaEstandar);
        }

        return ResponseEntity.ok(respuestaEstandar);
    }

    @PostMapping
    public ResponseEntity<StandardResponseDTO> setParametros(@RequestBody ParametrosRequestDTO parametros){

        StandardResponseDTO respuestaEstandar;
        String nombreMetodo = Thread.currentThread().getStackTrace()[1].getMethodName();

        try {
            ParametrosEntity parametrosEntity = parametrosService.convertirReqADTO(parametros);
            respuestaEstandar = parametrosService.almacenarParametro(parametrosEntity);

        }catch (Exception ex){
            respuestaEstandar = new StandardResponseDTO();
            respuestaEstandar.setCodigoRespuestaInterno(HttpStatus.INTERNAL_SERVER_ERROR.value());
            respuestaEstandar.setMensaje(SE_HA_PRODUCIDO_UN_ERROR_INTERNO_CREATARAMS.getVal());
            AliaticLogger.error(SE_HA_PRODUCIDO_UN_ERROR_INTERNO_CREATARAMS.getVal(), nombreClase,
                    nombreMetodo, ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuestaEstandar);
        }

        return ResponseEntity.ok(respuestaEstandar);
    }

}
