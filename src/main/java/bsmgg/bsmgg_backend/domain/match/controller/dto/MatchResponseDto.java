package bsmgg.bsmgg_backend.domain.match.controller.dto;

import java.util.List;

public record MatchResponseDto(
        List<MatchInfoResponseDto> matches,
        Integer page
) {
}
