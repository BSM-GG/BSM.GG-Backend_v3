package bsmgg.bsmgg_backend.domain.summoner.service;

import bsmgg.bsmgg_backend.domain.riot.dto.RiotAccountDto;
import bsmgg.bsmgg_backend.domain.riot.service.RiotApiService;
import bsmgg.bsmgg_backend.domain.summoner.controller.dto.SummonerRequestDto;
import bsmgg.bsmgg_backend.domain.summoner.repository.SummonerRepository;
import bsmgg.bsmgg_backend.global.error.exception.BSMGGException;
import bsmgg.bsmgg_backend.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException.*;

@Service
@RequiredArgsConstructor
public class SummonerService {

    private final SummonerRepository summonerRepository;
    private final RiotApiService riotApiService;

    public void saveSummoner(SummonerRequestDto dto) {
        try {
            RiotAccountDto account = riotApiService.getAccount(dto.gameName(), dto.tagLine());
            System.out.println(account);
        } catch (Forbidden e) {
            throw new BSMGGException(ErrorCode.INVALID_OR_EXPIRED_RIOT_TOKEN);
        } catch (NotFound e) {
            throw new BSMGGException(ErrorCode.SUMMONER_NOT_FOUND);
        } catch (Exception e) {
            throw new BSMGGException(ErrorCode.BAD_REQUEST_ERROR);
        }



    }
}
