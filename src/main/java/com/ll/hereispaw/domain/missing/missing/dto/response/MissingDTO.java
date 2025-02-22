package com.ll.hereispaw.domain.missing.missing.dto.response;

import com.ll.hereispaw.domain.missing.missing.entity.Missing;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MissingDTO {
    private String title;
    private String content;
    private String nickname;

    public MissingDTO(Missing missing) {
        this.nickname = missing.getAuthor().getNickname();
        this.title = missing.getTitle();
        this.content = missing.getContent();
    }
}
