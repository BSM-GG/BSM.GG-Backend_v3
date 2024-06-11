package bsmgg.bsmgg_backend.domain.match.service;

import bsmgg.bsmgg_backend.domain.match.domain.Match;
import bsmgg.bsmgg_backend.domain.match.repository.MatchRepository;
import bsmgg.bsmgg_backend.global.mapping.MappingService;
import bsmgg.bsmgg_backend.domain.riot.dto.InfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MatchPostService {

    private final MatchRepository matchRepository;
    private final MappingService mappingService;

    public Match save(String matchId, InfoDto info) {
        return matchRepository.save(Match.builder()
                .id(matchId)
                .gameType(mappingService.getQueue(info.queueId()))
                .gameStartedAt(info.gameStartTimestamp()/1000)
                .gameDuration(info.gameDuration())
                .gameEndAt(info.gameEndTimestamp()/1000)
                .build()
        );
    }
}
