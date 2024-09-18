package com.tallerwebi.presentacion;
import com.tallerwebi.dominio.ServicioRecetaImpl;
import com.tallerwebi.dominio.Receta;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

public class ControladorNuevaReceta {

    @Autowired
    private ServicioRecetaImpl ServicioRecetaImpl;

    @PostMapping("/guardar")
    public String guardarReceta(@RequestParam("titulo") String titulo,
                                @RequestParam("descripcion") String descripcion,
                                @RequestParam("tiempo") Double tiempoPreparacion,
                                @RequestParam("categoria") String categoriaId,
                                @RequestParam("ingredientes") String  ingredientes,
                                @RequestParam("imagen") MultipartFile imagen
                                ) {

        // Crear una nueva instancia de Receta
        Receta receta = new Receta();
        receta.setTitulo(titulo);
        receta.setDescripcion(descripcion);
        receta.setTiempo_preparacion(tiempoPreparacion);
        receta.setCategoria(categoriaId);
        receta.setIngredientes(ingredientes);

        ServicioRecetaImpl.guardarReceta(receta);

        return "redirect:/vistaReceta";
    }
}