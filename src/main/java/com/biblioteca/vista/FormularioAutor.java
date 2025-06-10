package com.biblioteca.vista;

import com.biblioteca.dao.AutorDAO;
import com.biblioteca.modelo.Autor;

import javax.persistence.EntityManagerFactory;
import javax.swing.*;
import java.awt.*;

public class FormularioAutor extends JDialog {
    private final AutorDAO autorDAO;
    private final JTextField txtNombre;
    private final JTextField txtNacionalidad;

    public FormularioAutor(JFrame parent, EntityManagerFactory emf) {
        super(parent, "Agregar Autor", true);
        setSize(350, 200);
        setLocationRelativeTo(parent);

        autorDAO = new AutorDAO(emf);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panel.add(txtNombre);

        panel.add(new JLabel("Nacionalidad:"));
        txtNacionalidad = new JTextField();
        panel.add(txtNacionalidad);

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(e -> guardarAutor());
        panel.add(btnGuardar);

        getContentPane().add(panel);
    }

    private void guardarAutor() {
        String nombre = txtNombre.getText().trim();
        String nacionalidad = txtNacionalidad.getText().trim();

        if (nombre.isEmpty() || nacionalidad.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe completar ambos campos.");
            return;
        }

        Autor autor = new Autor();
        autor.setNombre(nombre);
        autor.setNacionalidad(nacionalidad);
        autorDAO.guardarAutor(autor);

        JOptionPane.showMessageDialog(this, "Autor guardado exitosamente.");
        dispose();
    }
}
