package bsmgg.bsmgg_backend.domain.participant.service;

import bsmgg.bsmgg_backend.domain.match.domain.Match;
import bsmgg.bsmgg_backend.domain.participant.domain.Participant;
import bsmgg.bsmgg_backend.domain.participant.repository.ParticipantJdbcRepository;
import bsmgg.bsmgg_backend.domain.riot.dto.ParticipantDto;
import bsmgg.bsmgg_backend.domain.summoner.service.SummonerGetService;
import bsmgg.bsmgg_backend.domain.summoner.service.SummonerPostService;
import bsmgg.bsmgg_backend.global.mapping.MappingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ParticipantService {

    private final SummonerGetService summonerGetService;
    private final MappingService mappingService;
    private final ParticipantJdbcRepository participantJdbcRepository;
    private final SummonerPostService summonerPostService;

    public void saveAll(Match match, List<ParticipantDto> dtos) {
        List<String> puuids = new ArrayList<>();
        for (ParticipantDto dto : dtos) {
            puuids.add(dto.getPuuid());
        }
        Map<String, Boolean> existingPuuids = summonerGetService.getExistingSummoners(puuids);
        List<ParticipantDto> nonExistingSummonerParticipantDto = new ArrayList<>();
        int count = 0;
        for (ParticipantDto dto : dtos) {
            dto.setRoleCnt(count);
            if (!existingPuuids.getOrDefault(dto.getPuuid(), false)) {
                nonExistingSummonerParticipantDto.add(dto);
            }
            count++;
        }
        summonerPostService.saveFromParticipants(nonExistingSummonerParticipantDto);

        List<Participant> participants = new ArrayList<>();
        for (ParticipantDto dto : nonExistingSummonerParticipantDto) {
            participants.add(new Participant(dto, match, mappingService));
        }
        participantJdbcRepository.saveAll(participants);
    }
}
