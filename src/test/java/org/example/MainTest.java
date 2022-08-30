package org.example;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {


    @Test
    public void fluxText() {

        Flux<String> numeros = Flux.just("one", "two", "three")
                .log();
        numeros.subscribe(System.out::println);

    }

    @Test
    public void fluxTextWithConcat() {

        Flux<String> numeros = Flux.just("one", "two", "three")
                .concatWith(Flux.just("jeje"))
                .log();
        numeros.subscribe(System.out::println);

    }

    @Test
    public void fluxTextWithException() {

        Flux<String> numeros = Flux.just("one", "two", "three")
                .concatWith(Flux.error(new RuntimeException("error happened")))
                .log();
        numeros.subscribe(System.out::println, e -> System.err.println("The exception is " + e));

    }

    @Test
    public void fluxTextWithExceptionNoMoreData() {

        Flux<String> numeros = Flux.just("one", "two", "three")
                .concatWith(Flux.error(new RuntimeException("error happened")))
                .concatWith(Flux.just("four"))
                .log();
        numeros.subscribe(System.out::println, e -> System.err.println("The exception is " + e));

    }

    @Test
    public void fluxTextCompleted() {

        Flux<String> numeros = Flux.just("one", "two", "three")
                .log();
        numeros.subscribe(System.out::println, System.err::println, () -> System.out.println("completed"));

    }

    @Test
    public void fluxTextTestVerifier() {

        Flux<String> numeros = Flux.just("one", "two", "three")
                .log();

        StepVerifier.create(numeros)
                .expectNext("one")
                .expectNext("two")
                .expectNext("three")
                .verifyComplete();

    }

    @Test
    public void fluxTextTestVerifierError() {

        Flux<String> numeros = Flux.just("one", "two", "three")
                .concatWith(Flux.error(new RuntimeException("error")))
                .log();

        StepVerifier.create(numeros)
                .expectNext("one")
                .expectNext("two")
                .expectNext("three")
                .expectError(RuntimeException.class)
                .verify();

    }

    @Test
    public void fluxTextTestNextCount() {

        Flux<String> numeros = Flux.just("one", "two", "three")
                .concatWith(Flux.error(new RuntimeException("error")))
                .log();

        StepVerifier.create(numeros)
                .expectNext("one")
                .expectNextCount(2) // espera 2 elementos más (two y three) + la excepcion
                .expectError(RuntimeException.class)
                .verify();

    }
}