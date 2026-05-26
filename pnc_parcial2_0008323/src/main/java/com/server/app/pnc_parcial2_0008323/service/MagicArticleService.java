package com.server.app.pnc_parcial2_0008323.service;

import com.server.app.pnc_parcial2_0008323.dto.ArticleRequestDTO;
import com.server.app.pnc_parcial2_0008323.dto.ArticleResponseDTO;
import com.server.app.pnc_parcial2_0008323.entity.MagicArticle;
import com.server.app.pnc_parcial2_0008323.entity.MagicProvider;
import com.server.app.pnc_parcial2_0008323.enums.ArticleType;
import com.server.app.pnc_parcial2_0008323.exception.BusinessRuleException;
import com.server.app.pnc_parcial2_0008323.exception.ConflictException;
import com.server.app.pnc_parcial2_0008323.exception.ResourceNotFoundException;
import com.server.app.pnc_parcial2_0008323.mapper.MagicArticleMapper;
import com.server.app.pnc_parcial2_0008323.repository.MagicArticleRepository;
import com.server.app.pnc_parcial2_0008323.repository.MagicProviderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MagicArticleService {

    private final MagicArticleRepository articleRepository;
    private final MagicProviderRepository providerRepository;
    private final MagicArticleMapper articleMapper;

    @Transactional
    public ArticleResponseDTO create(ArticleRequestDTO dto) {
        // RN: nombre único (sin distinción de mayúsculas)
        if (articleRepository.existsByNameIgnoreCase(dto.getName())) {
            throw new ConflictException("Ya existe un artículo con el nombre: " + dto.getName());
        }

        MagicProvider provider = getProviderOrThrow(dto.getProviderId());

        // RN: tipo del artículo debe coincidir con el tipo del proveedor
        validateProviderType(provider, dto.getType());

        MagicArticle article = articleMapper.toEntity(dto);
        article.setProvider(provider);

        return articleMapper.toResponseDTO(articleRepository.save(article));
    }

    @Transactional(readOnly = true)
    public List<ArticleResponseDTO> findAll(ArticleType category, BigDecimal maxPrice, Long providerId) {
        return articleRepository.findByFilters(category, maxPrice, providerId)
                .stream()
                .map(articleMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ArticleResponseDTO findById(Long id) {
        return articleMapper.toResponseDTO(getArticleOrThrow(id));
    }

    @Transactional
    public ArticleResponseDTO update(Long id, ArticleRequestDTO dto) {
        MagicArticle article = getArticleOrThrow(id);


        articleRepository.findByNameIgnoreCase(dto.getName())
                .filter(found -> !found.getId().equals(id))
                .ifPresent(found -> {
                    throw new ConflictException("Ya existe otro artículo con el nombre: " + dto.getName());
                });

        MagicProvider provider = getProviderOrThrow(dto.getProviderId());


        validateProviderType(provider, dto.getType());

        articleMapper.updateEntityFromDTO(dto, article);
        article.setProvider(provider);

        return articleMapper.toResponseDTO(articleRepository.save(article));
    }

    @Transactional
    public void delete(Long id) {
        getArticleOrThrow(id);
        articleRepository.deleteById(id);
    }


    private MagicArticle getArticleOrThrow(Long id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Artículo no encontrado con ID: " + id));
    }

    private MagicProvider getProviderOrThrow(Long id) {
        return providerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Proveedor no encontrado con ID: " + id));
    }

    private void validateProviderType(MagicProvider provider, ArticleType articleType) {
        if (!provider.getType().equals(articleType)) {
            throw new BusinessRuleException(
                "El proveedor '" + provider.getName() + "' es de tipo " + provider.getType() +
                " y no puede abastecer artículos de tipo " + articleType
            );
        }
    }
}
