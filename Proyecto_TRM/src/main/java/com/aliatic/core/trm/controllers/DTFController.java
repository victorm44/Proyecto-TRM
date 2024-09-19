package com.aliatic.core.trm.controllers;

import com.aliatic.core.trm.config.AliaticLogger;
import com.aliatic.core.trm.domain.dto.DTFRequestDTO;
import com.aliatic.core.trm.domain.dto.StandardResponseDTO;
import com.aliatic.core.trm.persistence.entities.DTFEntity;
import com.aliatic.core.trm.services.DTFService;
import jakarta.persistence.EntityNotFoundException;
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
@RequestMapping(path = "/dtf", produces = MediaType.APPLICATION_JSON_VALUE)
public class DTFController {

    private String nombreClase = null;
    private final DTFService dtfService;

    public DTFController(DTFService dtfService) {
        this.nombreClase = this.getClass().getName();
        this.dtfService = dtfService;
    }

    @CrossOrigin
    @GetMapping
    public ResponseEntity<StandardResponseDTO> getDTFs(
            @RequestParam(name = "q", required = false, defaultValue = "") String q,
            @RequestParam(name = "pagina", required = false, defaultValue = "0") Integer numeroPagina) {

        StandardResponseDTO respuestaEstandar = new StandardResponseDTO();
        respuestaEstandar.setFechaHora(new Date());

        try {
            Page<DTFEntity> dtfEntities = dtfService.findByTasaContaining(q, numeroPagina);

            if (dtfEntities.getTotalElements() < 1) {
                respuestaEstandar.setCodigoRespuestaInterno(HttpStatus.NO_CONTENT.value());
                respuestaEstandar.setMensaje(HttpStatus.NO_CONTENT.getReasonPhrase());
                return ResponseEntity.status(HttpStatus.OK).body(respuestaEstandar);
            }

            respuestaEstandar.setCodigoRespuestaInterno(HttpStatus.OK.value());
            respuestaEstandar.setMensaje(HttpStatus.OK.getReasonPhrase());
            respuestaEstandar.setPayload(dtfEntities);
            return ResponseEntity.status(HttpStatus.OK).body(respuestaEstandar);

        } catch (Exception ex) {
            respuestaEstandar.setCodigoRespuestaInterno(HttpStatus.INTERNAL_SERVER_ERROR.value());
            respuestaEstandar.setMensaje(ERROR_INTERNO_OBTENER_DTF.getVal());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuestaEstandar);
        }
    }

    @CrossOrigin
    @PostMapping
    public ResponseEntity<StandardResponseDTO> createDTF(@RequestBody DTFRequestDTO requestDTO) {
        StandardResponseDTO respuestaEstandar = new StandardResponseDTO();
        respuestaEstandar.setFechaHora(new Date());

        try {
            DTFEntity dtfEntity = dtfService.convertirReqADTO(requestDTO);
            StandardResponseDTO respuestaDTF = dtfService.almacenarDTF(dtfEntity);
            return ResponseEntity.status(respuestaDTF.getCodigoRespuestaInterno()).body(respuestaDTF);

        } catch (Exception ex) {
            respuestaEstandar.setCodigoRespuestaInterno(HttpStatus.INTERNAL_SERVER_ERROR.value());
            respuestaEstandar.setMensaje(ERROR_INTERNO_CREAR_DTF.getVal());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuestaEstandar);
        }
    }

    @CrossOrigin
    @PutMapping(path = "/{id}")
    public ResponseEntity<StandardResponseDTO> updateDTF(@PathVariable("id") Long id, @RequestBody DTFRequestDTO requestDTO) {
        StandardResponseDTO respuestaEstandar = new StandardResponseDTO();
        respuestaEstandar.setFechaHora(new Date());

        try {
            DTFEntity dtfEntity = dtfService.convertirReqADTO(requestDTO);
            StandardResponseDTO respuestaDTF = dtfService.updateDTF(id, dtfEntity);
            return ResponseEntity.status(respuestaDTF.getCodigoRespuestaInterno()).body(respuestaDTF);

        } catch (Exception ex) {
            respuestaEstandar.setCodigoRespuestaInterno(HttpStatus.INTERNAL_SERVER_ERROR.value());
            respuestaEstandar.setMensaje(ERROR_INTERNO_ACTUALIZAR_DTF.getVal());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuestaEstandar);
        }
    }
}
