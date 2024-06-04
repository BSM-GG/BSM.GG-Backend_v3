package bsmgg.bsmgg_backend.domain.riot.dto;

public record SummonerDTO(
        String accountId,
        Integer profileIconId,
        Long revisionDate,
        String id,
        String puuid,
        Long summonerLevel
) {
}
