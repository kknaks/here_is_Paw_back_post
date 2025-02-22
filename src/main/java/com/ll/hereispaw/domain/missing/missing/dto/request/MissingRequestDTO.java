package com.ll.hereispaw.domain.missing.missing.dto.request;

import com.ll.hereispaw.domain.missing.Auhtor.entity.Author;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MissingRequestDTO {

    private Author author;

    @NotBlank(message = "title은 필수입력입니다.")
    private String title;
    @NotBlank(message = "content는 필수입력입니다.")
    private String content;


}
