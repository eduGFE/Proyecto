package vista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import controlador.Coordinador_Clientes;

/**   
 * Crea y construye la ventana VentanaGestionClientes,
 * tambi�n crea enlace con el Controlador_Clientes e 
 * implementa eventos de los botones.  
 * @author Miguel Herrero L�pez (2� DAM). 
 * */ 
public class VentanaGestionClientes extends JFrame{
	
	//Objeto que permite la relaci�n entre esta clase y la clase Coordinador_Clientes.
	private Coordinador_Clientes miCoordinador_clientes;

	/**
	 * Constructor de la clase.
	 */
	public VentanaGestionClientes() {
		inicializarVentana();
	}

	/**
	 * M�todo privado donde se inicializan los componentes de la ventana.
	 */
	private void inicializarVentana() {
		//Opciones de visualizaci�n/actuaci�n de la ventana:
		setTitle("MEN\u00DA CLIENTES");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(0, 0, 300, 310);; //Tama�o
		getContentPane().setLayout(null);
		setResizable(false); //Tama�o fijo
		setLocationRelativeTo(null); //Posici�n en pantalla	

		//Botones y eventos
		JButton btnMenuImportar = new JButton("Importar Clientes");
		btnMenuImportar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				miCoordinador_clientes.ocultarVentanaGestionClientes();
				miCoordinador_clientes.mostrarVentanaImportarClientes();
			}
		});
		btnMenuImportar.setBounds(42, 11, 200, 35);
		getContentPane().add(btnMenuImportar);
		
		JButton btnMenuInsertar = new JButton("Insertar Cliente");
		btnMenuInsertar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				miCoordinador_clientes.ocultarVentanaGestionClientes();
				miCoordinador_clientes.mostrarVentanaInsertarClientes();
			}		
		});
		btnMenuInsertar.setBounds(42, 55, 200, 35);
		getContentPane().add(btnMenuInsertar);
		
		JButton btnMenuModificar = new JButton("Modificar Cliente");
		btnMenuModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				miCoordinador_clientes.ocultarVentanaGestionClientes();
				miCoordinador_clientes.mostrarVentanaModificarClientes();
			}
		});
		btnMenuModificar.setBounds(42, 101, 200, 35);
		getContentPane().add(btnMenuModificar);
		
		JButton btnMenuBorrar = new JButton("Borrar Cliente");
		btnMenuBorrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				miCoordinador_clientes.ocultarVentanaGestionClientes();
				miCoordinador_clientes.mostrarVentanaBorrarClientes();
			}
		});
		btnMenuBorrar.setBounds(42, 145, 200, 35);
		getContentPane().add(btnMenuBorrar);
		
		JButton btnMenuConsultar = new JButton("Consultar Cliente");
		btnMenuConsultar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				miCoordinador_clientes.ocultarVentanaGestionClientes();
				miCoordinador_clientes.mostrarVentanaConsultarClientes();
			}
		});
		btnMenuConsultar.setBounds(42, 191, 200, 35);
		getContentPane().add(btnMenuConsultar);
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				miCoordinador_clientes.ocultarVentanaGestionClientes();
				miCoordinador_clientes.mostrarVentanaMenuPrincipal();	
			}
		});
		btnVolver.setBounds(78, 237, 129, 23);
		getContentPane().add(btnVolver);
	}
	
	/**
	 * M�todo que enlaza la clase con el coordinador.
	 */
	public void setCoordinadorClientes(Coordinador_Clientes miCoordinador_clientes) {
		this.miCoordinador_clientes = miCoordinador_clientes;
	}
}//FIN
