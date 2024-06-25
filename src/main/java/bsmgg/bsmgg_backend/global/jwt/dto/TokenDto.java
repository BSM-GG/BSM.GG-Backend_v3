package bsmgg.bsmgg_backend.global.jwt.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class TokenDto {
    private String accessToken;
    //private String refreshToken;
    private String gameName;
    private String tagLine;
}
