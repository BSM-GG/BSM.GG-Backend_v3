package bsmgg.bsmgg_backend.domain.match.controller;

import bsmgg.bsmgg_backend.domain.match.service.MatchService;
import bsmgg.bsmgg_backend.domain.summoner.controller.dto.SummonerRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/match")
public class MatchController {

    private final MatchService matchService;

    @PostMapping
    public void updateRecord(@RequestBody SummonerRequestDto dto) {
        matchService.saveMatches(dto);
    }
}
