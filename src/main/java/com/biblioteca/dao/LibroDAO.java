package com.biblioteca.dao;

import com.biblioteca.modelo.Libro;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class LibroDAO {
    private final EntityManagerFactory emf;

    public LibroDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public void guardarLibro(Libro libro) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(libro);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void eliminarLibro(Libro libro) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Libro libroAEliminar = em.find(Libro.class, libro.getId());
            if (libroAEliminar != null) {
                em.remove(libroAEliminar);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public List<Libro> obtenerTodosLosLibros() {
        EntityManager em = emf.createEntityManager();
        List<Libro> libros = em.createQuery("SELECT l FROM Libro l", Libro.class).getResultList();
        em.close();
        return libros;
    }
}
