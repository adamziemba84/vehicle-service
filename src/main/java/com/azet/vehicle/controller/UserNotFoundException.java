package com.azet.vehicle.controller;

public class UserNotFoundException extends RuntimeException {
    UserNotFoundException(long id) {
        super(String.format("User with id %d not found", id));
    }
}
