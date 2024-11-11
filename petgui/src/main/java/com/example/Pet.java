package com.example;

public class Pet {
    private String name;
    private int age;
    private double cost;
    private String imagePath;

    public Pet(String name, int age, double cost, String imagePath) {
        this.name = name;
        this.age = age;
        this.cost = cost;
        this.imagePath = imagePath;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    
    public double getCost() { return cost; }
    public void setCost(double cost) { this.cost = cost; }
    
    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }
}
