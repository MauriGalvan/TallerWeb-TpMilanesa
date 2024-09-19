package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Receta;
import com.tallerwebi.dominio.RepositorioReceta;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import org.hibernate.query.Query;

import javax.transaction.Transactional;
import java.util.List;



@Repository
public class RepositorioRecetaImpl implements RepositorioReceta {

    @Autowired
    private SessionFactory sessionFactory;

    public RepositorioRecetaImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardar(Receta receta) {
        this.sessionFactory.getCurrentSession().save(receta);
    }

    @Override
    public List<Receta> getRecetas() {
        String hql = "FROM Receta";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        return query.getResultList();
    }

    @Override
    public Receta getRecetaPorId(int id) {
        List<Receta> recetas = getRecetas();
        Receta recetaEncontrada = null;
        for (Receta receta : recetas){
            if (id == receta.getId()){
                recetaEncontrada = receta;
            }
        }
        return recetaEncontrada;
    }

    @Transactional
    @Override
    public Receta getReceta(int id) {
        String hql = "From Receta r WHERE r.id = :id";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("id", id);
        return (Receta) query.uniqueResult();
    }
}
