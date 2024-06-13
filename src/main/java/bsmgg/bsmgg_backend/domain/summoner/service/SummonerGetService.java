package bsmgg.bsmgg_backend.domain.summoner.service;

import bsmgg.bsmgg_backend.domain.riot.dto.ParticipantDto;
import bsmgg.bsmgg_backend.domain.summoner.domain.Summoner;
import bsmgg.bsmgg_backend.domain.summoner.repository.SummonerRankingRepository;
import bsmgg.bsmgg_backend.domain.summoner.repository.SummonerRepository;
import bsmgg.bsmgg_backend.domain.summoner.repository.dto.RankingDto;
import bsmgg.bsmgg_backend.domain.summoner.repository.dto.SummonerRanking;
import bsmgg.bsmgg_backend.global.error.exception.BSMGGException;
import bsmgg.bsmgg_backend.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SummonerGetService {

    private final SummonerRepository summonerRepository;
    private final SummonerRankingRepository summonerRankingRepository;

    @Value("${riot.season-started-time}")
    private Long seasonStartedTime;

    public Summoner getSummonerById(String puuid) {
        return summonerRepository.findByPuuid(puuid);
    }

    public Summoner getSummonerByRiotName(String gameName, String tagLine) {
        return summonerRepository.findByGameNameAndTagLine(gameName, tagLine)
                .orElseThrow(() -> new BSMGGException(ErrorCode.SUMMONER_NOT_FOUND));
    }

    public Summoner getSummonerByParticipant(ParticipantDto dto) {
        Summoner summoner = summonerRepository.findByPuuid(dto.puuid());
        if (summoner == null) {
            summoner = summonerRepository.save(Summoner.builder()
                    .puuid(dto.puuid())
                    .riotId(dto.summonerId())
                    .gameName(dto.riotIdGameName())
                    .tagLine(dto.riotIdTagline())
                    .level(0L)
                    .profileIcon(dto.profileIcon())
                    .lastUpdated(seasonStartedTime)
                    .build()
            );
        }
        return summoner;
    }

    public List<String> getMostChampions(Summoner summoner) {
        List<String> mostChampions = new ArrayList<>();
        if (!Objects.equals(summoner.getMost1(), ""))
            mostChampions.add(summoner.getMost1());
        if (!Objects.equals(summoner.getMost2(), ""))
            mostChampions.add(summoner.getMost2());
        if (!Objects.equals(summoner.getMost3(), ""))
            mostChampions.add(summoner.getMost3());
        return mostChampions;
    }

    public SummonerRanking getSummonerWithRank(String puuid) {
        List<SummonerRanking> dtos = getSummonerRanking();
        for (SummonerRanking dto : dtos) {
            if (dto.summoner().getPuuid().equals(puuid)) {
                return dto;
            }
        }
        return new SummonerRanking(getSummonerById(puuid));
    }

    public SummonerRanking getSummonerWithRank(String gameName, String tagLine) {
        List<SummonerRanking> dtos = getSummonerRanking();
        for (SummonerRanking dto : dtos) {
            if (dto.summoner().getGameName().equals(gameName)
            && dto.summoner().getTagLine().equals(tagLine)) {
                return dto;
            }
        }
        return new SummonerRanking(getSummonerByRiotName(gameName, tagLine));
    }

    public List<SummonerRanking> getSummonerRanking() {
        return summonerRankingRepository.findAllWithRanking();
    }
}
