package bsmgg.bsmgg_backend.domain.summoner.service;

import bsmgg.bsmgg_backend.domain.match.service.MatchService;
import bsmgg.bsmgg_backend.domain.summoner.controller.dto.ChangResponseDto;
import bsmgg.bsmgg_backend.domain.summoner.controller.dto.SummonerRankingResponseDto;
import bsmgg.bsmgg_backend.domain.summoner.controller.dto.SummonerRequestDto;
import bsmgg.bsmgg_backend.domain.summoner.controller.dto.SummonerResponseDto;
import bsmgg.bsmgg_backend.domain.summoner.domain.Summoner;
import bsmgg.bsmgg_backend.domain.user.domain.User;
import bsmgg.bsmgg_backend.domain.user.service.UserGetService;
import bsmgg.bsmgg_backend.domain.user.service.UserPostService;
import bsmgg.bsmgg_backend.global.error.exception.BSMGGException;
import bsmgg.bsmgg_backend.global.error.exception.ErrorCode;
import bsmgg.bsmgg_backend.global.mapping.MappingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SummonerService {

    private final SummonerPostService summonerPostService;
    private final UserGetService userGetService;
    private final UserPostService userPostService;
    private final SummonerGetService summonerGetService;
    private final MappingService mappingService;

    @Value("${riot.season-started-time}")
    private Long seasonStartedTime;

    public void assign(SummonerRequestDto dto) {
        Summoner summoner = summonerPostService.updateSummoner(dto.gameName().replace(" ", ""), dto.tagLine());
        summonerPostService.save(summoner);

        User user = userGetService.getUser();
        if (user == null || userGetService.getUserBySummoner(summoner) != null) {
            return;
        }
        user.setSummoner(summoner);
        userPostService.save(user);
    }

    public SummonerResponseDto getSummoner(String name) {
        if (name == null || name.isEmpty()) {
            User user = userGetService.getUser();
            return summonerGetService.getSummonerWithRank(user.getSummoner().getPuuid());
        } else {
            String[] nameInfo = name.split("-");
            if (nameInfo.length != 2)
                throw new BSMGGException(ErrorCode.SUMMONER_NOT_FOUND);
            return summonerGetService.getSummonerWithRank(nameInfo[0], nameInfo[1]);
        }
    }

    public SummonerRankingResponseDto getRanking(Integer page) {
        if (page == null) page = 0;
        List<SummonerResponseDto> summoners = summonerGetService.getAllRanking(page);
        for(SummonerResponseDto summoner : summoners) {
            summoner.update(mappingService);
        }
        return new SummonerRankingResponseDto(summoners, page);
    }

    public ChangResponseDto getChang() {
        long nowTime = System.currentTimeMillis() / 1000;
        long startTime = seasonStartedTime - 172800;
        long diff = nowTime - startTime;
        long week = 604800;
        long div = diff / week;
        long prevWeekStart = (div - 1) * week + startTime;
        long prevWeekEnd = div * week + startTime;

        return summonerGetService.getChang(prevWeekStart, prevWeekEnd);
    }
}
