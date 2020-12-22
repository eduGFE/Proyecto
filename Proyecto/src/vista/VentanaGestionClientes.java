package vista;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import controlador.Coordinador_Clientes;
import controlador.Coordinador_Ventas;


public class VentanaGestionClientes extends  JFrame{
	//Se crea un cordinador_clientes porque tiene un metodo para poder regresar a la ventana de inicio
	private Coordinador_Clientes coordinador_clientes = new Coordinador_Clientes();
	private JFrame frame;
	

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaGestionClientes window = new VentanaGestionClientes(args);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public VentanaGestionClientes(String[] args) {
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
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(false);
		
		JButton botonVolver = new JButton("Volver");
		botonVolver.setBounds(10, 237, 89, 23);
		botonVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				coordinador_clientes.mostrarVentanaMenuPrincipal(args);
				coordinador_clientes.ocultarVentanaGestionClientes();
			}
		});
		getContentPane().add(botonVolver);
		
	}
	
	public void setCoordinadorClientes(Coordinador_Clientes coordinador_clientes) {
		this.coordinador_clientes = coordinador_clientes;
	}

}
