package modelo.productos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

import controlador.Main_Principal;

//Clase encargada de mostrar los mensajes de productos por consola
public class Producto_Consola {
	static Scanner entrada = new Scanner(System.in);

	public static void main(String[] args) throws Exception {
		ArrayList<Producto_Dto> productos = new ArrayList<Producto_Dto>();
		int opcion;
		do {
			System.out.println("           Elige una opcion");
			System.out.println("Insertar un nuevo producto               1");
			System.out.println("Guardar productos en fichero             2");
			System.out.println("Salir                                    3");
			opcion = entrada.nextInt();
			switch (opcion) {
			case 1:
				productos = CrearObjeto(productos);
				break;
			case 2:
				if(IntroducirObjetoEnFichero(productos)==true) {
					System.out.println("Datos guardados en un fichero");
					productos.clear();
				}
				
				break;
			default:
				break;
			}
		} while (opcion != 3);
		System.out.println("Saliste");

	}

	// Crea objeto producto con la informacion necesario
	public static ArrayList<Producto_Dto> CrearObjeto(ArrayList<Producto_Dto> productos) {
		final AtomicInteger count = new AtomicInteger(0);
		Producto_Dto producto = null;
		int id = count.incrementAndGet();
		String descripcion;
		int stockanual = 0;
		int pvp;

		entrada.nextLine();
		System.out.println("Inserta la descripción del producto");
		descripcion = entrada.nextLine();
		while (esnumero(descripcion) == true) {
			System.out.println("Introduce una descripcion valida");
			descripcion = entrada.nextLine();
		}
		System.out.println("Introduce el stock anual");
		try {
			stockanual = entrada.nextInt();
		}catch(Exception e) {
			System.out.println("Stock anual no valido");
			entrada.nextLine();
			return productos;
		}
		
		entrada.nextLine();
		System.out.println("Introduce el precio del producto");
		try {
			pvp = entrada.nextInt();
		}catch(Exception e) {
			System.out.println("Precio no valido");
			entrada.nextLine();
			return productos;
		}
		producto = new Producto_Dto(id, descripcion, stockanual, pvp);
		productos.add(producto);
		return productos;
	}

	// Guarda dicha información en un fichero
	public static boolean IntroducirObjetoEnFichero(ArrayList<Producto_Dto> Productos_Dto) throws Exception {
		boolean guardados=false;
		Calendar fecha = new GregorianCalendar();
		File Productos = new File("Productos");
		String fecha1;
		int contadorfichero = 1;
		int ano = fecha.get(Calendar.YEAR);
		int mes = fecha.get(Calendar.MONTH);
		int dia = fecha.get(Calendar.DAY_OF_MONTH);
		if (dia <= 9 && mes > 9) {
			fecha1 = "PRODUCTOS0" + dia + mes + ano;
		} else if (dia > 9 && mes <= 9) {
			fecha1 = "PRODUCTOS" + dia + "0" + mes + ano;
		} else if (dia <= 9 && mes <= 9) {
			fecha1 = "PRODUCTOS0" + dia + "0" + mes + ano;
		} else {
			fecha1 = "PRODUCTOS" + dia + mes + ano;
		}
		Productos.mkdir();
		File ruta = new File(Productos, fecha1 + "_0" + contadorfichero + ".bin");
		while (ruta.exists()) {
			if (contadorfichero <= 9) {
				ruta = new File(Productos, fecha1 + "_0" + contadorfichero + ".bin");

			} else {
				ruta = new File(Productos, fecha1 + "_" + contadorfichero + ".bin");
			}
			contadorfichero++;
		}
		ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(ruta));
		if(Productos_Dto == null) {
			System.out.println("No hay productos que guardar");
		}else {
			for (int i = 0; i < Productos_Dto.size(); i++) {
				salida.writeObject(Productos_Dto.get(i));
				guardados=true;
			}
		}
		return guardados;
		
	}

	// Comprueba si un numero es numeros o se a introducido alguna letra u otro
	// caracter
	public static boolean esnumero(String cadena) {

		boolean resultado;

		try {
			Integer.parseInt(cadena);
			resultado = true;
		} catch (NumberFormatException excepcion) {
			resultado = false;
		}

		return resultado;
	}
}
