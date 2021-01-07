package modelo.clientes;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

/**
 * Realizar un programa, separado de la aplicaci�n principal, con GUI de consola que sea
 * capaz de leer productos por teclado y generar archivo binario con los productos a�adidos por
 * teclado. El nombre del archivo binario deber� tener el siguiente formato:
 * PRODUCTOSddmmaa_contadorDiario.bin, el contador diario empezar� siendo 01 e
 * increment�ndose en funci�n de los archivos anteriores contenidos en el directorio.
 * @author Miguel Herrero L�pez 
 */

public class Cliente_Consola {
	
	//Atributo global:
	private static Scanner entrada=new Scanner(System.in);
	
	public static void main(String[] args) {
		//Creamos array list de objetos Cliente.
		ArrayList<Cliente_Dto>listaClientes = new ArrayList<Cliente_Dto>();
		char opcionMenu = 'X';
		boolean quiereSalir=false;
		String nombreFichero="";
		
		//Carpeta donde se guardar�n los archivos creados:
		final File directorio = new File("Archivos Clientes para Importar");
		directorio.mkdir();	//Crea la carpeta (en el WorkSpace del proyecto) si no existe.	
			
		do {	
			mostrarMenu();
			
			//Recogemos la opcion del user
			opcionMenu = capturarOpcion();

			switch (opcionMenu) {
			case 'A':
				crearCliente(listaClientes); //Introducimos clientes (RAM).
				break;

			case 'B':
				//Si la lista de clientes tiene contenido...
				if (!listaClientesEsVacia(listaClientes)) {
					mostrarListaClientes(listaClientes); //Mostramos clientes (RAM).
				}
				break;

			case 'C':	
				//Si la lista de clientes tiene contenido...
				if (!listaClientesEsVacia(listaClientes)) {
					nombreFichero = generarNombreFichero(directorio);
					//Creamos y escribimos en fichero los objetos Cliente.
					escribirFicheroClientes(listaClientes, directorio, nombreFichero); 
					listaClientes.clear();//Limpiamos lista de clientes.
				}
				break;
				
			case 'S':
				quiereSalir= true;
				break;
			
			default:
				System.out.println("El caracter introducido no es v�lido como opci�n !!!");		
			}	
		}while (!quiereSalir);
		
		System.out.println("Gracias por utilizar la App !!!");
		entrada.close();
	}

	/**
	 * M�todo que captura y devuelve la opci�n elegida 
	 * por el user (comprobando previamente que no sea un 'Enter')
	 */
	private static char capturarOpcion() {
		char opcionMenu;
		String opcion = entrada.nextLine();
		if (opcion.equals("")) //Si es un 'Enter'...
			opcionMenu = 'X';	
		else 
			//Cogemos el primer caracter de la cadena y lo transformamos a may�sculas.
			opcionMenu = opcion.toUpperCase().charAt(0);
		return opcionMenu;
	}

	/**
	 * M�todo que muestra por consola el men� principal de la App.
	 */
	private static void mostrarMenu() {
		System.out.println("\n--------------- <<  MENU PRINCIPAL  >> ------------------");
		System.out.println("Elija una de las siguientes opciones y pulse enter:");
		System.out.println("    A. Introducir nuevo Cliente.");
		System.out.println("    B. Mostrar lista de clientes creada.");
		System.out.println("    C. Volcar lista de clientes en nuevo archivo.");
		System.out.println("    S. SALIR (sin volcar lista de clientes).");
	}
	
	/**
	 * M�todo que crea un nuevo objeto Cliente y lo a�ade al Array List,
	 * comprueba que el campo nombre tiene contenido.
	 */
	private static void crearCliente(ArrayList<Cliente_Dto> listaClientes) {

		//Pedimos datos del cliente y los guardamos.
		System.out.println("Vamos a introducir un nuevo Cliente:");
		//Nombre (comprobando que no est� vacio).
		String nombre="";
		do {
			System.out.println("Introduce el NOMBRE del cliente:");  
			nombre = entrada.nextLine();
		}while (nombre.isEmpty());
		//Direcci�n
		System.out.println("Introduce la DIRECCI�N del cliente:"); 
		String direccion = entrada.nextLine();
		//Poblaci�n
		System.out.println("Introduce la POBLACI�N del cliente:");
		String poblacion = entrada.nextLine();
		//Tel�fono
		System.out.println("Introduce el TEL�FONO del cliente:");
		String telefono = entrada.nextLine();
		//Nif
		System.out.println("Introduce el NIF del cliente:");
		String nif = entrada.nextLine();
			
		//Creamos objeto auxiliar con los datos del cliente.
		Cliente_Dto clienteAux = new Cliente_Dto(nombre,direccion,poblacion,telefono,nif);
		//A�adimos el objeto cliente creado a la lista de clientes (RAM).
		listaClientes.add(clienteAux);
		System.out.println("Cliente a�adido a la lista correctamente!!");	
	}
	
