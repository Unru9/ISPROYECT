package Principal;

import Controlador.Controlador;
import Modelo.ColeccionPeliculas;
import Modelo.ColeccionUsuario;

public class Main {

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Controlador c = new Controlador();
		c.mostrarVentana();
		/*ColeccionPeliculas cp= ColeccionPeliculas.getColeccionPeliculas();
		cp.cargarPeliculas("movie-titles.csv");
		cp.visPelis();*/
	}

}
