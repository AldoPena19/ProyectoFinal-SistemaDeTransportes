
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;

public class Usuario {
    // Método para validar el usuario y la contraseña
    public static boolean validarUsuario(String usuario, String contrasena) {
        Connection conexion = null;  // Declaración de la conexión
        ConexionBD conexionBD = new ConexionBD(); //Instancia a la coneccion
        MD5Generator md5 = new MD5Generator(); //Instancia a las contraseñas MD5
        boolean validacionExitosa = false;
        PreparedStatement pst = null;
        ResultSet query = null;


        try {
            // Conectar a la base de datos
            conexion = conexionBD.conectar();

            if (conexion == null) {
                throw new SQLException("No se pudo establecer conexion con la base de datos");
            }

            // Crear la consulta SQL
            String sql = "SELECT * FROM usuario WHERE usuario = ? AND password = ? ";
            pst = conexion.prepareStatement(sql);
            pst.setString(1, usuario);
            pst.setString(2, /*md5.getMD5(*/contrasena/*)*/);


            // Ejecutar la consulta
            query = pst.executeQuery();

            // Si hay resultados, significa que el usuario y la contraseña son válidos
            if (query.next()) {

                validacionExitosa = true;

            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            // Cerrar los recursos
            try {
                if (query != null) query.close();
                if (pst != null) pst.close();
                if (conexion != null) conexion.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return validacionExitosa;
    }

    public int obtenerRol(String usuario, String contrasena) {
        Connection conexion = null;  // Declaración de la conexión
        boolean validacionExitosa = false;
        PreparedStatement pst = null;
        ResultSet query = null;
        ConexionBD conexionBD = new ConexionBD();
        int rol = 0;

        try {
            // Conectar a la base de datos
            conexion = conexionBD.conectar();

            if (conexion == null) {
                throw new SQLException("No se pudo establecer conexion con la base de datos");
            }

            // Crear la consulta SQL
            String sql = "SELECT * FROM usuario WHERE usuario = ? AND password = ? ";
            pst = conexion.prepareStatement(sql);
            pst.setString(1, usuario);
            pst.setString(2, contrasena);


            // Ejecutar la consulta
            query = pst.executeQuery();

            // Si hay resultados, significa que el usuario y la contraseña son válidos
            if (query.next()) {
                validacionExitosa = validarUsuario(usuario, contrasena);
                rol = Integer.parseInt(query.getString("rol"));

            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            // Cerrar los recursos
            try {
                if (query != null) query.close();
                if (pst != null) pst.close();
                if (conexion != null) conexion.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return rol;

    }
}



    