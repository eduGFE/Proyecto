package modelo.productos;

import java.io.Serializable;

//Claser para crear objetos productos
public class Producto_Dto implements Serializable  {
	private int id;
	private String descripcion;
	private int stockanual; 
	private int pvp;
	
	
	public Producto_Dto(String desclipcion, int stockanual, int pvp) {
		this.descripcion = desclipcion;
		this.stockanual = stockanual;
		this.pvp = pvp;
	}
	
	
	
	public Producto_Dto(int id, String desclipcion, int stockanual, int pvp) {
		this.id = id;
		this.descripcion = desclipcion;
		this.stockanual = stockanual;
		this.pvp = pvp;
		
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String desclipcion) {
		this.descripcion = desclipcion;
	}
	public int getStockanual() {
		return stockanual;
	}
	public void setStockanual(int stockanual) {
		this.stockanual = stockanual;
	}
	public int getPvp() {
		return pvp;
	}
	public void setPvp(int pvp) {
		this.pvp = pvp;
	}

}
