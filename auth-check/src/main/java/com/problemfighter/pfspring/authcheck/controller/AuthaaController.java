package com.problemfighter.pfspring.authcheck.controller;

import com.problemfighter.pfspring.authcheck.model.data.AuthPerson;
import org.springframework.web.bind.annotation.*;

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
    public String add(@RequestBody AuthPerson authPerson) {
        if (authPerson == null){
            return "Invalid Data";
        }
        persons.add(authPerson);
        return "Added";
    }

    @RequestMapping(value = "/get/{id}", method = RequestMethod.POST)
    public AuthPerson get(@PathVariable("id") Integer id) {
        return persons.get(id);
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public String update(@PathVariable("id") Integer id, @RequestBody AuthPerson authPerson) {
        AuthPerson authPersonExist = persons.get(id);
        if (authPersonExist == null || authPerson == null){
            return "Invalid Index or invalid data";
        }
        persons.set(id, authPerson);
        return "Updated";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable("id") Integer id) {
        AuthPerson authPersonExist = persons.get(id);
        if (authPersonExist == null || id == null) {
            return "Invalid Index";
        }
        persons.remove(id);
        return "Removed";
    }

}
