package sistemacobros.bd;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Gerardo Solórzano
 */
public class ConexionBaseDatosTest {

    public static void main(String[] args) throws SQLException {
        Connection conn = ConexionBaseDatos.getConnection();
        Statement instruccion = conn.createStatement();
        ResultSet rs = instruccion.executeQuery("SELECT * FROM USUARIO");
        System.out.println(rs);
        ConexionBaseDatos.cerrarConexion();
    }
}
