package vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.table.DefaultTableModel;

import controlador.Coordinador_Ventas;
import modelo.ventas.Venta_Dto;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class VentanaGestionVentas extends  JFrame {

	//Se crea un cordinador_ventas porque tiene un metodo para poder regresar a la ventana de inicio
	private Coordinador_Ventas coordinador_ventas = new Coordinador_Ventas();
	private JFrame frame;
	private JButton botonMostrarVentas;
	private JButton botonEliminarVentas;
	private JComboBox comboBoxMostrar;
	private JButton botonExportar;
	private JTable tablaVentas;
	private DefaultTableModel modeloTablaVentas = new DefaultTableModel() {
			public boolean isCellEditable(int row, int column) {
             return false;
			}
	};

	public VentanaGestionVentas(String tipoConex) {
		setTitle("Ventana Gestion Ventas");
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {

		}
		initialize(tipoConex);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(String tipoConex) {
		String tipoConexion = tipoConex;
		tablaVentas = new JTable();
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 677, 174);
		getContentPane().add(scrollPane);
		scrollPane.setViewportView(tablaVentas);
		modeloTablaVentas.addColumn("Id venta");
		modeloTablaVentas.addColumn("Fecha venta");
		modeloTablaVentas.addColumn("ID cliente");
		modeloTablaVentas.addColumn("ID producto");
		modeloTablaVentas.addColumn("Cantidad");


		tablaVentas.setModel(modeloTablaVentas);

		setBounds(100, 100, 711, 331);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);

		JButton botonVolver = new JButton("Volver");
		botonVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				coordinador_ventas.mostrarVentanaMenuPrincipal();
				coordinador_ventas.ocultarVentanaGestionVentas();
			}
		});
		botonVolver.setBounds(10, 265, 135, 23);
		getContentPane().add(botonVolver);
		//BOTON INSERTAR VENTA
		JButton botonInsertaVenta = new JButton("Insertar nueva venta");
		botonInsertaVenta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				Venta_Dto venta = new Venta_Dto();
				String patronNumeros =  "[0-9]*";
				String idCliente;
				String idProducto;
				String cantidad;
				try {
					idCliente = JOptionPane.showInputDialog("Introduce ID cliente");
					idProducto = JOptionPane.showInputDialog("Introduce ID producto");
					cantidad = JOptionPane.showInputDialog("Introduce las unidades a vender");

					if(Pattern.matches(patronNumeros, idCliente)&& Pattern.matches(patronNumeros, idProducto) && Pattern.matches(patronNumeros, cantidad)) {
						venta.setIdCliente(Integer.parseInt(idCliente));
						venta.setIdProducto(Integer.parseInt(idProducto));
						venta.setCantidad(Integer.parseInt(cantidad));
						coordinador_ventas.insertaVenta(venta, tipoConex);
					}else {
						JOptionPane.showMessageDialog(null, "Los datos no se han introducido correctamente");
					}
				}catch(Exception np) {
					JOptionPane.showMessageDialog(null, "Operacion cancelada");
				}




			}
		});
		botonInsertaVenta.setBounds(10, 196, 162, 23);
		getContentPane().add(botonInsertaVenta);


		comboBoxMostrar = new JComboBox();
		comboBoxMostrar.setModel(new DefaultComboBoxModel(new String[] {"Mostrar por fecha.", "Mostrar por cliente."}));
		comboBoxMostrar.setToolTipText("");
		comboBoxMostrar.setBounds(196, 196, 144, 23);
		getContentPane().add(comboBoxMostrar);


		//BOTON MOSTRAR VENTAS
		botonMostrarVentas = new JButton("Mostrar ventas");
		botonMostrarVentas.setBounds(196, 230, 144, 23);
		botonMostrarVentas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modeloTablaVentas.setRowCount(0);
				//Si el comboBox marca intervalo de fechas:
				if(comboBoxMostrar.getSelectedIndex()==0){
					String fechaMin;
					String fechaMax;
					ArrayList<Venta_Dto> listadoMostrar=null;
					try {
						fechaMin = JOptionPane.showInputDialog("Introduce la fecha de inicio. El formato debe ser: YYYY-MM-DD");
						fechaMax = JOptionPane.showInputDialog("Introduce la fecha de fin. El formato debe ser: YYYY-MM-DD\"");
						listadoMostrar = coordinador_ventas.mostrarVentasPorFecha(fechaMin, fechaMax, tipoConexion);
					}catch(Exception np) {
						JOptionPane.showMessageDialog(null, "Operacion cancelada");
					}

					if(listadoMostrar != null) {
						if(listadoMostrar.isEmpty()){
							JOptionPane.showMessageDialog(null, "No se  han encontrado ventas en ese rango de fechas");
						}else{
							for(int i = 0; i<listadoMostrar.size();i++){
								Venta_Dto ventaAux = new Venta_Dto();
								ventaAux.setIdVenta(listadoMostrar.get(i).getIdVenta());
								ventaAux.setFechaVenta(listadoMostrar.get(i).getFechaVenta());
								ventaAux.setIdCliente(listadoMostrar.get(i).getIdCliente());
								ventaAux.setIdProducto(listadoMostrar.get(i).getIdProducto());
								ventaAux.setCantidad(listadoMostrar.get(i).getCantidad());
								Object[] registroVenta ={
										ventaAux.getIdVenta(),
										ventaAux.getFechaVenta(),
										ventaAux.getIdCliente(),
										ventaAux.getIdProducto(),
										ventaAux.getCantidad()
								};

								modeloTablaVentas.addRow(registroVenta);
							}
						}
					}



					//Si el comboBox marca busqueda por NIF
				}else if(comboBoxMostrar.getSelectedIndex()==1){
					String nifCliente;
					ArrayList<Venta_Dto> listadoMostrar = null;
					try {
						nifCliente = JOptionPane.showInputDialog("Introduce el NIF del cliente a consultar");
						listadoMostrar = coordinador_ventas.mostrarVentasPorNIF(nifCliente, tipoConexion);
					}catch(Exception np) {
						JOptionPane.showMessageDialog(null, "Operacion cancelada");
					}
					
					if(listadoMostrar!=null) {
						if(listadoMostrar.isEmpty()) {
							JOptionPane.showMessageDialog(null, "El cliente no ha realizado ninguna compra");
						}else {
							for(int i = 0; i<listadoMostrar.size();i++){
								Venta_Dto ventaAux = new Venta_Dto();
								ventaAux.setIdVenta(listadoMostrar.get(i).getIdVenta());
								ventaAux.setFechaVenta(listadoMostrar.get(i).getFechaVenta());
								ventaAux.setIdCliente(listadoMostrar.get(i).getIdCliente());
								ventaAux.setIdProducto(listadoMostrar.get(i).getIdProducto());
								ventaAux.setCantidad(listadoMostrar.get(i).getCantidad());
								Object[] registroVenta ={
										ventaAux.getIdVenta(),
										ventaAux.getFechaVenta(),
										ventaAux.getIdCliente(),
										ventaAux.getIdProducto(),
										ventaAux.getCantidad()
								};

								modeloTablaVentas.addRow(registroVenta);
							}
						}
					}
				}
			}
		});

		getContentPane().add(botonMostrarVentas);

		JComboBox comboBoxEliminar = new JComboBox();
		comboBoxEliminar.setModel(new DefaultComboBoxModel(new String[] {"Eliminar por ID.", "Eliminar por cliente."}));
		comboBoxEliminar.setBounds(375, 196, 144, 23);
		getContentPane().add(comboBoxEliminar);

		//BOTON ELIMINAR VENTAS
		botonEliminarVentas = new JButton("Eliminar ventas");
		botonEliminarVentas.setBounds(375, 230, 144, 23);
		botonEliminarVentas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					//Si el combobox marca eliminar por id

					if(comboBoxEliminar.getSelectedIndex()==0){
						String idVenta= JOptionPane.showInputDialog("Inserte el ID de la venta que desea eliminar");
						coordinador_ventas.borrarVentaPorID(idVenta, tipoConexion);

						//si el combobox marca eliminar por cliente
					}else if(comboBoxEliminar.getSelectedIndex()==1) {
						String nifCliente;
						nifCliente=JOptionPane.showInputDialog("Inserte el NIF del cliente cuya venta se quiere eliminar");
						coordinador_ventas.borrarVentaPorNIF(nifCliente, tipoConexion);
					}
				}catch(Exception np) {
					JOptionPane.showMessageDialog(null, "Operacion cancelada");
				}

			}
		});
		getContentPane().add(botonEliminarVentas);

		JComboBox comboBoxExportarTipo = new JComboBox();
		comboBoxExportarTipo.setModel(new DefaultComboBoxModel(new String[] {"Exportar XML", "Exportar CSV"}));
		comboBoxExportarTipo.setBounds(554, 196, 121, 23);
		getContentPane().add(comboBoxExportarTipo);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(false);

		JComboBox comboBoxExportarFiltro = new JComboBox();
		comboBoxExportarFiltro.setModel(new DefaultComboBoxModel(new String[] {"Exportar por fechas", "Exportar por cliente"}));
		comboBoxExportarFiltro.setToolTipText("");
		comboBoxExportarFiltro.setBounds(554, 230, 121, 23);
		getContentPane().add(comboBoxExportarFiltro);

		//BOTON EXPORTAR
		botonExportar = new JButton("Exportar datos");
		botonExportar.setBounds(554, 264, 121, 23);
		botonExportar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {


					if(comboBoxExportarTipo.getSelectedIndex()==0 && comboBoxExportarFiltro.getSelectedIndex()==0) {
						//Aqui iria el codigo de XML por fecha
					}else if(comboBoxExportarTipo.getSelectedIndex()==0 && comboBoxExportarFiltro.getSelectedIndex()==1) {
						//Aqui iría el codigo de XML por cliente

						//Si el combo box marca exportar a CSV y el otro combobox marca por fecha.
					}else if(comboBoxExportarTipo.getSelectedIndex()==1 && comboBoxExportarFiltro.getSelectedIndex()==0) {
						String fechaMin = JOptionPane.showInputDialog("Inserta la fecha de inicio de la busqueda en formato YYYY-MM-DD");
						String fechaMax = JOptionPane.showInputDialog("Inserta la fecha de fin de la busqueda en formato YYYY-MM-DD");
						coordinador_ventas.exportarCSVporFecha(fechaMin, fechaMax, tipoConexion);
						//Si el combobox marca exportar a CSV y el otro comboBox marca por cliente
					}else if(comboBoxExportarTipo.getSelectedIndex()==1 && comboBoxExportarFiltro.getSelectedIndex()==1) {
						String nifCliente;
						nifCliente=JOptionPane.showInputDialog("Inserte el NIF del cliente cuya venta se quiere eliminar");
						coordinador_ventas.exportarCSVporCliente(nifCliente, tipoConexion);

					}
				}catch(Exception np) {
					JOptionPane.showMessageDialog(null, "Operacion cancelada");
				}
			}
		});
		getContentPane().add(botonExportar);
	}
	public void setCoordinadorVentas(Coordinador_Ventas coordinador_ventas) {
		this.coordinador_ventas = coordinador_ventas;
	}
}