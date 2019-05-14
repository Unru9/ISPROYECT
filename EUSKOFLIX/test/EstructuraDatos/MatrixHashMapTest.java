package EstructuraDatos;

import static org.junit.Assert.*;

import org.junit.Test;

public class MatrixHashMapTest {


	@Test
	public void testTieneValores() {
		MatrixHashMap matriz = new MatrixHashMap();
		matriz.anadirDupla(1, "2", 1.0);
		matriz.anadirDupla(2, "3", 1.5);
		matriz.anadirDupla(3, "4", 2.0);
		
		assertEquals(matriz.tieneValores(2), true);
		assertEquals(matriz.tieneValores(4), false);
	}

}
