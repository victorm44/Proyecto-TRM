package com.aliatic.core.trm.controllers;

import com.aliatic.core.trm.config.AliaticLogger;
import com.aliatic.core.trm.domain.dto.StandardResponseDTO;
import com.aliatic.core.trm.domain.dto.TiposCambioRequestDTO;
import com.aliatic.core.trm.persistence.entities.TiposCambioEntity;
import com.aliatic.core.trm.services.TiposCambioService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

import static com.aliatic.core.trm.config.Textos.Es.*;
import static com.aliatic.core.trm.config.Constants.*;

@RestController
@RequestMapping(path = "/tiposcambio", produces = MediaType.APPLICATION_JSON_VALUE)
public class TiposCambioController {

    private String nombreClase;
    private TiposCambioService tiposCambioService;

    public TiposCambioController(TiposCambioService tiposCambioService){
        this.nombreClase = this.getClass().getName();
        this.tiposCambioService = tiposCambioService;
    }

    @CrossOrigin
    @GetMapping
    public ResponseEntity<StandardResponseDTO> getAllTiposCambio(
            @RequestParam(name = "pagina", required = false, defaultValue = "0") int numeroPagina) {
        StandardResponseDTO respuestaEstandar = new StandardResponseDTO();
        respuestaEstandar.setFechaHora(new Date());

        try {
            Page<TiposCambioEntity> tiposCambio = tiposCambioService.findAll(numeroPagina);
            if (tiposCambio.isEmpty()) {
                respuestaEstandar.setCodigoRespuestaInterno(HttpStatus.NO_CONTENT.value());
                respuestaEstandar.setMensaje(HttpStatus.NO_CONTENT.getReasonPhrase());
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(respuestaEstandar);
            }
            respuestaEstandar.setCodigoRespuestaInterno(HttpStatus.OK.value());
            respuestaEstandar.setMensaje(HttpStatus.OK.getReasonPhrase());
            respuestaEstandar.setPayload(tiposCambio);
            return ResponseEntity.ok(respuestaEstandar);
        } catch (Exception ex) {
            respuestaEstandar.setCodigoRespuestaInterno(HttpStatus.INTERNAL_SERVER_ERROR.value());
            respuestaEstandar.setMensaje(SE_HA_PRODUCIDO_UN_ERROR_INTERNO_LISTARAMS.getVal());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuestaEstandar);
        }
    }

    @CrossOrigin
    @GetMapping("/{id}")
    public ResponseEntity<StandardResponseDTO> getTipoCambioById(@PathVariable Long id) {
        StandardResponseDTO respuestaEstandar = new StandardResponseDTO();
        respuestaEstandar.setFechaHora(new Date());
        try {
            Optional<TiposCambioEntity> tipoCambio = tiposCambioService.findById(id);
            if (tipoCambio.isEmpty()) {
                respuestaEstandar.setCodigoRespuestaInterno(HttpStatus.NOT_FOUND.value());
                respuestaEstandar.setMensaje(HttpStatus.NO_CONTENT.getReasonPhrase());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuestaEstandar);
            }
            respuestaEstandar.setCodigoRespuestaInterno(HttpStatus.OK.value());
            respuestaEstandar.setMensaje(HttpStatus.OK.getReasonPhrase());
            respuestaEstandar.setPayload(tipoCambio.get());
            return ResponseEntity.ok(respuestaEstandar);
        } catch (Exception ex) {
            respuestaEstandar.setCodigoRespuestaInterno(HttpStatus.INTERNAL_SERVER_ERROR.value());
            respuestaEstandar.setMensaje(SE_HA_PRODUCIDO_UN_ERROR_INTERNO_LISTARAMS.getVal());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuestaEstandar);
        }
    }

    @CrossOrigin
    @GetMapping("/fuente/{fkfuente}")
    public ResponseEntity<StandardResponseDTO> getTiposCambioByFkFuente(
            @PathVariable Long fkfuente,
            @RequestParam(name = "pagina", required = false, defaultValue = PAGINA_CERO) Integer numeroPagina) {

        StandardResponseDTO respuestaEstandar = new StandardResponseDTO();
        respuestaEstandar.setFechaHora(new Date());
        String nombreMetodo = Thread.currentThread().getStackTrace()[1].getMethodName();

        try {
            Page<TiposCambioEntity> tiposCambio = tiposCambioService.findByFkFuente(fkfuente, numeroPagina);

            if (tiposCambio.getTotalElements() < 1){
                respuestaEstandar.setCodigoRespuestaInterno(HttpStatus.NO_CONTENT.value());
                respuestaEstandar.setMensaje(HttpStatus.NO_CONTENT.getReasonPhrase());
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(respuestaEstandar);
            }

            respuestaEstandar.setCodigoRespuestaInterno(HttpStatus.OK.value());
            respuestaEstandar.setMensaje(HttpStatus.OK.getReasonPhrase());
            respuestaEstandar.setPayload(tiposCambio);

        }catch (Exception ex){
            respuestaEstandar.setCodigoRespuestaInterno(HttpStatus.INTERNAL_SERVER_ERROR.value());
            respuestaEstandar.setMensaje(SE_HA_PRODUCIDO_UN_ERROR_INTERNO_LISTARAMS.getVal());
            AliaticLogger.error(SE_HA_PRODUCIDO_UN_ERROR_INTERNO_LISTARAMS.getVal(), nombreClase, nombreMetodo, ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuestaEstandar);
        }

        return ResponseEntity.ok(respuestaEstandar);
    }

    @CrossOrigin
    @PostMapping
    public ResponseEntity<StandardResponseDTO> setTiposCambio(@RequestBody TiposCambioRequestDTO tiposCambio){

        StandardResponseDTO respuestaEstandar;
        String nombreMetodo = Thread.currentThread().getStackTrace()[1].getMethodName();

        try {
            TiposCambioEntity tiposCambioEntity = tiposCambioService.convertirReqADTO(tiposCambio);
            respuestaEstandar = tiposCambioService.almacenarTipoCambio(tiposCambioEntity);

        }catch (Exception ex){
            respuestaEstandar = new StandardResponseDTO();
            respuestaEstandar.setCodigoRespuestaInterno(HttpStatus.INTERNAL_SERVER_ERROR.value());
            respuestaEstandar.setMensaje(SE_HA_PRODUCIDO_UN_ERROR_INTERNO_CREATARAMS.getVal());
            AliaticLogger.error(SE_HA_PRODUCIDO_UN_ERROR_INTERNO_CREATARAMS.getVal(), nombreClase, nombreMetodo, ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuestaEstandar);
        }

        return ResponseEntity.ok(respuestaEstandar);
    }

    @CrossOrigin
    @PutMapping("/{id}")
    public ResponseEntity<StandardResponseDTO> updateTipoCambio(
            @PathVariable Long id,
            @RequestBody TiposCambioRequestDTO tiposCambioRequestDTO) {
        
        StandardResponseDTO respuestaEstandar = new StandardResponseDTO();
        respuestaEstandar.setFechaHora(new Date());

        try {
            TiposCambioEntity tiposCambioEntity = tiposCambioService.convertirReqADTO(tiposCambioRequestDTO);
            respuestaEstandar = tiposCambioService.updateTipoCambio(id, tiposCambioEntity);

        } catch (Exception ex) {
            respuestaEstandar.setCodigoRespuestaInterno(HttpStatus.INTERNAL_SERVER_ERROR.value());
            respuestaEstandar.setMensaje(SE_HA_PRODUCIDO_UN_ERROR_INTERNO_LISTARAMS.getVal());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuestaEstandar);
        }

        return ResponseEntity.ok(respuestaEstandar);
    }
}
