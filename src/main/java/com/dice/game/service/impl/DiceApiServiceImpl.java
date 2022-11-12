package com.dice.game.service.impl;

import com.dice.game.config.DiceApiConfig;
import com.dice.game.dto.DiceDto;
import com.dice.game.service.DiceApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class DiceApiServiceImpl implements DiceApiService {
    private final Logger log = LoggerFactory.getLogger(DiceApiServiceImpl.class);
    private final DiceApiConfig diceApiConfig;
    public DiceApiServiceImpl(DiceApiConfig diceApiConfig) {
        this.diceApiConfig = diceApiConfig;
    }
    public  DiceDto rollTheDice() {
        log.info("Invoking special Dice API");
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setConnectTimeout(5000);
        requestFactory.setReadTimeout(20000);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity request = new HttpEntity<>(headers);
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        String uri = UriComponentsBuilder
                .fromHttpUrl(diceApiConfig.getHostUri())
                .toUriString();
        DiceDto diceDto;
        ResponseEntity<DiceDto> response = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                request,
                DiceDto.class
        );
        log.info("Received Response from DiceApi");
        diceDto = response.getBody();
        return diceDto;
    }

}
