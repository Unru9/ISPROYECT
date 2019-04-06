package EstructuraDatos;


import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

//OBTENER DATOS
public class MatrixHashMap  {
	//ATRIBUTOS
	private HashMap<Integer, HashMap<String, Double>> structure;
	private Iterator<Entry<Integer, HashMap<String, Double>>> itr1;
	private Iterator<Entry<String, Double>> itr2;
	private Integer key1Actual;
	private String key2Actual;
	
	//CONSTRUCTORAS
	public MatrixHashMap() { 
		structure = new HashMap<Integer, HashMap<String,Double>>();
	}

	//METODOS
	//Dados clave1 y clave2 obtiene el valor de esa tupla si existe. Si no retorna null
	public Object obtDupla(Object key1, Object key2) {
		Object value=null;
		HashMap<String, Double> fila = structure.get(key1);
		if (fila != null)
			value = fila.get(key2);
		return value;
	}
	
	
	//Demuestra si para cierto valor, se ha añadido alguna dupla. 
	//(Si no se ha añadido nada, significa que no se ha inicializado el HashMap)
	public boolean tieneValores (int key1) {
		if (structure.get(key1)!= null) {
			return true;		
		}else{
			return false;
		}
}
	//Añade un valor en cierta dupla
	public void anadirDupla(int key1, String key2, Double value) {
		HashMap<String, Double> objetivo = structure.get(key1);
		if (tieneValores(key1)) {
			objetivo.put(key2, value);
		}else {
			objetivo = new HashMap<String, Double>();
			objetivo.put(key2,value);
			structure.put(key1, objetivo);
		}
	}
	
	public boolean existeDupla(int key1, String key2) {
		HashMap<String, Double> fila = structure.get(key1);
		if (fila.get(key2)!=null){
			return true;
		}else {
			return false;
		}
	}
	
	
	//METODOS PARA RECORRER structure
	private Iterator<Entry<Integer, HashMap<String, Double>>> iterator() {
		return structure.entrySet().iterator();
	}
	
	private Iterator<Entry<String, Double>> iteratorSegundaKey(int key1) {
		HashMap<String, Double> segundoHash = structure.get(key1);
		return segundoHash.entrySet().iterator();
	}
	
	/*public Iterator<Entry<Integer, HashMap<String, Double>>> getIterador() {
		return iterator();		
	}
	
	public Iterator<Entry<String, Double>> getIteradorSegundaKey(int key1) {
		return iteratorSegundaKey(key1);
	}*/
	
	//METODOS PARA RECORRER DUPLA POR DUPLA
	//reinicia iteradores, devuelve primer valor. Si no hay primer valor, devuelve null
	public Double recorrerMatriz() {
		 itr1 = iterator();
		 Double primerValor = null; 
		 Entry<Integer, HashMap<String, Double>> primerHash1 = null;
		if (itr1.hasNext()) {
		 	primerHash1 = itr1.next();
		 	key1Actual=primerHash1.getKey();
			int primeraKey = primerHash1.getKey();
			itr2 = iteratorSegundaKey(primeraKey);
		
			Entry<String, Double> primerHash2;
			if (itr2.hasNext()) {
				primerHash2 = itr2.next();
				key2Actual = primerHash2.getKey();
				primerValor = primerHash2.getValue();
			}
		}
		return primerValor;
	}
	
	//devuelve siguiente valor.
	public Double siguienteValor() {
		Double valorSiguiente = null;
		if (itr2.hasNext()) {
			Entry<String, Double> hash2 = itr2.next();
			valorSiguiente = hash2.getValue();
			key2Actual = hash2.getKey();
		}else if(itr1.hasNext()) {
			Entry<Integer, HashMap<String, Double>> hash1 = itr1.next();
		 	key1Actual=hash1.getKey();
			int primeraKey = hash1.getKey();
			itr2 = iteratorSegundaKey(primeraKey);
		
			Entry<Object, Object> primerHash2;
			if (itr2.hasNext()) {
				Entry<String, Double> hash2 = itr2.next();
				valorSiguiente = hash2.getValue();
				key2Actual = hash2.getKey();
			}
		}
		return valorSiguiente;
	}
	
	//Tiene siguiente valor. 
	public boolean tieneSiguienteValor() {
		return (itr1.hasNext()||itr2.hasNext());
	}
	
	public boolean tieneSiguienteValorHash2() {
		return itr2.hasNext();
	}
	
	public int getKey1Actual() {
		return key1Actual;
	}
	
	public String getKey2Actual() {
		return key2Actual;
	}
}
