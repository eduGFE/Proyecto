package modelo.productos;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import controlador.Coordinador_Productos;

//Clase que controlara que los datos introducidos son validos para la insercion en la BBDD
public class Producto_Logica {

<<<<<<< HEAD
	private Coordinador_Productos miCoordinadorProductos;

	//Enlace con Coordinador.
	public void setCoordinadorProductos(Coordinador_Productos miCoordinadorProductos) {
		this.miCoordinadorProductos = miCoordinadorProductos;
	}


	public void importarabbdd(String[] args,JTable table2,int fila) throws Exception {
=======
	
	public void importarabbdd(String tipoConex,JTable table2,int fila) throws Exception {
>>>>>>> feature/RamaEduardo
		Producto_Dao producto = new Producto_Dao();
		producto.importarabbdd(tipoConex,table2,fila);
	}
<<<<<<< HEAD

	public void consultarproductos(String[] args,DefaultTableModel model1) throws Exception {
=======
	
	public void consultarproductos(String tipoConex,DefaultTableModel model1) throws Exception {
>>>>>>> feature/RamaEduardo
		Producto_Dao producto = new Producto_Dao();
		producto.consultarproductos(tipoConex, model1);
	}
	public DefaultTableModel eliminarproducto(String tipoConex,int id,DefaultTableModel model1) throws Exception {
		Producto_Dao producto = new Producto_Dao();
		producto.eliminarproducto(tipoConex,id,model1);
		return model1;
	}
	public DefaultTableModel consultarproductoporid(String tipoConex,int id,DefaultTableModel model1) throws Exception {
		Producto_Dao producto = new Producto_Dao();
		producto.consultarproductoporid(tipoConex,id,model1);
		return model1;
	}
	
}
