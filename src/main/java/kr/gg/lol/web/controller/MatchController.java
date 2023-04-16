package kr.gg.lol.web.controller;

import kr.gg.lol.domain.match.dto.MatchDto;
import kr.gg.lol.domain.match.dto.RequestDto;
import kr.gg.lol.domain.match.service.MatchService;
import kr.gg.lol.domain.summoner.dto.SummonerDto;
import kr.gg.lol.web.usecase.MatchUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static kr.gg.lol.common.util.ApiUtils.success;
import static kr.gg.lol.common.util.ApiUtils.ApiResult;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
public class MatchController {

    private final MatchService matchService;
    private final MatchUpdateService matchUpdateService;

    @GetMapping("/matches/{puuId}")
    public ApiResult<List<String>> matchesByPuuId(@PathVariable String puuId){
        return success(matchService.getMatchesByPuuid(puuId, false));
    }

    @GetMapping("/match/{matchId}")
    public ApiResult<List<MatchDto>> leagueById(@PathVariable String matchId){
        return success(matchService.getMatchesByIds(matchId));
    }

    @PostMapping("/v1/matches")
    public ApiResult<List<MatchDto>> matches(@RequestBody RequestDto param){
        return success(matchService.getMatchesByIds(param
                .getIds()
                .toArray(new String[param.getIds().size()])));
    }

    @PostMapping("/matches/renewal")
    public ApiResult<Boolean> updateMatches(@RequestBody SummonerDto summonerDto){
        matchUpdateService.updateMatches(summonerDto.getName());
        return success(true);
    }

}
