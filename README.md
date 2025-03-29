# 🐾 Zoo Management System – Java Desktop App

A desktop application developed in **Java** using **Swing** and the **MVP (Model-View-Presenter)** architecture, designed to efficiently manage animals and their specimens in a zoo.

---

## 🎯 Purpose

This project demonstrates clean software architecture and design by implementing **MVP**, allowing a clear separation between:

- **Model**: Business logic and data
- **View**: GUI interface built with Swing
- **Presenter**: The mediator between View and Model

It supports functionalities such as adding, modifying, deleting, filtering, and exporting animals and their specimens.

---

## 🧱 Architecture: MVP Breakdown

### ✅ Model (`model.*`)
Contains all data logic and domain structure:

- **`entity` package**:  
  - `Animal` – Contains species data (name, category, habitat, etc.)  
  - `Exemplar` (a.k.a. *Specimen*) – A concrete instance of an `Animal` in the zoo (location, weight, image, etc.)

- **`repository` package**:  
  Handles communication with the SQL database using JDBC:
  - `AnimalRepository`
  - `ExemplarRepository` (handles specimen data)
  - `Repository` (base DB connection)

- **`enums` package**:  
  Enum types for domain constraints:
  - `Categorie`
  - `Habitat`
  - `Alimentatie`

---

### ✅ View (`view.*`)
Implements the GUI layer using **Java Swing**:

- `ZooDesktopGUI` – The main window with tabs for Animals and Specimens
- `UpdateAnimalDialog` – Dialog to update animal data
- `UpdateExemplarDialog` – Dialog to update specimen data

💡 **No business logic is placed in the view**, following the MVP principle.

---

### ✅ Presenter (`presenter.*`)
Contains the mediators that handle user interaction logic:

- `AnimalPresenter` – Talks to `AnimalRepository`, updates the GUI via `AnimalPresenterInterface`
- `ExemplarPresenter` – Connects the specimen view with its data repository
- Interfaces `AnimalPresenterInterface` and `ExemplarPresenterInterface` allow the presenters to remain decoupled from the actual GUI

📌 **Presenters don’t know about Swing – they only use interface methods**, ensuring clean decoupling.

---

## 📦 Packages Overview

```plaintext
model
├── entity
│   ├── Animal.java
│   └── Exemplar.java    (represents specimens)
├── enums
│   ├── Alimentatie.java
│   ├── Categorie.java
│   └── Habitat.java
├── repository
│   ├── Repository.java
│   ├── AnimalRepository.java
│   └── ExemplarRepository.java

presenter
├── AnimalPresenter.java
├── ExemplarPresenter.java
├── AnimalPresenterInterface.java
└── ExemplarPresenterInterface.java

view
├── ZooDesktopGUI.java
├── UpdateAnimalDialog.java
└── UpdateExemplarDialog.java
