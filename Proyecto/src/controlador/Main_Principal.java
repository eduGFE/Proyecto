package controlador;

import javax.swing.JOptionPane;

import modelo.clientes.Cliente_Logica;
import modelo.productos.Producto_Logica;
import vista.VentanaBorrarClientes;
import vista.VentanaConsultarClientes;
import vista.VentanaGestionClientes;
import vista.VentanaGestionProductos;
import vista.VentanaImportarClientes;
import vista.VentanaInsertarClientes;
import vista.VentanaMenuPrincipal;
import vista.VentanaModificarClientes;

public class Main_Principal {
	
	private VentanaMenuPrincipal miVentanaMenuPrincipal;
	//SUBSISTEMA CLIENTES:
	private Coordinador_Clientes miCoordinador_clientes;
	private Cliente_Logica miLogica_Cliente;
	private VentanaGestionClientes miVentanaGestionClientes;
	private VentanaImportarClientes miVentanaImportarClientes;
	private VentanaInsertarClientes miVentanaInsertarClientes;
	private VentanaModificarClientes miVentanaModificarClientes;
	private VentanaBorrarClientes miVentanaBorrarClientes;
	private VentanaConsultarClientes miVentanaConsultarClientes;
	//SUBSISTEMA PRODUCTOS:
	private Coordinador_Productos coordinador_productos;
	private Producto_Logica miLogica_Producto;
	private VentanaGestionProductos miVentanaGestionProductos;
	//SUBSISTEMA CLIENTES:
	//FALTAN LAS DE VICTOR.


	
	public static void main(String[] args) {
		Main_Principal inicio = new Main_Principal();
		inicio.iniciar(args);
	}
	
	//Main iniciador de la aplicacion, que mostrara la unica ventana que tiene SetVisible(true);
	private void iniciar(String[] args) {


		//Control de introducción de argumento con contenido 
		//y argumento dentro del rango permitido(1-2).
		if(args.length!=0 && (args[0].equals("1")||args[0].equals("2"))) {
			
			//Instanciamos clase común a todos los subsistemas.
			miVentanaMenuPrincipal = new VentanaMenuPrincipal(args);

			//Se instancian las clases de cada subsistema.
			instarciarClasesClientes(args);
			instarciarClasesProductos(args);
			
			//Se establecen las relaciones entre clases
			establecerRelacionesClasesClientes();
			establecerRelacionesClasesProductos();
			
			//Se establecen relaciones con la clase coordinador
			establecerRelacionesCoordinadorClientes();
			establecerRelacionesCoordinadorProductos();

			
		}else {		
			JOptionPane.showMessageDialog(null, "No ha insertado un argumento válido."
					+ "\nDebe introducir:"
					+ "\n 1 para Conexion MySQL."
					+ "\n 2 para Conexion SQLite.", "Información", JOptionPane.INFORMATION_MESSAGE);
			System.exit(0); //Cerramos la app.
		}
	}//FIN MAIN.
	
	
	/*
	 * MÉTODOS SUBSISTEMA CLIENTES:
	 */
	private void instarciarClasesClientes(String[] args) {
		String tipoConex = args[0]; //Guardamos argumento que define la conexión en un String. 
		miCoordinador_clientes= new Coordinador_Clientes();
		miLogica_Cliente= new Cliente_Logica();	
		
		miVentanaGestionClientes= new VentanaGestionClientes();
		miVentanaImportarClientes= new VentanaImportarClientes(tipoConex);
		miVentanaInsertarClientes= new VentanaInsertarClientes(tipoConex);
		miVentanaModificarClientes= new VentanaModificarClientes(tipoConex);
		miVentanaBorrarClientes= new VentanaBorrarClientes(tipoConex);
		miVentanaConsultarClientes= new VentanaConsultarClientes(tipoConex);

		
		if(args.length!=1) {
			JOptionPane.showMessageDialog(null, "No ha insertado un argumento valido", "Información",JOptionPane.INFORMATION_MESSAGE);
			System.exit(0);
		}else {
			int argumento = Integer.parseInt(args[0]);
			if(argumento!=1&&argumento!=2) {

				JOptionPane.showMessageDialog(null, "No ha insertado un argumento valido", "Información",JOptionPane.INFORMATION_MESSAGE);

				JOptionPane.showMessageDialog(null, "No ha insertado un argumento válido", "Información",JOptionPane.INFORMATION_MESSAGE);
				System.exit(0);
			}	
		}if(args.length!=0&&(args[0].equals("1")||args[0].equals("2"))) {
			
			
			String tipoConex = args[0]; //Guardamos argumento que define la conexión en un String. 
			VentanaMenuPrincipal = new VentanaMenuPrincipal(tipoConex);
			//SUBSISTEMA CLIENTES:
			miCoordinador_clientes= new Coordinador_Clientes();
			miLogica_Cliente= new Cliente_Logica();	
			miVentanaGestionClientes= new VentanaGestionClientes();
			miVentanaImportarClientes= new VentanaImportarClientes(tipoConex);
			miVentanaInsertarClientes= new VentanaInsertarClientes(tipoConex);
			miVentanaModificarClientes= new VentanaModificarClientes(tipoConex);
			miVentanaBorrarClientes= new VentanaBorrarClientes(tipoConex);
			miVentanaConsultarClientes= new VentanaConsultarClientes(tipoConex);	
			miLogica_Cliente.setCoordinadorClientes(miCoordinador_clientes);
			VentanaMenuPrincipal.setCoordinadorClientes(miCoordinador_clientes);
			miVentanaGestionClientes.setCoordinadorClientes(miCoordinador_clientes);
			miVentanaImportarClientes.setCoordinadorClientes(miCoordinador_clientes);
			miVentanaInsertarClientes.setCoordinadorClientes(miCoordinador_clientes);
			miVentanaModificarClientes.setCoordinadorClientes(miCoordinador_clientes);
			miVentanaBorrarClientes.setCoordinadorClientes(miCoordinador_clientes);
			miVentanaConsultarClientes.setCoordinadorClientes(miCoordinador_clientes);	
			miCoordinador_clientes.setCliente_Logica(miLogica_Cliente);
			miCoordinador_clientes.setVentanaMenuPrincipal(VentanaMenuPrincipal);
			miCoordinador_clientes.setVentanaGestionClientes(miVentanaGestionClientes);
			miCoordinador_clientes.setVentanaImportarClientes(miVentanaImportarClientes);
			miCoordinador_clientes.setVentanaInsertarClientes(miVentanaInsertarClientes);
			miCoordinador_clientes.setVentanaModificarClientes(miVentanaModificarClientes);
			miCoordinador_clientes.setVentanaBorrarClientes(miVentanaBorrarClientes);
			miCoordinador_clientes.setVentanaConsultarClientes(miVentanaConsultarClientes);
			
			
			miCoordinador_productos= new Coordinador_Productos();
			miLogica_Producto= new Producto_Logica();
			miVentanaGestionProductos = new VentanaGestionProductos(tipoConex);

		}			

	}	
	
