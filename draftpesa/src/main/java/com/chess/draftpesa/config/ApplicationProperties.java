package com.chess.draftpesa.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
@Component
public class ApplicationProperties {
    private String jwtSecret;
    private long jwtValidityInSeconds;
    private String userNotFoundMsg;
    private String userExistsMsg;
    private String tokenPrefix;

    public String getJwtSecret() {
        return jwtSecret;
    }

    public void setJwtSecret(String jwtSecret) {
        this.jwtSecret = jwtSecret;
    }

    public long getJwtValidityInSeconds() {
        return jwtValidityInSeconds;
    }

    public void setJwtValidityInSeconds(long jwtValidityInSeconds) {
        this.jwtValidityInSeconds = jwtValidityInSeconds;
    }

    public String getUserNotFoundMsg() {
        return userNotFoundMsg;
    }

    public void setUserNotFoundMsg(String userNotFoundMsg) {
        this.userNotFoundMsg = userNotFoundMsg;
    }

    public String getUserExistsMsg() {
        return userExistsMsg;
    }

    public void setUserExistsMsg(String userExistsMsg) {
        this.userExistsMsg = userExistsMsg;
    }

    public String getTokenPrefix() {
        return tokenPrefix;
    }

    public void setTokenPrefix(String tokenPrefix) {
        this.tokenPrefix = tokenPrefix;
    }

    @Override
    public String toString() {
        return "ApplicationProperties{" +
                "jwtSecret='" + jwtSecret + '\'' +
                ", jwtValidityInSeconds='" + jwtValidityInSeconds + '\'' +
                ", userNotFoundMsg='" + userNotFoundMsg + '\'' +
                ", userExistsMsg='" + userExistsMsg + '\'' +
                ", tokenPrefix='" + tokenPrefix + '\'' +
                '}';
    }
}
