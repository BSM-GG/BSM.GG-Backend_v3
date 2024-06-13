package bsmgg.bsmgg_backend.domain.summoner.service;

import bsmgg.bsmgg_backend.domain.summoner.controller.dto.SummonerRankingResponseDto;
import bsmgg.bsmgg_backend.domain.summoner.controller.dto.SummonerRequestDto;
import bsmgg.bsmgg_backend.domain.summoner.controller.dto.SummonerResponseDto;
import bsmgg.bsmgg_backend.domain.summoner.domain.Summoner;
import bsmgg.bsmgg_backend.domain.summoner.repository.dto.SummonerRanking;
import bsmgg.bsmgg_backend.domain.user.domain.User;
import bsmgg.bsmgg_backend.domain.user.service.UserGetService;
import bsmgg.bsmgg_backend.domain.user.service.UserPostService;
import bsmgg.bsmgg_backend.global.error.exception.BSMGGException;
import bsmgg.bsmgg_backend.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SummonerService {

    private final SummonerPostService summonerPostService;
    private final UserGetService userGetService;
    private final UserPostService userPostService;
    private final SummonerGetService summonerGetService;

    public void assign(SummonerRequestDto dto) {
        Summoner summoner = summonerPostService.updateSummoner(dto.gameName().replace(" ", ""), dto.tagLine());
        summonerPostService.save(summoner);

        User user = userGetService.getUser();
        if (user == null) {
            return;
        }
        user.setSummoner(summoner);
        userPostService.save(user);
    }

    public SummonerResponseDto getSummoner(String name) {
        User user = userGetService.getUser();
        SummonerRanking rankedSummoner;
        if (user != null) {
            rankedSummoner = summonerGetService.getSummonerWithRank(user.getSummoner().getPuuid());
        } else {
            String[] nameInfo = name.split("-");
            if (nameInfo.length != 2)
                throw new BSMGGException(ErrorCode.SUMMONER_NOT_FOUND);
            rankedSummoner = summonerGetService.getSummonerWithRank(nameInfo[0], nameInfo[1]);
        }
        List<String> mostChampions = summonerGetService.getMostChampions(rankedSummoner.summoner());
        return new SummonerResponseDto(rankedSummoner.user(), rankedSummoner.summoner(), mostChampions, rankedSummoner.ranking());
    }

    public SummonerRankingResponseDto getRanking(Integer page) {
        if (page == null) page = 0;
        List<SummonerRanking> summoners = summonerGetService.getSummonerRanking(page);
        List<SummonerResponseDto> list = new ArrayList<>();
        for (SummonerRanking s : summoners) {
            List<String> mostChampions = summonerGetService.getMostChampions(s.summoner());
            list.add(new SummonerResponseDto(s.user(), s.summoner(), mostChampions, s.ranking()));
        }
        return new SummonerRankingResponseDto(list, page);
    }
}
