package modelo.clientes;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import controlador.Coordinador_Clientes;

/**
 * Clase enlazada con el Controlador, que comprueba que se cumplen 
 * las restricciones/condiciones necesarias y las premisas exigidas para 
 * una correcta integridad entre los datos de la App y la Base de Datos.
 * 
 * @author Miguel Herrero L�pez (2�DAM)
 */
public class Cliente_Logica {

	private Coordinador_Clientes miCoordinadorClientes;

	//Enlace con Coordinador.
	public void setCoordinadorClientes(Coordinador_Clientes miCoordinadorClientes) {
		this.miCoordinadorClientes = miCoordinadorClientes;
	}
	
	/**      
	 * M�todo que es llamado desde el CONTROLADOR y recibe un objeto del tipo Cliente_Dto. 
	 * Comprueba que:
	 * 	El campo 'Nombre' est� relleno.
	 * 	No existe ning�n cliente en la BD con el mismo NIF (salvo que el campo est� vac�o).
	 *  La longitud de los campos no supera lo estipulado en la BD(seg�n enunciado).
	 * Si se cumplen coondiciones llama al m�todo encargado de insertar un Cliente en la BD (situado en la clase Cliente_DAO) 
	 * y le pasa por par�metro el objeto miClienteDAO.
	 */
	public void validarAltaCliente(Cliente_Dto miClienteDTO, String tipoConex) {
		
		//Guardamos en variables locales los campos de ClienteDTO por claridad de c�digo.
		String nombre = miClienteDTO.getNombre();
		String direccion = miClienteDTO.getDireccion();
		String poblacion = miClienteDTO.getPoblacion();
		String telef = miClienteDTO.getTelef();
		String nif = miClienteDTO.getNif();
		
		//Creamos objeto Cliente_Dao para poder llamar a los m�todos de dicha clase.
		Cliente_Dao miClienteDAO = new Cliente_Dao();
			
		//Comprobamos si el campo 'Nombre' est� vac�o.
		if (nombre.isEmpty()){
			mostrarMensajeError("No se ha completado la inserci�n."
					+ "\nEl campo 'Nombre' no puede estar vac�o !");
			
		//Comprobamos duplicidad de Nif en BD (salvo que el campo est� vac�o).
		}else if (miClienteDAO.buscarClientes(nif,  tipoConex)!=null && !nif.isEmpty()){
			mostrarMensajeError("No se ha completado la inserci�n."
					+ "\nYa existe un Cliente con el Nif '"+nif+"' !");
		//Comprobamos longitudes m�ximas de campos.
		}else if (!comprobarLongCampos(nombre,direccion,poblacion,telef,nif)) {
			mostrarMensajeError("No se ha completado la inserci�n."
					+ "\nRevise la longitud m�xima de los campos:"
					+ "\n Nombre, Direcci�n y Poblaci�n: max=50"
					+ "\n Tel�fono: max=20"
					+ "\n Nif: max=10");	
		}else { 
			//Si se cumplen TODAS las condiciones llamamos al m�todo que inserta cliente.
			miClienteDAO.insertarCliente(miClienteDTO,  tipoConex);
		}
	}
	
	/**      
	 * M�todo que es llamado desde el CONTROLADOR, llama al m�todo encargado 
	 * de consultar todos los clientes de la BD(situado en la clase Cliente_Dao).
	 * Devuelve el resultado del m�todo llamado(un ArrayList de objetos Cliente_Dto 
	 * si existen clientes, �ste tendr� valor null si no hay ning�n cliente).
	 * No vemos necesario realizar niguna validaci�n l�gica para esta b�squeda.
	 */
	public ArrayList<Cliente_Dto> validarConsultarTodosClientes(String tipoConex) {
		
		//Creamos objeto Cliente_Dao para poder llamar a los m�todos de dicha clase.
		Cliente_Dao miClienteDAO = new Cliente_Dao();
		return miClienteDAO.consultarTodosClientes(tipoConex);
	}
	
