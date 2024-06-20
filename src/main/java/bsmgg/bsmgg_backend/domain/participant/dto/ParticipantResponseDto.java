package bsmgg.bsmgg_backend.domain.participant.dto;

import bsmgg.bsmgg_backend.global.mapping.MappingService;
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
        Champion champion,
        Integer championLevel,
        Integer lane,
        String team,
        Spell spell1,
        Spell spell2,
        Perk mainPerk,
        Perk subPerk,
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
        List<Item> items,
        Item ward
) {
    public void update(MappingService mappingService) {
        champion.setName(mappingService.getChamp(champion.getId()));
        spell1.setName(mappingService.getSpellName(spell1.getId()));
        spell2.setName(mappingService.getSpellName(spell2.getId()));
        for (Item i : items) {
            i.setName(mappingService.getItem(i.getId()));
        }
        ward.setName(mappingService.getItem(ward.getId()));
        mainPerk.setId(mappingService.getPerkUri(mainPerk.getName()));
        subPerk.setId(mappingService.getPerkUri(subPerk.getName()));
    }
}
