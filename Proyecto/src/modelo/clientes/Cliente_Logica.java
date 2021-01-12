package modelo.clientes;

import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import controlador.Coordinador_Clientes;

/**
 * Clase enlazada con el Controlador, que comprueba que se cumplen 
 * las restricciones/condiciones necesarias y las premisas exigidas para 
 * una correcta integridad entre los datos de la App y la Base de Datos.
 * 
 * @author Miguel Herrero López (2ºDAM)
 */
public class Cliente_Logica {

	private Coordinador_Clientes miCoordinadorClientes;

	//Enlace con Coordinador.
	public void setCoordinadorClientes(Coordinador_Clientes miCoordinadorClientes) {
		this.miCoordinadorClientes = miCoordinadorClientes;
	}
	
	/**      
	 * Método que es llamado desde el CONTROLADOR y recibe un objeto del tipo Cliente_Dto. 
	 * Comprueba que:
	 * 	El campo 'Nombre' está relleno.
	 *  El formato del nombre sea correcto.
	 *  El formato de Población es correcto (siempre y cuando tenga contenido).
	 *  El formato de Teléfono es correcto (siempre y cuando tenga contenido).
	 *  El formato de NIF es correcto (siempre y cuando tenga contenido).
	 * 	No existe ningún cliente en la BD con el mismo NIF (salvo que el campo esté vacío).
	 *  La longitud de los campos no supera lo estipulado en la BD(según enunciado).
	 * Si se cumplen coondiciones llama al método encargado de insertar un Cliente en la BD (situado en la clase Cliente_DAO) 
	 * y le pasa por parámetro el objeto miClienteDAO.
	 */
	public void validarAltaCliente(Cliente_Dto miClienteDTO, String tipoConex) {
		
		//Guardamos en variables locales los campos de ClienteDTO por claridad de código.
		String nombre = miClienteDTO.getNombre();
		String direccion = miClienteDTO.getDireccion();
		String poblacion = miClienteDTO.getPoblacion();
		String telef = miClienteDTO.getTelef();
		String nif = miClienteDTO.getNif();

		//Creamos objeto Cliente_Dao para poder llamar a los métodos de dicha clase.
		Cliente_Dao miClienteDAO = new Cliente_Dao();

		//Comprobamos si el campo 'Nombre' está vacío.
		if (nombre.isEmpty()){
			mostrarMensajeError("No se ha completado la inserción."
					+ "\nEl campo 'Nombre' no puede estar vacío !");
			
		//Comprobamos formato de Nombre.	
		}else if (!esFormatoNombre(nombre)) {
			mostrarMensajeError("No se ha completado la inserción."
					+ "\nRevise el formato del NOMBRE:"
					+ "\nSólo puede contener letras y espacios(después de letras)!");
			
		//Comprobamos formato de Población.
		}else if (!esFormatoPoblacion(poblacion)){
			mostrarMensajeError("No se ha completado la inserción."
					+ "\nRevise el formato del POBLACIÓN:"
					+ "\nSólo puede contener letras y espacios(después de letras)!"
					+ "\nTambién se admite Población vacía.");
			
		//Comprobamos formato de Teléfono.	
		}else if (!esFormatoTelefono(telef)) {
			mostrarMensajeError("No se ha completado la inserción."
					+ "\nRevise el formato del TELÉFONO:"
					+ "\nSólo puede contener números y si fuese necesario el símbolo (+) para indicar el código de país."
					+ "\nTambién se admite Teléfono vacío.");
			
		//Comprobamos formato de Nif.
		}else if (!comprobarFormatoNif(nif)){	
			mostrarMensajeError("No se ha completado la inserción."
					+ "\nRevise el formato del NIF:"
					+ "\n DNI válido = 8 Dígitos + Letra."
					+ "\n NIE válido = Letra + 7 Dígitos + Letra."
					+ "\n También se admite NIF vacío.");	

		//Comprobamos duplicidad de Nif en BD (salvo que el campo esté vacío).
		}else if (miClienteDAO.buscarClientes(nif,  tipoConex)!=null && !nif.isEmpty()){
			mostrarMensajeError("No se ha completado la inserción."
					+ "\nYa existe un Cliente con el Nif '"+nif+"' !");
			
		//Comprobamos longitudes máximas de campos.
		}else if (!comprobarLongCampos(nombre,direccion,poblacion,telef,nif)) {
			mostrarMensajeError("No se ha completado la inserción."
					+ "\nRevise la longitud máxima de los campos:"
					+ "\n Nombre, Dirección y Población: max=50"
					+ "\n Teléfono: max=20"
					+ "\n Nif: max=10");	
		}else { 
			//Si se cumplen TODAS las condiciones llamamos al método que inserta cliente.
			miClienteDAO.insertarCliente(miClienteDTO,  tipoConex);
		}
	}

