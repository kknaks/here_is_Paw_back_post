package com.ll.hereispaw.global.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;

public class AppConfig {
    @Getter
    private static String siteFrontUrl;

    @Getter
    private static String devFrontUrl;

    @Value("${custom.site.frontUrl}")
    public void setSiteFrontUrl(String siteFrontUrl) {
        AppConfig.siteFrontUrl = siteFrontUrl;
    }

    @Value("${custom.dev.frontUrl}")
    public void setDevFrontUrl(String devFrontUrl) {
        AppConfig.devFrontUrl = devFrontUrl;
    }
    public static boolean isNotProd() {
        return true;
    }
}
