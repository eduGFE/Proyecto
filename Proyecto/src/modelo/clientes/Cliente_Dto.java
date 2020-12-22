package modelo.clientes;
/**
 * Clase que nos permite crear un objeto POJO (Plain Old Java Object).
 * Ser� un objeto que va a estar viajando por todas las capas, 
 * solo contiene atributos encapsulados, los m�todos set y get para cada atributo
 * y el m�todo toString para mostrar todos los atributos del objeto.
 * Es un objeto plano no hace ning�n tipo de operaci�n.
 * El objeto corresponde con la tabla CLIENTES.
 * Cada atributo corresponde con un campo (mapeo). 
 */
/*
 * ID num�rico, clave primaria.
 * NOMBRE varchar(50), no nulo.
 * DIRECCION varchar(50).
 * POBLACION varchar(50).
 * TELEF varchar(20).
 * NIF varchar(10).
 */
/**
 * @author Miguel Herrero L�pez 
 */
public class Cliente_Dto {
	
	//Atributos encapsulados:
	private int idCliente;
	private String nombre;
	private String direccion;
	private String poblacion;
	private int telef;
	private String nif;
	
	/*
	 * Getters: Devuelve los valores de cada atributo.
	 */
	public int getIdCliente() {
		return idCliente;
	}
	public String getNombre() {
		return nombre;
	}
	public String getDireccion() {
		return direccion;
	}
	public String getPoblacion() {
		return poblacion;
	}
	public int getTelef() {
		return telef;
	}
	public String getNif() {
		return nif;
	}
	
	/*
	 * Setters: Fija los valores de cada atributo, menos para el id, 
	 * puesto que este es generado autom�ticamente por el SGBD.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public void setPoblacion(String poblacion) {
		this.poblacion = poblacion;
	}
	public void setTelef(int telef) {
		this.telef = telef;
	}
	public void setNif(String nif) {
		this.nif = nif;
	}
	
	/*
	 * To String: Muestra el valor de todos los atributos de un objeto cliente.
	 */
	public String toString() {
		return "--------------------------------------------------"
				+ "\n ID: " + idCliente 
				+ "\n NOMBRE: " + nombre 
				+ "\n DIRECCI�N: " + direccion 
				+ "\n POBLACI�N: " + poblacion
				+ "\n TEL�FONO: " + poblacion 
				+ "\n NIF: " + nif;
	}
	
}//FIN class Cliente_Dto.
