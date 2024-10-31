import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Camion {
    private String matricula;
    private String modelo;
    private String tipo;
    private double tonelaje;

    private static ConexionBD conexionBD = new ConexionBD(); //Instancia a la coneccion
    private static Connection con = conexionBD.conectar();


    public Camion(String matricula, String modelo, String tipo, double tonelaje) {
        this.matricula = matricula;
        this.modelo = modelo;
        this.tipo = tipo;
        this.tonelaje = tonelaje;
    }





    public String getMatricula() { return matricula; }
    public String getModelo() { return modelo; }
    public String getTipo() { return tipo; }
    public double getTonelaje() { return tonelaje; }

    public void setMatricula(String matricula) { this.matricula = matricula; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public void setTonelaje(double tonelaje) { this.tonelaje = tonelaje; }

    public static void agregarCamion(Camion camion) throws SQLException {

        String sql = "INSERT INTO Camion (Matricula, Modelo, Tipo, Tonelaje,fecha_creacion) VALUES (?, ?, ?, ?,?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, camion.getMatricula());
        ps.setString(2, camion.getModelo());
        ps.setString(3, camion.getTipo());
        ps.setDouble(4, camion.getTonelaje());
        ps.setTimestamp(5, new java.sql.Timestamp(System.currentTimeMillis()));
        ps.executeUpdate();

    }

    public static List<Camion> obtenerCamiones() throws SQLException {

        String sql = "SELECT * FROM Camion where status = 'A'";
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);

        List<Camion> listaCamiones = new ArrayList<>();
        while (rs.next()) {
            Camion camion = new Camion(
                rs.getString("Matricula"),
                rs.getString("Modelo"),
                rs.getString("Tipo"),
                rs.getDouble("Tonelaje")
            );
            listaCamiones.add(camion);
        }
        return listaCamiones;
    }

    public static void actualizarCamion(Camion camion) throws SQLException {

        String sql = "UPDATE Camion SET Modelo=?, Tipo=?, Tonelaje=?, fecha_modificacion=? WHERE Matricula=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, camion.getModelo());
        ps.setString(2, camion.getTipo());
        ps.setDouble(3, camion.getTonelaje());
        ps.setTimestamp(4, new java.sql.Timestamp(System.currentTimeMillis()));
        ps.setString(5, camion.getMatricula());

        ps.executeUpdate();
    }

    public static void eliminarCamion(String matricula) throws SQLException {

        String sql = "UPDATE camion SET status = 'N', fecha_modificacion=? WHERE matricula=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setTimestamp(1,new java.sql.Timestamp(System.currentTimeMillis()));
        ps.setString(2, matricula);
        ps.executeUpdate();
    }

    public static String obtenerEstadoCamiones(String matricula) throws SQLException{

        PreparedStatement pst = null;
        ResultSet query = null;
        String estado = "";

        String sql = "SELECT * FROM camion where matricula = ?";
        pst = con.prepareStatement(sql);
        pst.setString(1, matricula);


        // Ejecutar la consulta
        query = pst.executeQuery();

        // Si hay resultados, significa que el usuario y la contraseña son válidos
        if (query.next()) {
            estado = query.getString("status");

        }
        return estado;
    }


}
