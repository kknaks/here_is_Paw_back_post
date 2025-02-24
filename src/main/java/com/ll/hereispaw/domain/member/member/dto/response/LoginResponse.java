package com.ll.hereispaw.domain.member.member.dto.response;

import lombok.NonNull;

public record LoginResponse(
        @NonNull MemberInfoDto item,
        @NonNull String apiKey,
        @NonNull String accessToken
) {
}
