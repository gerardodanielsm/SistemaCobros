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
public class Uso extends ModeloBase {

    private IntegerProperty idUso;
    private StringProperty uso;
    private IntegerProperty esDomestico;
    private StringProperty prefijo;
    private StringProperty abrevia;
    private IntegerProperty calculaRec;

    public Uso(Integer idUso, String uso, Integer esDomestico, String prefijo,
            String abrevia, Integer calculaRec) {
        this.idUso = new SimpleIntegerProperty(idUso);
        this.uso = new SimpleStringProperty(uso);
        this.esDomestico = new SimpleIntegerProperty(esDomestico);
        this.prefijo = new SimpleStringProperty(prefijo);
        this.abrevia = new SimpleStringProperty(abrevia);
        this.calculaRec = new SimpleIntegerProperty(calculaRec);
    }

    public Uso(Integer idUso, String uso) {
        this.idUso = new SimpleIntegerProperty(idUso);
        this.uso = new SimpleStringProperty(uso);
    }

    public Integer getIdUso() {
        return idUso.get();
    }

    public void setCveUso(Integer idUso) {
        this.idUso = new SimpleIntegerProperty(idUso);
    }

    public String getUso() {
        return uso.get();
    }

    public void setDescripcion(String uso) {
        this.uso = new SimpleStringProperty(uso);
    }

    public Integer getEsDomestico() {
        return esDomestico.get();
    }

    public void setEsDomestico(Integer esDomestico) {
        this.esDomestico = new SimpleIntegerProperty(esDomestico);
    }

    public String getPrefijo() {
        return prefijo.get();
    }

    public void setPrefijo(String prefijo) {
        this.prefijo = new SimpleStringProperty(prefijo);
    }

    public String getAbrevia() {
        return abrevia.get();
    }

    public void setAbrevia(String abrevia) {
        this.abrevia = new SimpleStringProperty(abrevia);
    }

    public Integer getCalculaRec() {
        return calculaRec.get();
    }

    public void setCalculaRec(Integer calculaRec) {
        this.calculaRec = new SimpleIntegerProperty(calculaRec);
    }

    public static void getListaUsos(Connection connection, ObservableList<Uso> lista) {
        try {
            Statement instruccion = connection.createStatement();
            ResultSet resultado = instruccion.executeQuery(
                    "select id_uso, "
                    + "uso, "
                    + "es_domestico, "
                    + "prefijo, "
                    + "abrevia, "
                    + "calcula_rec "
                    + "from uso "
                    + "order by id_uso");
            while (resultado.next()) {
                lista.add(new Uso(
                        resultado.getInt("id_uso"),
                        resultado.getString("uso"),
                        resultado.getInt("es_domestico"),
                        resultado.getString("prefijo"),
                        resultado.getString("abrevia"),
                        resultado.getInt("calcula_rec")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean insertarRegistro(Connection connection) {
        try {
            CallableStatement callableSp = connection.prepareCall("CALL uso_insertar(?, ?, ?, ?, ?)");
            callableSp.setString(1, uso.get());
            callableSp.setInt(2, esDomestico.get());
            callableSp.setString(3, prefijo.get());
            callableSp.setString(4, abrevia.get());
            callableSp.setInt(5, calculaRec.get());
            return callableSp.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean actualizarRegistro(Connection connection) {
        try {
            CallableStatement callableSp = connection.prepareCall("CALL uso_actualizar(?, ?, ?, ?, ?, ?)");
            callableSp.setInt(1, idUso.get());
            callableSp.setString(2, uso.get());
            callableSp.setInt(3, esDomestico.get());
            callableSp.setString(4, prefijo.get());
            callableSp.setString(5, abrevia.get());
            callableSp.setInt(6, calculaRec.get());
            return callableSp.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean eliminarRegistro(Connection connection) {
        try {
            CallableStatement callableSp = connection.prepareCall("CALL uso_eliminar(?)");
            callableSp.setInt(1, idUso.get());
            callableSp.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String toString() {
        return uso.get();
    }
}
