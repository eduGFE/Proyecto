package modelo.clientes;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import modelo.conexion.Conexion;
import vista.VentanaInsertarClientes;

/**
 * Clase que define y ejecuta sobre la Base de Datos las sentencias SQL 
 * necesarias para llevar a cabo la gestión de Clientes.
 * 
 * @author Miguel Herrero López (2ºDAM)
 */
public class Cliente_Dao {
	
	/**
	 * Método que busca en la BD un Cliente a través de un Nif(recibido por parámetro).
	 * Devuelve un ArrayList con el/los objetos ClienteDTO obtenidos 
	 * o null en caso de que no haya coincidencias.
	 */
	public ArrayList<Cliente_Dto> buscarClientes(String nif, String tipoConex) {
		//ArrayList que contendrá objetos Cliente_Dto.
		ArrayList<Cliente_Dto> listaClientesDTO = new ArrayList<Cliente_Dto>();
		boolean existe=false;
		 
		//Creamos conexion con BD
		Conexion conex= new Conexion(tipoConex);
	
		try {
			//Creamos sentencia a ejecutar.
			PreparedStatement sentencia = conex.getConexion().prepareStatement
					("SELECT * FROM clientes where nif = ? ");
			sentencia.setString(1, nif);
			//Ejecutamos la sentencia y recogemos el resultado.
			ResultSet res = sentencia.executeQuery();
			//Recorremos el resultado obtenido, guardándolo en un objeto tipo Cliente_Dto.
			while(res.next()){
				existe=true; //Si entra en el while, es que existen registros coincidentes.
				//Creamos objeto auxiliar para posteriormente guardar cada registro obtenido.
				Cliente_Dto miClienteDTOAux= new Cliente_Dto();
				//Actualizamos el objeto auxiliar con cada registro obtenido.
				miClienteDTOAux.setIdCliente(res.getInt("id"));
				miClienteDTOAux.setNombre(res.getString("nombre"));
				miClienteDTOAux.setDireccion(res.getString("direccion"));
				miClienteDTOAux.setPoblacion(res.getString("poblacion"));
				miClienteDTOAux.setTelef(res.getString("telef"));
				miClienteDTOAux.setNif(res.getString("nif"));
				//Añadimos cada objeto a la lista de Clientes.
				listaClientesDTO.add(miClienteDTOAux);
			}	
			sentencia.close(); //Cerramos sentencia y desconectamos de BD.
			conex.desconectar();

		} catch (SQLException e) {
			mostrarMensajeError("Error! No se pudieron obtener datos !");
		}  catch (Exception e) {
			mostrarMensajeError("Error! No se pudieron obtener datos !");
		}

		if (existe) { //Si existe algún cliente...		
			return listaClientesDTO; //Devolvemos la lista de Clientes_Dto.	
		} else { //Si no existe...		
			return null; //Devolvemos null.				
		}		
	}

	/**
	 * Método que inserta un Cliente(recibido por parámetro) en la BD.
	 */
	public void insertarCliente(Cliente_Dto miClienteDTO, String tipoConex) {
		//Creamos conexion con BD
		Conexion conex= new Conexion(tipoConex);

		try {
			//Creamos sentencia a ejecutar.
			PreparedStatement sentencia = conex.getConexion().prepareStatement
					("INSERT INTO clientes VALUES (?,?,?,?,?,?)");
			sentencia.setString(1, null);
			sentencia.setString(2, miClienteDTO.getNombre());
			sentencia.setString(3, miClienteDTO.getDireccion());
			sentencia.setString(4, miClienteDTO.getPoblacion());
			sentencia.setString(5, miClienteDTO.getTelef());
			sentencia.setString(6, miClienteDTO.getNif());
			
			sentencia.executeUpdate(); //Ejecutamos la sentencia.	
			mostrarMensajeExito("Cliente '"+miClienteDTO.getNombre()+"' insertado con éxito en la BD");
			
			//Limpiamos campos de VentanaInsertarClientes si el cliente se ha insertado con éxito.
			VentanaInsertarClientes.limpiarCampos();
			
			sentencia.close(); //Cerramos sentencia y desconectamos de BD.
			conex.desconectar();
			
			//Capturamos excepciones.		
		} catch (SQLException e) {
			mostrarMensajeError("Error! No se pudo realizar la inserción !");
		} catch (Exception e) {
			mostrarMensajeError("Error! No se pudo realizar la inserción !");
		}
	}

