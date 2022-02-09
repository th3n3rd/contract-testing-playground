package com.example.catalogue;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@Configuration
class Config {

    @Bean
    public CommonsRequestLoggingFilter requestLoggingFilter() {
        var filter = new CommonsRequestLoggingFilter();
        filter.setIncludeHeaders(true);
        filter.setIncludeQueryString(true);
        filter.setIncludeClientInfo(true);
        filter.setIncludePayload(true);
        return filter;
    }

}
