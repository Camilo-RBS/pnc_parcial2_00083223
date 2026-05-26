package com.server.app.pnc_parcial2_0008323.dto;


import com.server.app.pnc_parcial2_0008323.enums.ArticleType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ArticleRequestDTO {

    @NotBlank(message = "El nombre del artículo es obligatorio")
    private String name;

    @NotNull(message = "El tipo del artículo es obligatorio")
    private ArticleType type;

    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor a cero")
    private BigDecimal price;

    @NotNull(message = "El ID del proveedor es obligatorio")
    private Long providerId;
}
