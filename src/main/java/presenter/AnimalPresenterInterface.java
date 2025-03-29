package presenter;

import java.util.List;
import java.util.Map;

public interface AnimalPresenterInterface {
    void displayAnimals(List<Map<String, Object>> animals);

    void displayAnimal(Map<String, Object> animal);

    void showMessageAnimal(String message);

    String getAnimalName();
    String getAnimalSpecie();
    String getAnimalDiet();
    String getAnimalHabitat();
    String getAnimalWeight();
    String getAnimalAge();
    String getAnimalSpeed();
    int getIdSelectedAnimal();
}
