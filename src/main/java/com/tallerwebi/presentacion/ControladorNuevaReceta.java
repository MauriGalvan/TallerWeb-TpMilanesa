package com.tallerwebi.presentacion;
import com.tallerwebi.dominio.ServicioRecetaImpl;
import com.tallerwebi.dominio.Receta;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class ControladorNuevaReceta {

    @Autowired
    private ServicioRecetaImpl ServicioRecetaImpl;

    @GetMapping("/nueva-receta")
    public String mostrarFormulario(Model model) {
        model.addAttribute("receta", new Receta());
        return "vistaReceta";
    }


    @PostMapping("/guardarReceta")
    public String guardarReceta(
                                @RequestParam("titulo") String titulo,
                                @RequestParam("descripcion") String descripcion,
                                @RequestParam("tiempo") Double tiempoPreparacion,
                                @RequestParam("categoria") String categoriaId,
                                @RequestParam("ingredientes") String  ingredientes,
                                @RequestParam("imagen") MultipartFile imagen
                                ) throws IOException {

        String imagenUrl = almacenarImagen(imagen);

        // Crear una nueva instancia de Receta
        Receta receta = new Receta();
        receta.setTitulo(titulo);
        receta.setDescripcion(descripcion);
        receta.setTiempo_preparacion(tiempoPreparacion);
        receta.setCategoria(categoriaId);
        receta.setIngredientes(ingredientes);
        receta.setImagen(imagenUrl);

        ServicioRecetaImpl.guardarReceta(receta);

        return "redirect:/vistaReceta";
    }

    private String almacenarImagen(MultipartFile imagen) throws IOException {
        String imagenUrl = imagen.getOriginalFilename();
        String directorioDestino = "src/main/webapp/resources/core/images/";

        Path rutaDestino = Paths.get(directorioDestino + imagenUrl);

        Files.copy(imagen.getInputStream(), rutaDestino, StandardCopyOption.REPLACE_EXISTING);

        return "/images/" + imagenUrl;
    }
}