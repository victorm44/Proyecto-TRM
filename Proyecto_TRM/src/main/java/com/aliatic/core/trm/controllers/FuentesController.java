package com.aliatic.core.trm.controllers;

import com.aliatic.core.trm.domain.dto.FuentesRequestDTO;
import com.aliatic.core.trm.domain.dto.StandardResponseDTO;
import com.aliatic.core.trm.persistence.entities.FuentesEntity;
import com.aliatic.core.trm.services.FuentesService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping(path = "/fuentes", produces = MediaType.APPLICATION_JSON_VALUE)
public class FuentesController {

    private String nombreClase = null;
    private FuentesService fuentesService;

    public FuentesController(FuentesService fuentesService) {
        this.nombreClase = this.getClass().getName();
        this.fuentesService = fuentesService;
    }

    @CrossOrigin
    @GetMapping
    public ResponseEntity<StandardResponseDTO> getFuentes(
            @RequestParam(name = "q", required = false, defaultValue = "") String q,
            @RequestParam(name = "pagina", required = false, defaultValue = "0") Integer numeroPagina) {

        StandardResponseDTO respuestaEstandar = new StandardResponseDTO();
        respuestaEstandar.setFechaHora(new Date());

        try {
            Page<FuentesEntity> fuentes = fuentesService.findByCodigoFuenteContaining(q, numeroPagina);

            if (fuentes.getTotalElements() < 1) {
                respuestaEstandar.setCodigoRespuestaInterno(HttpStatus.NO_CONTENT.value());
                respuestaEstandar.setMensaje(HttpStatus.NO_CONTENT.getReasonPhrase());
                return ResponseEntity.status(HttpStatus.OK).body(respuestaEstandar);
            }

            respuestaEstandar.setCodigoRespuestaInterno(HttpStatus.OK.value());
            respuestaEstandar.setMensaje(HttpStatus.OK.getReasonPhrase());
            respuestaEstandar.setPayload(fuentes);

        } catch (Exception ex) {
            respuestaEstandar.setCodigoRespuestaInterno(HttpStatus.INTERNAL_SERVER_ERROR.value());
            respuestaEstandar.setMensaje(ex.getLocalizedMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuestaEstandar);
        }
        return ResponseEntity.status(HttpStatus.OK).body(respuestaEstandar);
    }

    @PostMapping
    public ResponseEntity<StandardResponseDTO> saveFuente(@RequestBody FuentesRequestDTO fuenteDTO) {

        StandardResponseDTO respuestaEstandar = new StandardResponseDTO();
        respuestaEstandar.setFechaHora(new Date());

        try {
            FuentesEntity fuente = fuentesService.convertirReqADTO(fuenteDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(fuentesService.almacenarFuente(fuente));
        } catch (Exception ex) {
            respuestaEstandar.setCodigoRespuestaInterno(HttpStatus.INTERNAL_SERVER_ERROR.value());
            respuestaEstandar.setMensaje(ex.getLocalizedMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuestaEstandar);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<StandardResponseDTO> updateFuente(
            @PathVariable("id") Long idFuente,
            @RequestBody FuentesRequestDTO fuenteDTO) {

        StandardResponseDTO respuestaEstandar = new StandardResponseDTO();
        respuestaEstandar.setFechaHora(new Date());

        try {
            FuentesEntity fuente = fuentesService.convertirReqADTO(fuenteDTO);
            return ResponseEntity.status(HttpStatus.OK).body(fuentesService.updateFuente(idFuente, fuente));
        } catch (EntityNotFoundException enfe) {
            respuestaEstandar.setCodigoRespuestaInterno(HttpStatus.NOT_FOUND.value());
            respuestaEstandar.setMensaje(enfe.getLocalizedMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuestaEstandar);
        } catch (Exception ex) {
            respuestaEstandar.setCodigoRespuestaInterno(HttpStatus.INTERNAL_SERVER_ERROR.value());
            respuestaEstandar.setMensaje(ex.getLocalizedMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuestaEstandar);
        }
    }
}
