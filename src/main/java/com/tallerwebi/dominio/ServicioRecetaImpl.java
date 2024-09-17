package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;

public class ServicioRecetaImpl implements ServicioReceta {

    private RepositorioReceta repositorioReceta;

    @Autowired
    public ServicioRecetaImpl(RepositorioReceta repositorioReceta) {
        this.repositorioReceta = repositorioReceta;
    }

    @Override
    public void guardarReceta(Receta receta){
        this.repositorioReceta.guardar(receta);
    }

}
