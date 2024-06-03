package bsmgg.bsmgg_backend.domain.riot.service;

import bsmgg.bsmgg_backend.domain.riot.dto.RiotAccountDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class RiotApiService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${riot.asia-url}")
    private String riotAsiaUrl;
    @Value("${riot.kr-url}")
    private String riotKrUrl;
    @Value("${riot.lol-url}")
    private String riotLolUrl;
    @Value("${riot.api-key}")
    private String apiKey;
    @Value("${riot.season-started-time}")
    private String seasonStartedTime;


    public RiotAccountDto getAccount(String puuid) {
        return restTemplate.getForObject(
                String.format("%s/account/v1/accounts/by-puuid/%s?api_key=%s", riotAsiaUrl, puuid, apiKey)
                , RiotAccountDto.class
        );
    }

    public RiotAccountDto getAccount(String gameName, String tagLine) {
        return restTemplate.getForObject(
                String.format("%s/account/v1/accounts/by-riot-id/%s/%s?api_key=%s", riotAsiaUrl, gameName, tagLine, apiKey)
                , RiotAccountDto.class
        );

    }
}
