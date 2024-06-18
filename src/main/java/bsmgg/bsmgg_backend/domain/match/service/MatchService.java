package bsmgg.bsmgg_backend.domain.match.service;

import bsmgg.bsmgg_backend.domain.match.controller.dto.MatchInfoResponseDto;
import bsmgg.bsmgg_backend.domain.match.controller.dto.MatchResponseDto;
import bsmgg.bsmgg_backend.domain.match.domain.Match;
import bsmgg.bsmgg_backend.domain.match.repository.MatchRepository;
import bsmgg.bsmgg_backend.domain.participant.dto.ParticipantResponseDto;
import bsmgg.bsmgg_backend.domain.participant.service.ParticipantGetService;
import bsmgg.bsmgg_backend.domain.participant.service.ParticipantService;
import bsmgg.bsmgg_backend.domain.riot.dto.MatchDto;
import bsmgg.bsmgg_backend.domain.riot.dto.ParticipantDto;
import bsmgg.bsmgg_backend.domain.riot.service.RiotApiService;
import bsmgg.bsmgg_backend.domain.summoner.controller.dto.SummonerRequestDto;
import bsmgg.bsmgg_backend.domain.summoner.domain.Summoner;
import bsmgg.bsmgg_backend.domain.summoner.service.SummonerGetService;
import bsmgg.bsmgg_backend.domain.summoner.service.SummonerPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    private final ParticipantGetService participantGetService;

    public void saveMatches(SummonerRequestDto dto) {
        Summoner summoner = summonerGetService.getSummonerByRiotName(dto.gameName(), dto.tagLine());

        List<String> matchIds;
        while (true) {
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

                if (newMatch.getGameEndAt() != null)
                    summoner.setLastUpdated(newMatch.getGameEndAt());
            }
            if (matchIds.size() < 100)
                break;
        }
        summonerPostService.updateMostChampions(summoner, summoner.getLastUpdated());
    }

    public MatchResponseDto getMatches(String name, Integer page) {
        Summoner summoner = summonerGetService.getSummonerByRiotName(name);
        if (page == null) page = 0;
        List<Match> matches = matchRepository.findAllBySummoner
                (summoner.getPuuid(), PageRequest.of(page, 10, Sort.by("game_end_at").descending()));
        List<Boolean> isWins = matchRepository.findIsWinBySummoner
                (summoner.getPuuid(), PageRequest.of(page, 10, Sort.by("game_end_at").descending()));

        List<MatchInfoResponseDto> matchResponse = new ArrayList<>();
        for (int i = 0; i < matches.size(); i++) {
            List<ParticipantResponseDto> participants = participantGetService.getAllByMatches(matches.get(i));
            matchResponse.add(new MatchInfoResponseDto(matches.get(i), isWins.get(i), participants));
        }

        return new MatchResponseDto(matchResponse, page);
    }
}
