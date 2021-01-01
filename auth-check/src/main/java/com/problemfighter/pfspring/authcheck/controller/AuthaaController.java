package com.problemfighter.pfspring.authcheck.controller;

import com.problemfighter.pfspring.authcheck.model.data.AuthPerson;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/auth-aa")
public class AuthaaController {

    private static final List<AuthPerson> persons = Arrays.asList(
            new AuthPerson("Abul", 1),
            new AuthPerson("Babul", 2),
            new AuthPerson("Kabul", 3),
            new AuthPerson("Sabul", 4)
    );


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<AuthPerson> list() {
        return persons;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public List<AuthPerson> add() {
        return persons;
    }

    @RequestMapping(value = "/get", method = RequestMethod.POST)
    public AuthPerson get() {
        return persons.get(0);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public List<AuthPerson> update() {
        return persons;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public List<AuthPerson> delete() {
        return persons;
    }

}
