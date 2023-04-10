package kr.gg.lol.domain.match.dto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class RequestDto {
    private List<String> ids = new ArrayList<>();

}
