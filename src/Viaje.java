import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Viaje {
    private static ConexionBD conexionBD = new ConexionBD(); //Instancia a la coneccion
    private static Connection con = conexionBD.conectar();

    private int idViaje;
    private String camioneroDPI;
    private String camionMatricula;
    private Timestamp fechaSalida;
    private Timestamp fechaRegreso;

    public Viaje(int idViaje, String camioneroDPI, String camionMatricula, Timestamp fechaSalida, Timestamp fechaRegreso) {
        this.idViaje = idViaje;
        this.camioneroDPI = camioneroDPI;
        this.camionMatricula = camionMatricula;
        this.fechaSalida = fechaSalida;
        this.fechaRegreso = fechaRegreso;
    }

    public int getIdViaje() {
        return idViaje;
    }

    public String getCamionero(){
        return camioneroDPI;
    }

    public String getMatricula(){
        return camionMatricula;
    }

    public Timestamp getFechaSalida(){
        return fechaSalida;
    }

    public Timestamp getFechaRegreso() {
        return fechaRegreso;
    }

    public static void agregarViaje(Viaje viaje) throws SQLException {
        String sql = "INSERT INTO viaje (dpi_camionero, matricula_camion, fecha_salida, fecha_regreso) VALUES ( ?, ?, ?, ?)";
        try (
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, viaje.camioneroDPI);
            pstmt.setString(2, viaje.camionMatricula);
            pstmt.setTimestamp(3, new java.sql.Timestamp(System.currentTimeMillis()));
            pstmt.setTimestamp(4, new java.sql.Timestamp(System.currentTimeMillis()));
            pstmt.executeUpdate();
        }
    }

    public static void actualizarViaje(Viaje viaje) throws SQLException {
        String sql = "UPDATE viaje SET dpi_camionero = ?, matricula_camion = ?, fecha_salida = ?, fecha_regreso = ? , fecha_modificacion = ? WHERE id_viaje = ?";
        try (
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, viaje.camioneroDPI);
            pstmt.setString(2, viaje.camionMatricula);
            pstmt.setTimestamp(3, viaje.fechaSalida);
            pstmt.setTimestamp(4, viaje.fechaRegreso);
            pstmt.setTimestamp(5, new java.sql.Timestamp(System.currentTimeMillis()));
            pstmt.setInt(6, viaje.getIdViaje());
            pstmt.executeUpdate();
        }
    }

    public static void eliminarViaje(int idViaje) throws SQLException {
        String sql = "DELETE FROM viaje WHERE id_viaje = ?";
        try (
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, idViaje);
            pstmt.executeUpdate();
        }
    }

    public static List<Viaje> obtenerViaje() throws SQLException {

        String sql = "SELECT * FROM viaje";
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);

        List<Viaje> listaviajes = new ArrayList<>();
        while (rs.next()) {
            Viaje viajes = new Viaje(
                    rs.getInt("id_viaje"),
                    rs.getString("dpi_camionero"),
                    rs.getString("matricula_camion"),
                    rs.getTimestamp("fecha_salida"),
                    rs.getTimestamp("fecha_regreso")
            );

            listaviajes.add(viajes);
        }
        return listaviajes;
    }
}
