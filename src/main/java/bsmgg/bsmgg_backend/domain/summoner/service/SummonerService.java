package bsmgg.bsmgg_backend.domain.summoner.service;

import bsmgg.bsmgg_backend.domain.riot.dto.LeagueEntryDTO;
import bsmgg.bsmgg_backend.domain.riot.dto.RiotAccountDto;
import bsmgg.bsmgg_backend.domain.riot.dto.SummonerDto;
import bsmgg.bsmgg_backend.domain.riot.service.RiotApiService;
import bsmgg.bsmgg_backend.domain.summoner.controller.dto.SummonerRequestDto;
import bsmgg.bsmgg_backend.domain.summoner.domain.Summoner;
import bsmgg.bsmgg_backend.domain.summoner.repository.SummonerRepository;
import bsmgg.bsmgg_backend.domain.user.domain.User;
import bsmgg.bsmgg_backend.domain.user.service.UserGetService;
import bsmgg.bsmgg_backend.domain.user.service.UserPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SummonerService {

    private final RiotApiService riotApiService;
    private final UserGetService userGetService;
    private final UserPostService userPostService;
    private final SummonerRepository summonerRepository;

    @Value("${riot.season-started-time}")
    private Long seasonStartedTime;

    public void assign(SummonerRequestDto dto) {
        RiotAccountDto account = riotApiService.getAccount(dto.gameName(), dto.tagLine());
        Summoner summoner = saveSummoner(account);

        User user = userGetService.getUser();
        if(user == null){
            System.out.println("히히 빔");
            return;
        }
        user.setSummoner(summoner);
        userPostService.save(user);
    }

    public Summoner saveSummoner(RiotAccountDto account) {
        SummonerDto riotSummoner = riotApiService.getSummoner(account.puuid());

        Optional<Summoner> existingSummoner = summonerRepository.findById(account.puuid());
        Summoner summoner;
        if (existingSummoner.isPresent()) {
            summoner = existingSummoner.get();
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

        LeagueEntryDTO[] rank = riotApiService.getRank(riotSummoner.id());
        LeagueEntryDTO solo = Arrays.stream(rank)
                .filter(entry -> "RANKED_SOLO_5x5".equals(entry.queueType()))
                .findFirst().orElse(null);
        LeagueEntryDTO flex = Arrays.stream(rank)
                .filter(entry -> "RANKED_FLEX_SR".equals(entry.queueType()))
                .findFirst().orElse(null);
        summoner.updateRank(solo, flex);
        return summonerRepository.save(summoner);
    }
}
