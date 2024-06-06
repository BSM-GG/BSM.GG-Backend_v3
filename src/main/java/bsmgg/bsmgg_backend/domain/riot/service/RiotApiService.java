package bsmgg.bsmgg_backend.domain.riot.service;

import bsmgg.bsmgg_backend.domain.riot.dto.MatchDto;
import bsmgg.bsmgg_backend.domain.riot.dto.RiotAccountDto;
import bsmgg.bsmgg_backend.domain.riot.dto.LeagueEntryDto;
import bsmgg.bsmgg_backend.domain.riot.dto.SummonerDto;
import bsmgg.bsmgg_backend.global.error.exception.BSMGGException;
import bsmgg.bsmgg_backend.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

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
        return throwRequest(
                String.format("%s/account/v1/accounts/by-puuid/%s?api_key=%s", riotAsiaUrl, puuid, apiKey),
                RiotAccountDto.class
        );
    }

    public RiotAccountDto getAccount(String gameName, String tagLine) {
        return throwRequest(
                String.format("%s/account/v1/accounts/by-riot-id/%s/%s?api_key=%s", riotAsiaUrl, gameName, tagLine, apiKey),
                RiotAccountDto.class
        );
    }

    public SummonerDto getSummoner(String puuid) {
        return throwRequest(
                String.format("%s/summoner/v4/summoners/by-puuid/%s?api_key=%s", riotKrUrl, puuid, apiKey),
                SummonerDto.class
        );
    }

    public LeagueEntryDto getRank(String riotId) {
        return throwRequest(
                String.format("%s/league/v4/entries/by-summoner/%s?api_key=%s", riotKrUrl, riotId, apiKey),
                LeagueEntryDto.class
        );
    }

    public MatchDto getMatch(String matchId) {
        return throwRequest(
                String.format("%s/match/v5/matches/%s?api_key=%s", riotAsiaUrl, matchId, apiKey),
                MatchDto.class
        );
    }

    public List<String> getMatches(String puuid, Long startTime) {
        return Arrays.asList(throwRequest(
                String.format("%s/match/v5/matches/by-puuid/%s/ids?startTime=%d&start=0&count=100&api_key=%s"
                        , riotAsiaUrl, puuid, startTime, apiKey),
                String[].class
        ));
    }

    public <T> T throwRequest(String url, Class<T> responseType) {
        try {
            T account = restTemplate.getForObject(url, responseType);
            System.out.println(account);
            return account;
        } catch (HttpClientErrorException.Forbidden e) {
            throw new BSMGGException(ErrorCode.INVALID_OR_EXPIRED_RIOT_TOKEN);
        } catch (HttpClientErrorException.NotFound e) {
            throw new BSMGGException(ErrorCode.SUMMONER_NOT_FOUND);
        } catch(RestClientException e) {
            throw new BSMGGException(ErrorCode.INTERNAL_SERVER_ERROR);
        } catch(Exception e) {
            e.printStackTrace();
            throw new BSMGGException(ErrorCode.I_AM_TEAPOT);
        }
    }
}
