package kr.gg.lol.domain.match.dto;

import lombok.Getter;
import java.util.ArrayList;
import java.util.List;

@Getter
public class StylesDto {
    protected String description;
    protected List<SelectionsDto> selections = new ArrayList<>();
    protected int style;

}
