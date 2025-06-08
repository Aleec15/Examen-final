package com.biblioteca.vista;

import com.biblioteca.dao.AutorDAO;
import com.biblioteca.dao.LibroDAO;
import com.biblioteca.modelo.Autor;
import com.biblioteca.modelo.Libro;

import javax.persistence.EntityManagerFactory;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class FormularioLibro extends JDialog {
    private final LibroDAO libroDAO;
    private final AutorDAO autorDAO;
    private final JTextField txtTitulo;
    private final JTextField txtAnio;
    private final JComboBox<Autor> comboAutores;

    public FormularioLibro(JFrame parent, EntityManagerFactory emf) {
        super(parent, "Agregar Libro", true);
        setSize(400, 250);
        setLocationRelativeTo(parent);

        libroDAO = new LibroDAO(emf);
        autorDAO = new AutorDAO(emf);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(new JLabel("Título:"));
        txtTitulo = new JTextField();
        panel.add(txtTitulo);

        panel.add(new JLabel("Año:"));
        txtAnio = new JTextField();
        panel.add(txtAnio);

        panel.add(new JLabel("Autor:"));
        comboAutores = new JComboBox<>();
        cargarAutores();
        panel.add(comboAutores);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(e -> guardarLibro());
        panel.add(btnGuardar);

        getContentPane().add(panel);
    }

    private void cargarAutores() {
        List<Autor> autores = autorDAO.obtenerTodos();
        comboAutores.removeAllItems();
        for (Autor autor : autores) {
            comboAutores.addItem(autor);
        }
    }

    private void guardarLibro() {
        String titulo = txtTitulo.getText();
        int anio;
        try {
            anio = Integer.parseInt(txtAnio.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Año debe ser un número.");
            return;
        }

        Autor autor = (Autor) comboAutores.getSelectedItem();
        if (autor == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un autor.");
            return;
        }

        Libro libro = new Libro();
        libro.setTitulo(titulo);
        libro.setAnio(anio);
        libro.setAutor(autor);

        libroDAO.guardarLibro(libro);
        JOptionPane.showMessageDialog(this, "Libro guardado exitosamente.");
        dispose();
    }
}
