package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Receta;
import com.tallerwebi.dominio.ServicioReceta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ControladorDetalleTest {


    private ControladorDetalleReceta controladorDetalleReceta;
    private ServicioReceta servicioRecetaMock;

    @BeforeEach
    public void setup() {
        servicioRecetaMock = mock(ServicioReceta.class);
        controladorDetalleReceta = new ControladorDetalleReceta(servicioRecetaMock);
    }

    @Test
    public void DebeRetornarLaVistaDetalleRecetaYSolamenteElNombreDeEsaComidaCuandoSeEjecutaElMetodoMostrarDetalleReceta(){
        //DADO
        int id = 1;
        String nombreReceta = "Milanesa napolitana";
        Receta recetaMock = new Receta();
        recetaMock.setId(id);
        recetaMock.setTitulo(nombreReceta);
        //CUANDO
        when(servicioRecetaMock.getUnaReceta(id)).thenReturn(recetaMock);
        ModelAndView modelAndView = controladorDetalleReceta.mostrarDetalleReceta(recetaMock.getId());
        //ENTONCES
        Receta recetaEnModelo = (Receta) modelAndView.getModel().get("unaReceta");
        assertThat(recetaEnModelo.getTitulo(), equalTo("Milanesa napolitana"));
    }

    @Test
    public void DebeRetornarLaVistaDetalleRecetaYSolamenteElNombreYLaImagenDeEsaComidaCuandoSeEjecutaElMetodoMostrarDetalleReceta(){
        //DADO
        int id = 1;
        String nombreReceta = "Milanesa napolitana";
        Receta recetaMock = new Receta();
        recetaMock.setId(id);
        recetaMock.setTitulo(nombreReceta);
        recetaMock.setImagen("https://i.postimg.cc/7hbGvN2c/mila-napo.webp");
        //CUANDO
        when(servicioRecetaMock.getUnaReceta(id)).thenReturn(recetaMock);
        ModelAndView modelAndView = controladorDetalleReceta.mostrarDetalleReceta(recetaMock.getId());
        //ENTONCES
        Receta recetaEnModelo = (Receta) modelAndView.getModel().get("unaReceta");
        assertThat(recetaEnModelo.getImagen(), equalTo("https://i.postimg.cc/7hbGvN2c/mila-napo.webp"));
    }


    @Test
    public void DebeRetornarLaVistaDetalleRecetaYSolamenteElNombreLaImagenYlosIngredientesDeEsaComidaCuandoSeEjecutaElMetodoMostrarDetalleReceta(){
        //DADO
        int id = 1;
        String nombreReceta = "Milanesa napolitana";
        Receta recetaMock = new Receta();
        recetaMock.setId(id);
        recetaMock.setTitulo(nombreReceta);
        recetaMock.setImagen("https://i.postimg.cc/7hbGvN2c/mila-napo.webp");
        recetaMock.setIngredientes("Jamón, Queso, Tapa pascualina, Huevo, Tomate");
        //CUANDO
        when(servicioRecetaMock.getUnaReceta(id)).thenReturn(recetaMock);
        ModelAndView modelAndView = controladorDetalleReceta.mostrarDetalleReceta(recetaMock.getId());
        //ENTONCES
        Receta recetaEnModelo = (Receta) modelAndView.getModel().get("unaReceta");
        assertThat(recetaEnModelo.getIngredientes(), equalTo("Jamón, Queso, Tapa pascualina, Huevo, Tomate"));
    }

}
