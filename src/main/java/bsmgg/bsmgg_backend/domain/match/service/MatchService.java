package bsmgg.bsmgg_backend.domain.match.service;

import bsmgg.bsmgg_backend.domain.match.controller.dto.MatchInfoResponseDto;
import bsmgg.bsmgg_backend.domain.match.controller.dto.MatchResponseDto;
import bsmgg.bsmgg_backend.domain.match.domain.Match;
import bsmgg.bsmgg_backend.domain.match.repository.MatchJdbcRepository;
import bsmgg.bsmgg_backend.domain.match.repository.dto.MatchInfoDto;
import bsmgg.bsmgg_backend.domain.participant.dto.ParticipantResponseDto;
import bsmgg.bsmgg_backend.domain.participant.service.ParticipantGetService;
import bsmgg.bsmgg_backend.domain.participant.service.ParticipantService;
import bsmgg.bsmgg_backend.domain.riot.dto.MatchDto;
import bsmgg.bsmgg_backend.domain.riot.service.RiotApiService;
import bsmgg.bsmgg_backend.domain.summoner.controller.dto.SummonerRequestDto;
import bsmgg.bsmgg_backend.domain.summoner.domain.Summoner;
import bsmgg.bsmgg_backend.domain.summoner.service.SummonerGetService;
import bsmgg.bsmgg_backend.domain.summoner.service.SummonerPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final RiotApiService riotApiService;
    private final SummonerGetService summonerGetService;
    private final SummonerPostService summonerPostService;
    private final ParticipantService participantService;
    private final MatchGetService matchGetService;
    private final MatchPostService matchPostService;
    private final ParticipantGetService participantGetService;
    private final MatchJdbcRepository matchJdbcRepository;

    public void saveMatches(SummonerRequestDto dto) {
        Summoner summoner = summonerGetService.getSummonerByRiotName(dto.gameName(), dto.tagLine());

        while (true) {
            List<String> matchIds = matchGetService.getNonExistMatchIds(summoner);
            if(matchIds == null)
                break;

            for (String matchId : matchIds) {
                MatchDto match = riotApiService.getMatch(matchId);
                Match newMatch = matchPostService.save(matchId, match.info());
                participantService.saveAll(newMatch, match.info().participants());

                if (newMatch.getGameEndAt() >= summoner.getLastUpdated())
                    summoner.setLastUpdated(newMatch.getGameEndAt()+1);
            }
            summonerPostService.save(summoner);
        }
        summonerPostService.updateMostChampions(summoner);
    }

    public void updateMost(SummonerRequestDto dto) {
        Summoner summoner = summonerGetService.getSummonerByRiotName(dto.gameName(), dto.tagLine());
        summonerPostService.updateMostChampions(summoner);
    }

    public MatchResponseDto getMatches(String name, Integer page) {
        Summoner summoner = summonerGetService.getSummonerByRiotName(name);
        if (page == null) page = 0;
        List<MatchInfoDto> matches = matchJdbcRepository.findWithIsWin(summoner.getPuuid(), page*10);

        List<MatchInfoResponseDto> matchResponse = new ArrayList<>();
        for (MatchInfoDto match : matches) {
            List<ParticipantResponseDto> participants = participantGetService.getAllByMatches(match);
            matchResponse.add(new MatchInfoResponseDto(match, participants));
        }

        return new MatchResponseDto(matchResponse, page);
    }
}
