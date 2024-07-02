package bsmgg.bsmgg_backend.domain.summoner.service;

import bsmgg.bsmgg_backend.domain.participant.dto.ChangInfoDto;
import bsmgg.bsmgg_backend.domain.participant.service.ParticipantGetService;
import bsmgg.bsmgg_backend.domain.summoner.controller.dto.ChangResponseDto;
import bsmgg.bsmgg_backend.domain.summoner.controller.dto.SummonerResponseDto;
import bsmgg.bsmgg_backend.domain.summoner.domain.Summoner;
import bsmgg.bsmgg_backend.domain.summoner.repository.SummonerJdbcRepository;
import bsmgg.bsmgg_backend.domain.summoner.repository.SummonerRepository;
import bsmgg.bsmgg_backend.domain.user.service.UserGetService;
import bsmgg.bsmgg_backend.global.error.exception.BSMGGException;
import bsmgg.bsmgg_backend.global.error.exception.ErrorCode;
import bsmgg.bsmgg_backend.global.mapping.MappingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SummonerGetService {

    private final UserGetService userGetService;
    private final SummonerRepository summonerRepository;
    private final SummonerJdbcRepository summonerJdbcRepository;
    private final ParticipantGetService participantGetService;
    private final MappingService mappingService;

    @Value("${riot.season-started-time}")
    private Long seasonStartedTime;

    public Summoner getSummonerById(String puuid) {
        return summonerRepository.findByPuuid(puuid);
    }

    public Summoner getSummonerByRiotNameOrThrow(String gameName, String tagLine) {
        return summonerRepository.findByGameNameAndTagLine(gameName, tagLine)
                .orElseThrow(() -> new BSMGGException(ErrorCode.SUMMONER_NOT_FOUND));
    }

    public Summoner getSummonerByRiotName(String gameName, String tagLine) {
        return summonerRepository.findByGameNameAndTagLine(gameName, tagLine)
                .orElse(Summoner.builder().build());
    }

    public Summoner getSummonerByRiotNameOrThrow(String name) {
        String[] nameInfo = name.split("-");
        if (nameInfo.length != 2)
            throw new BSMGGException(ErrorCode.SUMMONER_NOT_FOUND);
        return summonerRepository.findByGameNameAndTagLine(nameInfo[0], nameInfo[1])
                .orElseThrow(() -> new BSMGGException(ErrorCode.SUMMONER_NOT_FOUND));
    }

    public Map<String, Boolean> getExistingSummoners(List<String> puuids) {
        List<String> existingPuuids = summonerRepository.findIdsByPuuids(puuids);
        Map<String, Boolean> puuidMap = new HashMap<>();
        for (String puuid : existingPuuids) {
            puuidMap.put(puuid, true);
        }
        return puuidMap;
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
        String puuid = summonerJdbcRepository.findChangPuuidByTimes(prevWeekStart, prevWeekEnd);
        SummonerResponseDto summonerInfo = getSummonerWithRank(puuid);
        ChangInfoDto changInfo = participantGetService.getChangByPuuid(puuid, prevWeekStart, prevWeekEnd);
        return new ChangResponseDto(summonerInfo, changInfo);
    }

    public List<SummonerResponseDto> getAllRanking() {
        return summonerJdbcRepository.findAllWithRanking(-1);
    }

    public List<SummonerResponseDto> getAllRanking(int page) {
        return summonerJdbcRepository.findAllWithRanking(page);
    }
}
