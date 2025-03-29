package model.entity;

import model.enums.Alimentatie;
import model.enums.Categorie;
import model.enums.Habitat;

public class Animal {
    private int idAnimal;
    private String nume;
    private Alimentatie alimentatie;
    private Habitat habitat;
    private Categorie categorie;
    private double greutateMedie;
    private int varstaMedie;
    private double vitezaMedie;

    public Animal(int idAnimal, String nume, Categorie categorie, Alimentatie alimentatie, Habitat habitat, double greutateMedie, int varstaMedie, double vitezaMedie) {
        this.idAnimal = idAnimal;
        this.nume = nume;
        this.categorie = categorie;
        this.alimentatie = alimentatie;
        this.habitat = habitat;
        this.greutateMedie = greutateMedie;
        this.varstaMedie = varstaMedie;
        this.vitezaMedie = vitezaMedie;
    }

    public int getIdAnimal() {
        return idAnimal;
    }

    public void setIdAnimal(int idAnimal) {
        this.idAnimal = idAnimal;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public Alimentatie getAlimentatie() {
        return alimentatie;
    }

    public void setAlimentatie(Alimentatie alimentatie) {
        this.alimentatie = alimentatie;
    }

    public Habitat getHabitat() {
        return habitat;
    }

    public void setHabitat(Habitat habitat) {
        this.habitat = habitat;
    }

    public double getGreutateMedie() {
        return greutateMedie;
    }

    public void setGreutateMedie(double greutateMedie) {
        this.greutateMedie = greutateMedie;
    }

    public int getVarstaMedie() {
        return varstaMedie;
    }

    public void setVarstaMedie(int varstaMedie) {
        this.varstaMedie = varstaMedie;
    }

    public double getVitezaMedie() {
        return vitezaMedie;
    }

    public void setVitezaMedie(double vitezaMedie) {
        this.vitezaMedie = vitezaMedie;
    }

    @Override
    public String toString() {
        return "Animal{" +
                "idAnimal=" + idAnimal +
                ", nume='" + nume + '\'' +
                ", categorie='" + categorie + '\'' +
                ", alimentatie='" + alimentatie + '\'' +
                ", habitat='" + habitat + '\'' +
                ", greutateMedie=" + greutateMedie +
                ", varstaMedie=" + varstaMedie +
                ", vitezaMedie=" + vitezaMedie +
                '}';
    }
}
