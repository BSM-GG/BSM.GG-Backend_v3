package bsmgg.bsmgg_backend.domain.riot.dto;

import java.util.List;

public record TeamDto(
        List<BanDto> bans,
        ObjectivesDto objectives,
        Integer teamId,
        Boolean win
) {
}
