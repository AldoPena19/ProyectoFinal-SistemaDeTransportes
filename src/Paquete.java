import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Paquete {
    private static ConexionBD conexionBD = new ConexionBD(); //Instancia a la coneccion
    private static Connection con = conexionBD.conectar();

    private int codigoPaquete;
    private String descripcion;
    private String destinatario;
    private String direccionDestinatario;
    private int codigoDepartamento;
    private int motivoNoEntrega;  
    public Paquete(int codigoPaquete, String descripcion, String destinatario, String direccionDestinatario, int codigoDepartamento, int motivoNoEntrega) {
        this.codigoPaquete = codigoPaquete;
        this.descripcion = descripcion;
        this.destinatario = destinatario;
        this.direccionDestinatario = direccionDestinatario;
        this.codigoDepartamento = codigoDepartamento;
        this.motivoNoEntrega = motivoNoEntrega;
    }

    public Paquete(int codigoPaquete,int motivoNoEntrega) {
        this.codigoPaquete = codigoPaquete;
        this.motivoNoEntrega = motivoNoEntrega;
    }

    public int getCodigoPaquete() { return codigoPaquete; }
    public String getDescripcion() { return descripcion; }
    public String getDestinatario() { return destinatario; }
    public String getDireccionDestinatario() { return direccionDestinatario; }
    public int getCodigoDepartamento() { return codigoDepartamento; }
    public Integer getMotivoNoEntrega() { return motivoNoEntrega; }

    public void setCodigoPaquete(int codigoPaquete) { this.codigoPaquete = codigoPaquete; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public void setDestinatario(String destinatario) { this.destinatario = destinatario; }
    public void setDireccionDestinatario(String direccionDestinatario) { this.direccionDestinatario = direccionDestinatario; }
    public void setCodigoDepartamento(int codigoDepartamento) { this.codigoDepartamento = codigoDepartamento; }
    public void setMotivoNoEntrega(Integer motivoNoEntrega) { this.motivoNoEntrega = motivoNoEntrega; }

    public static void agregarPaquete(Paquete paquete) throws SQLException {

        String sql = "INSERT INTO Paquete (codigo_paquete, Descripcion, Destinatario, direccion_destinatario, codigo_departamento, fecha_creacion) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, paquete.getCodigoPaquete());
        ps.setString(2, paquete.getDescripcion());
        ps.setString(3, paquete.getDestinatario());
        ps.setString(4, paquete.getDireccionDestinatario());
        ps.setInt(5, paquete.getCodigoDepartamento());
        ps.setTimestamp(6,new java.sql.Timestamp(System.currentTimeMillis()));
        ps.executeUpdate();
    }

    public static List<Paquete> obtenerPaquetes() throws SQLException {

        String sql = "SELECT * FROM Paquete";
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);

        List<Paquete> listaPaquetes = new ArrayList<>();
        while (rs.next()) {
            Paquete paquete = new Paquete(
                rs.getInt("codigo_paquete"),
                rs.getString("Descripcion"),
                rs.getString("Destinatario"),
                rs.getString("direccion_destinatario"),
                rs.getInt("codigo_departamento"),
                rs.getObject("MotivoNoEntrega", Integer.class)  
            );
            listaPaquetes.add(paquete);
        }
        con.close();
        return listaPaquetes;
    }

    public static void actualizarPaquete(Paquete paquete) throws SQLException {

        String sql = "UPDATE Paquete SET Descripcion=?, Destinatario=?, direccion_destinatario=?, codigo_departamento=?, MotivoNoEntrega=?, fecha_modificacion=? WHERE codigo_paquete=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, paquete.getDescripcion());
        ps.setString(2, paquete.getDestinatario());
        ps.setString(3, paquete.getDireccionDestinatario());
        ps.setInt(4, paquete.getCodigoDepartamento());
        if (paquete.getMotivoNoEntrega() != null) {
            ps.setInt(5, paquete.getMotivoNoEntrega());
        } else {
            ps.setNull(5, Types.INTEGER);
        }
        ps.setTimestamp(6, new java.sql.Timestamp(System.currentTimeMillis()));
        ps.setInt(7, paquete.getCodigoPaquete());
        ps.executeUpdate();
    }

    public static void actualizarPaqueteCamionero(Paquete paquete) throws SQLException {

        String sql = "UPDATE Paquete SET MotivoNoEntrega=?, fecha_modificacion=? WHERE codigo_paquete=?";
        PreparedStatement ps = con.prepareStatement(sql);
        if (paquete.getMotivoNoEntrega() != null) {
            ps.setInt(1, paquete.getMotivoNoEntrega());
        } else {
            ps.setNull(1, Types.INTEGER);
        }
        ps.setTimestamp(2, new java.sql.Timestamp(System.currentTimeMillis()));
        ps.setInt(3, paquete.getCodigoPaquete());
        ps.executeUpdate();
    }

    public static void eliminarPaquete(int codigoPaquete) throws SQLException {

        String sql = "DELETE FROM Paquete WHERE codigo_paquete=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, codigoPaquete);
        ps.executeUpdate();
    }
}
