package vista;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import controlador.Coordinador_Clientes;
import modelo.clientes.Cliente_Dto;

import javax.swing.JRadioButton;

/**   
 * Crea y construye la ventana y también crea 
 * enlace con el Controlador_Clientes e 
 * implementa eventos de los botones.  
 * @author Miguel Herrero López (2º DAM). 
 * */ 
public class VentanaModificarClientes extends JFrame {

	//Objeto que permite la relación entre esta clase y la clase Coordinador_Clientes.
	private Coordinador_Clientes miCoordinador_clientes;

	//Componentes de la ventana.
	private JLabel lblListaClientes, lblCamposModificables1, lblCamposModificables2, lblInstrucciones;
	private JTable tablaListadoClientes;
	private DefaultTableModel infoTablaListadoClientes;
	private JScrollPane scrollPane_1;
	private JRadioButton radDireccion, radPoblacion, radTelefono;
	private JButton btnDescargarListaClientes, btnModificarCliente, btnVolver;

	/**
	 * Constructor de la clase.
	 */
	public VentanaModificarClientes(String tipoConex) {
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
		setTitle("MODIFICAR CLIENTES");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(0, 0, 800, 330); //Tamaño
		getContentPane().setLayout(null);
		setResizable(false); //Tamaño fijo
		setLocationRelativeTo(null); //Posición en pantalla

		//Etiqueta Tabla Lista de Clientes:
		lblListaClientes = new JLabel("CLIENTES EXISTENTES");
		lblListaClientes.setFont(new Font("Tahoma", Font.BOLD, 18));		
		lblListaClientes.setBounds(10, 11, 271, 20);
		lblListaClientes.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(lblListaClientes);

		//Tabla que mostrará el listado de Clientes de la BD:
		tablaListadoClientes = new JTable();
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 42, 606, 160);
		getContentPane().add(scrollPane_1);
		scrollPane_1.setViewportView(tablaListadoClientes);
		infoTablaListadoClientes = new DefaultTableModel() {
			//Sobreescribimos el método isCellEditable para 
			//que el user no pueda editar las celdas de la tabla.
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		infoTablaListadoClientes.addColumn("ID");
		infoTablaListadoClientes.addColumn("NOMBRE");
		infoTablaListadoClientes.addColumn("DIRECCIÓN");
		infoTablaListadoClientes.addColumn("POBLACIÓN");
		infoTablaListadoClientes.addColumn("TELÉFONO");
		infoTablaListadoClientes.addColumn("NIF");
		//Para sólo poder seleccionar una fila de la tabla.
		tablaListadoClientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tablaListadoClientes.setModel(infoTablaListadoClientes);

		//Etiquetas CAMPOS MODIFICABLES:
		lblCamposModificables1 = new JLabel("CAMPOS");
		lblCamposModificables1.setHorizontalAlignment(SwingConstants.CENTER);
		lblCamposModificables1.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblCamposModificables1.setBounds(611, 42, 183, 20);
		getContentPane().add(lblCamposModificables1);
		lblCamposModificables2 = new JLabel("MODIFICABLES");
		lblCamposModificables2.setHorizontalAlignment(SwingConstants.CENTER);
		lblCamposModificables2.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblCamposModificables2.setBounds(611, 67, 183, 20);
		getContentPane().add(lblCamposModificables2);

		//Radio-Buttons:
		radDireccion = new JRadioButton(" Direcci\u00F3n");
		radDireccion.setFont(new Font("Tahoma", Font.PLAIN, 12));
		radDireccion.setBounds(660, 95, 80, 23);
		getContentPane().add(radDireccion);
		radPoblacion = new JRadioButton(" Poblaci\u00F3n");
		radPoblacion.setFont(new Font("Tahoma", Font.PLAIN, 12));
		radPoblacion.setBounds(660, 125, 80, 23);
		getContentPane().add(radPoblacion);
		radTelefono = new JRadioButton(" Tel\u00E9fono");
		radTelefono.setFont(new Font("Tahoma", Font.PLAIN, 12));
		radTelefono.setBounds(660, 155, 80, 23);
		getContentPane().add(radTelefono);

		//Etiqueta INSTRUCCIONES:
		lblInstrucciones = new JLabel("Seleccione un cliente de la lista y marque el/los campo/s que quiera modificar");
		lblInstrucciones.setHorizontalAlignment(SwingConstants.CENTER);
		lblInstrucciones.setBounds(151, 215, 472, 20);
		getContentPane().add(lblInstrucciones);

		//Botón DESCARGAR CLIENTES:
		btnDescargarListaClientes = new JButton("DESCARGAR/ACTUALIZAR LISTA CLIENTES");
		btnDescargarListaClientes.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnDescargarListaClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				infoTablaListadoClientes.setRowCount(0); //Limpiamos contenido tabla.
				rellenarTablaListadoClientes( tipoConex);
			}
		});
		btnDescargarListaClientes.setBounds(272, 11, 335, 23);
		getContentPane().add(btnDescargarListaClientes);

		//Botón MODIFICAR CLIENTE:
		btnModificarCliente = new JButton("MODIFICAR CLIENTE");
		btnModificarCliente.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnModificarCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				accionesBotonModificarCliente( tipoConex);
			}
		});
		btnModificarCliente.setBounds(242, 246, 271, 34);
		getContentPane().add(btnModificarCliente);

		//Botón VOLVER:
		btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				infoTablaListadoClientes.setRowCount(0); //Limpiamos contenido tabla.
				miCoordinador_clientes.ocultarVentanaModificarClientes();
				miCoordinador_clientes.mostrarVentanaGestionClientes();	
			}
		});
		btnVolver.setBounds(10, 257, 89, 23);
		getContentPane().add(btnVolver);
	}

	/**
	 * Método que rellena la "Tabla Clientes" con los datos 
	 * de los mismos provenientes de la BD, para su visualización.
	 */	
	private void rellenarTablaListadoClientes(String tipoConex) {
		//Creamos ArrayList donde se guardarán los objetos DTO de los clientes.
		ArrayList<Cliente_Dto> listaClientesDTO = miCoordinador_clientes.consultarTodosClientes(tipoConex);
		
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
				infoTablaListadoClientes.addRow(registroCliente) ;
			}
			//Si no hay clientes en la BD...
		}else {
			mostrarMensajeEmergente("No existe ningún Cliente en la BD !");
			btnModificarCliente.setEnabled(false);
		} 
	}
	
	/**
	 * Método que contiene el código que se ejecutará cuando 
	 * pulsemos el botón 'MODIFICAR CLIENTE'.
	 */
	private void accionesBotonModificarCliente(String tipoConex) {
		//Guardamos el índice de la fila(cliente) seleccionada.
		int filaSelect = tablaListadoClientes.getSelectedRow();
		//Comprobamos si hay alguna fila(cliente) seleccionada y algún radio-button seleccionado.
		if (filaSelect!=-1 && (radDireccion.isSelected() || radPoblacion.isSelected() || radTelefono.isSelected())) {
			//Guardamos el id del cliente seleccionado.
			int idClienteSelect = (int) infoTablaListadoClientes.getValueAt(filaSelect, 0);
			
			//obtencionDatosNuevos(filaSelect,numColumna,radBut,nomCampo,IdSelect);
			String newDireccion = obtencionDatosNuevos(filaSelect, 2, radDireccion, "DIRECCIÓN", idClienteSelect);
			String newPoblacion = obtencionDatosNuevos(filaSelect, 3, radPoblacion, "POBLACIÓN", idClienteSelect);
			String newTelefono = obtencionDatosNuevos(filaSelect, 4, radTelefono, "TELÉFONO", idClienteSelect);

			if (newDireccion!=null && newPoblacion!=null && newTelefono!=null) {
				//Comenzamos proceso de actualización de Cliente en BD.
				miCoordinador_clientes.modificarCliente(idClienteSelect, newDireccion, newPoblacion, newTelefono,  tipoConex);
			}else {
				mostrarMensajeEmergente("Operación CANCELADA."
						+ "\nHa cometido un error al introducir los datos, inténtelo de nuevo por favor.");
			}			
		}else {
			mostrarMensajeEmergente("Debe seleccionar un CLIENTE y mínimo un CAMPO a modificar.");
		}
	}
	
	/**
	 * Método encargado de pedir al usuario nuevos valores para los campos de 
	 * un registro específico, cuyos radiobutons han sido seleccionados.
	 * Si algún radioButton en cuestion no ha sido seleccionado, captura el 
	 * valor original/antigüo del campo. 
	 * Finalmente devuelve dicho valor.
	 */	
	private String obtencionDatosNuevos(int filaSelect, int numColumna, 
			JRadioButton radBut, String nomCampo, int idSelect) {
		String datoValor="";
		if (radBut.isSelected()) {
			//Ok=valor ; Cancel=null ; X=null
			datoValor = JOptionPane.showInputDialog
					("Inserte la nueva "+nomCampo+" para el cliente con ID = "+idSelect+".");
		}else {
			datoValor = (String) infoTablaListadoClientes.getValueAt(filaSelect, numColumna);
		}
		return datoValor;
	}

	/**
	 * Método que muestra un mensaje (recibido por parámetro)
	 * al user, a través de un cuadro de diálogo.
	 */	
	private void mostrarMensajeEmergente(String mensaje) {
		JOptionPane.showMessageDialog(null,mensaje,"ATENCIÓN !!",JOptionPane.WARNING_MESSAGE);
	}
	
}//FIN


