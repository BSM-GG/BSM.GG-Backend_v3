package bsmgg.bsmgg_backend.domain.summoner.controller;

import bsmgg.bsmgg_backend.domain.summoner.controller.dto.ChangResponseDto;
import bsmgg.bsmgg_backend.domain.summoner.controller.dto.SummonerRankingResponseDto;
import bsmgg.bsmgg_backend.domain.summoner.controller.dto.SummonerRequestDto;
import bsmgg.bsmgg_backend.domain.summoner.controller.dto.SummonerResponseDto;
import bsmgg.bsmgg_backend.domain.summoner.service.SummonerService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/summoner")
public class SummonerController {

    private final SummonerService summonerService;

    @PostMapping("")
    public void assign(@RequestBody SummonerRequestDto dto) {
        summonerService.assign(dto);
    }

    @QueryMapping
    public SummonerResponseDto getSummoner(@Argument String name) {
        return summonerService.getSummoner(name);
    }

    @QueryMapping
    public SummonerRankingResponseDto getRanking(@Argument Integer page) {
        return summonerService.getRanking(page);
    }

    @QueryMapping
    public ChangResponseDto getChang() {
        return summonerService.getChang();
    }
}
