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
public class Tarifa extends ModeloBase {

    private IntegerProperty idTarifa;
    private Uso uso;
    private Servicio servicio;
    private Concepto concepto;
    private IntegerProperty cveDia;
    private IntegerProperty anio;
    private FloatProperty importeBim;
    private FloatProperty importeAnual;
    private IntegerProperty aplicaIva;

    public Tarifa(Integer idTarifa, Uso uso, Servicio servicio, Concepto concepto, Integer cveDia,
            Integer anio, Float importeBim, Float importeAnual, Integer aplicaIva) {
        this.idTarifa = new SimpleIntegerProperty(idTarifa);
        this.uso = uso;
        this.servicio = servicio;
        this.concepto = concepto;
        this.cveDia = new SimpleIntegerProperty(cveDia);
        this.anio = new SimpleIntegerProperty(anio);
        this.importeBim = new SimpleFloatProperty(importeBim);
        this.importeAnual = new SimpleFloatProperty(importeAnual);
        this.aplicaIva = new SimpleIntegerProperty(aplicaIva);
    }

    public Integer getIdTarifa() {
        return idTarifa.get();
    }

    public void setIdTarifa(Integer idTarifa) {
        this.idTarifa = new SimpleIntegerProperty(idTarifa);
    }

    public Uso getUso() {
        return uso;
    }

    public void setUso(Uso uso) {
        this.uso = uso;
    }

    public Servicio getServicio() {
        return servicio;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }

    public Concepto getConcepto() {
        return concepto;
    }

    public void setConcepto(Concepto concepto) {
        this.concepto = concepto;
    }

    public Integer getCveDia() {
        return cveDia.get();
    }

    public void setCveDia(Integer cveDia) {
        this.cveDia = new SimpleIntegerProperty(cveDia);
    }

    public Integer getAnio() {
        return anio.get();
    }

    public void setAnio(Integer anio) {
        this.anio = new SimpleIntegerProperty(anio);
    }

    public Float getImporteBim() {
        return importeBim.get();
    }

    public void setImporteBim(Float importeBim) {
        this.importeBim = new SimpleFloatProperty(importeBim);
    }

    public Float getImporteAnual() {
        return importeAnual.get();
    }

    public void setImporteAnual(Float importeAnual) {
        this.importeAnual = new SimpleFloatProperty(importeAnual);
    }

    public Integer getAplicaIva() {
        return aplicaIva.get();
    }

    public void setAplicaIva(Integer aplicaIva) {
        this.aplicaIva = new SimpleIntegerProperty(aplicaIva);
    }

    public static void getListaTarifas(Connection connection, ObservableList<Tarifa> lista) {
        try {
            Statement instruccion = connection.createStatement();
            ResultSet resultado = instruccion.executeQuery(
                    "select tar.id_tarifa, "
                    + "tar.id_uso, "
                    + "us.uso, "
                    + "tar.id_servicio, "
                    + "serv.servicio, "
                    + "tar.id_concepto, "
                    + "concep.concepto, "
                    + "tar.cve_dia, "
                    + "tar.anio, "
                    + "tar.importe_bim, "
                    + "tar.importe_anual, "
                    + "tar.aplica_iva "
                    + "from tarifa tar "
                    + "inner join uso us "
                    + "on tar.id_uso = us.id_uso "
                    + "inner join servicio serv "
                    + "on tar.id_servicio = serv.id_servicio "
                    + "inner join concepto concep "
                    + "on tar.id_concepto = concep.id_concepto "
                    + "order by tar.id_tarifa");
            while (resultado.next()) {
                lista.add(new Tarifa(
                        resultado.getInt("id_tarifa"),
                        new Uso(
                                resultado.getInt("id_uso"),
                                resultado.getString("uso")
                        ),
                        new Servicio(
                                resultado.getInt("id_servicio"),
                                resultado.getString("servicio")
                        ),
                        new Concepto(
                                resultado.getInt("id_concepto"),
                                resultado.getString("concepto")
                        ),
                        resultado.getInt("cve_dia"),
                        resultado.getInt("anio"),
                        resultado.getFloat("importe_bim"),
                        resultado.getFloat("importe_anual"),
                        resultado.getInt("aplica_iva")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean insertarRegistro(Connection connection) {
        try {
            CallableStatement callableSp = connection.prepareCall("CALL tarifa_insertar(?, ?, ?, ?, ?, ?, ?, ?)");
            callableSp.setInt(1, uso.getIdUso());
            callableSp.setInt(2, servicio.getIdServicio());
            callableSp.setInt(3, concepto.getIdConcepto());
            callableSp.setInt(4, cveDia.get());
            callableSp.setInt(5, anio.get());
            callableSp.setFloat(6, importeBim.get());
            callableSp.setFloat(7, importeAnual.get());
            callableSp.setInt(8, aplicaIva.get());
            return callableSp.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean actualizarRegistro(Connection connection) {
        try {
            CallableStatement callableSp = connection.prepareCall("CALL tarifa_actualizar(?, ?, ?, ?, ?, ?, ?, ?, ?)");
            callableSp.setInt(1, idTarifa.get());
            callableSp.setInt(2, uso.getIdUso());
            callableSp.setInt(3, servicio.getIdServicio());
            callableSp.setInt(4, concepto.getIdConcepto());
            callableSp.setInt(5, cveDia.get());
            callableSp.setInt(6, anio.get());
            callableSp.setFloat(7, importeBim.get());
            callableSp.setFloat(8, importeAnual.get());
            callableSp.setInt(9, aplicaIva.get());
            return callableSp.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean eliminarRegistro(Connection connection) {
        try {
            CallableStatement callableSp = connection.prepareCall("CALL tarifa_eliminar(?)");
            callableSp.setInt(1, idTarifa.get());
            return callableSp.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
