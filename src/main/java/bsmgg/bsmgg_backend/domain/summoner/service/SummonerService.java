package bsmgg.bsmgg_backend.domain.summoner.service;

import bsmgg.bsmgg_backend.domain.riot.dto.RiotAccountDto;
import bsmgg.bsmgg_backend.domain.riot.service.RiotApiService;
import bsmgg.bsmgg_backend.domain.summoner.controller.dto.SummonerRequestDto;
import bsmgg.bsmgg_backend.domain.summoner.domain.Summoner;
import bsmgg.bsmgg_backend.domain.user.domain.User;
import bsmgg.bsmgg_backend.domain.user.service.UserGetService;
import bsmgg.bsmgg_backend.domain.user.service.UserPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SummonerService {

    private final RiotApiService riotApiService;
    private final SummonerPostService summonerPostService;
    private final UserGetService userGetService;
    private final UserPostService userPostService;

    public void assign(SummonerRequestDto dto) {
        Summoner summoner = summonerPostService.saveSummoner(dto.gameName(), dto.tagLine());

        User user = userGetService.getUser();
        if(user == null){
            return;
        }
        user.setSummoner(summoner);
        userPostService.save(user);
    }
}
