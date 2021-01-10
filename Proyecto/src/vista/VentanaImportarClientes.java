package vista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;

import java.awt.Font;

import controlador.Coordinador_Clientes;
import modelo.clientes.Cliente_Dto;

/**   
 * Crea y construye la ventana e implementa eventos de los botones.  
 * 
 * @author Miguel Herrero López (2º DAM). 
 */ 
public class VentanaImportarClientes extends JFrame {

	//Objeto que permite la relación entre esta clase y la clase Coordinador_Clientes.
	private Coordinador_Clientes miCoordinador_clientes;

	//Carpeta donde están los archivos para importar:
	private final File directorio = new File("Archivos Clientes para Importar");

	//Componentes de la ventana.
	private JLabel lblArchivosImportados;
	private JTable tablaArchivosImportados, tablaNombreArchivos;
	private DefaultTableModel infoTablaArchivosImportados, infoTablaNombreArchivos;
	private JScrollPane scrollPane_1, scrollPane_2;
	private JButton btnImportarArchivos, btnInsertarClientes, btnActualizarListaArchivos, btnVolver;

	/**
	 * Constructor de la clase.
	 */
	public VentanaImportarClientes(String tipoConex) {
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
		setTitle("IMPORTAR CLIENTES");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(0, 0, 800, 300); //Tamaño
		getContentPane().setLayout(null);
		setResizable(false); //Tamaño fijo
		setLocationRelativeTo(null); //Posición en pantalla

		//Etiqueta Tabla Archivos Importados:
		lblArchivosImportados = new JLabel("CLIENTES IMPORTADOS: ");
		lblArchivosImportados.setFont(new Font("Tahoma", Font.BOLD, 18));		
		lblArchivosImportados.setBounds(10, 11, 514, 20);
		lblArchivosImportados.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(lblArchivosImportados);

		//Tabla que mostrará el contenido de los archivos importados:
		tablaArchivosImportados = new JTable();
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 42, 514, 160);
		getContentPane().add(scrollPane_1);
		scrollPane_1.setViewportView(tablaArchivosImportados);
		infoTablaArchivosImportados = new DefaultTableModel();
		infoTablaArchivosImportados.addColumn("NOMBRE");
		infoTablaArchivosImportados.addColumn("DIRECCIÓN");
		infoTablaArchivosImportados.addColumn("POBLACIÓN");
		infoTablaArchivosImportados.addColumn("TELÉFONO");
		infoTablaArchivosImportados.addColumn("NIF");
		//Para solo poder seleccionar una fila de la tabla.
		tablaArchivosImportados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tablaArchivosImportados.setModel(infoTablaArchivosImportados);

		//Tabla que mostrará los archivos disponibles:
		tablaNombreArchivos = new JTable();
		scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(534, 42, 240, 160);
		getContentPane().add(scrollPane_2);
		scrollPane_2.setViewportView(tablaNombreArchivos);	
		infoTablaNombreArchivos = new DefaultTableModel();
		infoTablaNombreArchivos.addColumn("Archivos disponibles:");
		tablaNombreArchivos.setModel(infoTablaNombreArchivos);

