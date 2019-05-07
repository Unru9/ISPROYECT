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
	public Iterator<Entry<Integer, HashMap<String, Double>>> iterator() {
		return structure.entrySet().iterator();
	}
	
	public Iterator<Entry<String, Double>> iteratorSegundaKey(int key1) {
		HashMap<String, Double> segundoHash = structure.get(key1);
		return segundoHash.entrySet().iterator();
	}
	
	public void anadir(int idUsuario,HashMap<String,Double> hm){
		this.structure.put(idUsuario, hm);
	}
	
	public HashMap<String, Double> obtHash(int idUsuario){
		return this.structure.get(idUsuario);
	}
		
	}
