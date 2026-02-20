package ug.daes.onboarding.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ug.daes.DAESService;
import ug.daes.PKICoreServiceException;
import ug.daes.Result;



@Configuration
public class SignatueServiceInitilizeConfig {

    private static Logger logger = LoggerFactory.getLogger(SignatueServiceInitilizeConfig.class);

    final static String CLASS = "SignatueServiceInitilizeConfig";


    @Bean
    public String signatueServiceInitilize() {
        try {
            Result result = DAESService.initPKINativeUtils();
            if (result.getStatus() == 0)
                System.out.println(new String(result.getStatusMessage()));
            else {
                System.out.println(new String(result.getResponse()));
            }
            return "";
        } catch (PKICoreServiceException e) {
            logger.error("Unexpected exception", e);
            return "";
        }

    }}