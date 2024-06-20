package bsmgg.bsmgg_backend.domain.participant.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
public class Item {

    private Integer id;
    @Setter
    private String name;

    public Item(Integer id) {
        this.id = id;
    }
}
