package bsmgg.bsmgg_backend.domain.match.domain;

import bsmgg.bsmgg_backend.domain.participant.domain.Participant;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "matches")
public class Match {

    @Id
    private String id;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "puuid")
    @JsonManagedReference
    List<Participant> participants;

    @Column
    private String gameType;
    @Column
    private Long gameStartedAt;
    @Column
    private Long gameEndAt;
    @Column
    private Long gameDuration;
}
