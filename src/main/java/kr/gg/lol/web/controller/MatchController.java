package kr.gg.lol.web.controller;

import kr.gg.lol.domain.match.dto.RequestDto;
import kr.gg.lol.domain.match.service.MatchService;
import kr.gg.lol.web.usecase.MatchUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
public class MatchController {

    private final MatchService matchService;
    private final MatchUpdateService matchUpdateService;

    @GetMapping("/matches/{puuId}")
    public ResponseEntity matchesByPuuId(@PathVariable String puuId){
        return matchService.getMatchesByPuuid(puuId);
    }

    @GetMapping("/match/{matchId}")
    public ResponseEntity leagueById(@PathVariable String matchId){
        return matchService.getMatchesByIds(matchId);
    }

    @PostMapping("/v1/matches")
    public ResponseEntity matches(@RequestBody RequestDto param){
        return matchService.getMatchesByIds(param.getIds().toArray(new String[param.getIds().size()]));
    }

    @PostMapping("/matches/renewal")
    public ResponseEntity updateMatches(@RequestBody String name){
        matchUpdateService.updateMatches(name);
        return ResponseEntity.ok()
                .build();
    }

}
