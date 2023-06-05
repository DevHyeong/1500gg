package kr.gg.lol.domain.post.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostPageRequest {
    private Long id;
    private int size;

    public boolean hasNotId(){
        return id == null;
    }

}
