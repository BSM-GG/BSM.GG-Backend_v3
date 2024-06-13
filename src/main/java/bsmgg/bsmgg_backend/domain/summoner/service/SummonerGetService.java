package bsmgg.bsmgg_backend.domain.summoner.service;

import bsmgg.bsmgg_backend.domain.riot.dto.ParticipantDto;
import bsmgg.bsmgg_backend.domain.summoner.controller.dto.SummonerResponseDto;
import bsmgg.bsmgg_backend.domain.summoner.domain.Summoner;
import bsmgg.bsmgg_backend.domain.summoner.repository.SummonerRankingRepository;
import bsmgg.bsmgg_backend.domain.summoner.repository.SummonerRepository;
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

    public SummonerResponseDto getSummonerWithRank(String puuid) {
        List<SummonerResponseDto> dtos = getSummonerRanking();
        for (SummonerResponseDto dto : dtos) {
            if (dto.puuid().equals(puuid)) {
                return dto;
            }
        }
        return new SummonerResponseDto(getSummonerById(puuid));
    }

    public SummonerResponseDto getSummonerWithRank(String gameName, String tagLine) {
        List<SummonerResponseDto> dtos = getSummonerRanking();
        for (SummonerResponseDto dto : dtos) {
            if (dto.gameName().replace(" ", "").equalsIgnoreCase(gameName.replace(" ", ""))
            && dto.tagLine().replace(" ", "").equalsIgnoreCase(tagLine.replace(" ", ""))) {
                return dto;
            }
        }
        return new SummonerResponseDto(getSummonerByRiotName(gameName, tagLine));
    }

    public List<SummonerResponseDto> getSummonerRanking() {
        return summonerRankingRepository.findAllWithRanking(-1);
    }

    public List<SummonerResponseDto> getSummonerRanking(int page) {
        return summonerRankingRepository.findAllWithRanking(page);
    }
}
