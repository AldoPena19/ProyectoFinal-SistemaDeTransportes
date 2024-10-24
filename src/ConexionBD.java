import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    // Información de conexión a la base de datos
    private String url = "jdbc:sqlserver://SQL8001.site4now.net:1433;databaseName=db_aae3ed_bdtubus";
    private String usuarioBD = "db_aae3ed_bdtubus_admin";
    private String contrasenaBD = "Programacion2024";
    private Connection conexion;

    // Método para establecer la conexión
    public Connection conectar() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conexion = DriverManager.getConnection(url, usuarioBD, contrasenaBD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return conexion;
    }

}
