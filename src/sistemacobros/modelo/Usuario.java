package sistemacobros.modelo;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
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
public class Usuario extends ModeloBase {

    private IntegerProperty idUsuario;
    private StringProperty nombre;
    private StringProperty apellidoPaterno;
    private StringProperty apellidoMaterno;
    private StringProperty usuario;
    private StringProperty password;
    private Perfil perfil;
    private Date fechaAlta;

    public Usuario(Integer idUsuario, String nombre, String apellidoPaterno, String apellidoMaterno, String usuario,
            String password, Perfil perfil, Date fechaAlta) {
        this.idUsuario = new SimpleIntegerProperty(idUsuario);
        this.nombre = new SimpleStringProperty(nombre);
        this.apellidoPaterno = new SimpleStringProperty(apellidoPaterno);
        this.apellidoMaterno = new SimpleStringProperty(apellidoMaterno);
        this.usuario = new SimpleStringProperty(usuario);
        this.password = new SimpleStringProperty(password);
        this.perfil = perfil;
        this.fechaAlta = fechaAlta;
    }

    public Integer getIdUsuario() {
        return idUsuario.get();
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = new SimpleIntegerProperty(idUsuario);
    }

    public String getNombre() {
        return nombre.get();
    }

    public void setNombre(String nombre) {
        this.nombre = new SimpleStringProperty(nombre);
    }

    public String getApellidoPaterno() {
        return apellidoPaterno.get();
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = new SimpleStringProperty(apellidoPaterno);
    }

    public String getApellidoMaterno() {
        return apellidoMaterno.get();
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = new SimpleStringProperty(apellidoMaterno);
    }

    public String getUsuario() {
        return usuario.get();
    }

    public void setUsuario(String usuario) {
        this.usuario = new SimpleStringProperty(usuario);
    }

    public String getPassword() {
        return password.get();
    }

    public void setPassword(String password) {
        this.password = new SimpleStringProperty(password);
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public Date getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(Date fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public static void getListaUsuarios(Connection connection, ObservableList<Usuario> lista) {
        try {
            Statement instruccion = connection.createStatement();
            ResultSet resultado = instruccion.executeQuery(
                    "SELECT inf.id_usuario, "
                    + "inf.nombre, "
                    + "inf.apellido_paterno, "
                    + "inf.apellido_materno, "
                    + "inf.usuario, "
                    + "inf.password, "
                    + "inf.id_perfil, "
                    + "perf.perfil, "
                    + "inf.fecha_alta "
                    + "FROM usuario inf "
                    + "inner join perfil perf "
                    + "on inf.id_perfil = perf.id_perfil "
                    + "order by inf.id_usuario");
            while (resultado.next()) {
                lista.add(new Usuario(
                        resultado.getInt("id_usuario"),
                        resultado.getString("nombre"),
                        resultado.getString("apellido_paterno"),
                        resultado.getString("apellido_materno"),
                        resultado.getString("usuario"),
                        resultado.getString("password"),
                        new Perfil(
                                resultado.getInt("id_perfil"),
                                resultado.getString("perfil")),
                        resultado.getDate("fecha_alta")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean insertarRegistro(Connection connection) {
        try {
            CallableStatement callableSp = connection.prepareCall("CALL usuario_insertar(?, ?, ?, ?, ?, ?)");
            callableSp.setString(1, nombre.get());
            callableSp.setString(2, apellidoPaterno.get());
            callableSp.setString(3, apellidoMaterno.get());
            callableSp.setString(4, usuario.get());
            callableSp.setString(5, password.get());
            callableSp.setInt(6, perfil.getIdPerfil());
            return callableSp.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean actualizarRegistro(Connection connection) {
        try {
            CallableStatement callableSp = connection.prepareCall("CALL usuario_actualizar(?, ?, ?, ?, ?, ?, ?)");
            callableSp.setInt(1, idUsuario.get());
            callableSp.setString(2, nombre.get());
            callableSp.setString(3, apellidoPaterno.get());
            callableSp.setString(4, apellidoMaterno.get());
            callableSp.setString(5, usuario.get());
            callableSp.setString(6, password.get());
            callableSp.setInt(7, perfil.getIdPerfil());
            return callableSp.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean eliminarRegistro(Connection connection) {
        try {
            CallableStatement callableSp = connection.prepareCall("CALL usuario_eliminar(?)");
            callableSp.setInt(1, idUsuario.get());
            return callableSp.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
