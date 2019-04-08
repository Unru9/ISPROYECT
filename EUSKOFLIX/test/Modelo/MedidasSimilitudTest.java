package Modelo;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

public class MedidasSimilitudTest {


	@SuppressWarnings("deprecation")
	@Test
	public void testCompararVectores() {
		ArrayList<Double> v1 = new ArrayList<Double>();
		v1.add(1.0);
		v1.add(3.0);
		v1.add(2.5);
		
		ArrayList<Double> v2 = new ArrayList<Double>();
		v2.add(1.0);
		v2.add(3.0);
		v2.add(2.5);
		
		assertEquals(MedidasSimilitud.getMedidasSimilitud().compararVectores(v1,v2),1.0);
		
		ArrayList<Double> v3 = new ArrayList<Double>();
		v3.add(2.0);
		v3.add(3.0);
		v3.add(-1.0);
		
		ArrayList<Double> v4 = new ArrayList<Double>();
		v4.add(-1.0);
		v4.add(1.0);
		v4.add(2.0);
		
		assertEquals(MedidasSimilitud.getMedidasSimilitud().compararVectores(v3,v4), 0.109);
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testCosenoVectores(){
		ArrayList<Double> v1 = new ArrayList<Double>();
		v1.add(1.0);
		v1.add(3.0);
		v1.add(2.5);
		
		ArrayList<Double> v2 = new ArrayList<Double>();
		v2.add(1.0);
		v2.add(3.0);
		v2.add(2.5);
		
		assertEquals(MedidasSimilitud.getMedidasSimilitud().cosenoVectores(v1,v2),1.0);
		
		ArrayList<Double> v3 = new ArrayList<Double>();
		v3.add(2.0);
		v3.add(3.0);
		v3.add(-1.0);
		
		ArrayList<Double> v4 = new ArrayList<Double>();
		v4.add(-1.0);
		v4.add(1.0);
		v4.add(2.0);
		
		assertEquals(MedidasSimilitud.getMedidasSimilitud().cosenoVectores(v3,v4), -0.109);
	}

	@Test
	public void testRellenarArray() {
		ArrayList<Double> v1 = new ArrayList<Double>();
		v1.add(-1.0);
		v1.add(1.0);
		v1.add(2.0);
		
		ArrayList<Double> v2 = new ArrayList<Double>();
		v2.add(-1.0);
		v2.add(1.0);
		v2.add(2.0);
		v2.add(0.0);
		v2.add(0.0);
		
		assertEquals(MedidasSimilitud.getMedidasSimilitud().rellenarArray(v1, 2), v2);
	}


	@Test
	public void testSortByComparator() {
		HashMap<Integer, Double> h1 = new HashMap<Integer, Double>();
		h1.put(8, 4.0);
		h1.put(9, 3.0);
		h1.put(2, 5.0);
		h1.put(3, 1.0);
		
		HashMap<Integer, Double> h2 = new HashMap<Integer, Double>();
		h2.put(3, 1.0);
		h2.put(9, 3.0);
		h2.put(8, 4.0);
		h2.put(2, 5.0);
		
		assertEquals(MedidasSimilitud.getMedidasSimilitud().sortByComparator(h1, true), h2);
	}


}
