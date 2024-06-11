package bsmgg.bsmgg_backend.domain.summoner.service;

import bsmgg.bsmgg_backend.domain.riot.dto.ParticipantDto;
import bsmgg.bsmgg_backend.domain.summoner.domain.Summoner;
import bsmgg.bsmgg_backend.domain.summoner.repository.SummonerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SummonerGetService {

    private final SummonerRepository summonerRepository;

    @Value("${riot.season-started-time}")
    private Long seasonStartedTime;

    public Summoner getSummonerById(String puuid) {
        return summonerRepository.findByPuuid(puuid);
    }

    public Optional<Summoner> getSummonerByRiotName(String gameName, String tagLine) {
        return summonerRepository.findByGameNameAndTagLine(gameName, tagLine);
    }

    public Summoner getSummonerByParticipant(ParticipantDto dto) {
        Summoner summoner = summonerRepository.findByPuuid(dto.puuid());
        if (summoner == null) {
            summoner = summonerRepository.save(Summoner.builder()
                    .puuid(dto.puuid())
                    .riotId(dto.summonerId())
                    .gameName(dto.riotIdGameName())
                    .tagLine(dto.riotIdTagline())
                    .level(0L)
                    .profileIcon(dto.profileIcon())
                    .lastUpdated(seasonStartedTime)
                    .build()
            );
        }
        return summoner;
    }
}
