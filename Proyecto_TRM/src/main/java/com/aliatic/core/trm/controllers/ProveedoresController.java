package com.aliatic.core.trm.controllers;

import com.aliatic.core.trm.domain.dto.ProveedoresRequestDTO;
import com.aliatic.core.trm.domain.dto.StandardResponseDTO;
import com.aliatic.core.trm.persistence.entities.ProveedoresEntity;
import com.aliatic.core.trm.services.ProveedoresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static com.aliatic.core.trm.config.Textos.Es.*;

import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/proveedores")
public class ProveedoresController {

    @Autowired
    private ProveedoresService proveedoresService;

    @CrossOrigin
    @GetMapping
    public ResponseEntity<StandardResponseDTO> getAllProveedores(
            @RequestParam(name = "pagina", required = false, defaultValue = "0") Integer numeroPagina) {
        StandardResponseDTO respuestaEstandar = new StandardResponseDTO();
        respuestaEstandar.setFechaHora(new Date());

        try {
            Page<ProveedoresEntity> proveedores = proveedoresService.findAllActivas(numeroPagina);

            if (proveedores.getTotalElements() < 1) {
                respuestaEstandar.setCodigoRespuestaInterno(HttpStatus.NO_CONTENT.value());
                respuestaEstandar.setMensaje(HttpStatus.NO_CONTENT.getReasonPhrase());
                return ResponseEntity.status(HttpStatus.OK).body(respuestaEstandar);
            }

            respuestaEstandar.setCodigoRespuestaInterno(HttpStatus.OK.value());
            respuestaEstandar.setMensaje(HttpStatus.OK.getReasonPhrase());
            respuestaEstandar.setPayload(proveedores);

        } catch (Exception ex) {
            respuestaEstandar.setCodigoRespuestaInterno(HttpStatus.INTERNAL_SERVER_ERROR.value());
            respuestaEstandar.setMensaje(ERROR_INTERNO_LISTAR_PROVEEDORES.getVal());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuestaEstandar);
        }

        return ResponseEntity.ok(respuestaEstandar);
    }

    @CrossOrigin
    @GetMapping("/{idProveedores}")
    public ResponseEntity<StandardResponseDTO> getProveedorById(@PathVariable Long idProveedores) {
        StandardResponseDTO respuestaEstandar = new StandardResponseDTO();
        respuestaEstandar.setFechaHora(new Date());
        try {
            Optional<ProveedoresEntity> proveedor = proveedoresService.findById(idProveedores);
            if (proveedor.isEmpty()) {
                respuestaEstandar.setCodigoRespuestaInterno(HttpStatus.NOT_FOUND.value());
                respuestaEstandar.setMensaje(HttpStatus.NO_CONTENT.getReasonPhrase());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuestaEstandar);
            }
            respuestaEstandar.setCodigoRespuestaInterno(HttpStatus.OK.value());
            respuestaEstandar.setMensaje(HttpStatus.OK.getReasonPhrase());
            respuestaEstandar.setPayload(proveedor.get());
            return ResponseEntity.ok(respuestaEstandar);
        } catch (Exception ex) {
            respuestaEstandar.setCodigoRespuestaInterno(HttpStatus.INTERNAL_SERVER_ERROR.value());
                respuestaEstandar.setMensaje(ERROR_INTERNO_LISTAR_PROVEEDORES.getVal());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuestaEstandar);
        }
    }

    @CrossOrigin
    @PostMapping
    public ResponseEntity<StandardResponseDTO> createProveedor(@RequestBody ProveedoresRequestDTO proveedorRequest) {
        StandardResponseDTO respuestaEstandar;
        try {
            ProveedoresEntity proveedor = proveedoresService.convertirReqADTO(proveedorRequest);
            respuestaEstandar = proveedoresService.almacenarProveedores(proveedor);
        } catch (Exception ex) {
            respuestaEstandar = new StandardResponseDTO();
            respuestaEstandar.setCodigoRespuestaInterno(HttpStatus.INTERNAL_SERVER_ERROR.value());
            respuestaEstandar.setMensaje(ERROR_INTERNO_CREAR_PROVEEDOR.getVal());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuestaEstandar);
        }
        return ResponseEntity.ok(respuestaEstandar);
    }

    @CrossOrigin
    @PutMapping("/{idProveedores}")
    public ResponseEntity<StandardResponseDTO> updateProveedor(
            @PathVariable Long idProveedores,
            @RequestBody ProveedoresRequestDTO proveedorRequest) {

        ProveedoresEntity proveedor = proveedoresService.convertirReqADTO(proveedorRequest);
        StandardResponseDTO updatedProveedor = proveedoresService.updateProveedores(idProveedores, proveedor);
        return new ResponseEntity<>(updatedProveedor, HttpStatus.OK);
    }
}
