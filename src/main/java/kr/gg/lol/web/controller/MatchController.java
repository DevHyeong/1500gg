package kr.gg.lol.web.controller;

import kr.gg.lol.domain.match.service.MatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
public class MatchController {

    private final MatchService matchService;

    @GetMapping("/matches/{puuId}")
    public ResponseEntity summonerByName(@PathVariable String puuId){
        return matchService.getMatchesByPuuid(puuId);
    }

    @GetMapping("/match/{matchId}")
    public ResponseEntity leagueById(@PathVariable String matchId){
        return matchService.getMatchByMatchId(matchId);
    }

}
