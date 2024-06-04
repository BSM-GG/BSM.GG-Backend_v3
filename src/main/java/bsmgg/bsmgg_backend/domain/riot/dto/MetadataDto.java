package bsmgg.bsmgg_backend.domain.riot.dto;

import java.util.List;

public record MetadataDto(
        String dataVersion,
        String matchId,
        List<String> participants
) {
}
