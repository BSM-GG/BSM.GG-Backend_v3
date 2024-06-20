package bsmgg.bsmgg_backend.domain.participant.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Perk {

    private String id;
    private String name;

    public Perk(String name) {
        this.name = name;
    }
}
