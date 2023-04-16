package kr.gg.lol.domain.match.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class RequestDto {
    private List<String> ids = new ArrayList<>();

}
