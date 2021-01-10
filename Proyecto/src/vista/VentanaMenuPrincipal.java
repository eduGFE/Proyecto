package vista;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import com.sun.javafx.event.EventQueue;

import controlador.Coordinador_Clientes;
import controlador.Coordinador_Productos;
import controlador.Coordinador_Ventas;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaMenuPrincipal extends  JFrame{
	//Creacion de los coordinadores para poder llammar a las ventanas correcpondientes 

	private Coordinador_Clientes miCoordinador_clientes;
	private Coordinador_Productos coordinador_productos;
	private Coordinador_Ventas coordinador_ventas;
	private JFrame Panel;

	public VentanaMenuPrincipal(String[] args) {

	private Coordinador_Clientes miCoordinador_clientes = new Coordinador_Clientes();
	private Coordinador_Productos coordinador_productos = new Coordinador_Productos();
	private Coordinador_Ventas coordinador_ventas = new Coordinador_Ventas();
	private JFrame Panel;

	public static void main(String tipoConex) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaMenuPrincipal window = new VentanaMenuPrincipal(tipoConex);
					VentanaGestionVentas miVentanaGestionVentas= new VentanaGestionVentas(tipoConex);
					window.Panel.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public VentanaMenuPrincipal(String tipoConex) {

		//Cambia la apariencia de las ventanas
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
			
		}
		
		initialize(tipoConex);
	}

	//Esta fucion reciber por parametro el argumento que le llega a la clase para conectarse a una base de datos u otra
	private void initialize(String tipoConex) {

		setBounds(100, 100, 250, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
	
		JButton BotonVentas = new JButton("Gestión Ventas");
		BotonVentas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				coordinador_ventas.mostrarVentanaGestionVentas(tipoConex);
				setVisible(false);
				
			}
		});
		BotonVentas.setBounds(37, 32, 168, 60);
		getContentPane().add(BotonVentas);
		
		JButton BotonClientes = new JButton("Gestión Clientes");
		BotonClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				miCoordinador_clientes.mostrarVentanaGestionClientes();
				setVisible(false);
			}
		});
		BotonClientes.setBounds(37, 103, 168, 60);
		getContentPane().add(BotonClientes);
		
		JButton BotonProductos = new JButton("Gestión Productos");
		BotonProductos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				coordinador_productos.mostrarVentanaGestionProductos();

				coordinador_productos.mostrarVentanaGestionProductos(tipoConex);

				setVisible(false);   
			}
		});
		BotonProductos.setBounds(36, 174, 168, 60);
		getContentPane().add(BotonProductos);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	//Coordinadores. Necesarios para unir cada boton con la parte logica de cada objeto.
	public void setCoordinadorClientes(Coordinador_Clientes miCoordinador_clientes) {
		this.miCoordinador_clientes = miCoordinador_clientes;
	}
	public void setCoordinadorVentas(Coordinador_Ventas coordinador_ventas) {
		this.coordinador_ventas = coordinador_ventas;
	}
	public void setCoordinadorProductos(Coordinador_Productos coordinador_productos) {
		this.coordinador_productos = coordinador_productos;
	}
	
}
