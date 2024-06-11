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
import bsmgg.bsmgg_backend.global.error.exception.BSMGGException;
import bsmgg.bsmgg_backend.global.error.exception.ErrorCode;
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
        Summoner summoner = summonerGetService.getSummonerByRiotName(dto.gameName(), dto.tagLine())
                .orElseThrow(() -> new BSMGGException(ErrorCode.SUMMONER_NOT_FOUND));

        List<String> matchIds;
        while(true) {
            matchIds = riotApiService.getMatches(summoner.getPuuid(), summoner.getLastUpdated());
            if (matchIds.isEmpty())
                break;

            long lastMatchTime = 0L;
            for (String matchId : matchIds) {
                Optional<Match> existingMatch = matchRepository.findById(matchId);
                if (existingMatch.isPresent())
                    continue;

                MatchDto match = riotApiService.getMatch(matchId);
                Match newMatch = matchPostService.save(matchId, match.info());

                List<ParticipantDto> participants = match.info().participants();
                participantService.saveAll(newMatch, participants);

                if(newMatch.getGameEndAt() != null)
                    lastMatchTime = newMatch.getGameEndAt();
            }
            summoner.setLastUpdated(lastMatchTime);
            summonerPostService.save(summoner);
            if (matchIds.size() < 100)
                break;
        }
        summonerPostService.saveSummoner(dto.gameName(), dto.tagLine());
    }
}
