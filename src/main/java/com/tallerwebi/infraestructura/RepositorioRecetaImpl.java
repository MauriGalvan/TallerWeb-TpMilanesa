package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Categoria;
import com.tallerwebi.dominio.Receta;
import com.tallerwebi.dominio.RepositorioReceta;
import com.tallerwebi.dominio.TiempoDePreparacion;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
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
        sessionFactory.getCurrentSession().save(receta);
    }


    @Override
    public void eliminar(Receta receta) {
        sessionFactory.getCurrentSession().delete(receta);
    }

    @Override
    public void actualizar(Receta receta) {
        sessionFactory.getCurrentSession().update(receta);
    }


    @Override
    public List<Receta> getRecetas() {
        String hql = "FROM Receta";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        return query.getResultList();
    }

    @Transactional
    @Override
    public Receta getRecetaPorId(int id) {
        String hql = "FROM Receta r WHERE r.id = :id";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("id", id);
        return (Receta)query.getSingleResult();
    }

    @Override
    public List<Receta> getRecetasPorCategoria(Categoria categoria){
        String hql = "FROM Receta r WHERE r.categoria = :categoria";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("categoria", categoria);
        return query.getResultList();
    }

    @Override
    public List<Receta> getRecetasPorTiempoDePreparacion(TiempoDePreparacion tiempo) {
        String hql = "FROM Receta r WHERE r.tiempo_preparacion = :tiempo";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("tiempo", tiempo);
        return query.getResultList();
    }

    @Override
    public List<Receta> getRecetasPorCategoriaYTiempoDePreparacion(Categoria categoria, TiempoDePreparacion tiempo) {
        String hql = "FROM Receta r WHERE r.tiempo_preparacion = :tiempo AND r.categoria = :categoria";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("tiempo", tiempo);
        query.setParameter("categoria", categoria);
        return query.getResultList();
    }

    @Override
    public List<Receta> buscarRecetasPorTitulo(String titulo) {
        String hql = "FROM Receta r WHERE lower(r.titulo) LIKE :titulo";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("titulo", "%" + titulo.toLowerCase() + "%");
        return query.getResultList();
    }

    @Override
    public List<Receta> buscarRecetasPorTituloYCategoria(String titulo, Categoria categoria) {
        String hql = "FROM Receta r WHERE lower(r.titulo) LIKE :titulo AND r.categoria = :categoria";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("titulo", "%" + titulo.toLowerCase() + "%");
        query.setParameter("categoria", categoria);
        return query.getResultList();
    }

    @Override
    public List<Receta> buscarRecetasPorTituloYTiempo(String titulo, TiempoDePreparacion tiempo) {
        String hql = "FROM Receta r WHERE lower(r.titulo) LIKE :titulo AND r.tiempo_preparacion = :tiempo";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("titulo", "%" + titulo.toLowerCase() + "%");
        query.setParameter("tiempo", tiempo);
        return query.getResultList();
    }

    @Override
    public List<Receta> buscarRecetasPorTituloCategoriaYTiempo(String titulo, Categoria categoria, TiempoDePreparacion tiempo) {
        String hql = "FROM Receta r WHERE lower(r.titulo) LIKE :titulo AND r.categoria = :categoria AND r.tiempo_preparacion = :tiempo";
        Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
        query.setParameter("titulo", "%" + titulo.toLowerCase() + "%");
        query.setParameter("categoria", categoria);
        query.setParameter("tiempo", tiempo);
        return query.getResultList();
    }



}