	/**
	 * M�todo que muestra por consola la lista de clientes actual (RAM).
	 */
	private static void mostrarListaClientes(ArrayList<Cliente_Dto> listaClientes) {
			System.out.println("----------LISTA CLIENTES----------");
			for (int i=0; i<listaClientes.size();i++)
				System.out.println(listaClientes.get(i));		
	}
	
	/**
	 * M�todo que genera y devuelve el nombre del nuevo fichero en formato 'CLIENTESddmmaa_xx.bin'.
	 */
	private static String generarNombreFichero(File directorio) {	
		String fechaActual = generarFechaActual();
		int contadorDiario = 1;
		String contadorDiarioStr = String.format("%02d",contadorDiario); //Formato de 2 d�gitos.
		String nombreFichero = "CLIENTES"+ fechaActual +"_"+contadorDiarioStr+".bin"; //Provisional.
		
		//COMPROBACI�N DE EXISTENCIA DE NOMBRE DE FICHERO IGUAL.
		//Recorremos el directorio guardando cada fichero en objeto ficheroEntrada.
		for (File ficheroEntrada : directorio.listFiles()) {
				//Si ya existe un fichero con el mismo nombre que el generado anteriormente...
		        if ((ficheroEntrada.getName().equals(nombreFichero))) {
		        	contadorDiario++; //Aumentamos el contador diario.
		        	if (contadorDiario<10) { //Si es menor a 10...
		        		//Fijamos formato en 2 d�gitos y guardamos el nombre.
		        		contadorDiarioStr = String.format("%02d",contadorDiario);
		        		nombreFichero = "CLIENTES"+ fechaActual +"_"+ contadorDiarioStr +".bin";
		        	} else { //Si NO es menor a 10 no es necesario fijar formato.
		        		nombreFichero = "CLIENTES"+ fechaActual +"_"+ contadorDiario +".bin";
		        	}	
		        }	        
		    }
		return nombreFichero;
	}
	
	/**
	 * M�todo que genera y devuelve la fecha actual en formato 'ddmmaa'.
	 */
	private static String generarFechaActual() {
		//Generamos fecha actual completa.
		Calendar fechaCompleta = Calendar.getInstance();
		
		//Guardamos el d�a de la fecha actual.
		String dia = Integer.toString(fechaCompleta.get(Calendar.DATE));
		//Si el d�a tiene menos de dos d�gitos, le a�adimos un cero a la izquierda.
		if (dia.length()<2) { 
			dia = String.format("%02d",Integer.valueOf(dia)); 
			}
		
		//Guardamos el mes de la fecha actual (+1 porque van de 0 a 11).
		String mes = Integer.toString(fechaCompleta.get(Calendar.MONTH)+1);
		//Si el mes tiene menos de dos d�gitos, le a�adimos un cero a la izquierda.
		if (mes.length()<2) { 
			mes = String.format("%02d",Integer.valueOf(mes)); 
			}
		
		//Guardamos las dos �ltimas cifras del a�o actual.
		String ano = Integer.toString((fechaCompleta.get(Calendar.YEAR))).substring(2,4);
		
		return dia+mes+ano; //Devolvemos concatenaci�n de fecha (ddmmaa).
	}
	
	/**
	 * M�todo que escribe en un fichero(nombre pasado por par�metro) que est� en el 
	 * directorio pasado por par�metro la lista de objetos Cliente(pasada por par�metro).
	 */
	private static void escribirFicheroClientes(ArrayList<Cliente_Dto> listaClientes, File directorio, String nombreFichero){

		File objFichero = new File(directorio+"\\"+nombreFichero); //Creamos objeto Fichero

		try {
			//Creamos flujo de salida (escritura bytes).
			FileOutputStream fileout = new FileOutputStream(objFichero); 
			
			//Conectamos flujo de bytes al flujo de datos.
			ObjectOutputStream objectOut = new ObjectOutputStream(fileout); 

			//Recorremos listaClientes y vamos a�adiendo cada objeto Cliente al fichero.
			for (Cliente_Dto objCliente : listaClientes) {
				objectOut.writeObject(objCliente);
				System.out.println("GRABADOS DATOS DE CLIENTE "
				+ (listaClientes.indexOf(objCliente)+1)+" en Fichero: "+nombreFichero);
			}

			objectOut.close(); //Cerramos flujo de escritura.		
			
		} catch (IOException e) {
			System.out.println("Error en la escritura del fichero !!!");
		}
	}
	
	/**
	 * M�todo que comprueba si la lista de Clientes est� vac�a.
	 */
	private static boolean listaClientesEsVacia(ArrayList<Cliente_Dto> listaClientes) {
		if (listaClientes.size()==0) {
			System.out.println("Lista de clientes vac�a !!");
			return true;
		}else {
			return false;
		}
	}

}//FIN
