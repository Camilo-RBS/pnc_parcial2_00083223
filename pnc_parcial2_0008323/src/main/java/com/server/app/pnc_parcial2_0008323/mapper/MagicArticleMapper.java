package com.server.app.pnc_parcial2_0008323.mapper;

import com.server.app.pnc_parcial2_0008323.dto.ArticleRequestDTO;
import com.server.app.pnc_parcial2_0008323.dto.ArticleResponseDTO;
import com.server.app.pnc_parcial2_0008323.entity.MagicArticle;
import org.springframework.stereotype.Component;

@Component
public class MagicArticleMapper {

    private final MagicProviderMapper providerMapper;

    public MagicArticleMapper(MagicProviderMapper providerMapper) {
        this.providerMapper = providerMapper;
    }

    public MagicArticle toEntity(ArticleRequestDTO dto) {
        MagicArticle article = new MagicArticle();
        article.setName(dto.getName());
        article.setType(dto.getType());
        article.setPrice(dto.getPrice());
        return article;
    }

    public ArticleResponseDTO toResponseDTO(MagicArticle entity) {
        ArticleResponseDTO dto = new ArticleResponseDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setType(entity.getType());
        dto.setPrice(entity.getPrice());
        dto.setProvider(providerMapper.toResponseDTO(entity.getProvider()));
        return dto;
    }

    public void updateEntityFromDTO(ArticleRequestDTO dto, MagicArticle entity) {
        entity.setName(dto.getName());
        entity.setType(dto.getType());
        entity.setPrice(dto.getPrice());
    }
}