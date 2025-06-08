package com.biblioteca.vista;

import com.biblioteca.dao.AutorDAO;
import com.biblioteca.modelo.Autor;

import javax.persistence.EntityManagerFactory;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class VentanaAutor extends JFrame {
    private final AutorDAO autorDAO;
    private final DefaultListModel<Autor> listModel;
    private final JList<Autor> listaAutores;

    public VentanaAutor(EntityManagerFactory emf) {
        autorDAO = new AutorDAO(emf);

        setTitle("Listado de Autores");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        listModel = new DefaultListModel<>();
        listaAutores = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(listaAutores);

        JButton btnEliminar = new JButton("Eliminar Autor");
        btnEliminar.addActionListener(e -> eliminarAutor());

        JPanel panelBoton = new JPanel();
        panelBoton.add(btnEliminar);

        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(panelBoton, BorderLayout.SOUTH);

        cargarAutores();
    }

    private void cargarAutores() {
        listModel.clear();
        List<Autor> autores = autorDAO.obtenerTodos();
        for (Autor autor : autores) {
            listModel.addElement(autor);
        }
    }

    private void eliminarAutor() {
        Autor autorSeleccionado = listaAutores.getSelectedValue();
        if (autorSeleccionado != null) {
            int confirmacion = JOptionPane.showConfirmDialog(this,
                    "¿Está seguro de eliminar el autor seleccionado?",
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION);

            if (confirmacion == JOptionPane.YES_OPTION) {
                autorDAO.eliminarAutor(autorSeleccionado);
                cargarAutores();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un autor.");
        }
    }
}
