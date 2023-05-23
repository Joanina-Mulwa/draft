package com.chess.draftpesa.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;

@EnableConfigurationProperties({ApplicationProperties.class})
@SpringBootTest(classes = {ApplicationProperties.class})
public class ServiceConfigsIT {
    @Autowired ApplicationProperties applicationProperties;

    @Test
    void givenApplicationProperties_Check_outer_values(){
        Assertions.assertAll(
                () -> Assertions.assertEquals("NVcLQlm83JUitn2ChhmNmnHZa4bwoeZlWYAJEmYhK+oY558POCfS0Z77b3J/XCogU1M2akzuzb0IFMJNc/X96g==", applicationProperties.getJwtSecret()),
                () -> Assertions.assertEquals("86400", applicationProperties.getJwtValidityInSeconds())
        );
    }

}
