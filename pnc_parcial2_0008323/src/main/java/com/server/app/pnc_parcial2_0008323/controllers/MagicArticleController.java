package com.server.app.pnc_parcial2_0008323.controllers;


import com.server.app.pnc_parcial2_0008323.dto.ArticleRequestDTO;
import com.server.app.pnc_parcial2_0008323.dto.ArticleResponseDTO;
import com.server.app.pnc_parcial2_0008323.enums.ArticleType;
import com.server.app.pnc_parcial2_0008323.service.MagicArticleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/artefacts")
@RequiredArgsConstructor
public class MagicArticleController {

    private final MagicArticleService articleService;


    @PostMapping
    public ResponseEntity<ArticleResponseDTO> create(@Valid @RequestBody ArticleRequestDTO dto) {
        ArticleResponseDTO response = articleService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @GetMapping
    public ResponseEntity<List<ArticleResponseDTO>> getAll(
            @RequestParam(required = false) ArticleType category,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) Long provider) {
        return ResponseEntity.ok(articleService.findAll(category, maxPrice, provider));
    }


    @GetMapping("/{id}")
    public ResponseEntity<ArticleResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(articleService.findById(id));
    }


    @PutMapping("/{id}")
    public ResponseEntity<ArticleResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody ArticleRequestDTO dto) {
        return ResponseEntity.ok(articleService.update(id, dto));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        articleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
