package presenter;

import java.util.List;
import java.util.Map;

public interface ExemplarPresenterInterface {
    void displayExemplars(List<Map<String, Object>> exemplars);

    void displayExemplar(Map<String, Object> exemplar);

    void showMessageExemplar(String message);

    // Get exemplar information from the view (inputs)
    String getExemplarLocation();
    String getExemplarWeight();
    String getExemplarAge();
    String getExemplarImagePath();
    String getAnimalId();
    int getIdSelectedExemplar();

}
