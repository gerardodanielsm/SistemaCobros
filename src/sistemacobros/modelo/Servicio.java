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
public class Servicio extends ModeloBase {

    private IntegerProperty idServicio;
    private StringProperty servicio;
    private StringProperty abrevia;
    private IntegerProperty usaRango;
    private IntegerProperty bimestral;

    public Servicio(Integer idServicio, String servicio, String abrevia, Integer usaRango, Integer bimestral) {
        this.idServicio = new SimpleIntegerProperty(idServicio);
        this.servicio = new SimpleStringProperty(servicio);
        this.abrevia = new SimpleStringProperty(abrevia);
        this.usaRango = new SimpleIntegerProperty(usaRango);
        this.bimestral = new SimpleIntegerProperty(bimestral);
    }

    public Servicio(Integer idServicio, String servicio) {
        this.idServicio = new SimpleIntegerProperty(idServicio);
        this.servicio = new SimpleStringProperty(servicio);
    }

    public Integer getIdServicio() {
        return idServicio.get();
    }

    public void setIdServicio(Integer idServicio) {
        this.idServicio = new SimpleIntegerProperty(idServicio);
    }

    public String getServicio() {
        return servicio.get();
    }

    public void setServicio(String servicio) {
        this.servicio = new SimpleStringProperty(servicio);
    }

    public String getAbrevia() {
        return abrevia.get();
    }

    public void setAbrevia(String abrevia) {
        this.abrevia = new SimpleStringProperty(abrevia);
    }

    public Integer getUsaRango() {
        return usaRango.get();
    }

    public void setUsaRango(Integer usaRango) {
        this.usaRango = new SimpleIntegerProperty(usaRango);
    }

    public Integer getBimestral() {
        return bimestral.get();
    }

    public void setBimestral(Integer bimestral) {
        this.bimestral = new SimpleIntegerProperty(bimestral);
    }

    public static void getListaServicios(Connection connection, ObservableList<Servicio> lista) {
        try {
            Statement instruccion = connection.createStatement();
            ResultSet resultado = instruccion.executeQuery(
                    "select id_servicio, "
                    + "servicio, "
                    + "abrevia, "
                    + "usa_rango, "
                    + "bimestral "
                    + "from servicio "
                    + "order by id_servicio");
            while (resultado.next()) {
                lista.add(new Servicio(
                        resultado.getInt("id_servicio"),
                        resultado.getString("servicio"),
                        resultado.getString("abrevia"),
                        resultado.getInt("usa_rango"),
                        resultado.getInt("bimestral")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean insertarRegistro(Connection connection) {
        try {
            CallableStatement callableSp = connection.prepareCall("CALL servicio_insertar(?, ?, ?, ?)");
            callableSp.setString(1, servicio.get());
            callableSp.setString(2, abrevia.get());
            callableSp.setInt(3, usaRango.get());
            callableSp.setInt(4, bimestral.get());
            return callableSp.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean actualizarRegistro(Connection connection) {
        try {
            CallableStatement callableSp = connection.prepareCall("CALL servicio_actualizar(?, ?, ?, ?, ?)");
            callableSp.setInt(1, idServicio.get());
            callableSp.setString(2, servicio.get());
            callableSp.setString(3, abrevia.get());
            callableSp.setInt(4, usaRango.get());
            callableSp.setInt(5, bimestral.get());
            return callableSp.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean eliminarRegistro(Connection connection) {
        try {
            CallableStatement callableSp = connection.prepareCall("CALL servicio_eliminar(?)");
            callableSp.setInt(1, idServicio.get());
            callableSp.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String toString() {
        return servicio.get();
    }
}
