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
 * @author Miguel Herrero L�pez (2� DAM). 
 */ 
public class VentanaImportarClientes extends JFrame {

	//Objeto que permite la relaci�n entre esta clase y la clase Coordinador_Clientes.
	private Coordinador_Clientes miCoordinador_clientes;

	//Carpeta donde est�n los archivos para importar:
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
		setTitle("IMPORTAR CLIENTES");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(0, 0, 800, 300); //Tama�o
		getContentPane().setLayout(null);
		setResizable(false); //Tama�o fijo
		setLocationRelativeTo(null); //Posici�n en pantalla

		//Etiqueta Tabla Archivos Importados:
		lblArchivosImportados = new JLabel("CLIENTES IMPORTADOS: ");
		lblArchivosImportados.setFont(new Font("Tahoma", Font.BOLD, 18));		
		lblArchivosImportados.setBounds(10, 11, 514, 20);
		lblArchivosImportados.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(lblArchivosImportados);

		//Tabla que mostrar� el contenido de los archivos importados:
		tablaArchivosImportados = new JTable();
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 42, 514, 160);
		getContentPane().add(scrollPane_1);
		scrollPane_1.setViewportView(tablaArchivosImportados);
		infoTablaArchivosImportados = new DefaultTableModel();
		infoTablaArchivosImportados.addColumn("NOMBRE");
		infoTablaArchivosImportados.addColumn("DIRECCI�N");
		infoTablaArchivosImportados.addColumn("POBLACI�N");
		infoTablaArchivosImportados.addColumn("TEL�FONO");
		infoTablaArchivosImportados.addColumn("NIF");
		//Para solo poder seleccionar una fila de la tabla.
		tablaArchivosImportados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tablaArchivosImportados.setModel(infoTablaArchivosImportados);

		//Tabla que mostrar� los archivos disponibles:
		tablaNombreArchivos = new JTable();
		scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(534, 42, 240, 160);
		getContentPane().add(scrollPane_2);
		scrollPane_2.setViewportView(tablaNombreArchivos);	
		infoTablaNombreArchivos = new DefaultTableModel();
		infoTablaNombreArchivos.addColumn("Archivos disponibles:");
		tablaNombreArchivos.setModel(infoTablaNombreArchivos);

		//Bot�n IMPORTAR ARCHIVO:
		btnImportarArchivos = new JButton("IMPORTAR a la App archivo/s seleccionado/s");
		btnImportarArchivos.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnImportarArchivos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				accionesBotonImportarArchivo();
			}
		});
		btnImportarArchivos.setBounds(534, 213, 240, 34);
		getContentPane().add(btnImportarArchivos);

		//Bot�n INSERTAR CLIENTE EN BD:
		btnInsertarClientes = new JButton("INSERTAR en BD Cliente seleccionado");
		btnInsertarClientes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				accionesBotonInsertarCliente( tipoConex);
			}
		});
		btnInsertarClientes.setBounds(133, 213, 271, 34);
		getContentPane().add(btnInsertarClientes);

		//Bot�n ACTUALIZAR LISTA DE ARCHIVOS:
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

		//Bot�n VOLVER:
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
	 * M�todo que rellena la "Tabla Archivos" con los nombres de los mismos 
	 * para que el user pueda seleccionarlos.
	 * Tambi�n comprueba que el directorio del PC donde se guardan dichos 
	 * archivos (constante global) no est� vaci�.
	 */	
	private void rellenarTablaNombreArchivos() {
		//Si el directorio existe y NO est� vac�o...
		if (directorio.list()!=null) {
			if (directorio.list().length!=0) {
				//Recorremos directorio de archivos y a�adimos sus nombres a la tabla:
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
	 * M�todo que lee e importa el contenido de un archivo (pasado por par�metro)
	 * a la tabla "Archivos Importados" para su visualizaci�n.
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
				
				//Construimos un array de Object y a�adimos su contenido a la tabla.
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
	 * M�todo que contiene el c�digo que se ejecutar� cuando 
	 * pulsemos el bot�n 'Importar Archivo'.
	 */
	private void accionesBotonImportarArchivo() {
		String nombreArchivo="";
		//Guardamos los �ndices de las filas(archivos) seleccionadas.
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
			mostrarMensajeEmergente("Ning�n archivo seleccionado !");
		}
	}
	
	/**
	 * M�todo que contiene el c�digo que se ejecutar� cuando 
	 * pulsemos el bot�n 'Insertar Cliente'.
	 */	
	private void accionesBotonInsertarCliente(String tipoConex) {
		//Guardamos el �ndice de la fila(cliente) seleccionada.
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

			//Comenzamos proceso de inserci�n de Cliente en BD.
			miCoordinador_clientes.altaCliente(miClienteDTO,  tipoConex);
			
		}else {
			mostrarMensajeEmergente("Ning�n cliente seleccionado !");
		}
	}
	
	/**
	 * M�todo que muestra un mensaje (p�sado por par�metro)
	 * al user, a trav�s de un cuadro de di�logo.
	 */	
	private void mostrarMensajeEmergente(String mensaje) {
		JOptionPane.showMessageDialog(null,mensaje,"ATENCI�N !!",JOptionPane.WARNING_MESSAGE);
	}
}//FIN