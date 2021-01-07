package controlador;

import javax.swing.JOptionPane;

import modelo.clientes.Cliente_Logica;
import vista.VentanaBorrarClientes;
import vista.VentanaConsultarClientes;
import vista.VentanaGestionClientes;
import vista.VentanaImportarClientes;
import vista.VentanaInsertarClientes;
import vista.VentanaMenuPrincipal;
import vista.VentanaModificarClientes;

public class Main_Principal {
	
	VentanaMenuPrincipal VentanaMenuPrincipal;
	//SUBSISTEMA CLIENTES:
	private Coordinador_Clientes miCoordinador_clientes;
	private Cliente_Logica miLogica_Cliente;
	private VentanaGestionClientes miVentanaGestionClientes;
	private VentanaImportarClientes miVentanaImportarClientes;
	private VentanaInsertarClientes miVentanaInsertarClientes;
	private VentanaModificarClientes miVentanaModificarClientes;
	private VentanaBorrarClientes miVentanaBorrarClientes;
	private VentanaConsultarClientes miVentanaConsultarClientes;
	
	public static void main(String[] args) {
		Main_Principal inicio = new Main_Principal();
		inicio.iniciar(args);
	}
	
	//Main iniciador de la aplicacion, que mostrara la unica ventana que tiene SetVisible(true);
	//Las demas ventana se generaran al pùlsar el boton correspondiente
	private void iniciar(String[] args) {
		
		if(args.length==0) {
			JOptionPane.showMessageDialog(null, "No ha insertado un argumento válido", "Información",
					JOptionPane.INFORMATION_MESSAGE);
			System.exit(0);
		}else {
			int argumento = Integer.parseInt(args[0]);
			if(argumento!=1&&argumento!=2) {
				JOptionPane.showMessageDialog(null, "No ha insertado un argumento válido", "Información",
						JOptionPane.INFORMATION_MESSAGE);
				System.exit(0);
			}	
		}if(args.length!=0&&(args[0].equals("1")||args[0].equals("2"))) {
			VentanaMenuPrincipal = new VentanaMenuPrincipal(args);
			//SUBSISTEMA CLIENTES:
			String tipoConex = args[0]; //Guardamos argumento que define la conexión en un String. 
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
		}			
	}	
}
