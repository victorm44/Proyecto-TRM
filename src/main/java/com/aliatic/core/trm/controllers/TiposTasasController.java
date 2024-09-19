package com.aliatic.core.trm.controllers;

import com.aliatic.core.trm.domain.dto.StandardResponseDTO;
import com.aliatic.core.trm.domain.dto.TiposTasasRequestDTO;
import com.aliatic.core.trm.persistence.entities.TiposTasasEntity;
import com.aliatic.core.trm.services.TiposTasasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static com.aliatic.core.trm.config.Textos.Es.*;
import java.util.Optional;
import java.util.Date;

@RestController
@RequestMapping(path = "/tipostasas", produces = MediaType.APPLICATION_JSON_VALUE)
public class TiposTasasController {

    private final TiposTasasService tiposTasasService;

    @Autowired
    public TiposTasasController(TiposTasasService tiposTasasService) {
        this.tiposTasasService = tiposTasasService;
    }
    @CrossOrigin
    @GetMapping
    public ResponseEntity<StandardResponseDTO> getAllTiposTasas(
            @RequestParam(name = "pagina", required = false, defaultValue = "0") int numeroPagina) {
        StandardResponseDTO respuestaEstandar = new StandardResponseDTO();
        respuestaEstandar.setFechaHora(new Date());

        try {
            Page<TiposTasasEntity> tiposTasas = tiposTasasService.findAll(numeroPagina);
            if (tiposTasas.isEmpty()) {
                respuestaEstandar.setCodigoRespuestaInterno(HttpStatus.NO_CONTENT.value());
                respuestaEstandar.setMensaje(HttpStatus.NO_CONTENT.getReasonPhrase());
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(respuestaEstandar);
            }
            respuestaEstandar.setCodigoRespuestaInterno(HttpStatus.OK.value());
            respuestaEstandar.setMensaje(HttpStatus.OK.getReasonPhrase());
            respuestaEstandar.setPayload(tiposTasas);
            return ResponseEntity.ok(respuestaEstandar);
        } catch (Exception ex) {
            respuestaEstandar.setCodigoRespuestaInterno(HttpStatus.INTERNAL_SERVER_ERROR.value());
            respuestaEstandar.setMensaje(ERROR_INTERNO_LISTAR_TIPOS_TASAS.getVal());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuestaEstandar);
        }
    }

    @CrossOrigin
    @GetMapping("/{id}")
    public ResponseEntity<StandardResponseDTO> getTiposTasasById(@PathVariable Long id) {
        StandardResponseDTO respuestaEstandar = new StandardResponseDTO();
        respuestaEstandar.setFechaHora(new Date());
        try {
            Optional<TiposTasasEntity> tiposTasas = tiposTasasService.findById(id);
            if (tiposTasas.isEmpty()) {
                respuestaEstandar.setCodigoRespuestaInterno(HttpStatus.NOT_FOUND.value());
                respuestaEstandar.setMensaje(TIPO_TASA_NO_ENCONTRADO.getVal() + id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuestaEstandar);
            }
            respuestaEstandar.setCodigoRespuestaInterno(HttpStatus.OK.value());
            respuestaEstandar.setMensaje(HttpStatus.OK.getReasonPhrase());
            respuestaEstandar.setPayload(tiposTasas.get());
            return ResponseEntity.ok(respuestaEstandar);
        } catch (Exception ex) {
            respuestaEstandar.setCodigoRespuestaInterno(HttpStatus.INTERNAL_SERVER_ERROR.value());
            respuestaEstandar.setMensaje(ERROR_INTERNO_LISTAR_TIPOS_TASAS.getVal());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuestaEstandar);
        }
    }

    @CrossOrigin
    @PostMapping
    public ResponseEntity<StandardResponseDTO> crearTiposTasas(@RequestBody TiposTasasRequestDTO requestDTO) {
        TiposTasasEntity tiposTasasEntity = tiposTasasService.convertirReqADTO(requestDTO);
        StandardResponseDTO response = tiposTasasService.almacenarTipoTasa(tiposTasasEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @CrossOrigin
    @PutMapping("/{id}")
    public ResponseEntity<StandardResponseDTO> actualizarTiposTasas(@PathVariable Long id, @RequestBody TiposTasasRequestDTO requestDTO) {
        TiposTasasEntity tiposTasasEntity = tiposTasasService.convertirReqADTO(requestDTO);
        StandardResponseDTO response = tiposTasasService.updateTipoTasa(id, tiposTasasEntity);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}