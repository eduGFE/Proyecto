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
	 *  El formato del nombre sea correcto.
	 *  El formato de Poblaci�n es correcto (siempre y cuando tenga contenido).
	 *  El formato de Tel�fono es correcto (siempre y cuando tenga contenido).
	 *  El formato de NIF es correcto (siempre y cuando tenga contenido).
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
			
		//Comprobamos formato de Nombre.	
		}else if (!esFormatoNombre(nombre)) {
			mostrarMensajeError("No se ha completado la inserci�n."
					+ "\nRevise el formato del NOMBRE:"
					+ "\nS�lo puede contener letras y espacios(despu�s de letras)!");
			
		//Comprobamos formato de Poblaci�n.
		}else if (!esFormatoPoblacion(poblacion)){
			mostrarMensajeError("No se ha completado la inserci�n."
					+ "\nRevise el formato del POBLACI�N:"
					+ "\nS�lo puede contener letras y espacios(despu�s de letras)!"
					+ "\nTambi�n se admite Poblaci�n vac�a.");
			
		//Comprobamos formato de Tel�fono.	
		}else if (!esFormatoTelefono(telef)) {
			mostrarMensajeError("No se ha completado la inserci�n."
					+ "\nRevise el formato del TEL�FONO:"
					+ "\nS�lo puede contener n�meros y si fuese necesario el s�mbolo (+) para indicar el c�digo de pa�s."
					+ "\nTambi�n se admite Tel�fono vac�o.");
			
		//Comprobamos formato de Nif.
		}else if (!comprobarFormatoNif(nif)){	
			mostrarMensajeError("No se ha completado la inserci�n."
					+ "\nRevise el formato del NIF:"
					+ "\n DNI v�lido = 8 D�gitos + Letra."
					+ "\n NIE v�lido = Letra + 7 D�gitos + Letra."
					+ "\n Tambi�n se admite NIF vac�o.");	

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
	 * 	El formato de Poblaci�n es correcto (siempre que tenga contenido).
	 */
	public void validarModificacionCliente(int idClienteSelect,String newDireccion, 
			String newPoblacion,String newTelefono, String tipoConex) {

		if (comprobarLongCampos("",newDireccion,newPoblacion,newTelefono,"")) {
			
			if (esFormatoPoblacion(newPoblacion)) {
				//Creamos objeto Cliente_Dao para poder llamar a los m�todos de dicha clase.
				Cliente_Dao miClienteDAO = new Cliente_Dao();
				miClienteDAO.actualizarCliente(idClienteSelect, newDireccion, newPoblacion, newTelefono,  tipoConex);
				
			}else {
				mostrarMensajeError("No se ha completado la inserci�n."
						+ "\nRevise el formato del POBLACI�N:"
						+ "\nS�lo puede contener letras y espacios(despu�s de letras)!"
						+ "\nTambi�n se admite Poblaci�n vac�a.");
			}		
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
	 *  El formato de NIF es correcto (salvo que el campo est� vac�o).
	 * 	Exista alg�n Cliente cuyo valor de nif sea igual al recibido por par�metro.
	 */
	public void validarBorradoClientes(String nif, String tipoConex) {
		//Creamos objeto Cliente_Dao para poder llamar a los m�todos de dicha clase.
		Cliente_Dao miClienteDAO = new Cliente_Dao();

		//Comprobamos formato de Nif..
		if (comprobarFormatoNif(nif)){

			if (miClienteDAO.buscarClientes(nif,  tipoConex)!=null) { //Si existe el Cliente...
				//Llamamos al m�todo(clase DAO) que ejecuta el borrado del Cliente.
				miClienteDAO.eliminarCliente(nif,  tipoConex);	
			}else {
				//Si no, avisamos al user que no existe ning�n Cliente con ese Nif.
				mostrarMensajeError("No existe ning�n Cliente con el Nif = "+nif+".");
			}
		}else {
			mostrarMensajeError("Revise el formato del NIF:"
					+ "\n DNI v�lido = 8 D�gitos + Letra."
					+ "\n NIE v�lido = Letra + 7 D�gitos + Letra."
					+ "\n Tambi�n se admite NIF vac�o.");	
		}
	}

	/**      
	 * M�todo que es llamado desde el CONTROLADOR, llama al m�todo(situado en la clase Cliente_Dao)
	 * encargado de consultar los datos de los clientes de la BD que coincidan 
	 * con el Nif (recibido por par�metro).
	 * Devuelve el resultado del m�todo llamado(un ArrayList de objetos Cliente_Dto 
	 * si existen clientes coincidentes, �ste tendr� valor null si no hay ning�n cliente coincidente.
	 * O devolver� directamente null si el formato del NIF no es adecuado.
	 * Comprobamos que:
	 *  El formato de NIF es correcto (salvo que el campo est� vac�o).
	 */
	public ArrayList<Cliente_Dto> validarConsultaClientes(String nif, String tipoConex) {

		//Comprobamos formato de Nif.
		if (comprobarFormatoNif(nif)){
			//Creamos objeto Cliente_Dao para poder llamar a los m�todos de dicha clase.
			Cliente_Dao miClienteDAO = new Cliente_Dao();
			return miClienteDAO.buscarClientes(nif,  tipoConex);
			
		}else {
			mostrarMensajeError("Revise el formato del NIF:"
					+ "\n DNI v�lido = 8 D�gitos + Letra."
					+ "\n NIE v�lido = Letra + 7 D�gitos + Letra."
					+ "\n Tambi�n se admite NIF vac�o.");	
		}
	return null;
	}
	
	////////////////////- M�TODOS PRIVADOS -////////////////////
	
	/**
	 * M�todo que comprueba que el String que recibe por par�metro 
	 * tenga un formato con letras (aunque contenga alg�n espacio entremedias).
	 */	
	private boolean esFormatoNombre(String cadena) {
		String patronCadena = "[A-Z|a-z|�|�|�|�|�|�|�|�|�|�|�|�| ]+$";

		if(Pattern.matches(patronCadena, cadena) && cadena.charAt(0) != ' '){
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * M�todo que comprueba que el String que recibe por par�metro 
	 * tenga un formato con letras (aunque contenga alg�n espacio entremedias).
	 * Tambi�n ser�a v�lido si est� vac�o.
	 */	
	private boolean esFormatoPoblacion(String cadena) {
		String patronCadena = "[A-Z|a-z|�|�|�|�|�|�|�|�|�|�|�|�| ]+$";
		 
		if(Pattern.matches(patronCadena, cadena) && cadena.charAt(0) != ' ' || cadena.isEmpty()){
				return true;
		}else {
			return false;
		}
	}
	
	/**
	 * M�todo que comprueba que el String que recibe por par�metro 
	 * tenga un formato de n�mero de tel�fono.
	 * Tambi�n ser�a v�lido si est� vac�o.
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
	 * M�todo que comprueba que el NIF (que recibe por par�metro) 
	 * tenga un formato adecuado.
	 * Tambi�n ser�a v�lido si est� vac�o.
	 */	
	private boolean comprobarFormatoNif(String nif) {
		//DNI v�lido = 8 D�gitos + Letra
		String patronDni = "\\d{8}[A-Z|a-z]{1}";
		//NIE v�lido = Letra + 7 D�gitos + Letra
		String patronNie = "[A-Z|a-z]{1}\\d{7}[A-Z|a-z]{1}";
		//Si el NIF tiene formato DNI o formato NIE...
		if(Pattern.matches(patronDni, nif) || Pattern.matches(patronNie, nif) || nif.isEmpty()){
			return true;
		}else {
			return false;
		}
	}

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