package com.assignment.assignment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter @Setter @NoArgsConstructor
class User {

    private @Id String name;
    private double salary;

    User(String name, double salary) {
        this.name = name;
        this.salary = salary;
    }
}