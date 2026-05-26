package com.server.app.pnc_parcial2_0008323.dto;


import com.server.app.pnc_parcial2_0008323.enums.ArticleType;
import lombok.*;

import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ArticleResponseDTO {
    private Long id;
    private String name;
    private ArticleType type;
    private BigDecimal price;
    private ProviderResponseDTO provider;
}
