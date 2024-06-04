package bsmgg.bsmgg_backend.domain.riot.dto;

import java.util.List;

public record PerkStyleDto(
        String description,
        List<PerkStyleSelectionDto> selections,
        Integer style
) {
}
