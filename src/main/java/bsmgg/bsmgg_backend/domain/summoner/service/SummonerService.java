package bsmgg.bsmgg_backend.domain.summoner.service;

import bsmgg.bsmgg_backend.domain.riot.dto.RiotAccountDto;
import bsmgg.bsmgg_backend.domain.riot.service.RiotApiService;
import bsmgg.bsmgg_backend.domain.riot.dto.SummonerDTO;
import bsmgg.bsmgg_backend.domain.summoner.controller.dto.SummonerRequestDto;
import bsmgg.bsmgg_backend.domain.summoner.domain.Summoner;
import bsmgg.bsmgg_backend.domain.summoner.repository.SummonerRepository;
import bsmgg.bsmgg_backend.domain.user.service.UserGetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SummonerService {

    private final RiotApiService riotApiService;
    private final UserGetService userGetService;
    private final SummonerRepository summonerRepository;

    public void assign(SummonerRequestDto dto) {
        RiotAccountDto account = riotApiService.getAccount(dto.gameName(), dto.tagLine());
        String riotId = saveSummoner(account);
        User user = userGetService.getUser();

    }

    public String saveSummoner(RiotAccountDto dto) {
        SummonerDTO summoner = riotApiService.getSummoner(account.puuid());
        Summoner newSummoner = Summoner.builder()

                .build();
        summonerRepository.save()
    }
}
