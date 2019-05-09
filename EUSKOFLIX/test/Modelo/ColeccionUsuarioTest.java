package Modelo;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ColeccionUsuarioTest {
	MatrizValoraciones mv;
	
	@Before
	public void setUp() throws Exception {
		mv = MatrizValoraciones.getMatrizValoraciones();
	}

	@After
	public void tearDown() throws Exception {
		mv = null;
	}
	
	@Test
	public void testCargarUsuarios(){
		/* Caso 1: no hay usuarios añadidas. */
		mv.cargarUsuarios("movie-ratings.csv");
		
		assertEquals(true, mv.contieneIdUsuario(1));
		assertEquals(false, mv.contieneIdUsuario(5573));
		
		/* Caso 2: hay peliculas añadidas. */
		mv.cargarUsuarios("movie-ratings.csv");
		
		assertEquals(true, mv.contieneIdUsuario(1));
		assertEquals(false, mv.contieneIdUsuario(5573));
	}
	
	public void testBorrarPeliculas(){
		mv.cargarUsuarios("movie-ratings.csv");
		mv.borrarUsuarios();
		
		String vacio = "Valoraciones de la pelicula  con ID: ";
		assertEquals(vacio + "11", mv.visRatingUsuario(11));
		assertEquals(vacio + "15", mv.visRatingUsuario(15));
	}
	
}
