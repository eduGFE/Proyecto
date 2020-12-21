package vista;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import controlador.Coordinador_Productos;
public class VentanaGestionProductos extends  JFrame {
	//Se crea un cordinador_productos porque tiene un metodo para poder regresar a la ventana de inicio
	private Coordinador_Productos coordinador_productos = new Coordinador_Productos();
	private JFrame frame;
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaGestionProductos window = new VentanaGestionProductos(args);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public VentanaGestionProductos(String[] args) {
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

	public void initialize(String[] args) {
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		JButton botonVolver = new JButton("Volver");
		botonVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				coordinador_productos.mostrarVentanaMenuPrincipal(args);
				setVisible(false);
			}
		});
		botonVolver.setBounds(10, 237, 89, 23);
		getContentPane().add(botonVolver);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(false);
	}
	public void setCoordinadorProductos(Coordinador_Productos coordinador_productos) {
		this.coordinador_productos = coordinador_productos;
	}
}
