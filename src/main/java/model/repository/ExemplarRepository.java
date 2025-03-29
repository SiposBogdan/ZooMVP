package model.repository;

import model.entity.Animal;
import model.entity.Exemplar;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExemplarRepository {

    public void addExemplar(Exemplar exemplar) {
        String sql = "INSERT INTO exemplars (animal_id, nume, locatie, greutate, varsta, imaginePath) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = Repository.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, exemplar.getAnimal().getIdAnimal());
            statement.setString(2, exemplar.getNume());
            statement.setString(3, exemplar.getLocatie());
            statement.setDouble(4, exemplar.getGreutate());
            statement.setInt(5, exemplar.getVarsta());
            statement.setString(6, exemplar.getImaginePath());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Exemplar> getAllExemplars() {
        List<Exemplar> exemplars = new ArrayList<>();
        String sql = "SELECT * FROM exemplars";

        try (Connection connection = Repository.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int animalId = resultSet.getInt("animal_id");
                String nume = resultSet.getString("nume");
                String locatie = resultSet.getString("locatie");
                double greutate = resultSet.getDouble("greutate");
                int varsta = resultSet.getInt("varsta");
                String imaginePath = resultSet.getString("imaginePath");

                Animal animal = new AnimalRepository().getAllAnimals().stream()
                        .filter(a -> a.getIdAnimal() == animalId)
                        .findFirst()
                        .orElse(null);

                Exemplar exemplar = new Exemplar(id, animal, nume, locatie, greutate, varsta, imaginePath);
                exemplars.add(exemplar);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exemplars;
    }

    public void updateExemplar(Exemplar exemplar) {
        String sql = "UPDATE exemplars SET animal_id = ?, nume = ?, locatie = ?, greutate = ?, varsta = ?, imaginePath = ? WHERE id = ?";

        try (Connection connection = Repository.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, exemplar.getAnimal().getIdAnimal());
            statement.setString(2, exemplar.getNume());
            statement.setString(3, exemplar.getLocatie());
            statement.setDouble(4, exemplar.getGreutate());
            statement.setInt(5, exemplar.getVarsta());
            statement.setString(6, exemplar.getImaginePath());
            statement.setInt(7, exemplar.getIdExemplar());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void deleteExemplar(int id) {
        String sql = "DELETE FROM exemplars WHERE id = ?";

        try (Connection connection = Repository.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<Exemplar> getExemplarsBySpecies(String species) {
        List<Exemplar> exemplars = new ArrayList<>();
        String sql = "SELECT * FROM exemplars WHERE animal_id IN (SELECT id FROM animals WHERE nume = ?)";

        try (Connection connection = Repository.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, species);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    int animalId = resultSet.getInt("animal_id");
                    String nume = resultSet.getString("nume");
                    String locatie = resultSet.getString("locatie");
                    double greutate = resultSet.getDouble("greutate");
                    int varsta = resultSet.getInt("varsta");
                    String imaginePath = resultSet.getString("imaginePath");

                    Animal animal = new AnimalRepository().getAllAnimals().stream()
                            .filter(a -> a.getIdAnimal() == animalId)
                            .findFirst()
                            .orElse(null);

                    Exemplar exemplar = new Exemplar(id, animal, nume, locatie, greutate, varsta, imaginePath);
                    exemplars.add(exemplar);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exemplars;
    }
}
