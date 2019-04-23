package Modelo;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class MatrizSimilitudesOrdenada {

	private HashMap<Integer, HashMap<Integer, Double>> matrizSimilitudesOrdenada;
	private static MatrizSimilitudesOrdenada mMatrizSimilitudesOrdenada;
	
	private MatrizSimilitudesOrdenada() {
		matrizSimilitudesOrdenada = new HashMap<Integer, HashMap<Integer,Double>>();
	}
	
	public static MatrizSimilitudesOrdenada getMatrizSimilitudesOrdenada() {
		if (mMatrizSimilitudesOrdenada==null)
			mMatrizSimilitudesOrdenada = new MatrizSimilitudesOrdenada();
		return mMatrizSimilitudesOrdenada;
	}

	public void GenerarMatrizSimilitudesOrdenada() throws Exception{
		MedidasSimilitud ms = MedidasSimilitud.getMedidasSimilitud();
		
		for (Entry<Integer, HashMap<Integer, Double>> entrada : ms.getEntrySet()){
			int idPel1 = entrada.getKey();
			HashMap<Integer, Double> res = ms.obtSimilitudesPelicula(idPel1);
			HashMap<Integer, Double> sortedMapAsc = sortByComparator(res, false);
			matrizSimilitudesOrdenada.put(idPel1, sortedMapAsc);
	
		}

		
		/*StringBuilder sb = new StringBuilder();
		for (Entry<Integer, HashMap<Integer, Double>> entrada : matrizSimilitudesOrdenada.entrySet()) {
			System.out.println(entrada);
			sb.append(entrada.toString() + "\n");
		}
		
		String output ="C:/Users/Unai/Desktop/matrizSimilitudesOrdenada.txt";
		writeFile(output,sb.toString());*/
		
	}
	
	public String modeloSimilitudProductoPelicula( int movieID){
		StringBuilder sb = new StringBuilder();
		sb.append("============================================= \n");
		sb.append("ID PELÍCULA                COSENO \n");
		sb.append("============================================= \n");
		HashMap<Integer, Double> aux = matrizSimilitudesOrdenada.get(movieID);
		for (Entry<Integer, Double> entrada : aux.entrySet()){
			int idPel1 = entrada.getKey();
			double cos = entrada.getValue();
			sb.append(idPel1 + " --------------> " + cos + "\n");
		}
		
		return sb.toString();
	}
	
    public HashMap<Integer, Double> sortByComparator(Map<Integer, Double> unsortMap, final boolean order)
    {

        List<Entry<Integer, Double>> list = new LinkedList<Entry<Integer, Double>>(unsortMap.entrySet());

        // Sorting the list based on values
        Collections.sort(list, new Comparator<Entry<Integer, Double>>()
        {
            public int compare(Entry<Integer, Double> o1,
                    Entry<Integer, Double> o2)
            {
                if (order)
                {
                    return o1.getValue().compareTo(o2.getValue());
                }
                else
                {
                    return o2.getValue().compareTo(o1.getValue());

                }
            }
        });
        HashMap<Integer, Double> sortedMap = new LinkedHashMap<Integer, Double>();
        for (Entry<Integer, Double> entry : list)
        {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }
    
/*	private static void writeFile(String output, String pResultado) throws Exception {
		BufferedWriter bw;
		try {
			bw = new BufferedWriter(new FileWriter(output));
			bw.write(pResultado);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error en la escritura de datos");
		}
	}
	
	*/
    public double gradoIdoneidad(int idUser, int idPel, int nProd){
    	HashMap<Integer, Double> similares = matrizSimilitudesOrdenada.get(idPel);
    	double numerador = 0;
    	double denominador = 0;
    	ColeccionUsuario cu = ColeccionUsuario.getColeccionUsuario();
    	Set<Entry<Integer, Double>> entradas = similares.entrySet();
    	Iterator<Entry<Integer, Double>> iteratorSimilares = entradas.iterator();
    	for (int j = 0; j < nProd; j++) {  // Suponemos que nProd < entradas.size()
			Entry<Integer, Double> entrada =  iteratorSimilares.next();
			double similitud = entrada.getValue();
    		int idPel2 = entrada.getKey();
    		numerador = numerador + (cu.obtValoracion(idUser, idPel2) * similitud);
    		denominador = denominador + similitud;
    	}
	
    	return numerador / denominador;
    }
}
