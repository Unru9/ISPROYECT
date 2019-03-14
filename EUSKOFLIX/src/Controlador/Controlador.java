package Controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;

import Modelo.ColeccionPeliculas;
import Modelo.ColeccionUsuario;
import Vista.VistaCargaDatos;

public class Controlador {
	//atributo
	private VistaCargaDatos miVista;
	
	public Controlador(){
		miVista= new VistaCargaDatos();
		//listeners
		this.miVista.setTitlesListener(new Titles());
		this.miVista.setTagsListener(new Tags());
		this.miVista.setRatingsListener(new Ratings());
		ColeccionPeliculas cp= ColeccionPeliculas.getColeccionPeliculas();
		cp.cargarPeliculas("movie-titles.csv");
		cp.cargarTags("movie-tags.csv");
		ColeccionUsuario cu= ColeccionUsuario.getColeccionUsuario();
		//cu.cargarUsuarios("movie-ratings");
	}
	
	public void mostrarVentana() {
		this.miVista.setVisible(true);
	}

	class Titles implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			ColeccionPeliculas cp= ColeccionPeliculas.getColeccionPeliculas();
			String aux = cp.visPelis();
			//System.out.println("=================" + aux);
			miVista.setTextoGeneral(aux);
			
			/*ColeccionPeliculas cp= ColeccionPeliculas.getColeccionPeliculas();
			JTable aux=new JTable(cp.toTableModel(cp.getHas()));
			miVista.setScroll(aux);*/
		}
	}
	
	class Tags implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			ColeccionPeliculas cp= ColeccionPeliculas.getColeccionPeliculas();
			String movieID = JOptionPane.showInputDialog("Introduce un movieID");
			if(!cp.containsKey(Integer.parseInt(movieID))){
				JOptionPane.showMessageDialog(miVista, "El movieID no existe");
			}
			String aux= cp.visTags(Integer.parseInt(movieID));
			miVista.setTextoGeneral(aux);
			
		}
	}
	
	class Ratings implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			
		}
	}
	
}
