package com.tallerwebi.dominio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

public class ServicioRecetaTest {

    @Mock
    private RepositorioReceta repositorioReceta;
    @Mock
    private RepositorioIngrediente repositorioIngrediente;

    private ServicioReceta servicioReceta;

    @BeforeEach
    public void inicializar(){
        this.repositorioReceta = mock(RepositorioReceta.class);
        this.repositorioIngrediente = mock(RepositorioIngrediente.class);
        this.servicioReceta = new ServicioRecetaImpl(repositorioReceta, repositorioIngrediente);
    }

    private List<Ingrediente> unosIngredientes(){
        return Arrays.asList(
                new Ingrediente("Carne", 1, Unidad_De_Medida.KILOGRAMOS, Tipo_Ingrediente.PROTEINA_ANIMAL),
                new Ingrediente("Huevo", 2, Unidad_De_Medida.UNIDAD, Tipo_Ingrediente.PROTEINA_ANIMAL),
                new Ingrediente("Papas", 10, Unidad_De_Medida.UNIDAD, Tipo_Ingrediente.VERDURA),
                new Ingrediente("Pan rallado", 200, Unidad_De_Medida.GRAMOS, Tipo_Ingrediente.CEREAL_O_GRANO)
        );
    }
    private Receta recetaMilanesaNapolitanaDeTreintaMinCreada(){
        byte[] imagen = new byte[]{0, 1};
        return new Receta ("Milanesa napolitana", TiempoDePreparacion.TREINTA_MIN, Categoria.ALMUERZO_CENA,
                imagen, this.unosIngredientes(), "Esto es una descripción de mila napo", ".");
    }
    private Receta recetaMilanesaConPapasDeVeinteMinCreada(){
        byte[] imagen = new byte[]{0, 1};
        return new Receta ("Milanesa con papas fritas", TiempoDePreparacion.VEINTE_MIN, Categoria.ALMUERZO_CENA,
                imagen, this.unosIngredientes(), "Milanesa con guarnición de papas fritas", ".");
    }
    private Receta recetaCafeConLecheDeDiezMinCreada(){
        byte[] imagen = new byte[]{0, 1};
        return new Receta ("Café cortado con tostadas", TiempoDePreparacion.DIEZ_MIN, Categoria.DESAYUNO_MERIENDA,
                imagen, this.unosIngredientes(), "Un clásico de las mañanas.", ".");
    }

    @Test
    public void queSePuedaGuardarUnaReceta() {
        Receta receta = this.recetaMilanesaNapolitanaDeTreintaMinCreada();
        int idBuscado = receta.getId();
        MultipartFile imagenMock = mock(MultipartFile.class);

        servicioReceta.guardarReceta(receta, imagenMock);

        Mockito.when(repositorioReceta.getRecetaPorId(idBuscado)).thenReturn(receta);
        Mockito.verify(repositorioReceta, times(1)).guardar(receta);
    }

    @Test
    public void queSePuedaFiltrarPorCategoriaYSeMuestrenSoloLosDesayunosYMeriendas(){
        Receta receta = this.recetaMilanesaNapolitanaDeTreintaMinCreada();
        Receta receta1 = this.recetaCafeConLecheDeDiezMinCreada();
        Categoria categoria = Categoria.DESAYUNO_MERIENDA;

        List<Receta> todasLasRecetasYaFiltradas = new ArrayList<>();
        todasLasRecetasYaFiltradas.add(receta1);

        Mockito.when(repositorioReceta.getRecetasPorCategoria(categoria)).thenReturn(todasLasRecetasYaFiltradas);

        List<Receta> recetasFiltradas = servicioReceta.getRecetasPorCategoria(categoria);

        Mockito.verify(repositorioReceta, times(1)).getRecetasPorCategoria(categoria);

        assertEquals(1, recetasFiltradas.size());
        assertThat(recetasFiltradas, not(hasItem(receta)));
    }

