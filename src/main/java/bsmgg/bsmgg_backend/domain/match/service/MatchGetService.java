package bsmgg.bsmgg_backend.domain.match.service;

import bsmgg.bsmgg_backend.domain.match.repository.MatchRepository;
import bsmgg.bsmgg_backend.domain.riot.service.RiotApiService;
import bsmgg.bsmgg_backend.domain.summoner.domain.Summoner;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchGetService {

    private final RiotApiService riotApiService;
    private final MatchRepository matchRepository;

    public List<String> getNonExistMatchIds(Summoner summoner) {
        List<String> matchIds = riotApiService.getMatches(summoner.getPuuid(), summoner.getLastUpdated());

        List<String> existingMatchIds = matchRepository.findIdsInIds(matchIds);
        matchIds.removeAll(existingMatchIds);
        long nowTime = System.currentTimeMillis() / 1000;
        long endTime = summoner.getLastUpdated() + 1296000;
        if (matchIds.isEmpty() && nowTime > endTime) {
            summoner.setLastUpdated(endTime);
        } else if(matchIds.isEmpty()) {
            return null;
        }
        Collections.reverse(matchIds);
        return matchIds;
    }
}
