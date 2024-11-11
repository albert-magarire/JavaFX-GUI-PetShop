package com.example;

import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class PetDataManager {
    private static final String CSV_FILE_PATH = "src\\main\\resources\\com\\example\\pets.csv";

    public static List<Pet> loadPets() {
        List<Pet> pets = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 4) {
                    String name = data[0];
                    int age = Integer.parseInt(data[1]);
                    double cost = Double.parseDouble(data[2]);
                    String imagePath = data[3];
                    pets.add(new Pet(name, age, cost, imagePath));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pets;
    }

    public static void savePets(List<Pet> pets) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CSV_FILE_PATH))) {
            for (Pet pet : pets) {
                bw.write(pet.getName() + "," + pet.getAge() + "," + pet.getCost() + "," + pet.getImagePath());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addPet(Pet pet) {
        List<Pet> pets = loadPets();
        pets.add(pet);
        savePets(pets);
    }

    public static void deletePet(Pet pet) {
        List<Pet> pets = loadPets();
        pets.removeIf(p -> p.getName().equals(pet.getName())); 
        savePets(pets);
    }
}
