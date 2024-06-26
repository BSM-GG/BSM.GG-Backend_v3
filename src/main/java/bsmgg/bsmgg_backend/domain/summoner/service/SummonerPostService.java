package bsmgg.bsmgg_backend.domain.summoner.service;

import bsmgg.bsmgg_backend.domain.participant.service.ParticipantGetService;
import bsmgg.bsmgg_backend.domain.riot.dto.LeagueEntryDTO;
import bsmgg.bsmgg_backend.domain.riot.dto.ParticipantDto;
import bsmgg.bsmgg_backend.domain.riot.dto.RiotAccountDto;
import bsmgg.bsmgg_backend.domain.riot.dto.SummonerDto;
import bsmgg.bsmgg_backend.domain.riot.service.RiotApiService;
import bsmgg.bsmgg_backend.domain.summoner.domain.Summoner;
import bsmgg.bsmgg_backend.domain.summoner.repository.SummonerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SummonerPostService {

    private final RiotApiService riotApiService;
    private final ParticipantGetService participantGetService;
    private final SummonerGetService summonerGetService;
    private final SummonerRepository summonerRepository;

    @Value("${riot.season-started-time}")
    private Long seasonStartedTime;


    public void save(Summoner summoner) {
        summonerRepository.save(summoner);
    }

    public Summoner updateSummoner(String gameName, String tagLine) {
        RiotAccountDto account = riotApiService.getAccount(gameName, tagLine);
        SummonerDto riotSummoner = riotApiService.getSummoner(account.puuid());

        Summoner summoner = summonerGetService.getSummonerById(account.puuid());
        if (summoner != null) {
            summoner.updateAccount(account, riotSummoner);
        } else {
            summoner = Summoner.builder()
                    .puuid(account.puuid())
                    .gameName(account.gameName())
                    .tagLine(account.tagLine())
                    .riotId(riotSummoner.id())
                    .profileIcon(riotSummoner.profileIconId())
                    .revisionDate(riotSummoner.revisionDate())
                    .level(riotSummoner.summonerLevel())
                    .lastUpdated(seasonStartedTime)
                    .build();
        }

        List<LeagueEntryDTO> rank = riotApiService.getRank(riotSummoner.id());
        LeagueEntryDTO solo = getRank(rank, "RANKED_SOLO_5x5");
        LeagueEntryDTO flex = getRank(rank, "RANKED_FLEX_SR");
        summoner.updateRank(solo, flex);
        return summoner;
    }

    private LeagueEntryDTO getRank(List<LeagueEntryDTO> rank, String matchType) {
        return rank.stream().filter(entry -> entry.queueType().equals(matchType))
                .findFirst().orElse(null);
    }

    public void updateMostChampions(Summoner summoner) {
        List<String> mostChampions = participantGetService.getMostChampions(summoner.getPuuid());
        summoner = updateSummoner(summoner.getGameName(), summoner.getTagLine());
        summoner.setMostChampions(mostChampions);
        save(summoner);
    }

    public void saveFromParticipants(List<ParticipantDto> dtos) {
        List<Summoner> summoners = new ArrayList<>();
        for(ParticipantDto dto : dtos) {
            summoners.add(Summoner.builder()
                    .puuid(dto.getPuuid())
                    .riotId(dto.getSummonerId())
                    .gameName(dto.getRiotIdGameName())
                    .tagLine(dto.getRiotIdTagline())
                    .level(0L)
                    .profileIcon(dto.getProfileIcon())
                    .lastUpdated(seasonStartedTime)
                    .build());
        }
        summonerRepository.saveAll(summoners);
    }
}
