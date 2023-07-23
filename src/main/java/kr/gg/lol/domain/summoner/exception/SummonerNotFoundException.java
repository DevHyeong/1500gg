package kr.gg.lol.domain.summoner.exception;

public class SummonerNotFoundException extends RuntimeException{
    private static final String message = "전적갱신할 소환사 정보가 없습니다.";
    public SummonerNotFoundException(){
        super(message);
    }
}
