package bsmgg.bsmgg_backend.domain.riot.dto;

public record LeagueEntryDTO(
        String leagueId,
        String summonerId,
        String queueType,
        String tier,
        String rank,
        Integer leaguePoints,
        Integer wins,
        Integer losses,
        Boolean hotStreak,
        Boolean veteran,
        Boolean freshBlood,
        Boolean inactive ,
        MiniSeriesDTO miniSeries
) {
}
