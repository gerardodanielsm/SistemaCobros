package sistemacobros.modelo;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

/**
 *
 * @author Gerardo Solórzano
 */
public class Perfil extends ModeloBase {

    private IntegerProperty idPerfil;
    private StringProperty perfil;

    public Perfil(Integer idPerfil, String perfil) {
        this.idPerfil = new SimpleIntegerProperty(idPerfil);
        this.perfil = new SimpleStringProperty(perfil);
    }

    public Integer getIdPerfil() {
        return idPerfil.get();
    }

    public void setIdPerfil(Integer idPerfil) {
        this.idPerfil = new SimpleIntegerProperty(idPerfil);
    }

    public String getPerfil() {
        return perfil.get();
    }

    public void setPerfil(String perfil) {
        this.perfil = new SimpleStringProperty(perfil);
    }

    public static void getListaPerfiles(Connection connection, ObservableList<Perfil> lista) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultado = statement.executeQuery("SELECT id_perfil, perfil from perfil");
            while (resultado.next()) {
                lista.add(new Perfil(
                        resultado.getInt("id_perfil"),
                        resultado.getString("perfil")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return perfil.get();
    }

    @Override
    public boolean insertarRegistro(Connection connection) {
        try {
            CallableStatement callableSp = connection.prepareCall("CALL perfil_insertar(?)");
            callableSp.setString(1, perfil.get());
            return callableSp.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean actualizarRegistro(Connection connection) {
        try {
            CallableStatement callableSp = connection.prepareCall("CALL perfil_actualizar(?, ?)");
            callableSp.setInt(1, idPerfil.get());
            callableSp.setString(2, perfil.get());
            return callableSp.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean eliminarRegistro(Connection connection) {
        try {
            CallableStatement callableSp = connection.prepareCall("CALL perfil_eliminar(?)");
            callableSp.setInt(1, idPerfil.get());
            callableSp.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
