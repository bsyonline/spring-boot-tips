package com.rolex.tips.controller;

import com.rolex.tips.model.Response;
import com.rolex.tips.model.User;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
@RestController
public class UserController {

    @GetMapping("/users/list")
    public Response list() {
        return Response.success(new ArrayList<>());
    }

    @PostMapping("/users/add")
    public Response add() {
        return Response.success(new User(1, "John"));
    }

    @PutMapping("/users/update")
    public Response update() {
        return Response.success(new User(1, "John"));
    }

    @GetMapping("/users/{id}")
    public Response get(@PathVariable("id") int id) {
        return Response.success(new User(1, "John"));
    }

    @DeleteMapping("/users/{id}")
    public Response delete(@PathVariable("id") int id) {
        return Response.success(null);
    }

}
