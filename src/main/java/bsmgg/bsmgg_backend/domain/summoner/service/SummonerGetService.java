package bsmgg.bsmgg_backend.domain.summoner.service;

import bsmgg.bsmgg_backend.domain.participant.dto.ChangInfoDto;
import bsmgg.bsmgg_backend.domain.participant.service.ParticipantGetService;
import bsmgg.bsmgg_backend.domain.riot.dto.ParticipantDto;
import bsmgg.bsmgg_backend.domain.summoner.controller.dto.ChangResponseDto;
import bsmgg.bsmgg_backend.domain.summoner.controller.dto.SummonerResponseDto;
import bsmgg.bsmgg_backend.domain.summoner.domain.Summoner;
import bsmgg.bsmgg_backend.domain.summoner.repository.SummonerRankingRepository;
import bsmgg.bsmgg_backend.domain.summoner.repository.SummonerRepository;
import bsmgg.bsmgg_backend.domain.user.service.UserGetService;
import bsmgg.bsmgg_backend.global.error.exception.BSMGGException;
import bsmgg.bsmgg_backend.global.error.exception.ErrorCode;
import bsmgg.bsmgg_backend.global.mapping.MappingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SummonerGetService {

    private final UserGetService userGetService;
    private final SummonerRepository summonerRepository;
    private final SummonerRankingRepository summonerRankingRepository;
    private final ParticipantGetService participantGetService;
    private final MappingService mappingService;

    @Value("${riot.season-started-time}")
    private Long seasonStartedTime;

    public Summoner getSummonerById(String puuid) {
        return summonerRepository.findByPuuid(puuid);
    }

    public Summoner getSummonerByRiotName(String gameName, String tagLine) {
        return summonerRepository.findByGameNameAndTagLine(gameName, tagLine)
                .orElseThrow(() -> new BSMGGException(ErrorCode.SUMMONER_NOT_FOUND));
    }

    public Summoner getSummonerByRiotName(String name) {
        String[] nameInfo = name.split("-");
        if (nameInfo.length != 2)
            throw new BSMGGException(ErrorCode.SUMMONER_NOT_FOUND);
        return summonerRepository.findByGameNameAndTagLine(nameInfo[0], nameInfo[1])
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

    public SummonerResponseDto getSummonerWithRank(String puuid) {
        List<SummonerResponseDto> dtos = getAllRanking();
        Integer userCount = userGetService.getUserCount();
        for (SummonerResponseDto dto : dtos) {
            if (dto.getPuuid().equals(puuid)) {
                dto.setUserCount(userCount);
                return dto;
            }
        }
        return new SummonerResponseDto(getSummonerById(puuid), userCount, mappingService);
    }

    public SummonerResponseDto getSummonerWithRank(String gameName, String tagLine) {
        List<SummonerResponseDto> dtos = getAllRanking();
        Integer userCount = userGetService.getUserCount();
        for (SummonerResponseDto dto : dtos) {
            if (dto.getGameName().replace(" ", "").equalsIgnoreCase(gameName.replace(" ", ""))
            && dto.getTagLine().replace(" ", "").equalsIgnoreCase(tagLine.replace(" ", ""))) {
                dto.setUserCount(userCount);
                return dto;
            }
        }
        return new SummonerResponseDto(getSummonerByRiotName(gameName, tagLine), userCount, mappingService);
    }

    public ChangResponseDto getChang(long prevWeekStart, long prevWeekEnd) {
        String puuid = summonerRankingRepository.findChangPuuidByTimes(prevWeekStart, prevWeekEnd);
        SummonerResponseDto summonerInfo = getSummonerWithRank(puuid);
        ChangInfoDto changInfo = participantGetService.getChangByPuuid(puuid, prevWeekStart, prevWeekEnd);
        return new ChangResponseDto(summonerInfo, changInfo);
    }

    public List<SummonerResponseDto> getAllRanking() {
        return summonerRankingRepository.findAllWithRanking(-1);
    }

    public List<SummonerResponseDto> getAllRanking(int page) {
        return summonerRankingRepository.findAllWithRanking(page);
    }
}
