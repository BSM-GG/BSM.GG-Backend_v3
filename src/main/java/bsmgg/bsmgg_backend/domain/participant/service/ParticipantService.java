package bsmgg.bsmgg_backend.domain.participant.service;

import bsmgg.bsmgg_backend.domain.match.domain.Match;
import bsmgg.bsmgg_backend.domain.participant.domain.Participant;
import bsmgg.bsmgg_backend.domain.participant.repository.ParticipantRepository;
import bsmgg.bsmgg_backend.domain.riot.dto.ParticipantDto;
import bsmgg.bsmgg_backend.domain.riot.dto.PerkStyleDto;
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
            PerkStyleDto mainPerk = dto.perks().styles().stream()
                    .findFirst().orElseThrow();
            PerkStyleDto subPerk = dto.perks().styles().stream()
                    .filter(perk -> Objects.equals(perk.description(), "subStyle"))
                    .findFirst().orElseThrow();

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
                    .cs(dto.totalMinionsKilled() + dto.totalAllyJungleMinionsKilled() + dto.totalEnemyJungleMinionsKilled())
                    .damage(dto.totalDamageDealtToChampions())
                    .damageRate(Math.round(dto.challenges().getTeamDamagePercentage()*100))
                    .gainDamage(dto.totalDamageTaken())
                    .heal(dto.totalHeal())
                    .earnedGold(dto.goldEarned())
                    .spentGold(dto.goldSpent())
                    .sightWard(dto.wardsPlaced())
                    .visionWard(dto.visionWardsBoughtInGame())
                    .visionScore(dto.visionScore())
                    .skillUsed(dto.challenges().getAbilityUses())
                    .spell1(mappingService.getSpellId(dto.summoner1Id()))
                    .spell2(mappingService.getSpellId(dto.summoner2Id()))
                    .spell1Used(dto.summoner1Casts())
                    .spell2Used(dto.summoner2Casts())
                    .item1(dto.item0())
                    .item2(dto.item1())
                    .item3(dto.item2())
                    .item4(dto.item3())
                    .item5(dto.item4())
                    .item6(dto.item5())
                    .ward(dto.item6() == 2056 ? 3340 : dto.item6())
                    .mainPerk(mappingService.getPerkName(mainPerk.selections().get(0).perk()))
                    .mainPerkPart1(mappingService.getPerkName(mainPerk.selections().get(1).perk()))
                    .mainPerkPart2(mappingService.getPerkName(mainPerk.selections().get(2).perk()))
                    .mainPerkPart3(mappingService.getPerkName(mainPerk.selections().get(3).perk()))
                    .subPerkStyle(mappingService.getPerkName(subPerk.style()))
                    .subPerkPart1(mappingService.getPerkName(subPerk.selections().get(0).perk()))
                    .subPerkPart2(mappingService.getPerkName(subPerk.selections().get(1).perk()))
                    .offensePerk(mappingService.getPerkName(dto.perks().statPerks().offense()))
                    .flexPerk(mappingService.getPerkName(dto.perks().statPerks().flex()))
                    .defensePerk(mappingService.getPerkName(dto.perks().statPerks().defense()))
                    .build());
            count++;
        }
        participantRepository.saveAll(participants);
    }

    private String getTeam(int count) {
        return count < 5 ? "BLUE" : "RED";
    }
}
