import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Departamento {

    private static ConexionBD conexionBD = new ConexionBD(); //Instancia a la coneccion
    private static Connection con = conexionBD.conectar();


    private int codigo;
    private String nombre;

    public Departamento(int codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public static void agregarDepartamento(Departamento departamento) throws SQLException {
        String sql = "INSERT INTO departamentos (codigo, nombre) VALUES (?, ?)";
        try (
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, departamento.getCodigo());
            pstmt.setString(2, departamento.getNombre());
            pstmt.executeUpdate();
        }
    }

    public static void actualizarDepartamento(Departamento departamento) throws SQLException {
        String sql = "UPDATE departamentos SET nombre = ? WHERE codigo = ?";
        try (
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, departamento.getNombre());
            pstmt.setInt(2, departamento.getCodigo());
            pstmt.executeUpdate();
        }
    }

    public static void eliminarDepartamento(int codigo) throws SQLException {
        String sql = "DELETE FROM departamentos WHERE codigo = ?";
        try (
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, codigo);
            pstmt.executeUpdate();
        }
    }
}
