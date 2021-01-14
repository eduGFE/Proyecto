package vista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;

import controlador.Coordinador_Clientes;
import modelo.clientes.Cliente_Dto;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**   
 * Crea y construye la ventana y también crea 
 * enlace con el Controlador_Clientes e 
 * implementa eventos de los botones.  
 * @author Miguel Herrero López (2º DAM). 
 * */ 

public class VentanaConsultarClientes extends JFrame {

	//Objeto que permite la relación entre esta clase y la clase Coordinador_Clientes.
	private Coordinador_Clientes miCoordinador_clientes;
	private JLabel labNif;
	private JTextField txtNif;
	private JTable tablaConsultaClientes;
	private DefaultTableModel infoTablaConsultaClientes;
	private JScrollPane scrollPane_1;
	private JButton btnConsultarClientes, btnVolver;

	/**
	 * Constructor de la clase.
	 */
	public VentanaConsultarClientes(String tipoConex) {
		inicializarVentana( tipoConex);
	}
	
	/**
	 * Método que enlaza la clase con el coordinador.
	 */
	public void setCoordinadorClientes(Coordinador_Clientes miCoordinador_clientes) {
		this.miCoordinador_clientes = miCoordinador_clientes;
	}
	
	////////////////////- MÉTODOS PRIVADOS -////////////////////

	/**
	 * Método privado donde se inicializan los componentes de la ventana.
	 */
	private void inicializarVentana(String tipoConex) {
		//Opciones de visualización/actuación de la ventana:
		setTitle("CONSULTAR CLIENTES");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(0, 0, 646, 244); //Tamaño
		getContentPane().setLayout(null);
		setResizable(false); //Tamaño fijo
		setLocationRelativeTo(null); //Posición en pantalla

		//Tabla que mostrará los Clientes coincidentes con la búsqueda:
		tablaConsultaClientes = new JTable();
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 56, 606, 102);
		getContentPane().add(scrollPane_1);
		scrollPane_1.setViewportView(tablaConsultaClientes);
		infoTablaConsultaClientes = new DefaultTableModel();
		infoTablaConsultaClientes.addColumn("ID");
		infoTablaConsultaClientes.addColumn("NOMBRE");
		infoTablaConsultaClientes.addColumn("DIRECCIÓN");
		infoTablaConsultaClientes.addColumn("POBLACIÓN");
		infoTablaConsultaClientes.addColumn("TELÉFONO");
		infoTablaConsultaClientes.addColumn("NIF");
		tablaConsultaClientes.setModel(infoTablaConsultaClientes);

		//Etiqueta y TextField NIF:
		labNif = new JLabel("NIF :");
		labNif.setFont(new Font("Tahoma", Font.BOLD, 14));
		labNif.setBounds(10, 21, 56, 20);
		getContentPane().add(labNif);	
		txtNif = new JTextField();
		txtNif.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtNif.setColumns(10);
		txtNif.setBounds(57, 20, 250, 25);
		getContentPane().add(txtNif);

		//Botón CONSULTAR CLIENTES:
		btnConsultarClientes = new JButton("CONSULTAR CLIENTES");
		btnConsultarClientes.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnConsultarClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				infoTablaConsultaClientes.setRowCount(0); //Limpiamos contenido tabla.
				mostrarClientesCoincidentes( tipoConex);
			}
		});
		btnConsultarClientes.setBounds(345, 11, 271, 34);
		getContentPane().add(btnConsultarClientes);

		//Botón VOLVER:
		btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				miCoordinador_clientes.ocultarVentanaConsultarClientes();
				miCoordinador_clientes.mostrarVentanaGestionClientes();	
			}
		});
		btnVolver.setBounds(10, 169, 89, 23);
		getContentPane().add(btnVolver);
	}
	
	/**
	 * Método que comienza el proceso de consulta de Clientes por Nif y muestra 
	 * los registros encontrados en la "Tabla Consulta Clientes" si los recibe. 
	 * Si no existen Clientes coincidentes avisa al user de ello mediante un cuadro de diálogo.
	 */	
	private void mostrarClientesCoincidentes(String tipoConex) {
		
		//Creamos ArrayList donde se guardarán los objetos DTO de los clientes, llamando al método que los busca.
		ArrayList<Cliente_Dto> listaClientesDTO = 
				miCoordinador_clientes.consultarClientes(txtNif.getText(),  tipoConex);
		
		if (listaClientesDTO!=null) { //Si hay clientes en la BD rellenamos la tabla

			//Recorremos nuestra listaClientesDTO:
			for (Cliente_Dto clienteAux : listaClientesDTO) {
				//Construimos un array de Object para cada registro y añadimos su contenido a la tabla.
				Object[] registroCliente = { clienteAux.getIdCliente(),
						clienteAux.getNombre(),
						clienteAux.getDireccion(),
						clienteAux.getPoblacion(),
						clienteAux.getTelef(),
						clienteAux.getNif() };
				infoTablaConsultaClientes.addRow(registroCliente) ;
			}
			//Si no hay clientes en la BD...
		}else {
			mostrarMensajeEmergente("No existe ningún Cliente en la BD con Nif = "+txtNif.getText()+" !");
		} 
	}
	
	/**
	 * Método que muestra un mensaje (recibido por parámetro)
	 * al user, a través de un cuadro de diálogo.
	 */	
	private void mostrarMensajeEmergente(String mensaje) {
		JOptionPane.showMessageDialog(null,mensaje,"ATENCIÓN !!",JOptionPane.WARNING_MESSAGE);
	}
	
}//FIN