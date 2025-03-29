package model.repository;

import model.entity.Animal;
import model.enums.Alimentatie;
import model.enums.Categorie;
import model.enums.Habitat;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AnimalRepository {


    public void addAnimal(Animal animal) {
        String sql = "INSERT INTO animals (nume, categorie, alimentatie, habitat, greutateMedie, varstaMedie, vitezaMedie) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = Repository.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, animal.getNume());
            statement.setString(2, String.valueOf(animal.getCategorie()));
            statement.setString(3, String.valueOf(animal.getAlimentatie()));
            statement.setString(4, String.valueOf(animal.getHabitat()));
            statement.setDouble(5, animal.getGreutateMedie());
            statement.setInt(6, animal.getVarstaMedie());
            statement.setDouble(7, animal.getVitezaMedie());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<Animal> getAllAnimals() {
        List<Animal> animals = new ArrayList<>();
        String sql = "SELECT * FROM animals";

        try (Connection connection = Repository.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nume = resultSet.getString("nume");
                String categorie = resultSet.getString("categorie");
                String alimentatie = resultSet.getString("alimentatie");
                String habitat = resultSet.getString("habitat");
                double greutate = resultSet.getDouble("greutateMedie");
                int varstaMedie = resultSet.getInt("varstaMedie");
                double vitezaMedie = resultSet.getDouble("vitezaMedie");

                Animal animal = new Animal(id, nume, Categorie.valueOf(categorie), Alimentatie.valueOf(alimentatie), Habitat.valueOf(habitat), greutate, varstaMedie, vitezaMedie);
                animals.add(animal);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return animals;
    }

    public void updateAnimal(Animal animal) {
        String sql = "UPDATE animals SET nume = ?, categorie = ?, alimentatie = ?, habitat = ?, greutateMedie = ?, varstaMedie = ?, vitezaMedie = ? WHERE id = ?";

        try (Connection connection = Repository.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, animal.getNume());
            statement.setString(2, String.valueOf(animal.getCategorie()));
            statement.setString(3, String.valueOf(animal.getAlimentatie()));
            statement.setString(4, String.valueOf(animal.getHabitat()));
            statement.setDouble(5, animal.getGreutateMedie());
            statement.setInt(6, animal.getVarstaMedie());
            statement.setDouble(7, animal.getVitezaMedie());
            statement.setInt(8, animal.getIdAnimal());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAnimal(int id) {
        String sql = "DELETE FROM animals WHERE id = ?";

        try (Connection connection = Repository.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Animal getAnimalsByName(String name) {
        String sql = "SELECT * FROM animals WHERE nume = ?";
        try (Connection connection = Repository.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String nume = resultSet.getString("nume");
                    String categorie = resultSet.getString("categorie");
                    String alimentatie = resultSet.getString("alimentatie");
                    String habitat = resultSet.getString("habitat");
                    double greutate = resultSet.getDouble("greutateMedie");
                    int varstaMedie = resultSet.getInt("varstaMedie");
                    double vitezaMedie = resultSet.getDouble("vitezaMedie");

                    return new Animal(id, nume, Categorie.valueOf(categorie), Alimentatie.valueOf(alimentatie), Habitat.valueOf(habitat), greutate, varstaMedie, vitezaMedie);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
