package com.server.app.pnc_parcial2_0008323.dto;


import com.server.app.pnc_parcial2_0008323.enums.ArticleType;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ProviderResponseDTO {
    private Long id;
    private String name;
    private ArticleType type;
}
