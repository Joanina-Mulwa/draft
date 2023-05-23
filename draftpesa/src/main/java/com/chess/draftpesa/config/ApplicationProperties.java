package com.chess.draftpesa.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
@Component
public class ApplicationProperties {
    private String jwtSecret;
    private String jwtValidityInSeconds;
    private String userNotFoundMsg;
    private String userExistsMsg;

    public String getJwtSecret() {
        return jwtSecret;
    }

    public void setJwtSecret(String jwtSecret) {
        this.jwtSecret = jwtSecret;
    }

    public String getJwtValidityInSeconds() {
        return jwtValidityInSeconds;
    }

    public void setJwtValidityInSeconds(String jwtValidityInSeconds) {
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

    @Override
    public String toString() {
        return "ApplicationProperties{" +
                "jwtSecret='" + jwtSecret + '\'' +
                ", jwtValidityInSeconds='" + jwtValidityInSeconds + '\'' +
                ", userNotFound='" + userNotFoundMsg + '\'' +
                ", userExists='" + userExistsMsg + '\'' +
                '}';
    }
}
