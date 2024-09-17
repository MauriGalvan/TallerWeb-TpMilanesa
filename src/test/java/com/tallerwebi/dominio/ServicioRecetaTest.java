package com.tallerwebi.dominio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

public class ServicioRecetaTest {

    @Mock
    private RepositorioReceta repositorioReceta;
    private ServicioReceta servicioReceta;

    @BeforeEach
    public void inicializar(){
        this.repositorioReceta = mock(RepositorioReceta.class);
        this.servicioReceta = new ServicioRecetaImpl(repositorioReceta);
    }

    @Test
    public void queSePuedaGuardarUnaReceta(){
        String titulo = "Milanesa napolitana";
        double tiempo_preparacion = 1.0;
        String categoria = "almuerzo";
        String imagen = "https://i.postimg.cc/7hbGvN2c/mila-napo.webp";
        ArrayList<String> ingredientes = new ArrayList<>(Arrays.asList("Jamón", "Queso", "Tapa pascualina", "Huevo", "Tomate"));
        String descripcion = "Esto es una descripción de mila napo";

        Receta receta = new Receta(titulo, tiempo_preparacion, categoria, imagen, ingredientes, descripcion);

        Mockito.when(repositorioReceta.guardar(any(Receta.class))).thenReturn(receta);

        servicioReceta.guardarReceta(receta);

        Mockito.verify(repositorioReceta, times(1)).guardar(receta);

    }

    @Test
    public void siLosDatosParaAgregarRecetaNoEstanCompletosEntoncesNoSeAgregaReceta(){

    }

    @Test
    public void siLosDatosParaAgregarRecetaEstanCompletosEntoncesSeAgregaReceta(){

    }


}
