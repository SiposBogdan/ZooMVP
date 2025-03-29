package view;

import presenter.AnimalPresenter;
import presenter.AnimalPresenterInterface;
import presenter.ExemplarPresenter;
import presenter.ExemplarPresenterInterface;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class ZooDesktopGUI extends JFrame implements AnimalPresenterInterface, ExemplarPresenterInterface {
    // ****************** ANIMAL COMPONENTS ******************
    private JButton adaugareAnimalButton, actualizareAnimalButton, stergereAnimalButton, cautareAnimalButton, refreshAnimalButton;
    private JTextField numeAnimalField, greutateAnimalField, varstaAnimalField, vitezaAnimalField, searchAnimalField, filterNameField;
    private JTable tabelAnimale;
    private JComboBox<String> alimentatieComboBox, specieComboBox, habitatComboBox, animalComboBox;
    private JComboBox<String> filterDietComboBox;
    private JComboBox<String> filterHabitatComboBox;

    private JButton filterButton, exportCSVButton, exportDocButton;
    private AnimalPresenter animalPresenter;

    // ****************** EXEMPLAR COMPONENTS ******************
    // New field for exemplar's own name
    private JButton adaugareExemplarButton, actualizareExemplarButton, stergereExemplarButton, cautareExemplarButton, refreshExemplarButton;
    private JTextField animalIdField, exemplarNameField, locatieField, greutateExemplarField, varstaExemplarField, imagePathField, searchExemplarField;
    private JTable tabelExemplare;
    private ExemplarPresenter exemplarPresenter;

    private JTabbedPane tabbedPane;

    public ZooDesktopGUI() {
        animalPresenter = new AnimalPresenter(this);
        exemplarPresenter = new ExemplarPresenter(this);
        initComponents();

        animalPresenter.loadAnimals();
        exemplarPresenter.loadExemplars();
        populateAnimalComboBox();

        setVisible(true);
    }

    private void initComponents() {
        setTitle("Zoo Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));


        tabbedPane = new JTabbedPane();

        JPanel animalPanel = createAnimalPanel();
        JPanel exemplarPanel = createExemplarPanel();

        tabbedPane.addTab("Animals", animalPanel);
        tabbedPane.addTab("Exemplare", exemplarPanel);

        add(tabbedPane, BorderLayout.CENTER);
    }

    // ================== ANIMAL PANEL ====================
    private JPanel createAnimalPanel() {
        // Main panel uses BorderLayout to separate sections vertically
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));

        // === Top Section: Animal Input and CRUD Operations ===
        JPanel operationsPanel = new JPanel(new GridBagLayout());
        operationsPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),
                "Animal Operations", TitledBorder.LEADING, TitledBorder.TOP));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // Row 0: Nume Animal and Greutate
        gbc.gridx = 0; gbc.gridy = 0;
        operationsPanel.add(new JLabel("Nume Animal:"), gbc);
        numeAnimalField = new JTextField(15);
        gbc.gridx = 1;
        operationsPanel.add(numeAnimalField, gbc);

        gbc.gridx = 2;
        operationsPanel.add(new JLabel("Greutate:"), gbc);
        greutateAnimalField = new JTextField(15);
        gbc.gridx = 3;
        operationsPanel.add(greutateAnimalField, gbc);

        // Row 1: Varsta and Viteza
        gbc.gridx = 0; gbc.gridy = 1;
        operationsPanel.add(new JLabel("Varsta:"), gbc);
        varstaAnimalField = new JTextField(15);
        gbc.gridx = 1;
        operationsPanel.add(varstaAnimalField, gbc);

        gbc.gridx = 2;
        operationsPanel.add(new JLabel("Viteza:"), gbc);
        vitezaAnimalField = new JTextField(15);
        gbc.gridx = 3;
        operationsPanel.add(vitezaAnimalField, gbc);

        // Row 2: Alimentatie and Specie
        gbc.gridx = 0; gbc.gridy = 2;
        operationsPanel.add(new JLabel("Alimentatie:"), gbc);
        alimentatieComboBox = new JComboBox<>(new String[]{"Ierbivor", "Carnivor", "Omnivor"});
        gbc.gridx = 1;
        operationsPanel.add(alimentatieComboBox, gbc);

        gbc.gridx = 2;
        operationsPanel.add(new JLabel("Categorie:"), gbc);
        specieComboBox = new JComboBox<>(new String[]{"Pasare", "Reptila", "Insecta", "Peste", "Amfibian", "Mamifer"});
        gbc.gridx = 3;
        operationsPanel.add(specieComboBox, gbc);

        // Row 3: Habitat
        gbc.gridx = 0; gbc.gridy = 3;
        operationsPanel.add(new JLabel("Habitat:"), gbc);
        habitatComboBox = new JComboBox<>(new String[]{"Terestru", "Acvatic", "Aviatic"});
        gbc.gridx = 1;
        operationsPanel.add(habitatComboBox, gbc);

        // Row 4: CRUD Buttons (using FlowLayout for simplicity)
        JPanel crudPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        adaugareAnimalButton = new JButton("Adaugare Animal");
        actualizareAnimalButton = new JButton("Actualizare Animal");
        stergereAnimalButton = new JButton("Stergere Animal");
        refreshAnimalButton = new JButton("Refresh");
        crudPanel.add(adaugareAnimalButton);
        crudPanel.add(actualizareAnimalButton);
        crudPanel.add(stergereAnimalButton);
        crudPanel.add(refreshAnimalButton);
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.gridwidth = 4;
        operationsPanel.add(crudPanel, gbc);
        gbc.gridwidth = 1; // reset

        // Row 5: Search Panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        searchPanel.add(new JLabel("Cauta Animal (Nume):"));
        searchAnimalField = new JTextField(15);
        searchPanel.add(searchAnimalField);
        cautareAnimalButton = new JButton("Cautare Animal");
        searchPanel.add(cautareAnimalButton);
        gbc.gridx = 0; gbc.gridy = 5;
        gbc.gridwidth = 4;
        operationsPanel.add(searchPanel, gbc);
        gbc.gridwidth = 1;

        // === Middle Section: Filter and Export Controls in One Row ===
        JPanel filterExportPanel = new JPanel(new GridBagLayout());
        filterExportPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),
                "Filtrare si Export", TitledBorder.LEADING, TitledBorder.TOP));
        GridBagConstraints fgbc = new GridBagConstraints();
        fgbc.insets = new Insets(5, 5, 5, 5);
        fgbc.fill = GridBagConstraints.HORIZONTAL;
        fgbc.anchor = GridBagConstraints.WEST;

        // Filter Specie
        gbc.gridx = 0;
        gbc.gridy = 9;
        filterExportPanel.add(new JLabel("Filtreaza Nume:"), gbc);
        filterNameField = new JTextField(15);
        gbc.gridx = 1;
        filterExportPanel.add(filterNameField, gbc);

        // Filter Diet
        fgbc.gridx = 2;
        filterExportPanel.add(new JLabel("Filtreaza Alimentatie:"), fgbc);
        filterDietComboBox = new JComboBox<>(new String[]{"", "Ierbivor", "Carnivor", "Omnivor"});
        fgbc.gridx = 3;
        filterExportPanel.add(filterDietComboBox, fgbc);

        // Filter Habitat
        fgbc.gridx = 4;
        filterExportPanel.add(new JLabel("Filtreaza Habitat:"), fgbc);
        filterHabitatComboBox = new JComboBox<>(new String[]{"", "Terestru", "Acvatic", "Aviatic"});
        fgbc.gridx = 5;
        filterExportPanel.add(filterHabitatComboBox, fgbc);

        // Filter Button
        fgbc.gridx = 6;
        filterButton = new JButton("Filtreaza");
        filterExportPanel.add(filterButton, fgbc);

        // Export Buttons
        fgbc.gridx = 7;
        exportCSVButton = new JButton("Export CSV");
        filterExportPanel.add(exportCSVButton, fgbc);

        fgbc.gridx = 8;
        exportDocButton = new JButton("Export DOCX");
        filterExportPanel.add(exportDocButton, fgbc);

        // === Bottom Section: Table Panel for Animals ===
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),
                "Lista Animale", TitledBorder.LEADING, TitledBorder.TOP));
        tabelAnimale = new JTable(new DefaultTableModel());

        JScrollPane scrollPane = new JScrollPane(tabelAnimale);
        scrollPane.setPreferredSize(new Dimension(750, 250));
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        setupAnimalButtons();
        // Assemble main panel
        mainPanel.add(operationsPanel, BorderLayout.NORTH);
        mainPanel.add(filterExportPanel, BorderLayout.CENTER);
        mainPanel.add(tablePanel, BorderLayout.SOUTH);

        return mainPanel;
    }


    private void setupAnimalButtons() {

        exportCSVButton.addActionListener(e -> {
            List<Map<String, Object>> animals = animalPresenter.getAllAnimals();
            try {
                animalPresenter.saveAnimalsToCSV(animals, "D:\\proiectare software\\tema1_zoo\\src\\main\\resources\\animals.csv");
                JOptionPane.showMessageDialog(this, "Animals saved to CSV successfully!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error saving CSV: " + ex.getMessage());
            }
        });

        exportDocButton.addActionListener(e -> {
            List<Map<String, Object>> animals = animalPresenter.getAllAnimals();
            try {
                animalPresenter.saveAnimalsToDoc(animals, "D:\\proiectare software\\tema1_zoo\\src\\main\\resources\\animals.docx");
                JOptionPane.showMessageDialog(this, "Animals saved to DOCX successfully!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error saving DOCX: " + ex.getMessage());
            }
        });


        filterButton.addActionListener(e -> {
            String nameFilter = filterNameField.getText().trim();
            String selectedDiet = (String) filterDietComboBox.getSelectedItem();
            String selectedHabitat = (String) filterHabitatComboBox.getSelectedItem();
            animalPresenter.filterAnimals(nameFilter, selectedDiet, selectedHabitat);
        });


        adaugareAnimalButton.addActionListener(e -> {
                    animalPresenter.addAnimal(
                            getAnimalName(), getAnimalSpecie(), getAnimalDiet(), getAnimalHabitat(),
                            getAnimalWeight(), getAnimalAge(), getAnimalSpeed()
                    );
                    animalPresenter.loadAnimals();
                    populateAnimalComboBox();
                }
        );

        actualizareAnimalButton.addActionListener(e -> {
            int id = getIdSelectedAnimal();
            if (id != -1) {
                int row = tabelAnimale.getSelectedRow();
                String name = tabelAnimale.getValueAt(row, 1).toString();
                String specie = tabelAnimale.getValueAt(row, 2).toString();
                String diet = tabelAnimale.getValueAt(row, 3).toString();
                String habitat = tabelAnimale.getValueAt(row, 4).toString();
                String weight = tabelAnimale.getValueAt(row, 5).toString();
                String age = tabelAnimale.getValueAt(row, 6).toString();
                String speed = tabelAnimale.getValueAt(row, 7).toString();

                UpdateAnimalDialog updateDialog = new UpdateAnimalDialog(this, animalPresenter, id, name, specie, diet, habitat, weight, age, speed);
                updateDialog.setVisible(true);
            } else {
                showMessageAnimal("Selectați un animal pentru actualizare!");
            }
        });

        stergereAnimalButton.addActionListener(e -> {
            int id = getIdSelectedAnimal();
            if (id != -1) {
                animalPresenter.deleteAnimal(id);
            } else {
                showMessageAnimal("Selectați un animal pentru ștergere!");
            }
        });

        cautareAnimalButton.addActionListener(e -> {
            String query = getSearchAnimalQuery().trim();
            if (query.isEmpty()) {
                animalPresenter.loadAnimals();
            } else {
                animalPresenter.searchAnimalByName(query);
            }
        });

        refreshAnimalButton.addActionListener(e -> animalPresenter.loadAnimals());
    }

    // ================== EXEMPLAR PANEL ====================
    private JPanel createExemplarPanel() {
        JPanel panel = new JPanel(new BorderLayout(10,10));

        // Top: Form Panel for exemplar operations
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Exemplar Operations", TitledBorder.LEADING, TitledBorder.TOP));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,10,5,10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Select Animal:"), gbc);
        animalComboBox = new JComboBox<>();
        populateAnimalComboBox();
        gbc.gridx = 1;
        formPanel.add(animalComboBox, gbc);

        // Row 1: Exemplar Name (new field)
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Nume Exemplar:"), gbc);
        exemplarNameField = new JTextField(15);
        gbc.gridx = 1;
        formPanel.add(exemplarNameField, gbc);

        // Row 2: Locatie
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Locatie:"), gbc);
        locatieField = new JTextField(15);
        gbc.gridx = 1;
        formPanel.add(locatieField, gbc);

        // Row 3: Greutate
        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(new JLabel("Greutate:"), gbc);
        greutateExemplarField = new JTextField(15);
        gbc.gridx = 1;
        formPanel.add(greutateExemplarField, gbc);

        // Row 4: Varsta
        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(new JLabel("Varsta:"), gbc);
        varstaExemplarField = new JTextField(15);
        gbc.gridx = 1;
        formPanel.add(varstaExemplarField, gbc);

        // Row 5: Image Path
        gbc.gridx = 0;
        gbc.gridy = 5;
        formPanel.add(new JLabel("Image Path:"), gbc);
        imagePathField = new JTextField(15);
        gbc.gridx = 1;
        formPanel.add(imagePathField, gbc);

        // Row 6: CRUD Buttons for exemplare
        gbc.gridx = 0;
        gbc.gridy = 6;
        adaugareExemplarButton = new JButton("Adaugare Exemplar");
        formPanel.add(adaugareExemplarButton, gbc);
        gbc.gridx = 1;
        actualizareExemplarButton = new JButton("Actualizare Exemplar");
        formPanel.add(actualizareExemplarButton, gbc);
        gbc.gridx = 2;
        stergereExemplarButton = new JButton("Stergere Exemplar");
        formPanel.add(stergereExemplarButton, gbc);
        gbc.gridx = 3;
        refreshExemplarButton = new JButton("Refresh");
        formPanel.add(refreshExemplarButton, gbc);

        // Row 7: Search field and button for exemplare (by species, for example)
        gbc.gridx = 0;
        gbc.gridy = 7;
        formPanel.add(new JLabel("Cauta Exemplar (Specie):"), gbc);
        gbc.gridx = 1;
        searchExemplarField = new JTextField(15);
        formPanel.add(searchExemplarField, gbc);
        gbc.gridx = 2;
        cautareExemplarButton = new JButton("Cautare Exemplar");
        formPanel.add(cautareExemplarButton, gbc);

        setupExemplarButtons();

        // Bottom: Table Panel for exemplare
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Lista Exemplare", TitledBorder.LEADING, TitledBorder.TOP));
        tabelExemplare = new JTable(new DefaultTableModel());
        tabelExemplare.setRowHeight(110);


        JScrollPane scrollPane = new JScrollPane(tabelExemplare);
        scrollPane.setPreferredSize(new Dimension(750, 250));
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        panel.add(formPanel, BorderLayout.NORTH);
        panel.add(tablePanel, BorderLayout.CENTER);
        return panel;
    }
    private void populateAnimalComboBox() {
        animalComboBox.removeAllItems();
        List<String> items = animalPresenter.getAnimalComboItems();
        for (String item : items) {
            animalComboBox.addItem(item);
        }
    }


    private void setupExemplarButtons() {
        adaugareExemplarButton.addActionListener(e -> {
            try {
                int animalId = Integer.parseInt(getAnimalId());
                exemplarPresenter.addExemplar(
                        animalId,
                        getExemplarName(),
                        getExemplarLocation(),
                        getExemplarWeight(),
                        getExemplarAge(),
                        getExemplarImagePath()
                );
            } catch (NumberFormatException ex) {
                showMessageExemplar("Invalid Animal ID. Please enter a valid number.");
            }
        });

        actualizareExemplarButton.addActionListener(e -> {
            int id = getIdSelectedExemplar();
            if (id != -1) {
                int row = tabelExemplare.getSelectedRow();
                String animalIdStr = tabelExemplare.getValueAt(row, 2).toString();
                String[] parts = animalIdStr.split(" - ");
                String selectedAnimalId = parts[0];

                String exemplarName = tabelExemplare.getValueAt(row, 1).toString();
                String locatie = tabelExemplare.getValueAt(row, 3).toString();
                String weight = tabelExemplare.getValueAt(row, 4).toString();
                String age = tabelExemplare.getValueAt(row, 5).toString();
                String imagePath = tabelExemplare.getValueAt(row, 7).toString();

                UpdateExemplarDialog updateDialog = new UpdateExemplarDialog(
                        this,
                        exemplarPresenter,
                        id,
                        selectedAnimalId,
                        exemplarName,
                        locatie,
                        weight,
                        age,
                        imagePath,
                        animalPresenter
                );
                updateDialog.setVisible(true);
            } else {
                showMessageExemplar("Selectați un exemplar pentru actualizare!");
            }
        });



        stergereExemplarButton.addActionListener(e -> {
            int id = getIdSelectedExemplar();
            if (id != -1) {
                exemplarPresenter.deleteExemplar(id);
            } else {
                showMessageExemplar("Selectați un exemplar pentru ștergere!");
            }
        });

        cautareExemplarButton.addActionListener(e -> {
            String query = searchExemplarField.getText().trim();
            if (query.isEmpty()) {
                exemplarPresenter.loadExemplars();
            } else {
                exemplarPresenter.searchExemplarBySpecies(query);
            }
        });

        refreshExemplarButton.addActionListener(e -> exemplarPresenter.loadExemplars());
    }

    // ================== ANIMAL PRESENTER INTERFACE METHODS ====================
    @Override
    public String getAnimalName() {
        return numeAnimalField.getText();
    }

    @Override
    public String getAnimalSpecie() {
        return (String) specieComboBox.getSelectedItem();
    }

    @Override
    public String getAnimalDiet() {
        return (String) alimentatieComboBox.getSelectedItem();
    }

    @Override
    public String getAnimalHabitat() {
        return (String) habitatComboBox.getSelectedItem();
    }

    @Override
    public String getAnimalWeight() {
        return greutateAnimalField.getText();
    }

    @Override
    public String getAnimalAge() {
        return varstaAnimalField.getText();
    }

    @Override
    public String getAnimalSpeed() {
        return vitezaAnimalField.getText();
    }

    @Override
    public int getIdSelectedAnimal() {
        int row = tabelAnimale.getSelectedRow();
        return (row != -1) ? (int) tabelAnimale.getValueAt(row, 0) : -1;
    }

    public String getSearchAnimalQuery() {
        return searchAnimalField.getText();
    }


    @Override
    public void displayAnimals(List<Map<String, Object>> animals) {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Nume");
        model.addColumn("Specie");
        model.addColumn("Alimentatie");
        model.addColumn("Habitat");
        model.addColumn("Greutate");
        model.addColumn("Varsta");
        model.addColumn("Viteza");

        for (Map<String, Object> animal : animals) {
            model.addRow(new Object[]{
                    animal.get("id"),
                    animal.get("nume"),
                    animal.get("categorie"),
                    animal.get("alimentatie"),
                    animal.get("habitat"),
                    animal.get("greutate"),
                    animal.get("varsta"),
                    animal.get("viteza")
            });

        }
        tabelAnimale.setModel(model);
    }

    @Override
    public void displayAnimal(Map<String, Object> animal) {
        // Display single animal in form fields if needed
        numeAnimalField.setText(animal.get("name").toString());
        specieComboBox.setSelectedItem(animal.get("specie").toString());
        alimentatieComboBox.setSelectedItem(animal.get("diet").toString());
        habitatComboBox.setSelectedItem(animal.get("habitat").toString());
        greutateAnimalField.setText(animal.get("weight").toString());
        varstaAnimalField.setText(animal.get("age").toString());
        vitezaAnimalField.setText(animal.get("speed").toString());
    }

    @Override
    public void showMessageAnimal(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    // ================== EXEMPLAR PRESENTER INTERFACE METHODS ====================
    public String getExemplarName() {
        return exemplarNameField.getText();
    }

    @Override
    public String getExemplarLocation() {
        return locatieField.getText();
    }

    @Override
    public String getExemplarWeight() {
        return greutateExemplarField.getText();
    }

    @Override
    public String getExemplarAge() {
        return varstaExemplarField.getText();
    }

    @Override
    public String getExemplarImagePath() {
        return imagePathField.getText();
    }

    @Override
    public String getAnimalId() {
        String selected = (String) animalComboBox.getSelectedItem();
        if (selected != null && selected.contains(" - ")) {
            return selected.split(" - ")[0];
        }
        return "";
    }

    @Override
    public int getIdSelectedExemplar() {
        int row = tabelExemplare.getSelectedRow();
        return (row != -1) ? (int) tabelExemplare.getValueAt(row, 0) : -1;
    }

    @Override
    public void displayExemplars(List<Map<String, Object>> exemplars) {
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 6) {
                    return ImageIcon.class;
                }
                return String.class;
            }
        };

        model.addColumn("ID");
        model.addColumn("Nume");
        model.addColumn("Animal");
        model.addColumn("Locatie");
        model.addColumn("Greutate");
        model.addColumn("Varsta");
        model.addColumn("Photo");
        model.addColumn("Path");

        for (Map<String, Object> exemplar : exemplars) {
            String pathStr = exemplar.get("imagePathStr").toString();
            ImageIcon icon = new ImageIcon(pathStr);
            if (icon.getIconWidth() == -1) {
                icon = new ImageIcon("resources/placeholder.png");
            }
            Image scaledImage = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            icon = new ImageIcon(scaledImage);
            model.addRow(new Object[]{
                    exemplar.get("id"),
                    exemplar.get("nume"),
                    exemplar.get("animal"),
                    exemplar.get("location"),
                    exemplar.get("weight"),
                    exemplar.get("age"),
                    icon,
                    pathStr
            });
        }
        tabelExemplare.setModel(model);
        // Hide the "Path" column (index 7)
        tabelExemplare.getColumnModel().getColumn(7).setMinWidth(0);
        tabelExemplare.getColumnModel().getColumn(7).setMaxWidth(0);
        tabelExemplare.getColumnModel().getColumn(7).setWidth(0);
    }

    @Override
    public void displayExemplar(Map<String, Object> exemplar) {
        animalIdField.setText(exemplar.get("animal").toString());
        exemplarNameField.setText(exemplar.get("nume").toString());
        locatieField.setText(exemplar.get("location").toString());
        greutateExemplarField.setText(exemplar.get("weight").toString());
        varstaExemplarField.setText(exemplar.get("age").toString());
        imagePathField.setText(exemplar.get("imagePath").toString());
    }

    @Override
    public void showMessageExemplar(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ZooDesktopGUI::new);
    }
}
