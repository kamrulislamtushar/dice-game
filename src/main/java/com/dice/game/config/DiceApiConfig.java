package com.dice.game.config;


import com.dice.game.utility.YamlPropertySourceFactory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:config/dice.yml", factory = YamlPropertySourceFactory.class)
@ConfigurationProperties(prefix = "dice.endpoint")
@Getter
@Setter
@NoArgsConstructor
public class DiceApiConfig {
    private String hostUri;
}
