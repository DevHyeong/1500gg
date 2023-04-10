package kr.gg.lol.web.controller;

import kr.gg.lol.domain.summoner.service.SummonerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
public class SummonerController {

    private final SummonerService summonerService;

    @GetMapping("/summoner/{name}")
    public ResponseEntity summonerByName(@PathVariable String name){
        return summonerService.getSummonerByName(name);
    }

    @GetMapping("/league/{id}")
    public ResponseEntity leagueById(@PathVariable String id){
        return summonerService.getLeagueById(id);
    }


}
