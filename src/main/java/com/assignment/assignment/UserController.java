package com.assignment.assignment;

import java.util.function.Predicate;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserRepository repository;

    UserController(UserRepository repository) {
        this.repository = repository;
    }

	@GetMapping("/users")
	Map<String, Object> all() {
        List<User> users = repository.findAll();
        // filtering out users with salary < 0 or > 4000
        Predicate<User> bySalary = user -> user.getSalary() > 0 && user.getSalary() <= 4000;
        List<User> results = users.stream().filter(bySalary).collect(Collectors.<User> toList());

        HashMap<String, Object> response = new HashMap<>();
        response.put("results", results);
        return response;
	}
}