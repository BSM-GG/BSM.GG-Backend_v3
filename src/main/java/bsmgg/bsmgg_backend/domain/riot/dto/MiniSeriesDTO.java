package bsmgg.bsmgg_backend.domain.riot.dto;

import java.io.Serializable;

public record MiniSeriesDTO(
        Integer looses,
        String progress,
        Integer target,
        Integer wins
) implements Serializable {
}
