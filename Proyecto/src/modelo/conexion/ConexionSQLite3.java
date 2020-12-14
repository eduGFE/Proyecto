package modelo.conexion;


import java.sql.*;

/**
 * Clase que permite conectar con la base de datos
 *
 */
public class ConexionSQLite3 {
   static String bd = "";
   static String url = ""+bd;

   Connection conexion = null;

   /** Constructor de DbConnection */
   public ConexionSQLite3() {
      try{
         //obtenemos el driver de para mysql
         Class.forName("org.sqlite.JDBC");
         //obtenemos la conexión
         conexion  = DriverManager.getConnection(url);

         if ( conexion !=null){
            System.out.println("Conección a base de datos "+bd+" OK");
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