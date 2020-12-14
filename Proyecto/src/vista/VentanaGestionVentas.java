package vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import controlador.coordinador_ventas;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaGestionVentas extends  JFrame {

	//Se crea un cordinador_ventas porque tiene un metodo para poder regresar a la ventana de inicio
	private coordinador_ventas coordinador_ventas = new coordinador_ventas();
	private JFrame frame;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaGestionVentas window = new VentanaGestionVentas(args);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public VentanaGestionVentas(String[] args) {
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
			
		}
		initialize(args);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(String[] args) {
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		JButton botonVolver = new JButton("Volver");
		botonVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				coordinador_ventas.mostrarVentanaMenuPrincipal(args);
				setVisible(false);
			}
		});
		botonVolver.setBounds(10, 237, 89, 23);
		getContentPane().add(botonVolver);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(false);
	}
	public void setCoordinadorVentas(coordinador_ventas coordinador_ventas) {
		this.coordinador_ventas = coordinador_ventas;
	}

}
