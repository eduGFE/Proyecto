package controlador;

import javax.swing.JOptionPane;

import vista.VentanaMenuPrincipal;

public class Main_Principal {

	VentanaMenuPrincipal VentanaMenuPrincipal;

	public static void main(String[] args) {
		Main_Principal inicio = new Main_Principal();
		inicio.iniciar(args);
	}
	//Main iniciador de la aplicacion, que mostrara la unica ventana que tiene SetVisible(true);
	//Las demas ventana se generaran al pùlsar el boton correspondiente
	private void iniciar(String[] args) {
		
		if(args.length==0) {
			JOptionPane.showMessageDialog(null, "No ha insertado un argumento valido", "Información",
					JOptionPane.INFORMATION_MESSAGE);
			System.exit(0);
		}else {
			int argumento = Integer.parseInt(args[0]);
			if(argumento!=1&&argumento!=2) {
				JOptionPane.showMessageDialog(null, "No ha insertado un argumento valido", "Información",
						JOptionPane.INFORMATION_MESSAGE);
				System.exit(0);
			}	
		}if(args.length!=0&&(args[0].equals("1")||args[0].equals("2"))) {
			VentanaMenuPrincipal = new VentanaMenuPrincipal(args);	
		}
		
		
	}
}