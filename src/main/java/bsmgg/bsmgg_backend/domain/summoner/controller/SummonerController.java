package bsmgg.bsmgg_backend.domain.summoner.controller;

import bsmgg.bsmgg_backend.domain.summoner.controller.dto.SummonerRequestDto;
import bsmgg.bsmgg_backend.domain.summoner.service.SummonerService;
import lombok.RequiredArgsConstructor;
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
        summonerService.saveSummoner(dto);
    }
}
