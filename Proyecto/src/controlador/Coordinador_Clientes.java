package controlador;

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import modelo.clientes.Cliente_Dto;
import modelo.clientes.Cliente_Logica;
import vista.VentanaBorrarClientes;
import vista.VentanaConsultarClientes;
import vista.VentanaGestionClientes;
import vista.VentanaImportarClientes;
import vista.VentanaInsertarClientes;
import vista.VentanaMenuPrincipal;
import vista.VentanaModificarClientes;
/**   
 * Clase que maneja la visualizaci�n de las clases Ventana(Vista) 
 * y enlaza la VISTA(Ventanas) con el MODELO(L�gica/BD). 
 *  
 * @author Miguel Herrero L�pez (2�DAM) 
 * 
 */ 
public class Coordinador_Clientes {

	private Cliente_Logica miLogicaCliente;
	private VentanaMenuPrincipal miVentanaMenuPrincipal;
	private VentanaGestionClientes miVentanaGestionClientes;
	private VentanaImportarClientes miVentanaImportarClientes;
	private VentanaInsertarClientes miVentanaInsertarClientes;
	private VentanaModificarClientes miVentanaModificarClientes;
	private VentanaBorrarClientes miVentanaBorrarClientes;
	private VentanaConsultarClientes miVentanaConsultarClientes;

////////////////////- GETTERS -////////////////////
	
	public Cliente_Logica getCliente_Logica() {
		return miLogicaCliente;
	}
	public VentanaMenuPrincipal getVentanaMenuPrincipal() {
		return miVentanaMenuPrincipal;
	}
	public VentanaGestionClientes getVentanaGestionClientes() {
		return miVentanaGestionClientes;
	}
	public VentanaImportarClientes getVentanaImportarClientes() {
		return miVentanaImportarClientes;
	}
	public VentanaInsertarClientes getVentanaInsertarClientes() {
		return miVentanaInsertarClientes;
	}
	public VentanaModificarClientes getVentanaModificarClientes() {
		return miVentanaModificarClientes;
	}
	public VentanaBorrarClientes getVentanaBorrarClientes() {
		return miVentanaBorrarClientes;
	}
	public VentanaConsultarClientes getVentanaConsultarClientes() {
		return miVentanaConsultarClientes;
	}
	
////////////////////- SETTERS -////////////////////
	
	public void setCliente_Logica(Cliente_Logica miLogicaCliente) {
		this.miLogicaCliente = miLogicaCliente;
	}
	public void setVentanaMenuPrincipal(VentanaMenuPrincipal miVentanaMenuPrincipal) {
		this.miVentanaMenuPrincipal = miVentanaMenuPrincipal;
	}
	public void setVentanaGestionClientes(VentanaGestionClientes miVentanaGestionClientes) {
		this.miVentanaGestionClientes = miVentanaGestionClientes;
	}
	public void setVentanaImportarClientes(VentanaImportarClientes miVentanaImportarClientes) {
		this.miVentanaImportarClientes = miVentanaImportarClientes;
	}
	public void setVentanaInsertarClientes(VentanaInsertarClientes miVentanaInsertarClientes) {
		this.miVentanaInsertarClientes = miVentanaInsertarClientes;
	}
	public void setVentanaModificarClientes(VentanaModificarClientes miVentanaModificarClientes) {
		this.miVentanaModificarClientes = miVentanaModificarClientes;
	}
	public void setVentanaBorrarClientes(VentanaBorrarClientes miVentanaBorrarClientes) {
		this.miVentanaBorrarClientes = miVentanaBorrarClientes;
	}
	public void setVentanaConsultarClientes(VentanaConsultarClientes miVentanaConsultarClientes) {
		this.miVentanaConsultarClientes = miVentanaConsultarClientes;
	}

	////////////////////- MOSTRAR VENTANAS -////////////////////

	public void mostrarVentanaMenuPrincipal() {
		//Limpiamos ventana. Pasamos por par�metro un array con todos los componentes del panel de la ventana.
		inicializarComponentes(getVentanaMenuPrincipal().getContentPane().getComponents());
		miVentanaMenuPrincipal.setVisible(true);
	}
	public void mostrarVentanaGestionClientes() {
		//Limpiamos ventana. Pasamos por par�metro un array con todos los componentes del panel de la ventana.
		inicializarComponentes(getVentanaGestionClientes().getContentPane().getComponents());
		miVentanaGestionClientes.setVisible(true);
	}
	public void mostrarVentanaImportarClientes() {
		//Limpiamos ventana. Pasamos por par�metro un array con todos los componentes del panel de la ventana.
		inicializarComponentes(getVentanaImportarClientes().getContentPane().getComponents());
		miVentanaImportarClientes.setVisible(true);
	}
	public void mostrarVentanaInsertarClientes() {
		//Limpiamos ventana. Pasamos por par�metro un array con todos los componentes del panel de la ventana.
		inicializarComponentes(getVentanaInsertarClientes().getContentPane().getComponents());
		miVentanaInsertarClientes.setVisible(true);
	}
	public void mostrarVentanaModificarClientes() {
		//Limpiamos ventana. Pasamos por par�metro un array con todos los componentes del panel de la ventana.
		inicializarComponentes(getVentanaModificarClientes().getContentPane().getComponents());
		miVentanaModificarClientes.setVisible(true);
	}
	public void mostrarVentanaBorrarClientes() {
		//Limpiamos ventana. Pasamos por par�metro un array con todos los componentes del panel de la ventana.
		inicializarComponentes(getVentanaBorrarClientes().getContentPane().getComponents());
		miVentanaBorrarClientes.setVisible(true);
	}
	public void mostrarVentanaConsultarClientes() {
		//Limpiamos ventana. Pasamos por par�metro un array con todos los componentes del panel de la ventana.
		inicializarComponentes(getVentanaConsultarClientes().getContentPane().getComponents());
		miVentanaConsultarClientes.setVisible(true);
	}

