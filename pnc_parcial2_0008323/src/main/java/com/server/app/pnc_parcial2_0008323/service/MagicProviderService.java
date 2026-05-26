package com.server.app.pnc_parcial2_0008323.service;


import com.server.app.pnc_parcial2_0008323.dto.ProviderRequestDTO;
import com.server.app.pnc_parcial2_0008323.dto.ProviderResponseDTO;
import com.server.app.pnc_parcial2_0008323.entity.MagicProvider;
import com.server.app.pnc_parcial2_0008323.exception.ConflictException;
import com.server.app.pnc_parcial2_0008323.exception.ResourceNotFoundException;
import com.server.app.pnc_parcial2_0008323.mapper.MagicProviderMapper;
import com.server.app.pnc_parcial2_0008323.repository.MagicArticleRepository;
import com.server.app.pnc_parcial2_0008323.repository.MagicProviderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MagicProviderService {

    private final MagicProviderRepository providerRepository;
    private final MagicArticleRepository articleRepository;
    private final MagicProviderMapper providerMapper;

    @Transactional
    public ProviderResponseDTO create(ProviderRequestDTO dto) {

        if (providerRepository.existsByNameIgnoreCase(dto.getName())) {
            throw new ConflictException("Ya existe un proveedor con el nombre: " + dto.getName());
        }

        MagicProvider provider = providerMapper.toEntity(dto);
        return providerMapper.toResponseDTO(providerRepository.save(provider));
    }

    @Transactional(readOnly = true)
    public ProviderResponseDTO findById(Long id) {
        return providerMapper.toResponseDTO(getProviderOrThrow(id));
    }

    @Transactional
    public ProviderResponseDTO update(Long id, ProviderRequestDTO dto) {
        MagicProvider provider = getProviderOrThrow(id);


        providerRepository.findByNameIgnoreCase(dto.getName())
                .filter(found -> !found.getId().equals(id))
                .ifPresent(found -> {
                    throw new ConflictException("Ya existe otro proveedor con el nombre: " + dto.getName());
                });

        providerMapper.updateEntityFromDTO(dto, provider);
        return providerMapper.toResponseDTO(providerRepository.save(provider));
    }

    @Transactional
    public void delete(Long id) {
        getProviderOrThrow(id);


        if (articleRepository.existsByProviderId(id)) {
            throw new ConflictException(
                "No se puede eliminar el proveedor porque tiene artículos asociados. " +
                "Elimine o reasigne los artículos primero."
            );
        }

        providerRepository.deleteById(id);
    }

    private MagicProvider getProviderOrThrow(Long id) {
        return providerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Proveedor no encontrado con ID: " + id));
    }
}
