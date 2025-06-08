package com.biblioteca.dao;

import com.biblioteca.modelo.Autor;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class AutorDAO {
    private final EntityManagerFactory emf;

    public AutorDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public void guardarAutor(Autor autor) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(autor);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
    
public void eliminarAutor(Autor autor) {
    EntityManager em = emf.createEntityManager();
    try {
        em.getTransaction().begin();
        Autor autorAEliminar = em.find(Autor.class, autor.getId());
        if (autorAEliminar != null) {
            em.remove(autorAEliminar);
        }
        em.getTransaction().commit();
    } finally {
        em.close();
    }
}

    public List<Autor> obtenerTodos() {
        EntityManager em = emf.createEntityManager();
        List<Autor> autores = em.createQuery("SELECT a FROM Autor a", Autor.class).getResultList();
        em.close();
        return autores;
    }
}
