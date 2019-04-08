package Modelo;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ColeccionUsuarioTest {
	ColeccionUsuario cu;
	
	@Before
	public void setUp() throws Exception {
		cu = ColeccionUsuario.getColeccionUsuario();
	}

	@After
	public void tearDown() throws Exception {
		cu = null;
	}
	
	@Test
	public void testCargarUsuarios(){
		/* Caso 1: no hay usuarios añadidas. */
		cu.cargarUsuarios("movie-ratings.csv");
		
		assertEquals(true, cu.contieneIdUsuario(1));
		assertEquals(false, cu.contieneIdUsuario(5573));
		
		/* Caso 2: hay peliculas añadidas. */
		cu.cargarUsuarios("movie-ratings.csv");
		
		assertEquals(true, cu.contieneIdUsuario(1));
		assertEquals(false, cu.contieneIdUsuario(5573));
	}
	
	public void testBorrarPeliculas(){
		cu.cargarUsuarios("movie-ratings.csv");
		cu.borrarUsuarios();
		
		String vacio = "Valoraciones de la pelicula  con ID: ";
		assertEquals(vacio + "11", cu.visRatingUsuario(11));
		assertEquals(vacio + "15", cu.visRatingUsuario(15));
	}
	
}
