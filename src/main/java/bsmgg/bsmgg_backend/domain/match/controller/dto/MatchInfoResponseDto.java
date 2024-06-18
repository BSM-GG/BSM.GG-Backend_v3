package bsmgg.bsmgg_backend.domain.match.controller.dto;

import bsmgg.bsmgg_backend.domain.match.domain.Match;
import bsmgg.bsmgg_backend.domain.participant.dto.ParticipantResponseDto;

import java.util.List;

public record MatchInfoResponseDto(
        String gameType,
        Boolean isWin,
        Long gameStartedAt,
        Long gameDuration,
        List<ParticipantResponseDto> participants
) {
    public MatchInfoResponseDto(Match match, Boolean isWin, List<ParticipantResponseDto> participants) {
        this(
                match.getGameType(),
                isWin,
                match.getGameStartedAt(),
                match.getGameDuration(),
                participants
        );
    }
}