	/**      
	 * Método que es llamado desde el CONTROLADOR, llama al método encargado 
	 * de consultar todos los clientes de la BD(situado en la clase Cliente_Dao).
	 * Devuelve el resultado del método llamado(un ArrayList de objetos Cliente_Dto 
	 * si existen clientes, éste tendrá valor null si no hay ningún cliente).
	 * No vemos necesario realizar niguna validación lógica para esta búsqueda.
	 */
	public ArrayList<Cliente_Dto> validarConsultarTodosClientes(String tipoConex) {
		
		//Creamos objeto Cliente_Dao para poder llamar a los métodos de dicha clase.
		Cliente_Dao miClienteDAO = new Cliente_Dao();
		return miClienteDAO.consultarTodosClientes(tipoConex);
	}
	
	/**      
	 * Método que es llamado desde el CONTROLADOR, llama al método (situado en la clase Cliente_Dao)
	 * encargado de actualizar los datos de un Cliente de la BD.
	 * Comprobamos que:
	 * 	La longitud de los campos a actualizar no supera lo estipulado en la BD(según enunciado).
	 * 	El formato de Población es correcto (siempre que tenga contenido).
	 */
	public void validarModificacionCliente(int idClienteSelect,String newDireccion, 
			String newPoblacion,String newTelefono, String tipoConex) {

		if (comprobarLongCampos("",newDireccion,newPoblacion,newTelefono,"")) {
			
			if (esFormatoPoblacion(newPoblacion)) {
				//Creamos objeto Cliente_Dao para poder llamar a los métodos de dicha clase.
				Cliente_Dao miClienteDAO = new Cliente_Dao();
				miClienteDAO.actualizarCliente(idClienteSelect, newDireccion, newPoblacion, newTelefono,  tipoConex);
				
			}else {
				mostrarMensajeError("No se ha completado la inserción."
						+ "\nRevise el formato del POBLACIÓN:"
						+ "\nSólo puede contener letras y espacios(después de letras)!"
						+ "\nTambién se admite Población vacía.");
			}		
		}else { 
			mostrarMensajeError("No se ha completado la actualización de datos."
					+ "\nRevise la longitud máxima de los campos:"
					+ "\n Dirección y Población: max=50"
					+ "\n Teléfono: max=20");	
		}
	}
	
	/**      
	 * Método que es llamado desde el CONTROLADOR, llama al método (situado en la clase Cliente_Dao)
	 * encargado de eliminar los datos de los Clientes que coincidan con el nif recibido por parámetro.
	 * Comprobamos que:
	 *  El formato de NIF es correcto (salvo que el campo esté vacío).
	 * 	Exista algún Cliente cuyo valor de nif sea igual al recibido por parámetro.
	 */
	public void validarBorradoClientes(String nif, String tipoConex) {
		//Creamos objeto Cliente_Dao para poder llamar a los métodos de dicha clase.
		Cliente_Dao miClienteDAO = new Cliente_Dao();

		//Comprobamos formato de Nif..
		if (comprobarFormatoNif(nif)){

			if (miClienteDAO.buscarClientes(nif,  tipoConex)!=null) { //Si existe el Cliente...
				//Llamamos al método(clase DAO) que ejecuta el borrado del Cliente.
				miClienteDAO.eliminarCliente(nif,  tipoConex);	
			}else {
				//Si no, avisamos al user que no existe ningún Cliente con ese Nif.
				mostrarMensajeError("No existe ningún Cliente con el Nif = "+nif+".");
			}
		}else {
			mostrarMensajeError("Revise el formato del NIF:"
					+ "\n DNI válido = 8 Dígitos + Letra."
					+ "\n NIE válido = Letra + 7 Dígitos + Letra."
					+ "\n También se admite NIF vacío.");	
		}
	}

