package com.secretroomwebsite.enums;

import com.secretroomwebsite.exception.ResourceNotFoundException;

public enum Brands {
    VictoriasSecret("Victoria's Secret"),
    BathAndBody("Bath & Body");

    private final String name;

    Brands(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Brands fromName(String name) {
        for (Brands brand : Brands.values()) {
            if (brand.getName().equals(name)) {
                return brand;
            }
        }
        throw new ResourceNotFoundException("No brand with name " + name + " found");
    }
}