    @Test
    public void queSePuedaFiltrarPorTiempoDePreparacionYSeMuestrenSoloLosQueTienen30Min(){
        Receta receta = this.recetaMilanesaNapolitanaDeTreintaMinCreada();
        Receta receta1 = this.recetaCafeConLecheDeDiezMinCreada();
        TiempoDePreparacion tiempo_preparacion = TiempoDePreparacion.TREINTA_MIN;

        List<Receta> todasLasRecetasYaFiltradas = new ArrayList<>();
        todasLasRecetasYaFiltradas.add(receta);

        Mockito.when(repositorioReceta.getRecetasPorTiempoDePreparacion(tiempo_preparacion)).thenReturn(todasLasRecetasYaFiltradas);

        List<Receta> recetasFiltradas = servicioReceta.getRecetasPorTiempoDePreparacion(tiempo_preparacion);

        Mockito.verify(repositorioReceta, times(1)).getRecetasPorTiempoDePreparacion(tiempo_preparacion);

        assertEquals(1, recetasFiltradas.size());
        assertThat(recetasFiltradas, not(hasItem(receta1)));
    }

    @Test
    public void queSePuedaFiltrarPorCategoriaYPorTiempoYSeMuestreElAlmuerzoOCenaDe30Min(){
        Receta receta = this.recetaMilanesaNapolitanaDeTreintaMinCreada();
        Receta receta1 = this.recetaCafeConLecheDeDiezMinCreada();
        Receta receta2 = this.recetaMilanesaConPapasDeVeinteMinCreada();
        TiempoDePreparacion tiempo_preparacion = TiempoDePreparacion.TREINTA_MIN;
        Categoria categoria = Categoria.ALMUERZO_CENA;

        List<Receta> todasLasRecetasYaFiltradas = new ArrayList<>();
        todasLasRecetasYaFiltradas.add(receta);

        Mockito.when(repositorioReceta.getRecetasPorCategoriaYTiempoDePreparacion(categoria, tiempo_preparacion)).thenReturn(todasLasRecetasYaFiltradas);

        List<Receta> recetasFiltradas = servicioReceta.getRecetasPorCategoriaYTiempoDePreparacion(categoria, tiempo_preparacion);

        Mockito.verify(repositorioReceta, times(1)).getRecetasPorCategoriaYTiempoDePreparacion(categoria, tiempo_preparacion);

        assertEquals(1, recetasFiltradas.size());
        assertThat(recetasFiltradas, not(hasItem(receta1)));
        assertThat(recetasFiltradas, not(hasItem(receta2)));
    }

    @Test
    public void queSePuedaObtenerUnaRecetaPorId(){
        Receta receta = this.recetaCafeConLecheDeDiezMinCreada();

        Mockito.when(repositorioReceta.getRecetaPorId(receta.getId())).thenReturn(receta);

        servicioReceta.getUnaRecetaPorId(receta.getId());

        Mockito.verify(repositorioReceta, times(1)).getRecetaPorId(receta.getId());
    }

    @Test
    public void queSePuedaEliminarUnaReceta() {
        // Crea la receta de prueba y configura el mock
        Receta receta = this.recetaMilanesaNapolitanaDeTreintaMinCreada();

        // Configura el mock para que devuelva la receta cuando se llame a getRecetaPorId
        Mockito.when(repositorioReceta.getRecetaPorId(receta.getId())).thenReturn(receta);

        // Llama al método que queremos probar
        servicioReceta.eliminarReceta(receta);

        // Verifica que se haya llamado a getRecetaPorId y luego a eliminar en repositorioReceta
        Mockito.verify(repositorioReceta, times(1)).getRecetaPorId(receta.getId());
        Mockito.verify(repositorioReceta, times(1)).eliminar(receta);

        // Verifica que se eliminen los ingredientes de la receta
        for (Ingrediente ingrediente : receta.getIngredientes()) {
            Mockito.verify(repositorioIngrediente, times(1)).eliminar(ingrediente);
        }
    }

