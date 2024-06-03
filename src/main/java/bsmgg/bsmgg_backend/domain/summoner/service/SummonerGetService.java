package bsmgg.bsmgg_backend.domain.summoner.service;

import bsmgg.bsmgg_backend.domain.summoner.domain.Summoner;
import bsmgg.bsmgg_backend.domain.summoner.repository.SummonerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SummonerGetService {

    private final SummonerRepository summonerRepository;

    public Summoner getSummonerByPuuid(String puuid) {
        return summonerRepository.findByPuuid(puuid);
    }
}
