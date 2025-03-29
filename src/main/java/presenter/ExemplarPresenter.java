package presenter;

import model.entity.Animal;
import model.entity.Exemplar;
import model.enums.Alimentatie;
import model.enums.Categorie;
import model.enums.Habitat;
import model.repository.ExemplarRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExemplarPresenter {
    private ExemplarPresenterInterface view;
    private ExemplarRepository repository;

    public ExemplarPresenter(ExemplarPresenterInterface view) {
        this.view = view;
        this.repository = new ExemplarRepository();
    }


    public void loadExemplars() {
        List<Exemplar> exemplars = repository.getAllExemplars();
        List<Map<String, Object>> exemplarData = convertExemplarsToMapList(exemplars);
        view.displayExemplars(exemplarData);
    }

    public void addExemplar(int animalId, String nume, String location, String weight, String age, String imagePath) {
        try {
            double parsedWeight = Double.parseDouble(weight);
            int parsedAge = Integer.parseInt(age);

            Animal animal = new model.repository.AnimalRepository().getAllAnimals()
                    .stream()
                    .filter(a -> a.getIdAnimal() == animalId)
                    .findFirst()
                    .orElse(null);

            if (animal == null) {
                view.showMessageExemplar("Animal with id " + animalId + " not found.");
                return;
            }

            if (imagePath == null || imagePath.trim().isEmpty()) {
                imagePath = "D:\\proiectare software\\tema1_zoo\\src\\main\\resources\\placeholder.png";
            }

            Exemplar exemplar = new Exemplar(0, animal, nume, location, parsedWeight, parsedAge, imagePath);
            repository.addExemplar(exemplar);
            view.showMessageExemplar("Exemplar added successfully!");
            loadExemplars();
        } catch (NumberFormatException e) {
            view.showMessageExemplar("Please enter valid numbers for weight and age.");
        }
    }


    public void updateExemplar(int id, int animalId, String nume, String location, String weight, String age, String imagePath) {
        try {
            double parsedWeight = Double.parseDouble(weight);
            int parsedAge = Integer.parseInt(age);

            Animal animal = new model.repository.AnimalRepository().getAllAnimals()
                    .stream()
                    .filter(a -> a.getIdAnimal() == animalId)
                    .findFirst()
                    .orElse(null);

            if (animal == null) {
                view.showMessageExemplar("Animal with id " + animalId + " not found.");
                return;
            }

            Exemplar exemplar = new Exemplar(id, animal, nume, location, parsedWeight, parsedAge, imagePath);
            repository.updateExemplar(exemplar);
            view.showMessageExemplar("Exemplar updated successfully!");
            loadExemplars();
        } catch (NumberFormatException e) {
            view.showMessageExemplar("Please enter valid numbers for weight and age.");
        }
    }


    public void deleteExemplar(int id) {
        repository.deleteExemplar(id);
        view.showMessageExemplar("Exemplar deleted successfully!");
        loadExemplars();
    }

    public void searchExemplarBySpecies(String species) {
        System.out.println("Searching exemplars for species: " + species);
        List<Exemplar> exemplars = repository.getExemplarsBySpecies(species);
        List<Map<String, Object>> exemplarData = convertExemplarsToMapList(exemplars);
        if (!exemplarData.isEmpty()) {
            view.displayExemplars(exemplarData);
        } else {
            view.showMessageExemplar("No exemplars found for species: " + species);
        }
    }


    private Map<String, Object> convertExemplarToMap(Exemplar exemplar) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", exemplar.getIdExemplar());
        map.put("nume", exemplar.getNume());
        map.put("animal", exemplar.getAnimal().getNume());
        map.put("location", exemplar.getLocatie());
        map.put("weight", exemplar.getGreutate());
        map.put("age", exemplar.getVarsta());
        map.put("imagePathStr", exemplar.getImaginePath());
        return map;
    }


    private List<Map<String, Object>> convertExemplarsToMapList(List<Exemplar> exemplars) {
        List<Map<String, Object>> result = new ArrayList<>();
        for (Exemplar exemplar : exemplars) {
            result.add(convertExemplarToMap(exemplar));
        }
        return result;
    }



}