		//Botón IMPORTAR ARCHIVO:
		btnImportarArchivos = new JButton("IMPORTAR a la App archivo/s seleccionado/s");
		btnImportarArchivos.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnImportarArchivos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				accionesBotonImportarArchivo();
			}
		});
		btnImportarArchivos.setBounds(534, 213, 240, 34);
		getContentPane().add(btnImportarArchivos);

		//Botón INSERTAR CLIENTE EN BD:
		btnInsertarClientes = new JButton("INSERTAR en BD Cliente seleccionado");
		btnInsertarClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				accionesBotonInsertarCliente( tipoConex);
			}
		});
		btnInsertarClientes.setBounds(133, 213, 271, 34);
		getContentPane().add(btnInsertarClientes);

		//Botón ACTUALIZAR LISTA DE ARCHIVOS:
		btnActualizarListaArchivos = new JButton("Actualizar lista de archivos");
		btnActualizarListaArchivos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				infoTablaNombreArchivos.setRowCount(0); //Limpiamos contenido tabla.
				//Volvemos a rellenar la tabla con la info actualizada.
				rellenarTablaNombreArchivos(); 
			}
		});
		btnActualizarListaArchivos.setBounds(558, 11, 193, 23);
		getContentPane().add(btnActualizarListaArchivos);

		//Botón VOLVER:
		btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				infoTablaArchivosImportados.setRowCount(0); //Limpiamos contenido tabla.
				miCoordinador_clientes.ocultarVentanaImportarClientes();
				miCoordinador_clientes.mostrarVentanaGestionClientes();	
			}
		});
		btnVolver.setBounds(20, 219, 69, 23);
		getContentPane().add(btnVolver);
		
		rellenarTablaNombreArchivos();
	}

	/**
	 * Método que rellena la "Tabla Archivos" con los nombres de los mismos 
	 * para que el user pueda seleccionarlos.
	 * También comprueba que el directorio del PC donde se guardan dichos 
	 * archivos (constante global) no está vació.
	 */	
	private void rellenarTablaNombreArchivos() {
		//Si el directorio existe y NO está vacío...
		if (directorio.list()!=null) {
			if (directorio.list().length!=0) {
				//Recorremos directorio de archivos y añadimos sus nombres a la tabla:
				for (String nombrefichero : directorio.list()) {
					infoTablaNombreArchivos.addRow(new Object[] {nombrefichero});
				}
			}else {
				infoTablaNombreArchivos.addRow(new Object[] {"No hay archivos en el directorio"});
				btnImportarArchivos.setEnabled(false);
			}
		}else {
			infoTablaNombreArchivos.addRow(new Object[] {"El directorio NO existe"});
			btnImportarArchivos.setEnabled(false);
		}
	}
	
	/**
	 * Método que lee e importa el contenido de un archivo (pasado por parámetro)
	 * a la tabla "Archivos Importados" para su visualización.
	 */	
	private void importarArchivoSelect(String nombreArchivo) {
		File objFichero = new File(directorio+"\\"+nombreArchivo); //Crea objeto Fichero
		try {
			FileInputStream fileIn = new FileInputStream(objFichero); //Crea el flujo de entrada (lectura).
			ObjectInputStream dataIn = new ObjectInputStream(fileIn); //Conecta el flujo de bytes al flujo de datos.

			Cliente_Dto clienteAux; //Cliente_Dto auxiliar para lectura.
			
			//Lectura del fichero (mientras queden bytes por leer).
			while (fileIn.available() > 0) { 
				//Leemos un Object(clase Padre) y lo casteamos a Cliente_Dto(clase hija).
				clienteAux = (Cliente_Dto) dataIn.readObject();
				
				//Construimos un array de Object y añadimos su contenido a la tabla.
				Object[] file = { clienteAux.getNombre(),
						clienteAux.getDireccion(),
						clienteAux.getPoblacion(),
						clienteAux.getTelef(),
						clienteAux.getNif() };
				infoTablaArchivosImportados.addRow(file);
			}
			dataIn.close(); //Cerramos el conector del flujo de lectura.
			
		} catch (FileNotFoundException e) {
			mostrarMensajeEmergente("Fichero NO encontrado !!");
		} catch (IOException e) {
			mostrarMensajeEmergente("Error de Entrada !!!");
		} catch (ClassNotFoundException e) {
			mostrarMensajeEmergente("Error en la lectura del fichero !!!");
		}		
	}
	
	/**
	 * Método que contiene el código que se ejecutará cuando 
	 * pulsemos el botón 'Importar Archivo'.
	 */
	private void accionesBotonImportarArchivo() {
		String nombreArchivo="";
		//Guardamos los índices de las filas(archivos) seleccionadas.
		int[] filasSelect = tablaNombreArchivos.getSelectedRows();
		//Comprobamos si hay alguna fila(archivo) seleccionada
		if (filasSelect.length!=0) {
			//Recorremos filas seleccionadas
			for (int fila : filasSelect) {
				//Obtenemos nombre del archivo seleccionado.
				nombreArchivo = infoTablaNombreArchivos.getValueAt(fila, 0).toString();
				importarArchivoSelect(nombreArchivo);
			}
		}else {
			mostrarMensajeEmergente("Ningún archivo seleccionado !");
		}
	}
	
	/**
	 * Método que contiene el código que se ejecutará cuando 
	 * pulsemos el botón 'Insertar Cliente'.
	 */	
	private void accionesBotonInsertarCliente(String tipoConex) {
		//Guardamos el índice de la fila(cliente) seleccionada.
		int filaSelect = tablaArchivosImportados.getSelectedRow();
		//Comprobamos si hay alguna fila(cliente) seleccionada
		if (filaSelect!=-1) {
			//Creamos objCliente y lo rellenamos con los campos del registro seleccionado.
			Cliente_Dto miClienteDTO = new Cliente_Dto();
			miClienteDTO.setNombre(infoTablaArchivosImportados.getValueAt(filaSelect, 0).toString());
			miClienteDTO.setDireccion(infoTablaArchivosImportados.getValueAt(filaSelect, 1).toString());
			miClienteDTO.setPoblacion(infoTablaArchivosImportados.getValueAt(filaSelect, 2).toString());
			miClienteDTO.setTelef(infoTablaArchivosImportados.getValueAt(filaSelect, 3).toString());
			miClienteDTO.setNif(infoTablaArchivosImportados.getValueAt(filaSelect, 4).toString());

			//Comenzamos proceso de inserción de Cliente en BD.
			miCoordinador_clientes.altaCliente(miClienteDTO,  tipoConex);
			
		}else {
			mostrarMensajeEmergente("Ningún cliente seleccionado !");
		}
	}
	
	/**
	 * Método que muestra un mensaje (pásado por parámetro)
	 * al user, a través de un cuadro de diálogo.
	 */	
	private void mostrarMensajeEmergente(String mensaje) {
		JOptionPane.showMessageDialog(null,mensaje,"ATENCIÓN !!",JOptionPane.WARNING_MESSAGE);
	}
}//FIN