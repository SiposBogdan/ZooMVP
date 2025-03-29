package model.entity;
import model.entity.Animal;
public class Exemplar {
    private int idExemplar;
    private Animal animal;
    private String nume;
    private String locatie;
    private double greutate;
    private int varsta;
    private String imaginePath;

    public Exemplar(int idExemplar, Animal animal, String nume,String locatie, double greutate, int varsta, String imaginePath) {
        this.idExemplar = idExemplar;
        this.animal = animal;
        this.locatie = locatie;
        this.greutate = greutate;
        this.varsta = varsta;
        this.imaginePath = imaginePath;
        this.nume = nume;
    }


    // Getters and Setters
    public int getIdExemplar() {
        return idExemplar;
    }

    public void setIdExemplar(int idExemplar) {
        this.idExemplar = idExemplar;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public String getLocatie() {
        return locatie;
    }

    public void setLocatie(String locatie) {
        this.locatie = locatie;
    }

    public double getGreutate() {
        return greutate;
    }

    public void setGreutate(double greutate) {
        this.greutate = greutate;
    }

    public int getVarsta() {
        return varsta;
    }

    public void setVarsta(int varsta) {
        this.varsta = varsta;
    }

    public String getImaginePath() {
        return imaginePath;
    }

    public void setImaginePath(String imaginePath) {
        this.imaginePath = imaginePath;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    @Override
    public String toString() {
        return "Exemplar{" +
                "idExemplar=" + idExemplar +
                ", animal=" + animal.getNume() +
                ", locatie='" + locatie + '\'' +
                ", greutate=" + greutate +
                ", varsta=" + varsta +
                ", imaginePath='" + imaginePath + '\'' +
                '}';
    }
}
