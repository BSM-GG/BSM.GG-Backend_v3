package bsmgg.bsmgg_backend.domain.match.service;

import bsmgg.bsmgg_backend.domain.match.domain.Match;
import bsmgg.bsmgg_backend.domain.match.repository.MatchRepository;
import bsmgg.bsmgg_backend.domain.participant.service.ParticipantService;
import bsmgg.bsmgg_backend.domain.riot.dto.MatchDto;
import bsmgg.bsmgg_backend.domain.riot.dto.ParticipantDto;
import bsmgg.bsmgg_backend.domain.riot.service.RiotApiService;
import bsmgg.bsmgg_backend.domain.summoner.controller.dto.SummonerRequestDto;
import bsmgg.bsmgg_backend.domain.summoner.domain.Summoner;
import bsmgg.bsmgg_backend.domain.summoner.service.SummonerGetService;
import bsmgg.bsmgg_backend.domain.summoner.service.SummonerPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final RiotApiService riotApiService;
    private final SummonerGetService summonerGetService;
    private final SummonerPostService summonerPostService;
    private final ParticipantService participantService;
    private final MatchPostService matchPostService;
    private final MatchRepository matchRepository;

    public void saveMatches(SummonerRequestDto dto) {
        Summoner summoner = summonerGetService.getSummonerByRiotName(dto.gameName(), dto.tagLine());

        List<String> matchIds;
        while(true) {
            matchIds = riotApiService.getMatches(summoner.getPuuid(), summoner.getLastUpdated());
            if (matchIds.isEmpty())
                break;

            for (String matchId : matchIds) {
                Optional<Match> existingMatch = matchRepository.findById(matchId);
                if (existingMatch.isPresent())
                    continue;

                MatchDto match = riotApiService.getMatch(matchId);
                Match newMatch = matchPostService.save(matchId, match.info());

                List<ParticipantDto> participants = match.info().participants();
                participantService.saveAll(newMatch, participants);

                if(newMatch.getGameEndAt() != null)
                    summoner.setLastUpdated(newMatch.getGameEndAt());
            }
            if (matchIds.size() < 100)
                break;
        }
        summonerPostService.updateMostChampions(summoner, summoner.getLastUpdated());
    }
}
