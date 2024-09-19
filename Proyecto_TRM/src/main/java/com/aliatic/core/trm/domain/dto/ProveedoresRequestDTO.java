package com.aliatic.core.trm.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProveedoresRequestDTO {
    @NotBlank(message = "El código del proveedor no puede estar vacío")
    @Size(max = 100, message = "El código del proveedor no puede superar los 100 caracteres")
    private String codigoProveedores;

    @NotBlank(message = "La denominación del proveedor no puede estar vacía")
    @Size(max = 100, message = "La denominación del proveedor no puede superar los 100 caracteres")
    private String denominacionProveedores;

    @Size(max = 1000, message = "La URL del proveedor no puede superar los 1000 caracteres")
    private String urlProveedores;

    private int estado;
}
