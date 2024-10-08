package com.northcoders.demospringbootapp.controller;

import com.northcoders.demospringbootapp.dao.CoordinatesDAO;
import com.northcoders.demospringbootapp.dao.SunriseSunsetDAO;
import com.northcoders.demospringbootapp.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

@RestController
@RequestMapping("api/v1")
public class DemoController {

    @GetMapping("/hello")
    public String greeting() {
        return "Hello there";
    }

    @GetMapping("/people")
    public ArrayList<Person> getPersons() {
        ArrayList<Person> people = new ArrayList<>();
        people.add(new Person("Andrei", 25, "andrei@email.com", "Brighton", "Kebab"));
        people.add(new Person("Red", 27, "red@email.com", "London", "Burger"));

        return people;
    }

    @GetMapping("/coordinatesCity")
    @ResponseBody
    public String getCity(@RequestParam String name) {
        return "City is " + name;
    }

    @GetMapping("/coordinates")
    @ResponseBody
    public ArrayList<Coordinates> getCoordinatesForCity(@RequestParam String name) {
        String cityName = name;
        CoordinatesDAO cDAO = new CoordinatesDAO();
        ResponseEntity<Data> coordinatesMono = cDAO.getResponseBody(cityName);
        ArrayList<Coordinates> coordinates =  coordinatesMono.getBody().results();

        return coordinates;
    }

    @GetMapping("/suntimesForCoordinates")
    @ResponseBody
    public SunriseSunset getSuntimes(@RequestParam Double lat, Double lng) {
        SunriseSunsetDAO ssDAO = new SunriseSunsetDAO();
        ResponseEntity<Results> ssMono = ssDAO.getResponseBody(lat, lng);
        SunriseSunset suntimes = ssMono.getBody().results();

        return suntimes;
    }

    @GetMapping("/suntimesForCity")
    @ResponseBody
    public SunriseSunset getSuntimesForCityParam(@RequestParam String name) {

        ArrayList<Coordinates> coordinatesList = getCoordinatesForCity(name);

        return getSuntimes(coordinatesList);
    }

    @GetMapping("/suntimes")
    public SunriseSunset getSuntimesViaInput() {
        Scanner scanner = new Scanner(System.in);
        String cityName;

        while (true) {
            try {
                System.out.println("Please input a city: ");
                cityName = scanner.next();
                break;
            } catch (InputMismatchException e) {
                System.out.println("That city does not exist. Please enter another.");
                scanner.next();
            }
        }
        scanner.close();
        ArrayList<Coordinates> coordinatesList = getCoordinatesForCity(cityName);

        return getSuntimes(coordinatesList);
    }

    public SunriseSunset getSuntimes(ArrayList<Coordinates> coordinatesList) {
        Coordinates coordinates = coordinatesList.getFirst();
        SunriseSunset sunriseSunset = getSuntimes(coordinates.latitude(), coordinates.longitude());

        return sunriseSunset;
    }

}
