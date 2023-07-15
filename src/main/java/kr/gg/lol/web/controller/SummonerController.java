package kr.gg.lol.web.controller;

import kr.gg.lol.common.util.ApiUtils;
import kr.gg.lol.domain.summoner.dto.LeagueDto;
import kr.gg.lol.domain.summoner.dto.SummonerDto;
import kr.gg.lol.domain.summoner.service.SummonerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static kr.gg.lol.common.util.ApiUtils.success;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
public class SummonerController {

    private final SummonerService summonerService;

    @GetMapping("/summoner/{name}")
    public ApiUtils.ApiResult<SummonerDto> summonerByName(@PathVariable String name){
        return success(summonerService.getSummonerByName(name));
    }

    @GetMapping("/league/{id}")
    public ApiUtils.ApiResult<List<LeagueDto>> leagueById(@PathVariable String id){
        return success(summonerService.getLeagueById(id));
    }

}
