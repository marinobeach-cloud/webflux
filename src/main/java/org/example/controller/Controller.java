package org.example.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;

@RestController
public class Controller {

    @GetMapping(value = "/flux", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<Integer> getFlux() {
        return Flux.just(1,2,3,4,5,6,7,8,9,10)
                .delayElements(Duration.ofSeconds(1))
                .log();
    }

    @GetMapping(value = "/fluxInterval", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<Long> getFluxInterval() {
        return Flux.interval(Duration.ofSeconds(1))
                .log();
    }
}
