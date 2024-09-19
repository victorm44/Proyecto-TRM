package com.aliatic.core.trm.controllers;

import com.aliatic.core.trm.domain.dto.StandardResponseDTO;
import com.aliatic.core.trm.domain.dto.MonedasRequestDTO;
import com.aliatic.core.trm.persistence.entities.MonedasEntity;
import com.aliatic.core.trm.services.MonedasService;
import org.springframework.beans.factory.annotation.Autowired;
import static com.aliatic.core.trm.config.Textos.Es.*;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/monedas")
public class MonedasController {

    @Autowired
    private MonedasService monedasService;

    @CrossOrigin
    @GetMapping
    public ResponseEntity<StandardResponseDTO> getAllMonedas(
            @RequestParam(name = "pagina", required = false, defaultValue = "0") Integer numeroPagina) {
        StandardResponseDTO respuestaEstandar = new StandardResponseDTO();
        respuestaEstandar.setFechaHora(new Date());

        try {
            Page<MonedasEntity> monedas = monedasService.findAllActivas(numeroPagina);

            if (monedas.getTotalElements() < 1) {
                respuestaEstandar.setCodigoRespuestaInterno(HttpStatus.NO_CONTENT.value());
                respuestaEstandar.setMensaje(HttpStatus.NO_CONTENT.getReasonPhrase());
                return ResponseEntity.status(HttpStatus.OK).body(respuestaEstandar);
            }

            respuestaEstandar.setCodigoRespuestaInterno(HttpStatus.OK.value());
            respuestaEstandar.setMensaje(HttpStatus.OK.getReasonPhrase());
            respuestaEstandar.setPayload(monedas);

        } catch (Exception ex) {
            respuestaEstandar.setCodigoRespuestaInterno(HttpStatus.INTERNAL_SERVER_ERROR.value());
            respuestaEstandar.setMensaje(ERROR_INTERNO_LISTAR_MONEDAS.getVal());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuestaEstandar);
        }

        return ResponseEntity.ok(respuestaEstandar);
    }

    @CrossOrigin
    @GetMapping("/{idMoneda}")
    public ResponseEntity<StandardResponseDTO> getMonedaById(@PathVariable Long idMoneda) {
        StandardResponseDTO respuestaEstandar = new StandardResponseDTO();
        respuestaEstandar.setFechaHora(new Date());
        try {
            Optional<MonedasEntity> moneda = monedasService.findById(idMoneda);
            if (moneda.isEmpty()) {
                respuestaEstandar.setCodigoRespuestaInterno(HttpStatus.NOT_FOUND.value());
                respuestaEstandar.setMensaje(NO_SE_ENCONTRO_LA_MONEDA.getVal() + idMoneda);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuestaEstandar);
            }
            respuestaEstandar.setCodigoRespuestaInterno(HttpStatus.OK.value());
            respuestaEstandar.setMensaje(HttpStatus.OK.getReasonPhrase());
            respuestaEstandar.setPayload(moneda.get());
            return ResponseEntity.ok(respuestaEstandar);
        } catch (Exception ex) {
            respuestaEstandar.setCodigoRespuestaInterno(HttpStatus.INTERNAL_SERVER_ERROR.value());
            respuestaEstandar.setMensaje(ERROR_INTERNO_OBTENER_MONEDA.getVal() + idMoneda);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuestaEstandar);
        }
    }

    @CrossOrigin
    @PostMapping
    public ResponseEntity<StandardResponseDTO> createMoneda(@RequestBody MonedasRequestDTO monedaRequest) {
        StandardResponseDTO respuestaEstandar;
        try {
            MonedasEntity moneda = monedasService.convertirReqADTO(monedaRequest);
            respuestaEstandar = monedasService.almacenarMoneda(moneda);
        } catch (Exception ex) {
            respuestaEstandar = new StandardResponseDTO();
            respuestaEstandar.setCodigoRespuestaInterno(HttpStatus.INTERNAL_SERVER_ERROR.value());
            respuestaEstandar.setMensaje(ERROR_INTERNO_CREAR_MONEDA.getVal());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuestaEstandar);
        }
        return ResponseEntity.ok(respuestaEstandar);
    }

    @CrossOrigin
    @PutMapping("/{idMoneda}")
    public ResponseEntity<StandardResponseDTO> updateMoneda(@PathVariable Long idMoneda, @RequestBody MonedasRequestDTO monedaRequest) {
        StandardResponseDTO respuestaEstandar;
        try {
            MonedasEntity moneda = monedasService.convertirReqADTO(monedaRequest);
            respuestaEstandar = monedasService.updateMoneda(idMoneda, moneda);
        } catch (Exception ex) {
            respuestaEstandar = new StandardResponseDTO();
            respuestaEstandar.setCodigoRespuestaInterno(HttpStatus.INTERNAL_SERVER_ERROR.value());
            respuestaEstandar.setMensaje(ERROR_INTERNO_ACTUALIZAR_MONEDA.getVal());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuestaEstandar);
        }
        return ResponseEntity.ok(respuestaEstandar);
    }
}
