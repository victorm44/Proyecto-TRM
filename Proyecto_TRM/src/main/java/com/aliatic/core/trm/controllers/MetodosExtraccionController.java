package com.aliatic.core.trm.controllers;

import com.aliatic.core.trm.domain.dto.MetodosExtraccionRequestDTO;
import com.aliatic.core.trm.domain.dto.StandardResponseDTO;
import com.aliatic.core.trm.persistence.entities.MetodosExtracionEntity;
import com.aliatic.core.trm.services.MetodosExtraccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static com.aliatic.core.trm.config.Textos.Es.*;

import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/metodosExtraccion")
public class MetodosExtraccionController {

    @Autowired
    private MetodosExtraccionService metodosExtraccionService;

    @CrossOrigin
    @PostMapping
    public ResponseEntity<StandardResponseDTO> createMetodoExtraccion(@RequestBody MetodosExtraccionRequestDTO requestDTO) {
        StandardResponseDTO respuestaEstandar = new StandardResponseDTO();
        respuestaEstandar.setFechaHora(new Date());

        try {
            MetodosExtracionEntity entity = metodosExtraccionService.convertirReqADTO(requestDTO);
            MetodosExtracionEntity createdEntity = metodosExtraccionService.save(entity);
            respuestaEstandar.setCodigoRespuestaInterno(HttpStatus.OK.value());
            respuestaEstandar.setMensaje(HttpStatus.OK.getReasonPhrase());
            respuestaEstandar.setPayload(createdEntity);
            return ResponseEntity.ok(respuestaEstandar);
        } catch (Exception ex) {
            respuestaEstandar.setCodigoRespuestaInterno(HttpStatus.INTERNAL_SERVER_ERROR.value());
            respuestaEstandar.setMensaje(NO_SE_ENCONTRO_EL_METODO_EXTRACCION.getVal());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuestaEstandar);
        }
    }

    @CrossOrigin
    @GetMapping("/{id}")
    public ResponseEntity<StandardResponseDTO> getMetodoExtraccionById(@PathVariable Long id) {
        StandardResponseDTO respuestaEstandar = new StandardResponseDTO();
        respuestaEstandar.setFechaHora(new Date());

        try {
            Optional<MetodosExtracionEntity> optionalEntity = metodosExtraccionService.findById(id);
            if (optionalEntity.isEmpty()) {
                respuestaEstandar.setCodigoRespuestaInterno(HttpStatus.NOT_FOUND.value());
                respuestaEstandar.setMensaje(NO_SE_ENCONTRO_EL_METODO_EXTRACCION.getVal() + id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuestaEstandar);
            }
            respuestaEstandar.setCodigoRespuestaInterno(HttpStatus.OK.value());
            respuestaEstandar.setMensaje(HttpStatus.OK.getReasonPhrase());
            respuestaEstandar.setPayload(optionalEntity.get());
            return ResponseEntity.ok(respuestaEstandar);
        } catch (Exception ex) {
            respuestaEstandar.setCodigoRespuestaInterno(HttpStatus.INTERNAL_SERVER_ERROR.value());
            respuestaEstandar.setMensaje(ERROR_INTERNO_OBTENER_METODO_EXTRACCION.getVal() + id);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuestaEstandar);
        }
    }

    @CrossOrigin
    @PutMapping("/{idMetodosExtraccion}")
    public ResponseEntity<StandardResponseDTO> updateMetodoExtraccion(@PathVariable Long idMetodosExtraccion,
                                                                      @RequestBody MetodosExtraccionRequestDTO requestDTO) {
        StandardResponseDTO respuestaEstandar = new StandardResponseDTO();
        respuestaEstandar.setFechaHora(new Date());

        try {
            StandardResponseDTO response = metodosExtraccionService.updateMetodosExtraccion(idMetodosExtraccion, requestDTO);
            return ResponseEntity.status(response.getCodigoRespuestaInterno()).body(response);
        } catch (Exception ex) {
            respuestaEstandar.setCodigoRespuestaInterno(HttpStatus.INTERNAL_SERVER_ERROR.value());
            respuestaEstandar.setMensaje(ERROR_INTERNO_ACTUALIZAR_METODO_EXTRACCION.getVal() + idMetodosExtraccion);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuestaEstandar);
        }
    }

    @CrossOrigin
    @GetMapping
    public ResponseEntity<StandardResponseDTO> getAllMetodosExtraccion(Pageable pageable) {
        StandardResponseDTO respuestaEstandar = new StandardResponseDTO();
        respuestaEstandar.setFechaHora(new Date());

        try {
            Page<MetodosExtracionEntity> page = metodosExtraccionService.findAll(pageable);
            if (page.getTotalElements() < 1) {
                respuestaEstandar.setCodigoRespuestaInterno(HttpStatus.NO_CONTENT.value());
                respuestaEstandar.setMensaje(HttpStatus.NO_CONTENT.getReasonPhrase());
                return ResponseEntity.status(HttpStatus.OK).body(respuestaEstandar);
            }
            respuestaEstandar.setCodigoRespuestaInterno(HttpStatus.OK.value());
            respuestaEstandar.setMensaje(HttpStatus.OK.getReasonPhrase());
            respuestaEstandar.setPayload(page);
            return ResponseEntity.ok(respuestaEstandar);
        } catch (Exception ex) {
            respuestaEstandar.setCodigoRespuestaInterno(HttpStatus.INTERNAL_SERVER_ERROR.value());
            respuestaEstandar.setMensaje(ERROR_INTERNO_LISTAR_METODOS_EXTRACCION.getVal());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuestaEstandar);
        }
    }
}
