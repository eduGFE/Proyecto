package modelo.productos;

import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import vista.VentanaGestionProductos;
import vista.VentanaMenuPrincipal;

//Clase que controlara que los datos introducidos son validos para la insercion en la BBDD
public class Producto_Logica {

	
	public void importarabbdd(String[] args,JTable table2,int fila) throws Exception {
		Producto_Dao producto = new Producto_Dao();
		producto.importarabbdd(args,table2,fila);
	}
	
	public void consultarproductos(String[] args,DefaultTableModel model1) throws Exception {
		Producto_Dao producto = new Producto_Dao();
		producto.consultarproductos(args, model1);
	}
	public DefaultTableModel eliminarproducto(String[] args,int id,DefaultTableModel model1) throws Exception {
		Producto_Dao producto = new Producto_Dao();
		producto.eliminarproducto(args,id,model1);
		return model1;
	}
	public DefaultTableModel consultarproductoporid(String[] args,int id,DefaultTableModel model1) throws Exception {
		Producto_Dao producto = new Producto_Dao();
		producto.consultarproductoporid(args,id,model1);
		return model1;
	}
	
}
