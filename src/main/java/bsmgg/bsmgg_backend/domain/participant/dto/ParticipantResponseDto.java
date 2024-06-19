package bsmgg.bsmgg_backend.domain.participant.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record ParticipantResponseDto(
        String gameName,
        String tagLine,
        String soloTier,
        Integer soloPoint,
        String flexTier,
        Integer flexPoint,
        Integer level,
        String championE,
        String championK,
        Integer championLevel,
        Integer lane,
        String team,
        String spell1,
        String spell2,
        String mainPerk,
        String subPerk,
        Integer killRate,
        Integer kills,
        Integer assists,
        Integer deaths,
        Integer damage,
        Integer gainDamage,
        Integer cs,
        Integer visionScore,
        Integer sightWard,
        Integer visionWard,
        List<String> items,
        String ward
) {
}
