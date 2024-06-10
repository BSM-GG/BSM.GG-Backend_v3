package bsmgg.bsmgg_backend.global.oauth;

import leehj050211.bsmOauth.BsmOauth;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class BsmOauthConfig {

    @Value("${BSM_AUTH_CLIENT_ID}")
    private String BSM_AUTH_CLIENT_ID;
    @Value("${BSM_AUTH_CLIENT_SECRET}")
    private String BSM_AUTH_CLIENT_SECRET;

    @Bean("bsmOauth")
    public BsmOauth bsmOauth() {
        return new BsmOauth(BSM_AUTH_CLIENT_ID, BSM_AUTH_CLIENT_SECRET);
    }
}