    @Test
    public void queSePuedaBuscarRecetasPorTituloYSeEncuentrenLasCorrectas() {
        // Definir algunos títulos y recetas
        String tituloBuscado = "Milanesa";
        Receta receta1 = this.recetaMilanesaNapolitanaDeTreintaMinCreada();
        Receta receta2 = this.recetaMilanesaConPapasDeVeinteMinCreada();
        Receta receta3 = this.recetaCafeConLecheDeDiezMinCreada();

        List<Receta> recetasFiltradas = new ArrayList<>();
        recetasFiltradas.add(receta1);
        recetasFiltradas.add(receta2);

        Mockito.when(repositorioReceta.buscarRecetasPorTitulo(tituloBuscado)).thenReturn(recetasFiltradas);

        List<Receta> recetasEncontradas = servicioReceta.buscarRecetasPorTitulo(tituloBuscado);

        Mockito.verify(repositorioReceta, times(1)).buscarRecetasPorTitulo(tituloBuscado);

        assertEquals(2, recetasEncontradas.size());
        assertThat(recetasEncontradas, hasItem(hasProperty("titulo", equalTo("Milanesa napolitana"))));
        assertThat(recetasEncontradas, hasItem(hasProperty("titulo", equalTo("Milanesa con papas fritas"))));
        assertThat(recetasEncontradas, not(hasItem(receta3)));
    }

    @Test
    public void queSePuedaBuscarRecetasPorTituloYNoSeEncuentrenCoincidencias() {
        String tituloBuscado = "Pizza";

        Mockito.when(repositorioReceta.buscarRecetasPorTitulo(tituloBuscado)).thenReturn(new ArrayList<>());

        List<Receta> recetasEncontradas = servicioReceta.buscarRecetasPorTitulo(tituloBuscado);

        Mockito.verify(repositorioReceta, times(1)).buscarRecetasPorTitulo(tituloBuscado);

        assertEquals(0, recetasEncontradas.size());
    }

    @Test
    public void queSePuedanActualizarLasVisitasDeLasRecetas() {
        Receta receta = this.recetaMilanesaNapolitanaDeTreintaMinCreada();
        Receta receta1 = this.recetaCafeConLecheDeDiezMinCreada();

        this.servicioReceta.actualizarVisitasDeReceta(receta);
        this.servicioReceta.actualizarVisitasDeReceta(receta);
        this.servicioReceta.actualizarVisitasDeReceta(receta1);

        Mockito.verify(repositorioReceta, times(2)).actualizar(receta);
        Mockito.verify(repositorioReceta, times(1)).actualizar(receta1);
        assertEquals(receta.getContadorVisitas(), 2);
        assertEquals(receta1.getContadorVisitas(), 1);
    }

    @Test
    public void queSePuedanBuscarTodasLasRecetasYSePuedanOrdenarPorPopularidadDependiendoLaCantidadDeVisitas() {
        Receta receta = this.recetaMilanesaNapolitanaDeTreintaMinCreada();
        Receta receta1 = this.recetaCafeConLecheDeDiezMinCreada();
        Receta receta2 = this.recetaMilanesaConPapasDeVeinteMinCreada();
        List<Receta> todasLasRecetas = new ArrayList<>();
        todasLasRecetas.add(receta);
        todasLasRecetas.add(receta1);
        todasLasRecetas.add(receta2);

        Mockito.when(repositorioReceta.getRecetas()).thenReturn(todasLasRecetas);
        this.servicioReceta.actualizarVisitasDeReceta(receta);
        this.servicioReceta.actualizarVisitasDeReceta(receta);
        this.servicioReceta.actualizarVisitasDeReceta(receta1);

        Mockito.verify(repositorioReceta, times(2)).actualizar(receta);
        Mockito.verify(repositorioReceta, times(1)).actualizar(receta1);
        assertEquals(receta.getContadorVisitas(), 2);
        assertEquals(receta1.getContadorVisitas(), 1);
        assertEquals(receta2.getContadorVisitas(), 0);
        //la posición
        assertEquals(receta, todasLasRecetas.get(0));
        assertEquals(receta1, todasLasRecetas.get(1));
        assertEquals(receta2, todasLasRecetas.get(2));
    }

