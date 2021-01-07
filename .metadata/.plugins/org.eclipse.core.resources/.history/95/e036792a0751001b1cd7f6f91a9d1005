package modelo.conexion;


import java.sql.*;

/**
 * Clase que permite conectar con la base de datos
 *
 */
public class ConexionSQLite3 {
   static String bd = "practica_1.db";
   static String url = "jdbc:sqlite:C:\\Users\\Javie\\OneDrive\\Desktop\\Pen\\BBDD\\SQLite\\"+bd;

   Connection conexion = null;

   /** Constructor de DbConnection */
   public ConexionSQLite3() {
      try{
         //obtenemos el driver de para mysql
         Class.forName("org.sqlite.JDBC");
         //obtenemos la conexión
         conexion  = DriverManager.getConnection(url);

         if ( conexion !=null){
         }
      }
      catch(SQLException e){
         System.out.println(e);
      }catch(ClassNotFoundException e){
         System.out.println(e);
      }catch(Exception e){
         System.out.println(e);
      }
   }
   /**Permite retornar la conexión*/
   public Connection getConnection(){
      return  conexion ;
   }

   public void desconectar(){
	   conexion  = null;
   }

}