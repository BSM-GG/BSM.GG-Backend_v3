package bsmgg.bsmgg_backend.domain.participant.service;

import bsmgg.bsmgg_backend.domain.match.domain.Match;
import bsmgg.bsmgg_backend.domain.participant.domain.Participant;
import bsmgg.bsmgg_backend.domain.participant.repository.ParticipantRepository;
import bsmgg.bsmgg_backend.domain.riot.dto.ParticipantDto;
import bsmgg.bsmgg_backend.domain.riot.dto.PerkStyleSelectionDto;
import bsmgg.bsmgg_backend.domain.summoner.domain.Summoner;
import bsmgg.bsmgg_backend.domain.summoner.service.SummonerGetService;
import bsmgg.bsmgg_backend.global.mapping.MappingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ParticipantService {

    private final SummonerGetService summonerGetService;
    private final ParticipantRepository participantRepository;
    private final MappingService mappingService;

    public void saveAll(Match match, List<ParticipantDto> dtos) {
        List<Participant> participants = new ArrayList<>();
        int count = 0;

        for (ParticipantDto dto : dtos) {
            Summoner summoner = summonerGetService.getSummonerByParticipant(dto);
            List<PerkStyleSelectionDto> mainPerk = dto.perks().styles().stream()
                    .findFirst().orElseThrow().selections();
            List<PerkStyleSelectionDto> subPerk = dto.perks().styles().stream()
                    .filter(perk -> Objects.equals(perk.description(), "subStyle"))
                    .findFirst().orElseThrow().selections();

            participants.add(Participant.builder()
                    .match(match)
                    .summoner(summoner)
                    .isWin(dto.win())
                    .champion(dto.championName())
                    .championLevel(dto.champLevel())
                    .lane(count)
                    .team(getTeam(count))
                    .killRate(Math.round(dto.challenges().getKillParticipation()*100))
                    .kills(dto.kills())
                    .assists(dto.assists())
                    .deaths(dto.deaths())
                    .cs(dto.totalMinionsKilled())
                    .damage(dto.totalDamageDealt())
                    .damageRate(Math.round(dto.challenges().getTeamDamagePercentage()*100))
                    .gainDamage(dto.totalDamageTaken())
                    .heal(dto.totalHeal())
                    .earnedGold(dto.goldEarned())
                    .spentGold(dto.goldSpent())
                    .sightWard(dto.wardsPlaced())
                    .visionWard(dto.visionWardsBoughtInGame())
                    .visionScore(dto.visionScore())
                    .skillUsed(dto.challenges().getAbilityUses())
                    .spell1(mappingService.getSpell(dto.summoner1Id()))
                    .spell2(mappingService.getSpell(dto.summoner2Id()))
                    .spell1Used(dto.summoner1Casts())
                    .spell2Used(dto.summoner2Casts())
                    .item1(mappingService.getItem(dto.item0()))
                    .item2(mappingService.getItem(dto.item1()))
                    .item3(mappingService.getItem(dto.item2()))
                    .item4(mappingService.getItem(dto.item3()))
                    .item5(mappingService.getItem(dto.item4()))
                    .item6(mappingService.getItem(dto.item5()))
                    .ward(mappingService.getItem(dto.item6()))
                    .mainPerk(mappingService.getPerk(mainPerk.get(0).perk()))
                    .mainPerkPart1(mappingService.getPerk(mainPerk.get(1).perk()))
                    .mainPerkPart2(mappingService.getPerk(mainPerk.get(2).perk()))
                    .mainPerkPart3(mappingService.getPerk(mainPerk.get(3).perk()))
                    .subPerkStyle(mappingService.getPerk(subPerk.get(1).perk()))
                    .subPerkPart1(mappingService.getPerk(subPerk.get(0).perk()))
                    .subPerkPart2(mappingService.getPerk(subPerk.get(1).perk()))
                    .offensePerk(mappingService.getPerk(dto.perks().statPerks().offense()))
                    .flexPerk(mappingService.getPerk(dto.perks().statPerks().flex()))
                    .defensePerk(mappingService.getPerk(dto.perks().statPerks().defense()))
                    .build());
            count++;
        }
        participantRepository.saveAll(participants);
    }

    private String getTeam(int count) {
        return count < 5 ? "BLUE" : "RED";
    }
}
