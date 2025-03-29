package view;

import presenter.AnimalPresenter;
import javax.swing.*;
import java.awt.*;

public class UpdateAnimalDialog extends JDialog {
    private JTextField nameField, weightField, ageField, speedField;
    private JComboBox<String> specieComboBox, dietComboBox, habitatComboBox;
    private JButton saveButton, cancelButton;

    private int animalId;
    private AnimalPresenter animalPresenter;

    public UpdateAnimalDialog(JFrame parent, AnimalPresenter animalPresenter, int animalId,
                              String name, String specie, String diet, String habitat,
                              String weight, String age, String speed) {
        super(parent, "Update Animal", true);
        this.animalPresenter = animalPresenter;
        this.animalId = animalId;
        setSize(400, 350);
        setLocationRelativeTo(parent);
        initComponents(name, specie, diet, habitat, weight, age, speed);
    }

    private void initComponents(String name, String specie, String diet, String habitat,
                                String weight, String age, String speed) {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Nume
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Nume Animal:"), gbc);
        nameField = new JTextField(name, 15);
        gbc.gridx = 1;
        panel.add(nameField, gbc);

        // Greutate
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Greutate:"), gbc);
        weightField = new JTextField(weight, 15);
        gbc.gridx = 1;
        panel.add(weightField, gbc);

        // Varsta
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Varsta:"), gbc);
        ageField = new JTextField(age, 15);
        gbc.gridx = 1;
        panel.add(ageField, gbc);

        // Viteza
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Viteza:"), gbc);
        speedField = new JTextField(speed, 15);
        gbc.gridx = 1;
        panel.add(speedField, gbc);

        // Specie
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(new JLabel("Specie:"), gbc);
        String[] species = {"Pasare", "Reptila", "Insecta", "Peste", "Amfibian", "Mamifer"};
        specieComboBox = new JComboBox<>(species);
        specieComboBox.setSelectedItem(specie);
        gbc.gridx = 1;
        panel.add(specieComboBox, gbc);

        // Alimentatie
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(new JLabel("Alimentatie:"), gbc);
        String[] diets = {"Ierbivor", "Carnivor", "Omnivor"};
        dietComboBox = new JComboBox<>(diets);
        dietComboBox.setSelectedItem(diet);
        gbc.gridx = 1;
        panel.add(dietComboBox, gbc);

        // Habitat
        gbc.gridx = 0;
        gbc.gridy = 6;
        panel.add(new JLabel("Habitat:"), gbc);
        String[] habitats = {"Terestru", "Acvatic", "Aviatic"};
        habitatComboBox = new JComboBox<>(habitats);
        habitatComboBox.setSelectedItem(habitat);
        gbc.gridx = 1;
        panel.add(habitatComboBox, gbc);

        // Buttons panel
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        saveButton = new JButton("Save");
        cancelButton = new JButton("Cancel");
        buttonsPanel.add(saveButton);
        buttonsPanel.add(cancelButton);

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        panel.add(buttonsPanel, gbc);

        add(panel);

        // Set up button actions
        saveButton.addActionListener(e -> {
            animalPresenter.updateAnimal(animalId,
                    nameField.getText(),
                    (String) specieComboBox.getSelectedItem(),
                    (String) dietComboBox.getSelectedItem(),
                    (String) habitatComboBox.getSelectedItem(),
                    weightField.getText(),
                    ageField.getText(),
                    speedField.getText());
            dispose();
        });

        cancelButton.addActionListener(e -> dispose());
    }
}
