package kr.gg.lol.domain.match.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StylesDto {

    private String description;
    private List<SelectionsDto> selections = new ArrayList<>();
    private int style;
}
