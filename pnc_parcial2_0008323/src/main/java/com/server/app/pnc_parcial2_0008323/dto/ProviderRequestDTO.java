package com.server.app.pnc_parcial2_0008323.dto;


import com.server.app.pnc_parcial2_0008323.enums.ArticleType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ProviderRequestDTO {
    @NotBlank(message = "El nombre del proveedor es obligatorio")
    private String name;

    @NotNull(message = "El tipo del proveedor es obligatorio")
    private ArticleType type;
}
