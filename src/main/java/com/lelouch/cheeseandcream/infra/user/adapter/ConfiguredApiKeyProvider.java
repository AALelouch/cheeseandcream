package com.lelouch.cheeseandcream.infra.user.adapter;

import com.lelouch.cheeseandcream.application.user.query.ApiKeyProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ConfiguredApiKeyProvider implements ApiKeyProvider {

    private final String apiKey;

    public ConfiguredApiKeyProvider(@Value("${app.api-key}") String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public String getApiKey() {
        return apiKey;
    }
}
