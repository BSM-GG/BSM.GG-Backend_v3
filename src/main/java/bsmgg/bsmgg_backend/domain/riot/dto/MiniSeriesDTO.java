package bsmgg.bsmgg_backend.domain.riot.dto;

public record MiniSeriesDTO(
        Integer looses,
        String progress,
        Integer target,
        Integer wins
) {
}
