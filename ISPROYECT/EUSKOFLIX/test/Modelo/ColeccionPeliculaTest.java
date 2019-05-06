package Modelo;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class ColeccionPeliculaTest {
	ColeccionPeliculas cp;
	
	
	@Before
	public void setUp() throws Exception {
		cp = ColeccionPeliculas.getColeccionPeliculas();
	}

	@After
	public void tearDown() throws Exception {
		cp = null;
	}
	
	@Test
	public void testCargarPeliculas(){
		/* Caso 1: no hay peliculas añadidas. */
		cp.cargarPeliculas("movie-titles.csv");
		
		assertEquals(true, cp.contieneIDPelicula(11));
		assertEquals(false, cp.contieneIDPelicula(15));
		
		/* Caso 2: hay peliculas añadidas. */
		cp.cargarPeliculas("movie-titles.csv");
		
		assertEquals(true, cp.contieneIDPelicula(11));
		assertEquals(false, cp.contieneIDPelicula(15));
	}
	
	@Test
	public void testCargarTags(){
		/* Caso 1: no hay tags añadidas. */
		cp.crearMatrizEtiquetaProductos("movie-tags.csv");
		
		//cp.obtTagsPelicula("");
		
		/* Caso 2: hay tags añadidas. */
		cp.crearMatrizEtiquetaProductos("movie-tags.csv");
	}
	
	public void testBorrarPeliculas(){
		cp.cargarPeliculas("movie-titles.csv");
		cp.borrarPeliculas();
		
		String vacio = "";
		assertEquals(vacio, cp.visPelis());
	}
}
