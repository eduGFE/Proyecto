package controlador;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import modelo.productos.Producto_Logica;
import vista.VentanaGestionProductos;
import vista.VentanaMenuPrincipal;

//Clase de servira de union entre las distintas ventanas, ademas sera la encargada de que la informacion introducida en la ventana llegue a la clase logica
//y de hay a la clase dao del objeto correspondiente 
public class Coordinador_Productos {

	private Producto_Logica miProducto_Logica;
	private VentanaMenuPrincipal miVentanaMenuPrincipal;
	private VentanaGestionProductos miVentanaGestionProductos;


	////////////////////- GETTERS -////////////////////

	public Producto_Logica getProducto_Logica() {
		return miProducto_Logica;
	}
	public VentanaMenuPrincipal getVentanaMenuPrincipal() {
		return miVentanaMenuPrincipal;
	}
	public VentanaGestionProductos getVentanaGestionProductos() {
		return miVentanaGestionProductos;
	}

	////////////////////- SETTERS -////////////////////

	public void setProducto_Logica(Producto_Logica miProducto_Logica) {
		this.miProducto_Logica = miProducto_Logica;
	}
	public void setVentanaMenuPrincipal(VentanaMenuPrincipal miVentanaMenuPrincipal) {
		this.miVentanaMenuPrincipal = miVentanaMenuPrincipal;
	}
	public void setVentanaGestionProductos(VentanaGestionProductos miVentanaGestionProductos) {
		this.miVentanaGestionProductos = miVentanaGestionProductos;
	}

	////////////////////- MOSTRAR VENTANAS -////////////////////

	public void mostrarVentanaMenuPrincipal() {
		miVentanaMenuPrincipal.setVisible(true);
	}
	public void mostrarVentanaGestionProductos() {
		miVentanaGestionProductos.setVisible(true);
	}

	////////////////////- OCULTAR VENTANAS -////////////////////

	public void ocultarVentanaMenuPrincipal() {
		miVentanaMenuPrincipal.setVisible(false);	
	}
	public void ocultarVentanaGestionProductos() {
		miVentanaGestionProductos.setVisible(false);	
	}

	////////////////////- MÉTODOS VALIDACIÓN LOGICA -////////////////////

	public void importarabbdd(String tipoConex,JTable table2,int fila) throws Exception {
		miProducto_Logica =new Producto_Logica();
		miProducto_Logica.importarabbdd(tipoConex,table2,fila);
	}
	public void consultarproductos(String tipoConex,DefaultTableModel model1) throws Exception {
		miProducto_Logica =new Producto_Logica();
		miProducto_Logica.consultarproductos(tipoConex,model1);
	}
	public DefaultTableModel eliminarproducto(String tipoConex,int id,DefaultTableModel model1) throws Exception {
		miProducto_Logica =new Producto_Logica();
		miProducto_Logica.eliminarproducto(tipoConex,id,model1);
		return model1;
	}
	public DefaultTableModel consultarproductoporid(String tipoConex,int id,DefaultTableModel model1) throws Exception {
		miProducto_Logica =new Producto_Logica();
		miProducto_Logica.consultarproductoporid(tipoConex,id,model1);
		return model1;
	}




}

