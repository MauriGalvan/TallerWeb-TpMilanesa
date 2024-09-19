package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Receta;
import com.tallerwebi.dominio.ServicioReceta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
public class ControladorDetalleReceta {

    private final ServicioReceta servicioReceta;

    @Autowired
    public ControladorDetalleReceta(ServicioReceta servicioRecetaMock) {
        this.servicioReceta = servicioRecetaMock;
    }

//    @RequestMapping(value = "/detalleReceta")
//    public ModelAndView mostrarDetalleReceta(@ModelAttribute Receta receta) {
//        ModelMap modelo = new ModelMap();
//        modelo.put("titulo", receta.getTitulo());
//        modelo.put("imagen", receta.getImagen());
//        modelo.put("ingredientes", receta.getIngredientes());
//
//        return new ModelAndView("detalleReceta", modelo);
//    }
    @RequestMapping("/detalleReceta")
    public ModelAndView mostrarDetalleReceta(int id) {
        ModelMap modelo = new ModelMap();
        Receta r = servicioReceta.getUnaReceta(id);
        modelo.put("unaReceta", r);
        return new ModelAndView("detalleReceta", modelo);
    }
}
