package controlador;

import vista.VentanaMenuPrincipal;

public class Main_principal {

	VentanaMenuPrincipal VentanaMenuPrincipal;

	public static void main(String[] args) {
		Main_principal inicio = new Main_principal();
		inicio.iniciar(args);

	}
	//Main iniciador de la aplicacion, que mostrara la unica ventana que tiene SetVisible(true);
	//Las demas ventana se generaran al pùlsar el boton correspondiente
	private void iniciar(String[] args) {
		VentanaMenuPrincipal = new VentanaMenuPrincipal(args);
	}
}
