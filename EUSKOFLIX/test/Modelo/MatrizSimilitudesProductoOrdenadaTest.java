package Modelo;

import static org.junit.Assert.*;

import org.junit.Test;

public class MatrizSimilitudesProductoOrdenadaTest {

	@Test
	public void testVisualizarPeliculasAfinesProducto() {
		MatrizSimilitudesProductoOrdenada ms = MatrizSimilitudesProductoOrdenada.getMatrizSimilitudesProductoOrdenada();
		assertEquals(ms.visualizarPeliculasAfinesProducto(13, 10), "====================================================== LAS PELÍCULAS MÁS AFINES AL USUARIO :13====================================================== The Sixth Sense (1999) --> 3.7244248017678405 Minority Report (2002) --> 3.652378785909269 The Shawshank Redemption (1994) --> 3.6388284904790145 Dances with Wolves (1990) --> 3.6380442429453614 The Godfather (1972) --> 3.6279231995748065 Braveheart (1995) --> 3.6216615263468728 A Beautiful Mind (2001) --> 3.608298887122416 Saving Private Ryan (1998) --> 3.5316547235553215 E.T. the Extra-Terrestrial (1982) --> 3.5022397176598354 Forrest Gump (1994) --> 3.4847881540471595");
	}

}
