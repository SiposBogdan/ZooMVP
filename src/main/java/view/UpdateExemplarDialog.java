package view;

import presenter.AnimalPresenter;
import presenter.ExemplarPresenter;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.List;
import java.util.Map;

public class UpdateExemplarDialog extends JDialog {
    private JComboBox<String> animalComboBox;
    private JTextField exemplarNameField, locatieField, weightField, ageField, imagePathField;
    private JButton saveButton, cancelButton, browseButton;
    private int exemplarId;
    private ExemplarPresenter exemplarPresenter;
    private AnimalPresenter animalPresenter;

    /**
     * Constructor.
     * @param parent The parent frame.
     * @param exemplarPresenter The presenter for exemplars.
     * @param exemplarId The id of the exemplar to update.
     * @param selectedAnimalId The currently associated animal id (as String).
     * @param exemplarName The exemplar's current name.
     * @param locatie The current location.
     * @param weight The current weight.
     * @param age The current age.
     * @param imagePath The current image path.
     * @param animalPresenter The presenter to retrieve animal data.
     */
    public UpdateExemplarDialog(JFrame parent, ExemplarPresenter exemplarPresenter, int exemplarId,
                                String selectedAnimalId, String exemplarName, String locatie,
                                String weight, String age, String imagePath, AnimalPresenter animalPresenter) {
        super(parent, "Update Exemplar", true);
        this.exemplarId = exemplarId;
        this.exemplarPresenter = exemplarPresenter;
        this.animalPresenter = animalPresenter;
        setSize(400, 450);
        setLocationRelativeTo(parent);
        initComponents(selectedAnimalId, exemplarName, locatie, weight, age, imagePath);
    }

    private void initComponents(String selectedAnimalId, String exemplarName, String locatie,
                                String weight, String age, String imagePath) {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Row 0: Animal ComboBox
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Select Animal:"), gbc);
        animalComboBox = new JComboBox<>();
        populateAnimalComboBox(selectedAnimalId);
        gbc.gridx = 1;
        panel.add(animalComboBox, gbc);

        // Row 1: Exemplar Nume
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Nume Exemplar:"), gbc);
        exemplarNameField = new JTextField(exemplarName, 15);
        gbc.gridx = 1;
        panel.add(exemplarNameField, gbc);

        // Row 2: Locatie
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Locatie:"), gbc);
        locatieField = new JTextField(locatie, 15);
        gbc.gridx = 1;
        panel.add(locatieField, gbc);

        // Row 3: Greutate
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Greutate:"), gbc);
        weightField = new JTextField(weight, 15);
        gbc.gridx = 1;
        panel.add(weightField, gbc);

        // Row 4: Vasrta
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(new JLabel("Varsta:"), gbc);
        ageField = new JTextField(age, 15);
        gbc.gridx = 1;
        panel.add(ageField, gbc);

        // Row 5: Image Path cu Browse Button
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(new JLabel("Image Path:"), gbc);
        imagePathField = new JTextField(imagePath, 15);
        gbc.gridx = 1;
        panel.add(imagePathField, gbc);
        browseButton = new JButton("Browse");
        gbc.gridx = 2;
        panel.add(browseButton, gbc);
        browseButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(UpdateExemplarDialog.this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                imagePathField.setText(selectedFile.getAbsolutePath());
            }
        });

        // Row 6: Buttons Panel
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        saveButton = new JButton("Save");
        cancelButton = new JButton("Cancel");
        buttonsPanel.add(saveButton);
        buttonsPanel.add(cancelButton);
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 3;
        panel.add(buttonsPanel, gbc);

        add(panel);

        // Save button
        saveButton.addActionListener(e -> {
            try {
                String selected = (String) animalComboBox.getSelectedItem();
                int animalId = Integer.parseInt(selected.split(" - ")[0]);
                String imagePathInput = imagePathField.getText();
                exemplarPresenter.updateExemplar(
                        exemplarId,
                        animalId,
                        exemplarNameField.getText(),
                        locatieField.getText(),
                        weightField.getText(),
                        ageField.getText(),
                        imagePathInput
                );
                dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid input. Please check your inputs.");
            }
        });

        cancelButton.addActionListener(e -> dispose());
    }

    /**
     * Populates the animalComboBox using the AnimalPresenter.
     * @param selectedAnimalId The id (as a String) of the animal currently associated with the exemplar.
     */
    private void populateAnimalComboBox(String selectedAnimalId) {
        List<Map<String, Object>> animals = animalPresenter.getAllAnimals();
        animalComboBox.removeAllItems();
        for (Map<String, Object> animal : animals) {
            String item = animal.get("id") + " - " + animal.get("nume");
            animalComboBox.addItem(item);
        }
        for (int i = 0; i < animalComboBox.getItemCount(); i++) {
            String item = animalComboBox.getItemAt(i);
            if (item.startsWith(selectedAnimalId + " - ")) {
                animalComboBox.setSelectedIndex(i);
                break;
            }
        }
    }
}
