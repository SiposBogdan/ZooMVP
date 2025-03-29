package org.example;

import view.ZooDesktopGUI;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

            SwingUtilities.invokeLater(ZooDesktopGUI::new);
    }

}