    @Test
    public void queSePuedanFiltrarLasRecetasPorAlmuerzoOCenaYSePuedanOrdenarPorPopularidadDependiendoLaCantidadDeVisitas() {
        Receta receta = this.recetaMilanesaNapolitanaDeTreintaMinCreada();
        Receta receta1 = this.recetaCafeConLecheDeDiezMinCreada();
        Receta receta2 = this.recetaMilanesaConPapasDeVeinteMinCreada();
        Categoria categoria = Categoria.ALMUERZO_CENA;
        List<Receta> recetasFiltradas = new ArrayList<>();
        recetasFiltradas.add(receta);
        recetasFiltradas.add(receta2);

        Mockito.when(repositorioReceta.getRecetasPorCategoria(categoria)).thenReturn(recetasFiltradas);
        this.servicioReceta.actualizarVisitasDeReceta(receta);
        this.servicioReceta.actualizarVisitasDeReceta(receta);
        this.servicioReceta.actualizarVisitasDeReceta(receta2);

        Mockito.verify(repositorioReceta, times(2)).actualizar(receta);
        Mockito.verify(repositorioReceta, times(1)).actualizar(receta2);
        assertEquals(receta.getContadorVisitas(), 2);
        assertEquals(receta2.getContadorVisitas(), 1);
        assertEquals(receta, recetasFiltradas.get(0));
        assertEquals(receta2, recetasFiltradas.get(1));
        assertThat(recetasFiltradas, not(hasItem(receta1)));
    }

    @Test
    public void queSePuedanFiltrarLasRecetasPorTiempoDe30MinYSePuedanOrdenarPorPopularidadDependiendoLaCantidadDeVisitas() {
        Receta receta = this.recetaMilanesaNapolitanaDeTreintaMinCreada();
        Receta receta1 = this.recetaCafeConLecheDeDiezMinCreada();
        Receta receta2 = this.recetaMilanesaConPapasDeVeinteMinCreada();
        receta1.setTiempo_preparacion(TiempoDePreparacion.TREINTA_MIN); //ahora es cafe con leche de 30 min
        TiempoDePreparacion tiempo = TiempoDePreparacion.TREINTA_MIN;
        List<Receta> recetasFiltradas = new ArrayList<>();
        recetasFiltradas.add(receta);
        recetasFiltradas.add(receta1);

        Mockito.when(repositorioReceta.getRecetasPorTiempoDePreparacion(tiempo)).thenReturn(recetasFiltradas);
        this.servicioReceta.actualizarVisitasDeReceta(receta);
        this.servicioReceta.actualizarVisitasDeReceta(receta);
        this.servicioReceta.actualizarVisitasDeReceta(receta1);

        Mockito.verify(repositorioReceta, times(2)).actualizar(receta);
        Mockito.verify(repositorioReceta, times(1)).actualizar(receta1);
        assertEquals(receta.getContadorVisitas(), 2);
        assertEquals(receta1.getContadorVisitas(), 1);
        assertEquals(receta, recetasFiltradas.get(0));
        assertEquals(receta1, recetasFiltradas.get(1));
        assertThat(recetasFiltradas, not(hasItem(receta2)));
    }

    @Test
    public void queSePuedanFiltrarLasRecetasPorTiempoDe30MinYAlmuerzoOCenaYSePuedanOrdenarPorPopularidadDependiendoLaCantidadDeVisitas() {
        Receta receta = this.recetaMilanesaNapolitanaDeTreintaMinCreada();
        Receta receta1 = this.recetaCafeConLecheDeDiezMinCreada();
        Receta receta2 = this.recetaMilanesaConPapasDeVeinteMinCreada();
        receta2.setTiempo_preparacion(TiempoDePreparacion.TREINTA_MIN); //ahora es milanesa con papas de 30 min
        TiempoDePreparacion tiempo = TiempoDePreparacion.TREINTA_MIN;
        Categoria categoria = Categoria.ALMUERZO_CENA;
        List<Receta> recetasFiltradas = new ArrayList<>();
        recetasFiltradas.add(receta);
        recetasFiltradas.add(receta2);

        Mockito.when(repositorioReceta.getRecetasPorCategoriaYTiempoDePreparacion(categoria, tiempo)).thenReturn(recetasFiltradas);
        this.servicioReceta.actualizarVisitasDeReceta(receta);
        this.servicioReceta.actualizarVisitasDeReceta(receta);
        this.servicioReceta.actualizarVisitasDeReceta(receta2);

        Mockito.verify(repositorioReceta, times(2)).actualizar(receta);
        Mockito.verify(repositorioReceta, times(1)).actualizar(receta2);
        assertEquals(receta.getContadorVisitas(), 2);
        assertEquals(receta2.getContadorVisitas(), 1);
        assertEquals(receta, recetasFiltradas.get(0));
        assertEquals(receta2, recetasFiltradas.get(1));
        assertThat(recetasFiltradas, not(hasItem(receta1)));
    }

