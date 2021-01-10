package modelo.ventas;

import java.sql.Date;

//Clase para crear objetos ventas
public class Venta_Dto {
    private int idVenta;
    private Date fechaVenta;
    private int idCliente;
    private int idProducto;
    private int cantidad;
    
    public Venta_Dto(){
        this.idVenta = 0;
        this.fechaVenta = null;
        this.idCliente = 0;
        this.idProducto = 0;
        this.cantidad = 0;
    }
    
    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public Date getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(Date fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

}