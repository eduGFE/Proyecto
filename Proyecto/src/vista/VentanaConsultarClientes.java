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
 * Crea y construye la ventana y tambi�n crea 
 * enlace con el Controlador_Clientes e 
 * implementa eventos de los botones.  
 * @author Miguel Herrero L�pez (2� DAM). 
 * */ 

public class VentanaConsultarClientes extends JFrame {

	//Objeto que permite la relaci�n entre esta clase y la clase Coordinador_Clientes.
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
	 * M�todo que enlaza la clase con el coordinador.
	 */
	public void setCoordinadorClientes(Coordinador_Clientes miCoordinador_clientes) {
		this.miCoordinador_clientes = miCoordinador_clientes;
	}
	
	////////////////////- M�TODOS PRIVADOS -////////////////////

	/**
	 * M�todo privado donde se inicializan los componentes de la ventana.
	 */
	private void inicializarVentana(String tipoConex) {
		//Opciones de visualizaci�n/actuaci�n de la ventana:
		setTitle("CONSULTAR CLIENTES");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(0, 0, 646, 244); //Tama�o
		getContentPane().setLayout(null);
		setResizable(false); //Tama�o fijo
		setLocationRelativeTo(null); //Posici�n en pantalla

		//Tabla que mostrar� los Clientes coincidentes con la b�squeda:
		tablaConsultaClientes = new JTable();
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 56, 606, 102);
		getContentPane().add(scrollPane_1);
		scrollPane_1.setViewportView(tablaConsultaClientes);
		infoTablaConsultaClientes = new DefaultTableModel();
		infoTablaConsultaClientes.addColumn("ID");
		infoTablaConsultaClientes.addColumn("NOMBRE");
		infoTablaConsultaClientes.addColumn("DIRECCI�N");
		infoTablaConsultaClientes.addColumn("POBLACI�N");
		infoTablaConsultaClientes.addColumn("TEL�FONO");
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

		//Bot�n CONSULTAR CLIENTES:
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

		//Bot�n VOLVER:
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
	 * M�todo que comienza el proceso de consulta de Clientes por Nif y muestra 
	 * los registros encontrados en la "Tabla Consulta Clientes" si los recibe. 
	 * Si no existen Clientes coincidentes avisa al user de ello mediante un cuadro de di�logo.
	 */	
	private void mostrarClientesCoincidentes(String tipoConex) {
		
		//Creamos ArrayList donde se guardar�n los objetos DTO de los clientes, llamando al m�todo que los busca.
		ArrayList<Cliente_Dto> listaClientesDTO = 
				miCoordinador_clientes.consultarClientes(txtNif.getText(),  tipoConex);
		
		if (listaClientesDTO!=null) { //Si hay clientes en la BD rellenamos la tabla

			//Recorremos nuestra listaClientesDTO:
			for (Cliente_Dto clienteAux : listaClientesDTO) {
				//Construimos un array de Object para cada registro y a�adimos su contenido a la tabla.
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
			mostrarMensajeEmergente("No existe ning�n Cliente en la BD con Nif = "+txtNif.getText()+" !");
		} 
	}
	
	/**
	 * M�todo que muestra un mensaje (recibido por par�metro)
	 * al user, a trav�s de un cuadro de di�logo.
	 */	
	private void mostrarMensajeEmergente(String mensaje) {
		JOptionPane.showMessageDialog(null,mensaje,"ATENCI�N !!",JOptionPane.WARNING_MESSAGE);
	}
	
}//FIN