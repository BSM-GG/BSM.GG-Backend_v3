package bsmgg.bsmgg_backend.domain.participant.service;

import bsmgg.bsmgg_backend.domain.participant.repository.ParticipantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ParticipantGetService {

    private final ParticipantRepository participantRepository;

    public List<String> getMostChampionsById(String puuid) {
        //return participantRepository.
        return new ArrayList<>();
    }
}
