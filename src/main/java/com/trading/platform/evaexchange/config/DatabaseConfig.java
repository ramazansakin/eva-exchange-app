package com.trading.platform.evaexchange.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.trading.platform.evaexchange")
@EnableJpaAuditing
public class DatabaseConfig {
}
