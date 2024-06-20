package bsmgg.bsmgg_backend.domain.participant.service;

import bsmgg.bsmgg_backend.domain.match.repository.dto.MatchInfoDto;
import bsmgg.bsmgg_backend.domain.participant.dto.ChangInfoDto;
import bsmgg.bsmgg_backend.domain.participant.dto.ParticipantResponseDto;
import bsmgg.bsmgg_backend.domain.participant.repository.ParticipantJdbcRepository;
import bsmgg.bsmgg_backend.domain.participant.repository.ParticipantRepository;
import bsmgg.bsmgg_backend.global.mapping.MappingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParticipantGetService {

    private final ParticipantRepository participantRepository;
    private final ParticipantJdbcRepository participantJdbcRepository;
    private final MappingService mappingService;

    public List<String> getMostChampions(String puuid) {
        return participantRepository.findMostChampionsByPuuid(puuid);
    }

    public List<ParticipantResponseDto> getAllByMatches(MatchInfoDto match) {
        List<ParticipantResponseDto> participants = participantJdbcRepository.findAllByMatchIn(match);
        for (ParticipantResponseDto participant : participants) {
            participant.update(mappingService);
        }
        return participants;
    }

    public ChangInfoDto getChangByPuuid(String puuid, long prevWeekStart, long prevWeekEnd) {
        return participantJdbcRepository.findChangByPuuid(puuid, prevWeekStart, prevWeekEnd);
    }
}
