package EstructuraDatos;

import static org.junit.Assert.*;

import org.junit.Test;

public class MatrixHashMapTest {

	@Test
	public void testRecorrerMatriz() {
		MatrixHashMap matriz = new MatrixHashMap();
		
		assertTrue(matriz.recorrerMatriz() == null);
		
		matriz.anadirDupla(1, "2", 1.0);
		matriz.anadirDupla(2, "3", 1.5);
		matriz.anadirDupla(3, "4", 2.0);
		
		assertTrue(matriz.recorrerMatriz() == 1.0);
	}

	@Test
	public void testTieneValores() {
		MatrixHashMap matriz = new MatrixHashMap();
		matriz.anadirDupla(1, "2", 1.0);
		matriz.anadirDupla(2, "3", 1.5);
		matriz.anadirDupla(3, "4", 2.0);
		
		assertEquals(matriz.tieneValores(2), true);
		assertEquals(matriz.tieneValores(4), false);
	}

	@Test
	public void testSiguienteValor() {
		MatrixHashMap matriz = new MatrixHashMap();
		matriz.anadirDupla(1, "2", 1.0);
		matriz.anadirDupla(2, "3", 1.5);
		matriz.anadirDupla(3, "4", 2.0);
		
		matriz.recorrerMatriz();
		matriz.siguienteValor();
		double a = matriz.siguienteValor();
		assertTrue(a==2.0);

	}

	@Test
	public void testTieneSiguienteValor() {
		MatrixHashMap matriz = new MatrixHashMap();
		matriz.anadirDupla(1, "2", 1.0);
		matriz.anadirDupla(2, "3", 1.5);
		matriz.anadirDupla(3, "4", 2.0);
		
		matriz.recorrerMatriz();
		assertEquals(matriz.tieneSiguienteValor(), true);
		matriz.siguienteValor();
		assertEquals(matriz.tieneSiguienteValor(), true);
		matriz.siguienteValor();
		assertEquals(matriz.tieneSiguienteValor(), false);
	}

}
