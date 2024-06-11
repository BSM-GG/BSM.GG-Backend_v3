package bsmgg.bsmgg_backend.domain.participant.repository;

import bsmgg.bsmgg_backend.domain.participant.domain.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, String> {
}
