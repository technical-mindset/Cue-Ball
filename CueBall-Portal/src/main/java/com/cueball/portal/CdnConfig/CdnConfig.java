package com.cueball.portal.CdnConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class CdnConfig {

    private final Environment environment;

    @Autowired
    public CdnConfig(Environment environment) {
        this.environment = environment;
    }

    public String getCdnBasePath() {
        return environment.getProperty("cdn.base.path");
    }

}
