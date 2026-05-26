package com.server.app.pnc_parcial2_0008323.mapper;

import com.server.app.pnc_parcial2_0008323.dto.ProviderRequestDTO;
import com.server.app.pnc_parcial2_0008323.dto.ProviderResponseDTO;
import com.server.app.pnc_parcial2_0008323.entity.MagicProvider;
import org.springframework.stereotype.Component;

@Component
public class MagicProviderMapper {

    public MagicProvider toEntity(ProviderRequestDTO dto) {
        MagicProvider provider = new MagicProvider();
        provider.setName(dto.getName());
        provider.setType(dto.getType());
        return provider;
    }

    public ProviderResponseDTO toResponseDTO(MagicProvider entity) {
        ProviderResponseDTO dto = new ProviderResponseDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setType(entity.getType());
        return dto;
    }

    public void updateEntityFromDTO(ProviderRequestDTO dto, MagicProvider entity) {
        entity.setName(dto.getName());
        entity.setType(dto.getType());
    }
}