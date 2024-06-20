package bsmgg.bsmgg_backend.domain.participant.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
public class Champion {

    private final String id;
    @Setter
    private String name;

    public Champion(String id) {
        this.id = id;
    }
}
