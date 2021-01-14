package modelo.productos;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import modelo.conexion.Conexion;

import java.sql.*;

//Clase que contendra las sentancias SQL necesarias para llevar la gestion de los productos
public class Producto_Dao {
	// Inserta objetos en la bbdd
	public void importarabbdd(String tipoConex, JTable table2, int fila) throws Exception {
		insertardatos(tipoConex, leerfichero(table2, fila));

	}

	// Metodo que se usa para leer los datos del fichero y meterlos en un objeto
	// para posteriormente introduce ese objeto en un arraylis
	//////////////////////////////////////////////////// NO
	// TOCAR///////////////////////////////////////////////////////////////////////////////
	public static ArrayList<Producto_Dto> leerfichero(JTable table2, int fila) throws Exception {
		ArrayList<Producto_Dto> Productos = new ArrayList<Producto_Dto>();
		Productos.clear();
		String nombrearchivo;
		Producto_Dto producto = null;
		String descripcion;
		double stockanual;
		double pvp;
		boolean existe =false;
		if (fila != -1) {
			nombrearchivo = table2.getModel().getValueAt(fila, 0).toString();
			File fichero = new File("Productos\\" + nombrearchivo);
			ObjectInputStream dataIS = null;
			try {
				dataIS = new ObjectInputStream(new FileInputStream(fichero));
				while (true) {
					producto = (Producto_Dto) dataIS.readObject();
					descripcion = producto.getDescripcion();
					stockanual = producto.getStockanual();
					pvp = producto.getPvp();
					Productos.add(producto);
					existe=true;
				}
				
			} catch (EOFException eo) {
			} catch (StreamCorruptedException x) {
				x.printStackTrace();
			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(null, "Archivo no encontrado", "Informaci�n",
						JOptionPane.INFORMATION_MESSAGE);
			}
			if(existe==true) {
				dataIS.close(); //
			}
			
		} else {
			JOptionPane.showMessageDialog(null, "Seleccione un archivo que importar", "Informaci�n",
					JOptionPane.INFORMATION_MESSAGE);
		}
		return Productos;
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// Inserta los datos del arraylist en la bbdd
	public static boolean insertardatos(String tipoConex, ArrayList<Producto_Dto> Productos) {
		PreparedStatement Sentencia = null;
		boolean insertado = false;
		Conexion conex = new Conexion(tipoConex);
		try {

			Sentencia = conex.getConexion().prepareStatement("INSERT INTO productos VALUES (?,?,?,?)");
			for (int i = 0; i < Productos.size(); i++) {
				if (Productos.get(i).getDescripcion().length() > 50) {
					JOptionPane.showMessageDialog(null,
							"La descripci�n:" + Productos.get(i).getDescripcion()
									+ "\n excede el limite permitido para ser insertado en la base de datos",
							"Informaci�n", JOptionPane.INFORMATION_MESSAGE);
				} else {
					insertado = true;
					Sentencia.setString(1, null);
					Sentencia.setString(2, Productos.get(i).getDescripcion());
					Sentencia.setInt(3, Productos.get(i).getStockanual());
					Sentencia.setDouble(4, Productos.get(i).getPvp());
					Sentencia.executeUpdate();

				}

			}
			if (insertado == true) {
				JOptionPane.showMessageDialog(null, "Archivo importado con exito", "Informaci�n",
						JOptionPane.INFORMATION_MESSAGE);
			}

			Sentencia.close();
			conex.desconectar();

		} catch (Exception e) {
			e.printStackTrace();
		}

		Productos.clear();
		return insertado;

	}

	// Sirve para consultar todos los productos que hay en la tabla productos
	public DefaultTableModel consultarproductos(String tipoConex, DefaultTableModel model1) throws Exception {
		PreparedStatement Sentencia = null;
		Object[] file = null;
		model1.setRowCount(0);
		Conexion conex = new Conexion(tipoConex);
		try {
			Sentencia = conex.getConexion().prepareStatement("SELECT * from productos");
			ResultSet result = Sentencia.executeQuery();
			while (result.next()) {
				file = new Object[] { result.getInt("Id"), result.getString("descripcion"),
						result.getDouble("stockanual"), result.getDouble("PVP") };
				model1.addRow(file);
			}
			if (model1.getRowCount() == 0) {
				model1.addRow(new Object[] { "No hay productos" });
			}
			Sentencia.close();
			conex.desconectar();
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		return model1;

	}

	// Sirve para eliminar un producto por ID
	public DefaultTableModel eliminarproducto(String tipoConex, int id, DefaultTableModel model1) throws Exception {
		PreparedStatement Sentencia = null;
		boolean existe = false;
		Object[] file = null;
		model1.setRowCount(0);
		Conexion conex = new Conexion(tipoConex);
		try {
			Sentencia = conex.getConexion().prepareStatement("SELECT * from productos where id=?");
			Sentencia.setInt(1, id);
			ResultSet result = Sentencia.executeQuery();
			while (result.next()) {
				existe = true;
			}
			if (existe == true) {
				Sentencia = conex.getConexion().prepareStatement("DELETE from productos where id=?");
				Sentencia.setInt(1, id);
				Sentencia.executeUpdate();
				JOptionPane.showMessageDialog(null, "Producto borrado", "Advertencia", JOptionPane.WARNING_MESSAGE);
				file = new Object[] { result.getInt("Id"), result.getString("descripcion"),
						result.getDouble("stockanual"), result.getDouble("PVP") };
				model1.addRow(file);
			} else {
				JOptionPane.showMessageDialog(null, "Producto no encontrado", "Informaci�n",
						JOptionPane.INFORMATION_MESSAGE);
			}
			Sentencia.close();
			conex.desconectar();
			

		} catch (Exception e) {

		}
		return model1;
	}

//Sirve para consultar un producto por ID
	public DefaultTableModel consultarproductoporid(String tipoConex, int id, DefaultTableModel model1)
			throws Exception {
		PreparedStatement Sentencia = null;
		boolean existe = false;
		Object[] file = null;
		model1.setRowCount(0);
		Conexion conex = new Conexion(tipoConex);
		try {

			Sentencia = conex.getConexion().prepareStatement("SELECT * from productos where id=?");
			Sentencia.setInt(1, id);
			ResultSet result = Sentencia.executeQuery();
			while (result.next()) {
				file = new Object[] { result.getInt("Id"), result.getString("descripcion"),
						result.getDouble("stockanual"), result.getDouble("PVP") };
				model1.addRow(file);
				existe = true;
			}
			if (existe == false) {
				JOptionPane.showMessageDialog(null, "Producto no encontrado", "Informaci�n",
						JOptionPane.INFORMATION_MESSAGE);
			}
			Sentencia.close();
			conex.desconectar();
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		return model1;
	}

}