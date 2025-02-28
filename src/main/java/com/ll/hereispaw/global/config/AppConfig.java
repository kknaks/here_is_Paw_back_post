package com.ll.hereispaw.global.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;

public class AppConfig {
    @Getter
    private static String siteFrontUrl;

    @Value("${custom.site.frontUrl}")
    public void setSiteFrontUrl(String siteFrontUrl) {
        AppConfig.siteFrontUrl = siteFrontUrl;
    }
}
