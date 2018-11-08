package sistemacobros.modelo;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;

/**
 *
 * @author Gerardo Solórzano
 */
public class Rango extends ModeloBase {

    private IntegerProperty idRango;
    private Uso uso;
    private FloatProperty inicio;
    private FloatProperty fin;
    private FloatProperty importe;
    private FloatProperty impExtra;
    private IntegerProperty anio;

    public Rango(Integer idRango, Uso uso, Float inicio, Float fin, Float importe,
            Float impExtra, Integer anio) {
        this.idRango = new SimpleIntegerProperty(idRango);
        this.uso = uso;
        this.inicio = new SimpleFloatProperty(inicio);
        this.fin = new SimpleFloatProperty(fin);
        this.importe = new SimpleFloatProperty(importe);
        this.impExtra = new SimpleFloatProperty(impExtra);
        this.anio = new SimpleIntegerProperty(anio);
    }

    public Integer getIdRango() {
        return idRango.get();
    }

    public void setIdRango(Integer idRango) {
        this.idRango = new SimpleIntegerProperty(idRango);
    }

    public Uso getUso() {
        return uso;
    }

    public void setUso(Uso uso) {
        this.uso = uso;
    }

    public Float getInicio() {
        return inicio.get();
    }

    public void setInicio(Float inicio) {
        this.inicio = new SimpleFloatProperty(inicio);
    }

    public Float getFin() {
        return fin.get();
    }

    public void setFin(Float fin) {
        this.fin = new SimpleFloatProperty(fin);
    }

    public Float getImporte() {
        return importe.get();
    }

    public void setImporte(Float importe) {
        this.importe = new SimpleFloatProperty(importe);
    }

    public Float getImpExtra() {
        return impExtra.get();
    }

    public void setImpExtra(Float impExtra) {
        this.impExtra = new SimpleFloatProperty(impExtra);
    }

    public Integer getAnio() {
        return anio.get();
    }

    public void setAnio(Integer anio) {
        this.anio = new SimpleIntegerProperty(anio);
    }

    public static void getListaRangos(Connection connection, ObservableList<Rango> lista) {
        try {
            Statement instruccion = connection.createStatement();
            ResultSet resultado = instruccion.executeQuery(
                    "select ran.id_rango, ran.id_uso, us.uso, ran.inicio, ran.fin, ran.importe, ran.imp_extra, ran.anio  from rango ran inner join uso us on ran.id_uso = us.id_uso order by ran.id_rango");
            while (resultado.next()) {
                lista.add(new Rango(
                        resultado.getInt("id_rango"),
                        new Uso(
                                resultado.getInt("id_uso"),
                                resultado.getString("uso")
                        ),
                        resultado.getFloat("inicio"),
                        resultado.getFloat("fin"),
                        resultado.getFloat("importe"),
                        resultado.getFloat("imp_extra"),
                        resultado.getInt("anio")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean insertarRegistro(Connection connection) {
        try {
            CallableStatement callableSp = connection.prepareCall("CALL rango_insertar(?, ?, ?, ?, ?, ?)");
            callableSp.setInt(1, uso.getIdUso());
            callableSp.setFloat(2, inicio.get());
            callableSp.setFloat(3, fin.get());
            callableSp.setFloat(4, importe.get());
            callableSp.setFloat(5, impExtra.get());
            callableSp.setInt(6, anio.get());
            return callableSp.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean actualizarRegistro(Connection connection) {
        try {
            CallableStatement callableSp = connection.prepareCall("CALL rango_actualizar(?, ?, ?, ?, ?, ?, ?)");
            callableSp.setInt(1, idRango.get());
            callableSp.setInt(2, uso.getIdUso());
            callableSp.setFloat(3, inicio.get());
            callableSp.setFloat(4, fin.get());
            callableSp.setFloat(5, importe.get());
            callableSp.setFloat(6, impExtra.get());
            callableSp.setInt(7, anio.get());
            return callableSp.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean eliminarRegistro(Connection connection) {
        try {
            CallableStatement callableSp = connection.prepareCall("CALL rango_eliminar(?)");
            callableSp.setInt(1, idRango.get());
            return callableSp.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
