package com.biblioteca.vista;

import com.biblioteca.dao.AutorDAO;
import com.biblioteca.dao.LibroDAO;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal extends JFrame {
    private final EntityManagerFactory emf;
    private final AutorDAO autorDAO;
    private final LibroDAO libroDAO;

    public VentanaPrincipal() {
        emf = Persistence.createEntityManagerFactory("BibliotecaPU");
        autorDAO = new AutorDAO(emf);
        libroDAO = new LibroDAO(emf);

        setTitle("Sistema de Biblioteca");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1, 10, 10));

        JButton btnAutores = new JButton("Gestionar Autores");
        btnAutores.addActionListener(e -> {
            VentanaAutor ventanaAutor = new VentanaAutor(emf);
            ventanaAutor.setVisible(true);
        });

        JButton btnLibros = new JButton("Gestionar Libros");
        btnLibros.addActionListener(e -> {
            VentanaLibro ventanaLibro = new VentanaLibro(emf);
            ventanaLibro.setVisible(true);
        });

        JButton btnAgregarLibro = new JButton("Agregar Libro");
        btnAgregarLibro.addActionListener(e -> {
            FormularioLibro formulario = new FormularioLibro(this, emf);
            formulario.setVisible(true);
        });

        panel.add(btnAutores);
        panel.add(btnLibros);
        panel.add(btnAgregarLibro);

        add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VentanaPrincipal ventana = new VentanaPrincipal();
            ventana.setVisible(true);
        });
    }
}
