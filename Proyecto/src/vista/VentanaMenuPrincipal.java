package vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import controlador.Coordinador_Clientes;
import controlador.Coordinador_Productos;
import controlador.Coordinador_Ventas;
import modelo.conexion.ConexionMySQL;
import modelo.conexion.ConexionSQLite3;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaMenuPrincipal extends  JFrame{
	//Creacion de los coordinadores para poder llammar a las ventanas correcpondientes 
	private Coordinador_Clientes coordinador_clientes = new Coordinador_Clientes();;
	private Coordinador_Productos coordinador_productos = new Coordinador_Productos();
	private Coordinador_Ventas coordinador_ventas = new Coordinador_Ventas();
	private JFrame Panel;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaMenuPrincipal window = new VentanaMenuPrincipal(args);
					VentanaGestionVentas miVentanaGestionVentas= new VentanaGestionVentas(args);
					window.Panel.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public VentanaMenuPrincipal(String[] args) {
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
		
		initialize(args);
	}

	//Esta fucion reciber por parametro el argumento que le llega a la clase para conectarse a una base de datos u otra
	private void initialize(String[] args) {
		int argumento = Integer.parseInt(args[0]);
		if(argumento==1) {
		ConexionMySQL conexion = new ConexionMySQL();	 
		}else if (argumento==2) {
		ConexionSQLite3 conexion = new ConexionSQLite3();	
		}
		setBounds(100, 100, 250, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
	
		JButton BotonVentas = new JButton("Gestión Ventas");
		BotonVentas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				coordinador_ventas.mostrarVentanaGestionVentas(args);
				setVisible(false);
				
			}
		});
		BotonVentas.setBounds(37, 32, 168, 60);
		getContentPane().add(BotonVentas);
		
		JButton BotonClientes = new JButton("Gestión Clientes");
		BotonClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				coordinador_clientes.mostrarVentanaGestionClientes(args);
				setVisible(false);
			}
		});
		BotonClientes.setBounds(37, 103, 168, 60);
		getContentPane().add(BotonClientes);
		
		JButton BotonProductos = new JButton("Gestión Productos");
		BotonProductos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				coordinador_productos.mostrarVentanaGestionProductos(args);
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
	public void setCoordinadorCliente(Coordinador_Clientes coordinador_clientes) {
		this.coordinador_clientes = coordinador_clientes;
	}
	public void setCoordinadorVentas(Coordinador_Ventas coordinador_ventas) {
		this.coordinador_ventas = coordinador_ventas;
	}
	public void setCoordinadorProductos(Coordinador_Productos coordinador_productos) {
		this.coordinador_productos = coordinador_productos;
	}
	
}
