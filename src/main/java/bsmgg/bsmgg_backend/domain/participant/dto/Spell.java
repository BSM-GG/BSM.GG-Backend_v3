package bsmgg.bsmgg_backend.domain.participant.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
public class Spell {

    private String id;
    @Setter
    private String name;

    public Spell(String id) {
        this.id = id;
    }
}
