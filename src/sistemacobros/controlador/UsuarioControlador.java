package sistemacobros.controlador;

import sistemacobros.vista.ManejadorPantallas;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import net.sf.jasperreports.engine.JRException;
import sistemacobros.bd.ConexionBaseDatos;
import sistemacobros.constantes.Constantes;
import sistemacobros.modelo.Perfil;
import sistemacobros.modelo.Usuario;
import sistemacobros.reporte.UsuarioReporte;

/**
 *
 * @author Gerardo Solórzano
 */
public class UsuarioControlador implements Initializable, ControlPantalla {
    
    ManejadorPantallas manejadorPantallas;

    // Columnas de tabla
    @FXML
    private TableColumn<Usuario, Number> clmnIdUsuario;
    @FXML
    private TableColumn<Usuario, String> clmnNombre;
    @FXML
    private TableColumn<Usuario, String> clmnApellidoPaterno;
    @FXML
    private TableColumn<Usuario, String> clmnApellidoMaterno;
    @FXML
    private TableColumn<Usuario, String> clmnUsuario;
    @FXML
    private TableColumn<Usuario, Perfil> clmnPerfil;
    @FXML
    private TableColumn<Usuario, Date> clmnFechaAlta;

    // Componentes GUI
    @FXML
    private TextField txtIdUsuario;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtApellidoPaterno;
    @FXML
    private TextField txtApellidoMaterno;
    @FXML
    private TextField txtUsuario;
    @FXML
    private ComboBox<Perfil> cmbPerfil;
    @FXML
    private TextField txtPassword;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnActualizar;
    @FXML
    private TableView<Usuario> tblView;

    // Colecciones
    private ObservableList<Perfil> listaPerfiles;
    private ObservableList<Usuario> listaUsuarios;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Connection conn = ConexionBaseDatos.getConnection();

        // Inicializar lista
        listaPerfiles = FXCollections.observableArrayList();
        listaUsuarios = FXCollections.observableArrayList();

        // Llenar lista
        Perfil.getListaPerfiles(conn, listaPerfiles);
        Usuario.getListaUsuarios(conn, listaUsuarios);

        // Enlazar listas
        cmbPerfil.setItems(listaPerfiles);
        tblView.setItems(listaUsuarios);

        // Enlazar columnas con atributos
        clmnIdUsuario.setCellValueFactory(new PropertyValueFactory<>("idUsuario"));
        clmnNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        clmnApellidoPaterno.setCellValueFactory(new PropertyValueFactory<>("apellidoPaterno"));
        clmnApellidoMaterno.setCellValueFactory(new PropertyValueFactory<>("apellidoMaterno"));
        clmnUsuario.setCellValueFactory(new PropertyValueFactory<>("usuario"));
        clmnPerfil.setCellValueFactory(new PropertyValueFactory<>("perfil"));
        clmnFechaAlta.setCellValueFactory(new PropertyValueFactory<>("fechaAlta"));
        gestionarEventos();

        //ConexionBaseDatos.cerrarConexion();
    }

    private void gestionarEventos() {
        tblView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Usuario>() {
            @Override
            public void changed(ObservableValue<? extends Usuario> arg0, Usuario valorAnterior,
                    Usuario valorSeleccionado) {
                if (valorSeleccionado != null) {
                    txtIdUsuario.setText(String.valueOf(valorSeleccionado.getIdUsuario()));
                    txtNombre.setText(valorSeleccionado.getNombre());
                    txtApellidoPaterno.setText(valorSeleccionado.getApellidoPaterno());
                    txtApellidoMaterno.setText(valorSeleccionado.getApellidoMaterno());
                    txtUsuario.setText(valorSeleccionado.getUsuario());
                    txtPassword.setText(valorSeleccionado.getPassword());
                    cmbPerfil.setValue(valorSeleccionado.getPerfil());

                    btnGuardar.setDisable(true);
                    btnEliminar.setDisable(false);
                    btnActualizar.setDisable(false);
                }
            }
        });
    }

    @Override
    public void guardarRegistro() {
        // Crear una nueva instancia
        Usuario usuario = new Usuario(0, txtNombre.getText(), txtApellidoPaterno.getText(),
                txtApellidoMaterno.getText(), txtUsuario.getText(), txtPassword.getText(),
                cmbPerfil.getSelectionModel().getSelectedItem(), new Date(System.currentTimeMillis()));
        // Llamar al metodo insertarRegistro
        Connection conn = ConexionBaseDatos.getConnection();
        usuario.insertarRegistro(conn);
        listaUsuarios.clear();
        Usuario.getListaUsuarios(conn, listaUsuarios);
        //ConexionBaseDatos.cerrarConexion();

        Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
        mensaje.setTitle("Registro agregado");
        mensaje.setContentText("El registro ha sido agregado exitosamente");
        mensaje.setHeaderText("Resultado:");
        mensaje.show();
    }

    @Override
    public void actualizarRegistro() {
        Usuario usuario = new Usuario(Integer.valueOf(txtIdUsuario.getText()), txtNombre.getText(),
                txtApellidoPaterno.getText(), txtApellidoMaterno.getText(), txtUsuario.getText(), txtPassword.getText(),
                cmbPerfil.getSelectionModel().getSelectedItem(), new Date(System.currentTimeMillis()));
        Connection conn = ConexionBaseDatos.getConnection();
        usuario.actualizarRegistro(conn);
        //ConexionBaseDatos.cerrarConexion();
        listaUsuarios.set(tblView.getSelectionModel().getSelectedIndex(), usuario);

        Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
        mensaje.setTitle("Registro actualizado");
        mensaje.setContentText("El registro ha sido actualizado exitosamente");
        mensaje.setHeaderText("Resultado:");
        mensaje.show();
    }

    @Override
    public void eliminarRegistro() {
        Connection conn = ConexionBaseDatos.getConnection();
        tblView.getSelectionModel().getSelectedItem().eliminarRegistro(conn);
        //ConexionBaseDatos.cerrarConexion();
        listaUsuarios.remove(tblView.getSelectionModel().getSelectedIndex());

        Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
        mensaje.setTitle("Registro eliminado");
        mensaje.setContentText("El registro ha sido eliminado exitosamente");
        mensaje.setHeaderText("Resultado:");
        mensaje.show();
    }

    @Override
    public void limpiarComponentes() {
        txtIdUsuario.setText(null);
        txtNombre.setText(null);
        txtApellidoPaterno.setText(null);
        txtApellidoMaterno.setText(null);
        txtUsuario.setText(null);
        cmbPerfil.setValue(null);
        txtPassword.setText(null);
        btnGuardar.setDisable(false);
        btnEliminar.setDisable(true);
        btnActualizar.setDisable(true);
    }

    @Override
    public void mostrarReporte() {
        try {
            new UsuarioReporte().mostrarReporte(tblView.getItems());
        } catch (JRException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void setScreenParent(ManejadorPantallas screenPage) {
        manejadorPantallas = screenPage;
    }
    
    @FXML
    public void goToPantallaPrincipal(ActionEvent event) {
        manejadorPantallas.setScreen(Constantes.VENTANA_PRINCIPAL);
    }
}
