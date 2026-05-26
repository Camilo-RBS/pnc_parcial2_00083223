package com.server.app.pnc_parcial2_0008323.controllers;


import com.server.app.pnc_parcial2_0008323.dto.ProviderRequestDTO;
import com.server.app.pnc_parcial2_0008323.dto.ProviderResponseDTO;
import com.server.app.pnc_parcial2_0008323.service.MagicProviderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/providers")
@RequiredArgsConstructor
public class MagicProviderController {

    private final MagicProviderService providerService;


    @PostMapping
    public ResponseEntity<ProviderResponseDTO> create(@Valid @RequestBody ProviderRequestDTO dto) {
        ProviderResponseDTO response = providerService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ProviderResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(providerService.findById(id));
    }


    @PutMapping("/{id}")
    public ResponseEntity<ProviderResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody ProviderRequestDTO dto) {
        return ResponseEntity.ok(providerService.update(id, dto));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        providerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
