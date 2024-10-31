import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MotivoNoEntrega {
    private int id_motivo;
    private String descripcion;

    private static ConexionBD conexionBD = new ConexionBD(); //Instancia a la coneccion
    private static Connection con = conexionBD.conectar();

    public MotivoNoEntrega(int id, String descripcion) {
        this.id_motivo = id;
        this.descripcion = descripcion;
    }

    public int getId() {
        return id_motivo;
    }

    public String getDescripcion() {
        return descripcion;
    }


    public static void agregarMotivo(MotivoNoEntrega motivo) throws SQLException {
        String sql = "INSERT INTO Catalogo_Motivo_No_Entrega  (id_motivo,descripcion) VALUES (?, ?)";
        try (
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, motivo.getId());
            pstmt.setString(2, motivo.getDescripcion());
            pstmt.executeUpdate();
        }
    }

    public static List<MotivoNoEntrega> obtenerMotivo() throws SQLException {

        String sql = "SELECT * FROM Catalogo_Motivo_No_Entrega";
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);

        List<MotivoNoEntrega> listamotivos = new ArrayList<>();
        while (rs.next()) {
            MotivoNoEntrega motivo = new MotivoNoEntrega(
                    rs.getInt("id_motivo"),
                    rs.getString("Descripcion")
            );
            listamotivos.add(motivo);
        }
        return listamotivos;
    }

    public static void actualizarMotivo(MotivoNoEntrega motivo) throws SQLException {
        String sql = "UPDATE Catalogo_Motivo_No_Entrega SET descripcion = ? WHERE id_motivo = ?";
        try (
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, motivo.getDescripcion());
            pstmt.setInt(2, motivo.getId());
            pstmt.executeUpdate();
        }
    }

    public static void eliminarMotivo(int id) throws SQLException {
        String sql = "DELETE FROM Catalogo_Motivo_No_Entrega WHERE id_motivo = ?";
        try (
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }


}
