package com.biblioteca.vista;

import com.biblioteca.dao.LibroDAO;
import com.biblioteca.modelo.Libro;

import javax.persistence.EntityManagerFactory;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class VentanaLibro extends JFrame {
    private final LibroDAO libroDAO;
    private final DefaultListModel<String> listModel;
    private final JList<String> listaLibros;

    public VentanaLibro(EntityManagerFactory emf) {
        libroDAO = new LibroDAO(emf);

        setTitle("Listado de Libros");
        setSize(500, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        listModel = new DefaultListModel<>();
        listaLibros = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(listaLibros);

        JButton btnEliminar = new JButton("Eliminar Libro");
        btnEliminar.addActionListener(e -> eliminarLibro());

        JPanel panelBoton = new JPanel();
        panelBoton.add(btnEliminar);

        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(panelBoton, BorderLayout.SOUTH);

        cargarLibros();
    }

    private void cargarLibros() {
        listModel.clear();
        List<Libro> libros = libroDAO.obtenerTodosLosLibros();
        for (Libro libro : libros) {
            String autorNombre = (libro.getAutor() != null) ? libro.getAutor().getNombre() : "Autor desconocido";
            listModel.addElement(libro.getTitulo() + " (" + libro.getAnio() + ") - Autor: " + autorNombre);
        }
    }

    private void eliminarLibro() {
        int index = listaLibros.getSelectedIndex();
        if (index != -1) {
            List<Libro> libros = libroDAO.obtenerTodosLosLibros();
            Libro libroSeleccionado = libros.get(index);

            int confirmacion = JOptionPane.showConfirmDialog(this,
                    "¿Está seguro de eliminar el libro seleccionado?",
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION);

            if (confirmacion == JOptionPane.YES_OPTION) {
                libroDAO.eliminarLibro(libroSeleccionado);
                cargarLibros();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un libro.");
        }
    }
}
