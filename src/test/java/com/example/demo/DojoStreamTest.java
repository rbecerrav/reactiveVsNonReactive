package com.example.demo;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DojoStreamTest {
    public List<Player> list = new ArrayList<>();

    @BeforeEach
    void setUp() {
        list = CsvUtilFile.getPlayers();
    }

    @Test
    void converterData(){
        assert list.size() == 18207;
    }

    @Test
    void jugadoresMayoresA35(){
        list.stream().flatMap(player -> {
            if(player.getAge() > 35){
                return Stream.of(player);
            }
            return Stream.empty();
        }).forEach(System.out::println);

    }

    @Test
    void jugadoresMayoresA35SegunClub(){

        Map<String, List<Player>> map = list.stream().collect(Collectors.groupingBy(Player::getClub));
        map.forEach((s, players) -> {
            System.out.println("Club: "+s);
            System.out.println("JUGADORES: ");
            players.forEach(player -> System.out.println(player.getName()));
        });

    }

    @Test
    void mejorJugadorConNacionalidadFrancia(){

        list.stream().filter(player -> player.getNational().equals("France"))
                .max(Comparator.comparing(player -> player.getWinners()))
                .ifPresent(System.out::println);
        System.out.println("manera del profe");
        list.stream().filter(player -> player.getNational().equals("France"))
                .reduce((player, player2) -> player.getWinners() > player2.getWinners() ? player : player2)
                .ifPresent(System.out::println);
    }


    @Test
    void clubsAgrupadosPorNacionalidad(){
        list.stream().collect(Collectors.groupingBy(Player::getNational))
                .forEach((s, players) -> {
                    System.out.println("Nacionalidad: "+s);
                    System.out.println("Clubs: ");
                    players.forEach(player -> System.out.println(player.getClub()));
                });

    }

    @Test
    void clubConElMejorJugador(){

        list.stream().reduce((player, player2) -> player.getWinners() > player2.getWinners() ? player : player2)
                .ifPresent(player -> System.out.println("Club con el mejor jugador: "+player.getClub()));

    }

    @Test
    void ElMejorJugador(){
        list.stream().reduce((player, player2) -> player.getWinners() > player2.getWinners() ? player : player2)
                .ifPresent(player -> System.out.println("El mejor jugador: "+player.getName()));

    }

    @Test
    void mejorJugadorSegunNacionalidad(){
        list.stream().collect(Collectors.groupingBy(Player::getNational))
                .forEach((s, players) -> {
                    System.out.println("Nacionalidad: "+s);
                    System.out.println("Mejor jugador: ");
                    players.stream().reduce((player, player2) -> player.getWinners() > player2.getWinners() ? player : player2)
                            .ifPresent(player -> System.out.println(player.getName()));
                });


    }


}
