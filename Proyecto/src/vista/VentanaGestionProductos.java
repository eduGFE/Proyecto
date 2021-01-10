package vista;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.table.DefaultTableModel;

import controlador.Coordinador_Productos;

public class VentanaGestionProductos extends JFrame implements Runnable {
	// Se crea un cordinador_productos porque tiene un metodo para poder regresar a
	// la ventana de inicio
	private Coordinador_Productos coordinador_productos;
	private JFrame frame;
	//tablas
	private JTable table1;
	private DefaultTableModel model1 = new DefaultTableModel();
	private JTable table2;
	private DefaultTableModel model2 = new DefaultTableModel();

	public VentanaGestionProductos(String[] args) {
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

	public void initialize(String[] args) {
		table1 = new JTable();
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 42, 444, 171);
		getContentPane().add(scrollPane_1);
		//Tabla con la informacion de la bbdd
		scrollPane_1.setViewportView(table1);
		model1.addColumn("Id");
		model1.addColumn("Descripción");
		model1.addColumn("Stock Anual");
		model1.addColumn("Pvp");
		table1.setModel(model1);
		//tabla para motar la informacion del directorio productos
		table2 = new JTable();
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(464, 42, 231, 171);
		getContentPane().add(scrollPane_2);
		scrollPane_2.setViewportView(table2);
		model2.addColumn("Nombre fichero");
		table2.setModel(model2);
		setBounds(100, 100, 711, 289);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		//Boton para volver al menu principal
		JButton botonVolver = new JButton("Volver");
		botonVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				coordinador_productos.mostrarVentanaMenuPrincipal();
				setVisible(false);
			}
		});
		botonVolver.setBounds(10, 224, 89, 23);
		getContentPane().add(botonVolver);
		//Boton para mandar la informacion desde el fichero a la bbdd
		JButton botonImportar = new JButton("Importar");
		botonImportar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int fila = table2.getSelectedRow();
				try {
					coordinador_productos.importarabbdd(args, table2, fila);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		botonImportar.setBounds(606, 11, 89, 23);
		getContentPane().add(botonImportar);
		JButton EliminarporId = new JButton("Eliminar producto por ID");
		EliminarporId.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id = "";
				//Bloque que codigo que sirve para que la aplicacion no deje de funcionar
				//Por dejar algun campo vacio o por si se a introducido informacion no valida
				id = JOptionPane.showInputDialog("Inserte el ID del producto que quiera eliminar");
				try {
					if (id == null) {
					} else {
						if (esnumero(id) == false) {
							JOptionPane.showMessageDialog(null, "Inserte un ID valido", "Información",
									JOptionPane.INFORMATION_MESSAGE);
						}else {
							if (!id.equals("")) {
								coordinador_productos.eliminarproducto(args, Integer.valueOf(id), model1);
							}
						}
					}
				} catch (NumberFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		EliminarporId.setBounds(532, 224, 163, 23);
		getContentPane().add(EliminarporId);

		JButton consultarporid = new JButton("Consultar producto por ID");
		consultarporid.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id = "";
				//Bloque que codigo que sirve para que la aplicacion no deje de funcionar
				//Por dejar algun campo vacio o por si se a introducido informacion no valida
				id = JOptionPane.showInputDialog("Inserte el ID del producto que quiera consultar");
				try {
					if (id == null) {
					} else {
						if (esnumero(id) == false) {
							JOptionPane.showMessageDialog(null, "Inserte un ID valido", "Información",
									JOptionPane.INFORMATION_MESSAGE);
						}
						 else {
							if (!id.equals("")) {
								coordinador_productos.consultarproductoporid(args, Integer.valueOf(id), model1);
							}

						}
					}

				} catch (NumberFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		consultarporid.setBounds(333, 224, 189, 23);
		getContentPane().add(consultarporid);

		JButton btnNewButton_4 = new JButton("Consultar todos los productos");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					coordinador_productos.consultarproductos(args, model1);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton_4.setBounds(109, 224, 219, 23);
		getContentPane().add(btnNewButton_4);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(false);
		Thread mihilo = new Thread(this);
		mihilo.start();
	}
	public void setCoordinadorProductos(Coordinador_Productos coordinador_productos) {
		this.coordinador_productos = coordinador_productos;
	}
	//Comprueba si un numero es numeros o se a introducido alguna letra u otro caracter
	 public static boolean esnumero(String cadena) {

	        boolean resultado;

	        try {
	            Integer.parseInt(cadena);
	            resultado = true;
	        } catch (NumberFormatException excepcion) {
	            resultado = false;
	        }

	        return resultado;
	    }
//Hilo que muestra la informacion del directio Productos nada mas se muestra la ventana
	public void run() {
		File directorio = new File("C:\\Users\\Javie\\Proyecto_AD\\Proyecto\\Proyecto\\Productos");
		String[] lista = directorio.list();
		Object[] file = null;
		for (int i = 0; i < lista.length; i++) {
			file = new Object[] { lista[i] };
			model2.addRow(file);
		}

	}
}
