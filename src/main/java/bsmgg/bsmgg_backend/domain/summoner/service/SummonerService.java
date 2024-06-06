package bsmgg.bsmgg_backend.domain.summoner.service;

import bsmgg.bsmgg_backend.domain.riot.dto.LeagueEntryDto;
import bsmgg.bsmgg_backend.domain.riot.dto.RiotAccountDto;
import bsmgg.bsmgg_backend.domain.riot.service.RiotApiService;
import bsmgg.bsmgg_backend.domain.riot.dto.SummonerDto;
import bsmgg.bsmgg_backend.domain.summoner.controller.dto.SummonerRequestDto;
import bsmgg.bsmgg_backend.domain.summoner.domain.Summoner;
import bsmgg.bsmgg_backend.domain.summoner.repository.SummonerRepository;
import bsmgg.bsmgg_backend.domain.user.domain.User;
import bsmgg.bsmgg_backend.domain.user.service.UserGetService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SummonerService {

    private final RiotApiService riotApiService;
    private final UserGetService userGetService;
    private final SummonerRepository summonerRepository;

    @Value("${riot.season-started-time}")
    private Long seasonStartedTime;

    public void assign(SummonerRequestDto dto) {
        RiotAccountDto account = riotApiService.getAccount(dto.gameName(), dto.tagLine());
        saveSummoner(account);

        User user = userGetService.getUser();
        System.out.println(user);

    }

    public void saveSummoner(RiotAccountDto account) {
        SummonerDto summoner = riotApiService.getSummoner(account.puuid());
        LeagueEntryDto rank = riotApiService.getRank(summoner.id());
        summonerRepository.findById(account.puuid()).ifPresentOrElse(
                s -> s.updateAccount(account, summoner),
                () -> summonerRepository.save(Summoner.builder()
                        .puuid(account.puuid())
                        .gameName(account.gameName())
                        .tagLine(account.tagLine())
                        .riotId(summoner.id())
                        .profileIcon(summoner.profileIconId())
                        .revisionDate(summoner.revisionDate())
                        .level(summoner.summonerLevel())
                        .last_updated(seasonStartedTime)
                        .build())
        );
        System.out.println(summoner);
        System.out.println(rank);
    }
}
