package sistemacobros.modelo;

import java.sql.Connection;

/**
 *
 * @author Gerardo Solórzano
 */
public abstract class ModeloBase {
    
    public abstract boolean insertarRegistro(Connection connection);
    
    public abstract boolean actualizarRegistro(Connection connection);
    
    public abstract boolean eliminarRegistro(Connection connection);
}
