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
import java.util.regex.Pattern;

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
				if (IntroducirObjetoEnFichero(productos) == true) {
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
		String descripcion = "";
		String stockanual = null;
		String pvp = null;
		entrada.nextLine();
		System.out.println("Inserta la descripción del producto");
		descripcion = entrada.nextLine();

		while (!esFormatodescripcionn(descripcion) || descripcion.equals("")) {
			System.out.println("Inserta una descripción valida");
			descripcion = entrada.nextLine();
		}
		System.out.println("Introduce el stock anual");
		stockanual = entrada.nextLine();
		while (!esFormatostock(stockanual)) {
			System.out.println("Inserta un stock valido");
			stockanual = entrada.nextLine();
		}
		System.out.println("Introduce el precio del producto");
		pvp = entrada.nextLine();

		while (!esFormatopvp(pvp)) {
			System.out.println("Inserta un pvp valido");
			pvp = entrada.nextLine();
			
		}

		if (stockanual.equals("") && !pvp.equals("")) {
			producto = new Producto_Dto(id, descripcion, 0, Double.parseDouble(pvp));
		} else if (pvp.equals("") && !stockanual.equals("")) {
			producto = new Producto_Dto(id, descripcion, Integer.parseInt(stockanual), 0);
		} else if (stockanual.equals("") && pvp.equals("")) {
			producto = new Producto_Dto(id, descripcion, 0, 0);
		} else {
			producto = new Producto_Dto(id, descripcion, Integer.parseInt(stockanual), Double.parseDouble(pvp));
		}
		productos.add(producto);
		return productos;
	}

	// Guarda dicha información en un fichero
	public static boolean IntroducirObjetoEnFichero(ArrayList<Producto_Dto> Productos_Dto) throws Exception {
		boolean guardados = false;
		Calendar fecha = new GregorianCalendar();
		File Productos = new File("Archivos Productos para Importar");
		String fecha1;
		int contadorfichero = 1;
		String ano = Integer.toString((fecha.get(Calendar.YEAR))).substring(2, 4);
		int mes = fecha.get(Calendar.MONTH) + 1;
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
		if (Productos_Dto.size() == 0) {
			System.out.println("Se ha creado un fichero sin datos");
		} else {
			for (int i = 0; i < Productos_Dto.size(); i++) {
				salida.writeObject(Productos_Dto.get(i));
				guardados = true;
			}
		}
		return guardados;

	}

	private static boolean esFormatodescripcionn(String cadena) {
		String patronCadena = "[A-Z|a-z|á|é|í|ó|ú|Á|É|Í|Ó|Ú|Ñ|ñ| ]+$";

		if (Pattern.matches(patronCadena, cadena) && cadena.charAt(0) != ' ' || cadena.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	private static boolean esFormatostock(String cadena) {
		String patronCadena2 = "[0-9]+$";

		if (Pattern.matches(patronCadena2, cadena) || cadena.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	private static boolean esFormatopvp(String cadena) {
		String patronCadena2 = "[0|1|2|3|4|5|6|7|8|9|.]+$";

		if (Pattern.matches(patronCadena2, cadena) && cadena.charAt(0) != '.' || cadena.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

}