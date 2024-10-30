import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Camionero {
    private static ConexionBD conexionBD = new ConexionBD(); //Instancia a la coneccion
    private static Connection con = conexionBD.conectar();


    private String dpi;
    private String nombre;
    private String telefono;
    private String direccion;
    private double salario;
    private String zona;

    public Camionero(String dpi, String nombre, String telefono, String direccion, double salario, String zona) {
        this.dpi = dpi;
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
        this.salario = salario;
        this.zona = zona;
    }

    public String getDpi() {
        return dpi;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public double getSalario() {
        return salario;
    }

    public String getZona() {
        return zona;
    }

    public void setDpi(String dpi) {
        this.dpi = dpi;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public static void agregarCamionero(Camionero camionero) throws SQLException {

        String sql = "INSERT INTO Camionero (DPI, Nombre, Telefono, Direccion, Salario, Zona, fecha_creacion) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, camionero.getDpi());
        ps.setString(2, camionero.getNombre());
        ps.setString(3, camionero.getTelefono());
        ps.setString(4, camionero.getDireccion());
        ps.setDouble(5, camionero.getSalario());
        ps.setString(6, camionero.getZona());
        // Establecer la fecha actual en el campo fecha_creacion
        ps.setTimestamp(7, new java.sql.Timestamp(System.currentTimeMillis()));
        ps.executeUpdate();
        con.close();
    }

    public static List<Camionero> obtenerCamioneros() throws SQLException {

        String sql = "SELECT * FROM Camionero";
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);

        List<Camionero> listaCamioneros = new ArrayList<>();
        while (rs.next()) {
            Camionero camionero = new Camionero(
                    rs.getString("DPI"),
                    rs.getString("Nombre"),
                    rs.getString("Telefono"),
                    rs.getString("Direccion"),
                    rs.getDouble("Salario"),
                    rs.getString("Zona")
            );
            listaCamioneros.add(camionero);
        }
        con.close();
        return listaCamioneros;
    }

    public static void actualizarCamionero(Camionero camionero) throws SQLException {

        String sql = "UPDATE Camionero SET Nombre=?, Telefono=?, Direccion=?, Salario=?, Zona=?, fecha_modificacion=? WHERE DPI=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, camionero.getNombre());
        ps.setString(2, camionero.getTelefono());
        ps.setString(3, camionero.getDireccion());
        ps.setDouble(4, camionero.getSalario());
        ps.setString(5, camionero.getZona());
        ps.setTimestamp(6, new java.sql.Timestamp(System.currentTimeMillis()));
        ps.setString(7, camionero.getDpi());
        ps.executeUpdate();
    }

    public static void eliminarCamionero(String dpi) throws SQLException {


        String sql = "DELETE FROM Camionero WHERE DPI=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, dpi);
        ps.executeUpdate();


    }


}
