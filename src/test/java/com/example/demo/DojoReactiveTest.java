package com.example.demo;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DojoReactiveTest {
    public List<Player> list = new ArrayList<>();

    @BeforeEach
    void setUp() {
        list = CsvUtilFile.getPlayers();
        Flux<Player> flux = Flux.fromIterable(list);
    }

    @Test
    void converterData(){

        assert list.size() == 18207;
    }

    @Test
    void jugadoresMayoresA35() {

        Flux<Player> flux = Flux.fromIterable(list);
        flux.filter(player -> player.getAge() > 35)
                .subscribe(System.out::println);

    }


@Test
    void jugadoresMayoresA35SegunClub(){

        Flux<Player> flux = Flux.fromIterable(list);
        flux.groupBy(Player::getClub)
                .subscribe(playerFlux -> {
                    System.out.println("Club: "+playerFlux.key());
                    System.out.println("JUGADORES: ");
                    playerFlux.subscribe(player -> System.out.println(player.getName()));
                });

    }


    @Test
    void mejorJugadorConNacionalidadFrancia(){

        Flux<Player> flux = Flux.fromIterable(list);
        flux.filter(player -> player.getNational().equals("France"))
                .reduce((player, player2) -> player.getWinners() > player2.getWinners() ? player : player2)
                .subscribe(System.out::println);

    }

    @Test
    void clubsAgrupadosPorNacionalidad(){
        Flux<Player> flux = Flux.fromIterable(list);
        flux.groupBy(Player::getNational)
                .subscribe(playerFlux -> {
                    System.out.println("Nacionalidad: "+playerFlux.key());
                    System.out.println("CLUBS: ");
                    playerFlux.subscribe(player -> System.out.println(player.getClub()));
                });


    }

    @Test
    void clubConElMejorJugador(){
        Flux<Player> flux = Flux.fromIterable(list);
        flux.reduce(((player, player2) ->  player.getWinners() > player2.getWinners() ? player : player2))
                .subscribe(player -> {
            System.out.println("Club CON EL MEJOR JUGADOR: "+player.getClub());
        });

    }

    @Test
    void clubConElMejorJugador2() {

    }

    @Test
    void ElMejorJugador() {
        Flux<Player> flux = Flux.fromIterable(list);
        flux.reduce(((player, player2) ->  player.getWinners() > player2.getWinners() ? player : player2))
                .subscribe(player -> {
                    System.out.println("MEJOR JUGADOR: "+player.getName());
                });
    }

    @Test
    void mejorJugadorSegunNacionalidad(){
        Flux<Player> flux = Flux.fromIterable(list);
        flux.groupBy(Player::getNational)
                .subscribe(playerFlux -> {
                    playerFlux.reduce(((player, player2) ->  player.getWinners() > player2.getWinners() ? player : player2))
                            .subscribe(player -> {
                                System.out.println("NACIONALIDAD: "+playerFlux.key());
                                System.out.println("MEJOR JUGADOR: "+player.getName());
                            });
                });


    }



}