	////////////////////- OCULTAR VENTANAS -////////////////////

	public void ocultarVentanaMenuPrincipal() {
		miVentanaMenuPrincipal.setVisible(false);	
	}
	public void ocultarVentanaGestionClientes() {
		miVentanaGestionClientes.setVisible(false);	
	}
	public void ocultarVentanaImportarClientes() {
		miVentanaImportarClientes.setVisible(false);
	}
	public void ocultarVentanaInsertarClientes() {
		miVentanaInsertarClientes.setVisible(false);
	}
	public void ocultarVentanaModificarClientes() {
		miVentanaModificarClientes.setVisible(false);
	}
	public void ocultarVentanaBorrarClientes() {
		miVentanaBorrarClientes.setVisible(false);
	}
	public void ocultarVentanaConsultarClientes() {
		miVentanaConsultarClientes.setVisible(false);
	}
	
////////////////////- M�TODOS VALIDACI�N LOGICA -////////////////////
	
	/**      
	 * M�todo que es llamado desde la VISTA y recibe un objeto del tipo Cliente_Dto. 
	 * Dentro llama al m�todo encargado de validar las altas de los Clientes 
	 * (situado en la clase Cliente_Logica) y le pasa por par�metro el objeto miClienteDTO.
	 */
	public void altaCliente(Cliente_Dto miClienteDTO, String tipoConex) {
		miLogicaCliente.validarAltaCliente(miClienteDTO,tipoConex);	
	}

	/**      
	 * M�todo que es llamado desde la VISTA. 
	 * Dentro llama al m�todo encargado de validar la consulta de todos los 
	 * clientes existentes en la BD (situado en la clase Cliente_Logica) 
	 * Devuelve un ArrayList con todos los clientes existentes.
	 */
	public ArrayList<Cliente_Dto> consultarTodosClientes(String tipoConex) {
		return miLogicaCliente.validarConsultarTodosClientes(tipoConex);
	}
	
	/**      
	 * M�todo que es llamado desde la VISTA. 
	 * Dentro llama al m�todo (situado en la clase Cliente_Logica) encargado 
	 * de validar las modificaciones de un registro(Cliente) existente en la BD.
	 * Recibe por par�metro el id del Cliente a actualizar y el valor de los 
	 * tres campos que se actualizar�n. 
	 */
	public void modificarCliente(int idClienteSelect, String newDireccion, String newPoblacion, String newTelefono, String tipoConex) {
		miLogicaCliente.validarModificacionCliente(idClienteSelect, newDireccion, newPoblacion, newTelefono, tipoConex);	
	}
	
	/**      
	 * M�todo que es llamado desde la VISTA. 
	 * Dentro llama al m�todo (situado en la clase Cliente_Logica) encargado 
	 * de validar la eliminaci�n de Clientes existentes en la BD. 
	 * Recibe por par�metro el Nif del Cliente a eliminar.
	 */
	public void borrarClientes(String nif, String tipoConex) {
		miLogicaCliente.validarBorradoClientes(nif, tipoConex);			
	}
	
	/**      
	 * M�todo que es llamado desde la VISTA. 
	 * Dentro llama al m�todo (situado en la clase Cliente_Logica) encargado de validar la 
	 * consulta de Clientes existentes en la BD cuyo Nif sea igual al recibido por par�metro. 
	 * Devuelve un ArrayList con todos los clientes existentes.
	 */
	public ArrayList<Cliente_Dto> consultarClientes(String nif, String tipoConex) {
		return miLogicaCliente.validarConsultaClientes(nif, tipoConex);	
	}
////////////////////- M�TODOS PRIVADOS -////////////////////
/**
* M�todo recursivo que resetea todos los componentes de la ventana.
* Lo utilizamos antes de mostrarla.
* Recibe por par�metros un array con todos los componentes del panel Principal de la ventana.
*/
	private static void inicializarComponentes(Component[] componentes) {
		//Recorremos array con todos los componentes de la ventana:
		for (int i = 0; i < componentes.length; i++) {

			//Cuando encuentre un JPanel, volver� a llamar a la funci�n y 
			//buscar� componentes dentro de ese JPanel. 
			//De esta manera revisar� todos los JPanel que tenga nuestra ventana.
			if (componentes[i] instanceof JPanel) {
				inicializarComponentes(((JPanel) componentes[i]).getComponents());

				//Si encuentra un JTextField borrar� su contenido.
			} else if (componentes[i] instanceof JTextField) {
				((JTextField) componentes[i]).setText("");

				//Si encuentra un JRadioButton lo "desmarcar�" y lo habilitar� para posteriores usos.
			} else if (componentes[i] instanceof JRadioButton) {
				((JRadioButton) componentes[i]).setSelected(false);

				//Si encuentra un JButton lo habilitar� para posteriores usos.
			} else if (componentes[i] instanceof JButton) {
				((JButton) componentes[i]).setEnabled(true);	

			}
		}
	}

}//FIN
