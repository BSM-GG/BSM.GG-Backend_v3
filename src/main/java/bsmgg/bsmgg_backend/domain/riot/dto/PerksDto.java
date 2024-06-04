package bsmgg.bsmgg_backend.domain.riot.dto;

import java.util.List;

public record PerksDto(
        PerkStatsDto statPerks,
        List<PerkStyleDto> styles
) {
}
