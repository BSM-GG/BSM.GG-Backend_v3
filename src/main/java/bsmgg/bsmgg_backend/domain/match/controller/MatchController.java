package bsmgg.bsmgg_backend.domain.match.controller;

import bsmgg.bsmgg_backend.domain.match.controller.dto.MatchResponseDto;
import bsmgg.bsmgg_backend.domain.match.service.MatchService;
import bsmgg.bsmgg_backend.domain.summoner.controller.dto.SummonerRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
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

    @PostMapping("/most")
    public void most(@RequestBody SummonerRequestDto dto) {
        matchService.updateMost(dto);
    }

    @QueryMapping
    public MatchResponseDto getMatches(@Argument String name, @Argument Integer page) {
        return matchService.getMatches(name, page);
    }
}