	/**
	 * Método que consulta todos los registros de Clientes en la BD.
	 * Devuelve un ArrayList con los registros obtenidos 
	 * o null en caso de que no existan clientes.
	 */
	public ArrayList<Cliente_Dto> consultarTodosClientes(String tipoConex) {
		//Creamos conexion con BD
		Conexion conex= new Conexion(tipoConex);
		//ArrayList que contendrá objetos Cliente_Dto.
		ArrayList<Cliente_Dto> listaClientesDTO = new ArrayList<Cliente_Dto>(); 
		boolean existen=false;

		try { //Creamos sentencia a ejecutar.
			PreparedStatement sentencia = conex.getConexion().prepareStatement
					("SELECT * FROM clientes");
			//Ejecutamos la sentencia y recogemos el resultado.
			ResultSet res = sentencia.executeQuery();

			//Recorremos el resultado obtenido, guardándolo en un objeto tipo Cliente_Dto.
			while(res.next()){
				existen=true; //Si entra en el while, es que existen registros de Clientes.
				//Creamos objeto auxiliar para posteriormente guardar cada registro obtenido.
				Cliente_Dto miClienteDTOAux= new Cliente_Dto();
				//Actualizamos el objeto auxiliar con cada registro obtenido.
				miClienteDTOAux.setIdCliente(res.getInt("id"));
				miClienteDTOAux.setNombre(res.getString("nombre"));
				miClienteDTOAux.setDireccion(res.getString("direccion"));
				miClienteDTOAux.setPoblacion(res.getString("poblacion"));
				miClienteDTOAux.setTelef(res.getString("telef"));
				miClienteDTOAux.setNif(res.getString("nif"));
				//Añadimos cada objeto a la lista de Clientes.
				listaClientesDTO.add(miClienteDTOAux);
			}	
			sentencia.close(); //Cerramos sentencia y desconectamos de BD.
			conex.desconectar();
			
		} catch (SQLException e) {
			mostrarMensajeError("Error! No se pudieron obtener datos !");
		} catch (Exception e) {
			mostrarMensajeError("Error! No se pudieron obtener datos !");
		}		
		
		if (existen) { //Si existe algún cliente...		
			return listaClientesDTO; //Devolvemos la lista de Clientes_Dto.	
		} else { //Si no existe...		
			return null; //Devolvemos null.				
		}		
	}
	
	/**
	 * Método que actualiza los datos(Direccion, Población y Teléfono recibidos por parámetro)
	 * de un Cliente(con Id recibido por parámetro) en la BD.
	 */
	public void actualizarCliente(int idClienteSelect, String newDireccion, String newPoblacion, String newTelefono, String tipoConex) {
		//Creamos conexion con BD
		Conexion conex= new Conexion(tipoConex);

		try {
			//Creamos sentencia a ejecutar.
			PreparedStatement sentencia = conex.getConexion().prepareStatement
					("UPDATE clientes SET direccion=?, poblacion=?, telef=? WHERE id=?");
			sentencia.setString(1, newDireccion);
			sentencia.setString(2, newPoblacion);
			sentencia.setString(3, newTelefono);
			sentencia.setInt(4, idClienteSelect);

			//Ejecutamos la sentencia.
			sentencia.executeUpdate();		
			mostrarMensajeExito("Cliente con ID='"+idClienteSelect+"' actualizado con éxito en la BD");

			//Cerramos sentencia y desconectamos de BD.
			sentencia.close();
			conex.desconectar();

			//Capturamos excepciones.		
		} catch (SQLException e) {
			mostrarMensajeError("Error! No se pudo realizar la actualización !");
		} catch (Exception e) {
			mostrarMensajeError("Error! No se pudo realizar la actualización !");
		}		
	}

	/**
	 * Método que elimina los registros de Clientes en la BD, cuyo 
	 * Nif coincida con el recibido por parámetro.
	 */
	public void eliminarCliente(String nif, String tipoConex) {
		//Creamos conexion con BD
		Conexion conex= new Conexion(tipoConex);

		try {
			//Creamos sentencia a ejecutar.
			PreparedStatement sentencia = conex.getConexion().prepareStatement
					("DELETE FROM clientes WHERE nif=?");
			sentencia.setString(1, nif);

			sentencia.executeUpdate(); //Ejecutamos la sentencia.	
			mostrarMensajeExito("Cliente con Nif="+nif+" borrado con éxito de la BD.");

			sentencia.close(); //Cerramos sentencia y desconectamos de BD.
			conex.desconectar();

			//Capturamos excepciones.		
		} catch (SQLException e) {
			mostrarMensajeError("Error! No se pudo realizar la eliminación !");
			e.printStackTrace();
		} catch (Exception e) {
			mostrarMensajeError("Error! No se pudo realizar la eliminación !");
		}		
	}
	
////////////////////- MÉTODOS PRIVADOS -////////////////////

/**
 * Métodos para mostrar mensajes (pásados por parámetro)
 * al user, a través de un cuadro de diálogo.
 */	
private void mostrarMensajeError(String mensaje) {
	JOptionPane.showMessageDialog(null,mensaje,"ATENCIÓN !!",JOptionPane.ERROR_MESSAGE);
}
	private void mostrarMensajeExito(String mensaje) {
		JOptionPane.showMessageDialog(null,mensaje,"BIEN HECHO !!",JOptionPane.INFORMATION_MESSAGE);
	}

}//FIN
