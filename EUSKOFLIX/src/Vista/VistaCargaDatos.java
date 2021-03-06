package Vista;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.GridBagLayout;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

public class VistaCargaDatos extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnTags;
	private JButton btnRatings;
	private JButton btnTitles;
	private JPanel panel_1;
	private JScrollPane scroll;
	private JTextArea textArea;
	// private JTable table;
	private static VistaCargaDatos frame;

	// nuevos botones
	private JButton btnIdoneidadPersona;
	private JButton btnIdoineidadProducto;
	private JButton btnAfinPelProducto;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new VistaCargaDatos();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VistaCargaDatos() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 451, 440);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.EAST);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
		panel.setLayout(gbl_panel);

		btnTitles = new JButton("Titles");
		btnTitles.setPreferredSize(new Dimension(100, 20));
		btnTitles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		GridBagConstraints gbc_btnNewButton_2 = new GridBagConstraints();
		gbc_btnNewButton_2.insets = new Insets(0, 0, 10, 0);
		gbc_btnNewButton_2.gridx = 0;
		gbc_btnNewButton_2.gridy = 1;
		panel.add(btnTitles, gbc_btnNewButton_2);

		btnTags = new JButton("Tags");
		btnTags.setPreferredSize(new Dimension(100, 20));
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 10, 0);
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 3;
		panel.add(btnTags, gbc_btnNewButton);

		btnRatings = new JButton("Ratings");
		btnRatings.setPreferredSize(new Dimension(100, 20));
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.insets = new Insets(0, 0, 10, 0);
		gbc_btnNewButton_1.gridx = 0;
		gbc_btnNewButton_1.gridy = 5;
		panel.add(btnRatings, gbc_btnNewButton_1);

		btnIdoineidadProducto = new JButton("IdoneidadProducto");
		btnIdoineidadProducto.setPreferredSize(new Dimension(150, 20));
		btnIdoineidadProducto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		GridBagConstraints gbc_btnNewButton_4 = new GridBagConstraints();
		gbc_btnNewButton_4.insets = new Insets(0, 0, 10, 0);
		gbc_btnNewButton_4.gridx = 0;
		gbc_btnNewButton_4.gridy = 7;
		panel.add(btnIdoineidadProducto, gbc_btnNewButton_4);

		btnAfinPelProducto = new JButton("AfinesProducto");
		btnAfinPelProducto.setPreferredSize(new Dimension(150, 20));
		btnAfinPelProducto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		GridBagConstraints gbc_btnNewButton_5 = new GridBagConstraints();
		gbc_btnNewButton_5.insets = new Insets(0, 0, 10, 0);
		gbc_btnNewButton_5.gridx = 0;
		gbc_btnNewButton_5.gridy = 9;
		panel.add(btnAfinPelProducto, gbc_btnNewButton_5);

		btnIdoneidadPersona = new JButton("AfinesPersona");
		btnIdoneidadPersona.setPreferredSize(new Dimension(120, 20));
		btnIdoneidadPersona.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		GridBagConstraints gbc_btnNewButton_3 = new GridBagConstraints();
		gbc_btnNewButton_3.insets = new Insets(0, 0, 10, 0);
		gbc_btnNewButton_3.gridx = 0;
		gbc_btnNewButton_3.gridy = 11;
		panel.add(btnIdoneidadPersona, gbc_btnNewButton_3);

		panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout(0, 0));

		textArea = new JTextArea(25, 80);
		scroll = new JScrollPane();
		scroll.setViewportView(textArea);
		panel_1.add(scroll, BorderLayout.CENTER);

		/*
		 * table = new JTable(); scroll = new JScrollPane(); scroll.add(table);
		 * //scroll.getViewport().add(table);
		 * panel_1.add(scroll,BorderLayout.CENTER);
		 */

		/*
		 * JScrollPane scrollPane_1 = new JScrollPane();
		 * panel_1.add(scrollPane_1, BorderLayout.CENTER);
		 * 
		 * textField = new JTextField();
		 * scrollPane_1.setViewportView(textField); textField.setColumns(10);
		 */
	}

	public void setVisible() {
		setVisible(true);
	}

	public void setRatingsListener(ActionListener listenTitlesBtn) {
		this.btnRatings.addActionListener(listenTitlesBtn);

	}

	public void setTitlesListener(ActionListener listenTitlesBtn) {
		btnTitles.addActionListener(listenTitlesBtn);

	}

	public void setTagsListener(ActionListener listenTitlesBtn) {
		this.btnTags.addActionListener(listenTitlesBtn);

	}

	public void idoneidadPersonaListener(ActionListener listenMatrizBtn) {
		btnIdoneidadPersona.addActionListener(listenMatrizBtn);

	}

	public void idoneidadProductoListener(ActionListener listenIdoneidadBtn) {
		btnIdoineidadProducto.addActionListener(listenIdoneidadBtn);

	}

	public void afinesListenerProducto(ActionListener listenAfinBtn) {
		btnAfinPelProducto.addActionListener(listenAfinBtn);

	}


	public void setTextoGeneral(String pTexto) {
		this.textArea.setText(pTexto);
	}

	public void setScroll(JTable pModel) {
		this.scroll.add(pModel);
	}

}