	private void establecerRelacionesClasesClientes() {
		miLogica_Cliente.setCoordinadorClientes(miCoordinador_clientes);
		miVentanaMenuPrincipal.setCoordinadorClientes(miCoordinador_clientes);
		miVentanaGestionClientes.setCoordinadorClientes(miCoordinador_clientes);
		miVentanaImportarClientes.setCoordinadorClientes(miCoordinador_clientes);
		miVentanaInsertarClientes.setCoordinadorClientes(miCoordinador_clientes);
		miVentanaModificarClientes.setCoordinadorClientes(miCoordinador_clientes);
		miVentanaBorrarClientes.setCoordinadorClientes(miCoordinador_clientes);
		miVentanaConsultarClientes.setCoordinadorClientes(miCoordinador_clientes);
	}
	
	private void establecerRelacionesCoordinadorClientes() {
		miCoordinador_clientes.setCliente_Logica(miLogica_Cliente);
		miCoordinador_clientes.setVentanaMenuPrincipal(miVentanaMenuPrincipal);
		miCoordinador_clientes.setVentanaGestionClientes(miVentanaGestionClientes);
		miCoordinador_clientes.setVentanaImportarClientes(miVentanaImportarClientes);
		miCoordinador_clientes.setVentanaInsertarClientes(miVentanaInsertarClientes);
		miCoordinador_clientes.setVentanaModificarClientes(miVentanaModificarClientes);
		miCoordinador_clientes.setVentanaBorrarClientes(miVentanaBorrarClientes);
		miCoordinador_clientes.setVentanaConsultarClientes(miVentanaConsultarClientes);
	}
	
	/*
	 * MÉTODOS SUBSISTEMA PRODUCTOS:
	 */
	private void instarciarClasesProductos(String[] args) {
		coordinador_productos= new Coordinador_Productos();
		miLogica_Producto= new Producto_Logica();		
		miVentanaGestionProductos= new VentanaGestionProductos(args);
	}
	
	private void establecerRelacionesClasesProductos() {
		miLogica_Producto.setCoordinadorProductos(coordinador_productos);
		miVentanaMenuPrincipal.setCoordinadorProductos(coordinador_productos);
		miVentanaGestionProductos.setCoordinadorProductos(coordinador_productos);
	}
	
	private void establecerRelacionesCoordinadorProductos() {
		coordinador_productos.setProducto_Logica(miLogica_Producto);
		coordinador_productos.setVentanaMenuPrincipal(miVentanaMenuPrincipal);
		coordinador_productos.setVentanaGestionProductos(miVentanaGestionProductos);
	}

	/*
	 * MÉTODOS SUBSISTEMA VENTAS:
	 */
	//FALTAN LOS DE VICTOR.

}