	/**      
	 * M�todo que es llamado desde el CONTROLADOR, llama al m�todo (situado en la clase Cliente_Dao)
	 * encargado de actualizar los datos de un Cliente de la BD.
	 * Comprobamos que:
	 * 	La longitud de los campos a actualizar no supera lo estipulado en la BD(seg�n enunciado).
	 */
	public void validarModificacionCliente(int idClienteSelect,String newDireccion, 
			String newPoblacion,String newTelefono, String tipoConex) {

		if (comprobarLongCampos("",newDireccion,newPoblacion,newTelefono,"")) {
			//Creamos objeto Cliente_Dao para poder llamar a los m�todos de dicha clase.
			Cliente_Dao miClienteDAO = new Cliente_Dao();
			miClienteDAO.actualizarCliente(idClienteSelect, newDireccion, newPoblacion, newTelefono,  tipoConex);
		}else { 
			mostrarMensajeError("No se ha completado la actualizaci�n de datos."
					+ "\nRevise la longitud m�xima de los campos:"
					+ "\n Direcci�n y Poblaci�n: max=50"
					+ "\n Tel�fono: max=20");	
		}
	}
	
	/**      
	 * M�todo que es llamado desde el CONTROLADOR, llama al m�todo (situado en la clase Cliente_Dao)
	 * encargado de eliminar los datos de los Clientes que coincidan con el nif recibido por par�metro.
	 * Comprobamos que:
	 * 	Exista alg�n Cliente cuyo valor de nif sea igual al recibido por par�metro.
	 */
	public void validarBorradoClientes(String nif, String tipoConex) {
		//Creamos objeto Cliente_Dao para poder llamar a los m�todos de dicha clase.
		Cliente_Dao miClienteDAO = new Cliente_Dao();
		
		if (miClienteDAO.buscarClientes(nif,  tipoConex)!=null) { //Si existe el Cliente...
			//Llamamos al m�todo(clase DAO) que ejecuta el borrado del Cliente.
			miClienteDAO.eliminarCliente(nif,  tipoConex);	
		}else {
			//Si no, avisamos al user que no existe ning�n Cliente con ese Nif.
			mostrarMensajeError("No existe ning�n Cliente con el Nif="+nif+".");
		}
	}

	/**      
	 * M�todo que es llamado desde el CONTROLADOR, llama al m�todo(situado en la clase Cliente_Dao)
	 * encargado de consultar los datos de los clientes de la BD que coincidan 
	 * con el Nif (recibido por par�metro).
	 * Devuelve el resultado del m�todo llamado(un ArrayList de objetos Cliente_Dto 
	 * si existen clientes coincidentes, �ste tendr� valor null si no hay ning�n cliente coincidente.
	 * No vemos necesario realizar niguna validaci�n l�gica para esta b�squeda.
	 */
	public ArrayList<Cliente_Dto> validarConsultaClientes(String nif, String tipoConex) {
		//Creamos objeto Cliente_Dao para poder llamar a los m�todos de dicha clase.
		Cliente_Dao miClienteDAO = new Cliente_Dao();
		return miClienteDAO.buscarClientes(nif,  tipoConex);
	}
	
	////////////////////- M�TODOS PRIVADOS -////////////////////

	/**
	 * M�todo que comprueba la longitud m�xima permitida(en la BD) 
	 * de los campos que se le pasan por par�metro.
	 */	
	private boolean comprobarLongCampos(String nombre, String direccion,String poblacion,String telef, String nif) {
		if (nombre.length()>50 || direccion.length()>50 || poblacion.length()>50 
				|| telef.length()>20 || nif.length()>10){
			return false;
		} else {
			return true;
		}		
	}

	/**
	 * M�todo para mostrar mensajes (p�sado por par�metro)
	 * al user, a trav�s de un cuadro de di�logo.
	 */	
	private void mostrarMensajeError(String mensaje) {
		JOptionPane.showMessageDialog(null,mensaje,"ATENCI�N !!",JOptionPane.ERROR_MESSAGE);
	}

	
	
}//FIN