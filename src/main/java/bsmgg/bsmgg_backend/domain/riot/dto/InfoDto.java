package bsmgg.bsmgg_backend.domain.riot.dto;

import java.util.List;

public record InfoDto(
        String endOfGameResult,
        Long gameCreation,
        Long gameDuration,
        Long gameEndTimestamp,
        Long gameId,
        String gameMode,
        String gameName,
        Long gameStartTimestamp,
        String gameType,
        String gameVersion,
        Integer mapId,
        List<ParticipantDto> participants,
        String platformId,
        Integer queueId,
        List<TeamDto> teams,
        String tournamentCode
) {
}
