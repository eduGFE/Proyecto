package vista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import controlador.Coordinador_Clientes;
import modelo.clientes.Cliente_Dto;

import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.Font;

/**   
 * Crea y construye la ventana e implementa eventos de los botones.  
 * 
 * @author Miguel Herrero López (2º DAM). 
 */ 
public class VentanaInsertarClientes extends JFrame {

	//Objeto que permite la relación entre esta clase y la clase Coordinador_Clientes.
	private Coordinador_Clientes miCoordinador_clientes;
	//Componentes de la ventana.
	private JLabel labNombre,labDireccion,labPoblacion,labTelefono,labNif;
	private static JTextField txtNombre,txtDireccion,txtPoblacion,txtTelefono,txtNif;
	private JButton btnInsertarCliente, btnVolver;


	/**
	 * Constructor de la clase.
	 */
	public VentanaInsertarClientes(String tipoConex) {
		inicializarVentana( tipoConex);
	}
	
	/**
	 * Método que enlaza la clase con el coordinador.
	 */
	public void setCoordinadorClientes(Coordinador_Clientes miCoordinador_clientes) {
		this.miCoordinador_clientes = miCoordinador_clientes;
	}
	
	/*
	 * Método llamado desde Cliente_Dao(por eso public y static) para limpiar 
	 * las cajas de texto una vez el cliente se haya insertado.
	 */
	public static void limpiarCampos() {
		txtNombre.setText("");
		txtDireccion.setText("");
		txtPoblacion.setText("");
		txtTelefono.setText("");
		txtNif.setText("");
	}

	////////////////////- MÉTODOS PRIVADOS -////////////////////

	/**
	 * Método donde se inicializan los componentes de la ventana.
	 */
	private void inicializarVentana(String tipoConex) {
		//Opciones de visualización/actuación de la ventana:
		setTitle("INSERTAR CLIENTES");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(0, 0, 437, 300); //Tamaño
		getContentPane().setLayout(null);
		setResizable(false); //Tamaño fijo
		setLocationRelativeTo(null); //Posición en pantalla

		//Etiqueta y TextField NOMBRE:
		labNombre = new JLabel("NOMBRE :");
		labNombre.setFont(new Font("Tahoma", Font.BOLD, 14));
		labNombre.setBounds(32, 20, 100, 20);
		getContentPane().add(labNombre);		
		txtNombre = new JTextField();
		txtNombre.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtNombre.setBounds(138, 20, 250, 25);
		getContentPane().add(txtNombre);
		txtNombre.setColumns(10);
		
		//Etiqueta y TextField DIRECCIÓN:
		labDireccion = new JLabel("DIRECCI\u00D3N :");
		labDireccion.setFont(new Font("Tahoma", Font.BOLD, 14));
		labDireccion.setBounds(32, 60, 100, 20);
		getContentPane().add(labDireccion);	
		txtDireccion = new JTextField();
		txtDireccion.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtDireccion.setColumns(10);
		txtDireccion.setBounds(138, 60, 250, 25);
		getContentPane().add(txtDireccion);
		
		//Etiqueta y TextField POBLACIÓN:
		labPoblacion = new JLabel("POBLACI\u00D3N :");
		labPoblacion.setFont(new Font("Tahoma", Font.BOLD, 14));
		labPoblacion.setBounds(32, 100, 100, 20);
		getContentPane().add(labPoblacion);	
		txtPoblacion = new JTextField();
		txtPoblacion.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtPoblacion.setColumns(10);
		txtPoblacion.setBounds(138, 100, 250, 25);
		getContentPane().add(txtPoblacion);
		
		//Etiqueta y TextField TELÉFONO:
		labTelefono = new JLabel("TEL\u00C9FONO :");
		labTelefono.setFont(new Font("Tahoma", Font.BOLD, 14));
		labTelefono.setBounds(32, 140, 100, 20);
		getContentPane().add(labTelefono);	
		txtTelefono = new JTextField();
		txtTelefono.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtTelefono.setColumns(10);
		txtTelefono.setBounds(138, 140, 250, 25);
		getContentPane().add(txtTelefono);
		
		//Etiqueta y TextField NIF:
		labNif = new JLabel("NIF :");
		labNif.setFont(new Font("Tahoma", Font.BOLD, 14));
		labNif.setBounds(32, 180, 100, 20);
		getContentPane().add(labNif);	
		txtNif = new JTextField();
		txtNif.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtNif.setColumns(10);
		txtNif.setBounds(138, 180, 250, 25);
		getContentPane().add(txtNif);
		
		//Botón INSERTAR CLIENTE:
		btnInsertarCliente = new JButton("INSERTAR Cliente en BD");
		btnInsertarCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				accionesBotonInsertarCliente( tipoConex);				
			}
		});
		btnInsertarCliente.setBounds(140, 216, 248, 34);
		getContentPane().add(btnInsertarCliente);
		
		//Botón VOLVER:
		btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				miCoordinador_clientes.ocultarVentanaInsertarClientes();
				miCoordinador_clientes.mostrarVentanaGestionClientes();	
			}
		});
		btnVolver.setBounds(20, 227, 89, 23);
		getContentPane().add(btnVolver);
	}
	
	/**
	 * Método que contiene el código que se ejecutará cuando 
	 * pulsemos el botón 'Insertar Cliente'.
	 */	
	private void accionesBotonInsertarCliente(String tipoConex) {
			//Creamos objeto Cliente_Dto y lo rellenamos con los valores de los campos de texto.
			Cliente_Dto miClienteDTO = new Cliente_Dto();
			miClienteDTO.setNombre(txtNombre.getText());
			miClienteDTO.setDireccion(txtDireccion.getText());
			miClienteDTO.setPoblacion(txtPoblacion.getText());
			miClienteDTO.setTelef(txtTelefono.getText());
			miClienteDTO.setNif(txtNif.getText());

			//Comenzamos proceso de inserción de Cliente en BD.
			miCoordinador_clientes.altaCliente(miClienteDTO,  tipoConex);
	}

	
}//FIN