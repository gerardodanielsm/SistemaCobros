package sistemacobros.bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Gerardo Solórzano
 */
public class ConexionBaseDatos {

    private static Connection connection = null;
    private static final String CONEXION_URL = "jdbc:mysql://localhost/sistema_cobros?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String CONEXION_USUARIO = "root";
    private static final String CONEXION_PASSWORD = "admin";
    private static final String CONEXION_DRIVER = "com.mysql.cj.jdbc.Driver";

    public static Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName(CONEXION_DRIVER);
                connection = DriverManager.getConnection(CONEXION_URL, CONEXION_USUARIO, CONEXION_PASSWORD);
            } catch (ClassNotFoundException | SQLException ex) {
                ex.printStackTrace();
            }
        }
        return connection;
    }

    public static void cerrarConexion() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
