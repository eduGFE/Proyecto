package vista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import controlador.Coordinador_Clientes;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**   
 * Crea y construye la ventana y también crea 
 * enlace con el Controlador_Clientes e 
 * implementa eventos de los botones.  
 * @author Miguel Herrero López (2º DAM). 
 */ 
public class VentanaBorrarClientes extends JFrame {

	//Objeto que permite la relación entre esta clase y la clase Coordinador_Clientes.
	private Coordinador_Clientes miCoordinador_clientes;
	private JLabel labInstrucciones1, labInstrucciones2, labNif;
	private JTextField txtNif;
	private JButton btnBorrarCliente, btnVolver;

	/**
	 * Constructor de la clase.
	 */
	public VentanaBorrarClientes(String tipoConex) {
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
		setTitle("BORRAR CLIENTES");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(0, 0, 400, 230); //Tamaño
		getContentPane().setLayout(null);
		setResizable(false); //Tamaño fijo
		setLocationRelativeTo(null); //Posición en pantalla

		//Etiquetas INSTRUCCIONES:
		labInstrucciones1 = new JLabel("Introduzca NIF del Cliente que desea borrar");
		labInstrucciones1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		labInstrucciones1.setHorizontalAlignment(SwingConstants.CENTER);
		labInstrucciones1.setBounds(41, 11, 297, 20);
		getContentPane().add(labInstrucciones1);		
		labInstrucciones2 = new JLabel(" y confirme pulsando el bot\u00F3n inferior.");
		labInstrucciones2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		labInstrucciones2.setHorizontalAlignment(SwingConstants.CENTER);
		labInstrucciones2.setBounds(41, 30, 297, 20);
		getContentPane().add(labInstrucciones2);

		//Etiqueta y TextField NIF:
		labNif = new JLabel("NIF :");
		labNif.setFont(new Font("Tahoma", Font.BOLD, 14));
		labNif.setBounds(41, 64, 56, 20);
		getContentPane().add(labNif);	
		txtNif = new JTextField();
		txtNif.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtNif.setColumns(10);
		txtNif.setBounds(88, 63, 250, 25);
		getContentPane().add(txtNif);

		//Botón BORRAR CLIENTE:
		btnBorrarCliente = new JButton("BORRAR CLIENTE");
		btnBorrarCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				accionesBotonBorrarCliente( tipoConex);
			}
		});
		btnBorrarCliente.setBounds(41, 106, 297, 34);
		getContentPane().add(btnBorrarCliente);

		//Botón VOLVER:
		btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				miCoordinador_clientes.ocultarVentanaBorrarClientes();
				miCoordinador_clientes.mostrarVentanaGestionClientes();	
			}
		});
		btnVolver.setBounds(144, 151, 89, 23);
		getContentPane().add(btnVolver);
	}

	/**
	 * Método que contiene el código que se ejecutará cuando 
	 * pulsemos el botón 'BorrarCliente'.
	 */
	private void accionesBotonBorrarCliente(String tipoConex) {
		//Si el txtNif está vacío...
		if (txtNif.getText().isEmpty()){
			//Preguntamos al user si quiere continuar.
			int eleccion = preguntarConfirmacion("Ha seleccionado un NIF sin contenido."
					+ "\n¿Desea borrar de la BD todos los clientes cuyo NIF esté vacío?");
			//0=yes, 1=no, -1=CerrarCuadro(X))
			if (eleccion==0) {
				//Comenzamos proceso de borrado de Cliente en la BD.
				miCoordinador_clientes.borrarClientes("",  tipoConex);
			}
		}else {
			//Si txtNif tiene contenido, comenzamos con el proceso de borrado.
			miCoordinador_clientes.borrarClientes(txtNif.getText(),  tipoConex);
			txtNif.setText("");
		}
	}
	
	/**
	 * Método que muestra un mensaje (pásado por parámetro)
	 * al user a través de un cuadro de diálogo, y le pide que confirme o desestime una opción.
	 * Devuelve la opción elegida. ( Yes=0, No=1, X=-1 )
	 */	
	private int preguntarConfirmacion(String mensaje) {
		int eleccion = JOptionPane.showConfirmDialog(null,mensaje,"ATENCIÓN !!",
				JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
		return eleccion;
	}
	
}//FIN