    @Test
    public void queSePuedanBuscarLasRecetasPorTituloYSePuedanOrdenarPorPopularidadDependiendoLaCantidadDeVisitas() {
        Receta receta = this.recetaMilanesaNapolitanaDeTreintaMinCreada();
        Receta receta1 = this.recetaCafeConLecheDeDiezMinCreada();
        Receta receta2 = this.recetaMilanesaConPapasDeVeinteMinCreada();
        String titulo = "milanesa";
        List<Receta> recetasFiltradas = new ArrayList<>();
        recetasFiltradas.add(receta);
        recetasFiltradas.add(receta2);

        Mockito.when(repositorioReceta.buscarRecetasPorTitulo(titulo)).thenReturn(recetasFiltradas);
        this.servicioReceta.actualizarVisitasDeReceta(receta);
        this.servicioReceta.actualizarVisitasDeReceta(receta);
        this.servicioReceta.actualizarVisitasDeReceta(receta2);

        Mockito.verify(repositorioReceta, times(2)).actualizar(receta);
        Mockito.verify(repositorioReceta, times(1)).actualizar(receta2);
        assertEquals(receta.getContadorVisitas(), 2);
        assertEquals(receta2.getContadorVisitas(), 1);
        assertEquals(receta, recetasFiltradas.get(0));
        assertEquals(receta2, recetasFiltradas.get(1));
        assertThat(recetasFiltradas, hasItem(hasProperty("titulo", equalTo("Milanesa napolitana"))));
        assertThat(recetasFiltradas, hasItem(hasProperty("titulo", equalTo("Milanesa con papas fritas"))));
        assertThat(recetasFiltradas, not(hasItem(receta1)));
    }

    @Test
    public void queCuandoSeBusquenTodasLasRecetasYTenganLaMismaCantidadDeVisitasNoHayaCriterioDeOrdenamiento(){
        Receta receta = this.recetaMilanesaNapolitanaDeTreintaMinCreada();
        Receta receta1 = this.recetaCafeConLecheDeDiezMinCreada();
        Receta receta2 = this.recetaMilanesaConPapasDeVeinteMinCreada();
        List<Receta> todasLasRecetas = new ArrayList<>();
        todasLasRecetas.add(receta);
        todasLasRecetas.add(receta1);
        todasLasRecetas.add(receta2);

        Mockito.when(repositorioReceta.getRecetas()).thenReturn(todasLasRecetas);
        assertEquals(receta.getContadorVisitas(), 0);
        assertEquals(receta1.getContadorVisitas(), 0);
        assertEquals(receta2.getContadorVisitas(), 0);
        assertThat(todasLasRecetas, hasItem(receta));
        assertThat(todasLasRecetas, hasItem(receta1));
        assertThat(todasLasRecetas, hasItem(receta2));
    }
    @Test
    public void queSePuedanEncontrarLasRecetasDeCadaAutor(){
        Receta receta = new Receta();
        receta.setTitulo("milanesa");
        receta.setAutor("mauri");
        repositorioReceta.guardar(receta);

        Receta receta2 = new Receta();
        receta2.setTitulo("pure");
        receta2.setAutor("mauri");
        repositorioReceta.guardar(receta2);
        Receta receta3 = new Receta();
        receta3.setTitulo("pure");
        receta3.setAutor("betular");
        repositorioReceta.guardar(receta2);

        String autorBuscado = "mauri";

        List<Receta> todasLasRecetas = new ArrayList<>();
        todasLasRecetas.add(receta);
        todasLasRecetas.add(receta2);

        Mockito.when(repositorioReceta.buscarPorAutor(autorBuscado)).thenReturn(todasLasRecetas);
        List<Receta> recetasEncontradas = servicioReceta.buscarRecetaPorAutor(autorBuscado);
        Mockito.verify(repositorioReceta, Mockito.times(1)).buscarPorAutor(autorBuscado);

        assertEquals(2, recetasEncontradas.size());
        assertThat(recetasEncontradas, hasItem(hasProperty("titulo", equalTo("milanesa"))));
        assertThat(recetasEncontradas, hasItem(hasProperty("titulo", equalTo("pure"))));
    }
}
