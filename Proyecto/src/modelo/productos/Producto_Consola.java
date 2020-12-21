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
				IntroducirObjetoEnFichero(productos);
				break;
			default:
				break;
			}
		} while (opcion != 3);
		System.out.println("Saliste");
		System.out.println("Productos guardados");

	}

	// Crea objeto producto con la informacion necesario
	public static ArrayList<Producto_Dto> CrearObjeto(ArrayList<Producto_Dto> productos) {
		Producto_Dto producto = null;
		String descripcion;
		int stockanual;
		int pvp;
		entrada.nextLine();
		System.out.println("Inserta la descripción del producto");
		descripcion = entrada.nextLine();
		System.out.println("Introduce el stock anual");
		stockanual = entrada.nextInt();
		System.out.println("Introduce el precio del producto");
		pvp = entrada.nextInt();
		producto = new Producto_Dto(descripcion, stockanual, pvp);
		productos.add(producto);
		return productos;
	}
	
	// Guarda dicha información en un fichero
	public static void IntroducirObjetoEnFichero(ArrayList<Producto_Dto> Productos_Dto) throws Exception {
		Calendar fecha = new GregorianCalendar();
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
		File ruta = new File("C:\\Users\\Javie\\Proyecto_AD\\Proyecto\\Proyecto\\" + fecha1 + "_0" + contadorfichero + ".bin");

		while (ruta.exists()) {
			if (contadorfichero <= 9) {
				ruta = new File("C:\\Users\\Javie\\Proyecto_AD\\Proyecto\\Proyecto\\" + fecha1 + "_0" + contadorfichero + ".bin");
	
			} else {
				ruta = new File("C:\\Users\\Javie\\Proyecto_AD\\Proyecto\\Proyecto\\" + fecha1 + "_" + contadorfichero + ".bin");
			}
			contadorfichero++;
		}
		ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(ruta));
		for (int i = 0; i < Productos_Dto.size(); i++) {
			salida.writeObject(Productos_Dto.get(i));
		}
	}
}
