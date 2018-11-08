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
public class Concepto extends ModeloBase {

    private IntegerProperty idConcepto;
    private StringProperty concepto;
    private StringProperty partida;
    private IntegerProperty reciboConcepto;
    private IntegerProperty muestraBoton;
    private IntegerProperty color;
    private IntegerProperty pedirImporte;

    public Concepto(Integer idConcepto, String concepto, String partida, Integer reciboConcepto,
            Integer muestraBoton, Integer color, Integer pedirImporte) {
        this.idConcepto = new SimpleIntegerProperty(idConcepto);
        this.concepto = new SimpleStringProperty(concepto);
        this.partida = new SimpleStringProperty(partida);
        this.reciboConcepto = new SimpleIntegerProperty(reciboConcepto);
        this.muestraBoton = new SimpleIntegerProperty(muestraBoton);
        this.color = new SimpleIntegerProperty(color);
        this.pedirImporte = new SimpleIntegerProperty(pedirImporte);
    }

    public Concepto(Integer idConcepto, String concepto) {
        this.idConcepto = new SimpleIntegerProperty(idConcepto);
        this.concepto = new SimpleStringProperty(concepto);
    }

    public Integer getIdConcepto() {
        return idConcepto.get();
    }

    public void setIdConcepto(Integer idConcepto) {
        this.idConcepto = new SimpleIntegerProperty(idConcepto);
    }

    public String getConcepto() {
        return concepto.get();
    }

    public void setConcepto(String concepto) {
        this.concepto = new SimpleStringProperty(concepto);
    }

    public String getPartida() {
        return partida.get();
    }

    public void setPartida(String partida) {
        this.partida = new SimpleStringProperty(partida);
    }

    public Integer getReciboConcepto() {
        return reciboConcepto.get();
    }

    public void setReciboConcepto(Integer reciboConcepto) {
        this.reciboConcepto = new SimpleIntegerProperty(reciboConcepto);
    }

    public Integer getMuestraBoton() {
        return muestraBoton.get();
    }

    public void setMuestraBoton(Integer muestraBoton) {
        this.muestraBoton = new SimpleIntegerProperty(muestraBoton);
    }

    public Integer getColor() {
        return color.get();
    }

    public void setColor(Integer color) {
        this.color = new SimpleIntegerProperty(color);
    }

    public Integer getPedirImporte() {
        return pedirImporte.get();
    }

    public void setPedirImporte(Integer pedirImporte) {
        this.pedirImporte = new SimpleIntegerProperty(pedirImporte);
    }

    public static void getListaConceptos(Connection connection, ObservableList<Concepto> lista) {
        try {
            Statement instruccion = connection.createStatement();
            ResultSet resultado = instruccion.executeQuery(
                    "select id_concepto, "
                    + "concepto, "
                    + "partida, "
                    + "recibo_concepto, "
                    + "muestra_boton, "
                    + "color, "
                    + "pedir_importe "
                    + "from concepto "
                    + "order by id_concepto");
            while (resultado.next()) {
                lista.add(new Concepto(
                        resultado.getInt("id_concepto"),
                        resultado.getString("concepto"),
                        resultado.getString("partida"),
                        resultado.getInt("recibo_concepto"),
                        resultado.getInt("muestra_boton"),
                        resultado.getInt("color"),
                        resultado.getInt("pedir_importe")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean insertarRegistro(Connection connection) {
        try {
            CallableStatement callableSp = connection.prepareCall("CALL concepto_insertar(?, ?, ?, ?, ?, ?)");
            callableSp.setString(1, concepto.get());
            callableSp.setString(2, partida.get());
            callableSp.setInt(3, reciboConcepto.get());
            callableSp.setInt(4, muestraBoton.get());
            callableSp.setInt(5, color.get());
            callableSp.setInt(6, pedirImporte.get());
            return callableSp.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean actualizarRegistro(Connection connection) {
        try {
            CallableStatement callableSp = connection.prepareCall("CALL concepto_actualizar(?, ?, ?, ?, ?, ?, ?)");
            callableSp.setInt(1, idConcepto.get());
            callableSp.setString(2, concepto.get());
            callableSp.setString(3, partida.get());
            callableSp.setInt(4, reciboConcepto.get());
            callableSp.setInt(5, muestraBoton.get());
            callableSp.setInt(6, color.get());
            callableSp.setInt(7, pedirImporte.get());
            return callableSp.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean eliminarRegistro(Connection connection) {
        try {
            CallableStatement callableSp = connection.prepareCall("CALL concepto_eliminar(?)");
            callableSp.setInt(1, idConcepto.get());
            callableSp.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String toString() {
        return concepto.get();
    }
}