	/**      
	 * Método que es llamado desde el CONTROLADOR, llama al método(situado en la clase Cliente_Dao)
	 * encargado de consultar los datos de los clientes de la BD que coincidan 
	 * con el Nif (recibido por parámetro).
	 * Devuelve el resultado del método llamado(un ArrayList de objetos Cliente_Dto 
	 * si existen clientes coincidentes, éste tendrá valor null si no hay ningún cliente coincidente.
	 * O devolverá directamente null si el formato del NIF no es adecuado.
	 * Comprobamos que:
	 *  El formato de NIF es correcto (salvo que el campo esté vacío).
	 */
	public ArrayList<Cliente_Dto> validarConsultaClientes(String nif, String tipoConex) {

		//Comprobamos formato de Nif.
		if (comprobarFormatoNif(nif)){
			//Creamos objeto Cliente_Dao para poder llamar a los métodos de dicha clase.
			Cliente_Dao miClienteDAO = new Cliente_Dao();
			return miClienteDAO.buscarClientes(nif,  tipoConex);
			
		}else {
			mostrarMensajeError("Revise el formato del NIF:"
					+ "\n DNI válido = 8 Dígitos + Letra."
					+ "\n NIE válido = Letra + 7 Dígitos + Letra."
					+ "\n También se admite NIF vacío.");	
		}
	return null;
	}
	
	////////////////////- MÉTODOS PRIVADOS -////////////////////
	
	/**
	 * Método que comprueba que el String que recibe por parámetro 
	 * tenga un formato con letras (aunque contenga algún espacio entremedias).
	 */	
	private boolean esFormatoNombre(String cadena) {
		String patronCadena = "[A-Z|a-z|á|é|í|ó|ú|Á|É|Í|Ó|Ú|Ñ|ñ| ]+$";

		if(Pattern.matches(patronCadena, cadena) && cadena.charAt(0) != ' '){
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * Método que comprueba que el String que recibe por parámetro 
	 * tenga un formato con letras (aunque contenga algún espacio entremedias).
	 * También sería válido si está vacío.
	 */	
	private boolean esFormatoPoblacion(String cadena) {
		String patronCadena = "[A-Z|a-z|á|é|í|ó|ú|Á|É|Í|Ó|Ú|Ñ|ñ| ]+$";
		 
		if(Pattern.matches(patronCadena, cadena) && cadena.charAt(0) != ' ' || cadena.isEmpty()){
				return true;
		}else {
			return false;
		}
	}
	
	/**
	 * Método que comprueba que el String que recibe por parámetro 
	 * tenga un formato de número de teléfono.
	 * También sería válido si está vacío.
	 */	
	private static boolean esFormatoTelefono(String cadena) {
		
		String patronCadena1 = "[+]{1}[0-9]+$";
		String patronCadena2 = "[0-9]+$";
		 
		if(Pattern.matches(patronCadena1,cadena) || Pattern.matches(patronCadena2,cadena) || cadena.isEmpty()){
				return true;
		}else {
			return false;
		}
	}
	
	/**
	 * Método que comprueba que el NIF (que recibe por parámetro) 
	 * tenga un formato adecuado.
	 * También sería válido si está vacío.
	 */	
	private boolean comprobarFormatoNif(String nif) {
		//DNI válido = 8 Dígitos + Letra
		String patronDni = "\\d{8}[A-Z|a-z]{1}";
		//NIE válido = Letra + 7 Dígitos + Letra
		String patronNie = "[A-Z|a-z]{1}\\d{7}[A-Z|a-z]{1}";
		//Si el NIF tiene formato DNI o formato NIE...
		if(Pattern.matches(patronDni, nif) || Pattern.matches(patronNie, nif) || nif.isEmpty()){
			return true;
		}else {
			return false;
		}
	}

	/**
	 * Método que comprueba la longitud máxima permitida(en la BD) 
	 * de los campos que se le pasan por parámetro.
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
	 * Método para mostrar mensajes (pásado por parámetro)
	 * al user, a través de un cuadro de diálogo.
	 */	
	private void mostrarMensajeError(String mensaje) {
		JOptionPane.showMessageDialog(null,mensaje,"ATENCIÓN !!",JOptionPane.ERROR_MESSAGE);
	}

	
	
}//FIN