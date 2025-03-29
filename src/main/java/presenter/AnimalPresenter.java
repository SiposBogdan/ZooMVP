package presenter;

import model.entity.Animal;
import model.enums.Alimentatie;
import model.enums.Categorie;
import model.enums.Habitat;
import model.repository.AnimalRepository;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;


import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class AnimalPresenter {
    private AnimalPresenterInterface view;
    private AnimalRepository repository;

    public AnimalPresenter(AnimalPresenterInterface view) {
        this.view = view;
        this.repository = new AnimalRepository();
    }


    public void loadAnimals() {
        List<Animal> animals = repository.getAllAnimals();
        List<Map<String, Object>> animalData = convertAnimalsToMapList(animals);
        view.displayAnimals(animalData);
    }

    public List<String> getAnimalComboItems() {
        List<Map<String, Object>> animals = getAllAnimals();
        List<String> items = new ArrayList<>();
        for (Map<String, Object> animal : animals) {
            String item = animal.get("id") + " - " + animal.get("nume");
            items.add(item);
        }
        return items;
    }


    public void addAnimal(String name, String categorie, String diet, String habitat, String weight, String age, String speed) {
        try {
            double parsedWeight = Double.parseDouble(weight);
            int parsedAge = Integer.parseInt(age);
            double parsedSpeed = Double.parseDouble(speed);

            Animal animal = new Animal(0, name, Categorie.valueOf(categorie), Alimentatie.valueOf(diet), Habitat.valueOf(habitat), parsedWeight, parsedAge, parsedSpeed);
            repository.addAnimal(animal);
            view.showMessageAnimal("Animal added successfully!");
            loadAnimals();
        } catch (NumberFormatException e) {
            view.showMessageAnimal("Please enter valid numbers for weight, age, and speed.");
        }
    }


    public void updateAnimal(int id, String name, String categorie, String diet, String habitat, String weight, String age, String speed) {
        try {
            double parsedWeight = Double.parseDouble(weight);
            int parsedAge = Integer.parseInt(age);
            double parsedSpeed = Double.parseDouble(speed);

            Animal animal = new Animal(id, name, Categorie.valueOf(categorie), Alimentatie.valueOf(diet), Habitat.valueOf(habitat), parsedWeight, parsedAge, parsedSpeed);
            repository.updateAnimal(animal);
            view.showMessageAnimal("Animal updated successfully!");
            loadAnimals();
        } catch (NumberFormatException e) {
            view.showMessageAnimal("Please enter valid numbers for weight, age, and speed.");
        }
    }


    public void deleteAnimal(int id) {
        repository.deleteAnimal(id);
        view.showMessageAnimal("Animal deleted successfully!");
        loadAnimals();
    }


    public void searchAnimalByName(String name) {
        Animal animal = repository.getAnimalsByName(name);
        if (animal != null) {
            Map<String, Object> animalMap = convertAnimalToMap(animal);
            List<Map<String, Object>> list = new ArrayList<>();
            list.add(animalMap);
            view.displayAnimals(list);
        } else {
            view.showMessageAnimal("Animal not found!");
        }
    }

    private Map<String, Object> convertAnimalToMap(Animal animal) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", animal.getIdAnimal());
        map.put("nume", animal.getNume());
        map.put("categorie", animal.getCategorie());
        map.put("alimentatie", animal.getAlimentatie());
        map.put("habitat", animal.getHabitat());
        map.put("greutate", animal.getGreutateMedie());
        map.put("varsta", animal.getVarstaMedie());
        map.put("viteza", animal.getVitezaMedie());
        return map;
    }

    private List<Map<String, Object>> convertAnimalsToMapList(List<Animal> animals) {
        List<Map<String, Object>> result = new ArrayList<>();
        for (Animal animal : animals) {
            result.add(convertAnimalToMap(animal));
        }
        return result;
    }

    public List<Map<String, Object>> getAllAnimals() {
        List<Animal> animals = repository.getAllAnimals();
        List<Map<String, Object>> animalList = new ArrayList<>();

        for (Animal animal : animals) {
            Map<String, Object> animalData = new HashMap<>();
            animalData.put("id", animal.getIdAnimal());
            animalData.put("nume", animal.getNume());
            animalData.put("categorie", animal.getCategorie());
            animalData.put("alimentatie", animal.getAlimentatie());
            animalData.put("habitat", animal.getHabitat());
            animalData.put("greutate", animal.getGreutateMedie());
            animalData.put("varsta", animal.getVarstaMedie());
            animalData.put("viteza", animal.getVarstaMedie());
            animalList.add(animalData);
        }

        return animalList;
    }
    public void filterAnimals(String nameFilter, String diet, String habitat) {

        List<Animal> animals = repository.getAllAnimals();

        List<Animal> filtered = animals.stream().filter(animal -> {
                    boolean matches = true;

                    if (nameFilter != null && !nameFilter.trim().isEmpty()) {
                        matches &= animal.getNume().toLowerCase().contains(nameFilter.trim().toLowerCase());
                    }
                    if (diet != null && !diet.trim().isEmpty()) {
                        matches &= animal.getAlimentatie().toString().equalsIgnoreCase(diet);
                    }
                    if (habitat != null && !habitat.trim().isEmpty()) {
                        matches &= animal.getHabitat().toString().equalsIgnoreCase(habitat);
                    }
                    return matches;
                }).sorted(Comparator.comparing(a -> a.getNume().toLowerCase()))
                .collect(Collectors.toList());

        List<Map<String, Object>> mapList = convertAnimalsToMapList(filtered);
        view.displayAnimals(mapList);
    }


    public void saveAnimalsToCSV(List<Map<String, Object>> animals, String filePath) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.append("ID,Nume,Categorie,Alimentatie,Habitat,Greutate,Varsta,Viteza\n");
            for (Map<String, Object> animal : animals) {
                writer.append(animal.get("id").toString()).append(",");
                writer.append(animal.get("nume").toString()).append(",");
                writer.append(animal.get("categorie").toString()).append(",");
                writer.append(animal.get("alimentatie").toString()).append(",");
                writer.append(animal.get("habitat").toString()).append(",");
                writer.append(animal.get("greutate").toString()).append(",");
                writer.append(animal.get("varsta").toString()).append(",");
                writer.append(animal.get("viteza").toString()).append("\n");
            }
        }
    }


    public void saveAnimalsToDoc(List<Map<String, Object>> animals, String filePath) throws IOException {
        XWPFDocument document = new XWPFDocument();
        XWPFParagraph header = document.createParagraph();
        XWPFRun headerRun = header.createRun();
        headerRun.setText("Lista Animale");
        headerRun.setBold(true);
        headerRun.setFontSize(16);
        for (Map<String, Object> animal : animals) {
            XWPFParagraph p = document.createParagraph();
            XWPFRun run = p.createRun();
            run.setText("ID: " + animal.get("id") +
                    ", Nume: " + animal.get("nume") +
                    ", Categorie: " + animal.get("categorie") +
                    ", Alimentatie: " + animal.get("alimentatie") +
                    ", Habitat: " + animal.get("habitat") +
                    ", Greutate: " + animal.get("greutate") +
                    ", Varsta: " + animal.get("varsta") +
                    ", Viteza: " + animal.get("viteza"));
        }
        try (FileOutputStream out = new FileOutputStream(filePath)) {
            document.write(out);
        }
        document.close();
    }



}
