package sistemacobros.controlador;

import java.net.URL;
import java.sql.Connection;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import sistemacobros.bd.ConexionBaseDatos;
import sistemacobros.constantes.Constantes;
import sistemacobros.modelo.Perfil;
import sistemacobros.vista.ManejadorPantallas;

/**
 *
 * @author Gerardo Solórzano
 */
public class PerfilControlador implements Initializable, ControlPantalla {

    ManejadorPantallas manejadorPantallas;

    // Columnas de tabla
    @FXML
    private TableColumn<Perfil, Number> clmnIdPerfil;
    @FXML
    private TableColumn<Perfil, String> clmnPerfil;

    // Componentes GUI
    @FXML
    private TextField txtIdPerfil;
    @FXML
    private TextField txtPerfil;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnActualizar;
    @FXML
    private TableView<Perfil> tblView;

    // Colecciones
    private ObservableList<Perfil> listaPerfiles;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Connection conn = ConexionBaseDatos.getConnection();

        // Inicializar lista
        listaPerfiles = FXCollections.observableArrayList();

        // Llenar lista
        Perfil.getListaPerfiles(conn, listaPerfiles);

        // Enlazar listas
        tblView.setItems(listaPerfiles);

        // Enlazar columnas con atributos
        clmnIdPerfil.setCellValueFactory(new PropertyValueFactory<>("idPerfil"));
        clmnPerfil.setCellValueFactory(new PropertyValueFactory<>("perfil"));
        gestionarEventos();
    }

    private void gestionarEventos() {
        tblView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Perfil>() {
            @Override
            public void changed(ObservableValue<? extends Perfil> arg0, Perfil valorAnterior,
                    Perfil valorSeleccionado) {
                if (valorSeleccionado != null) {
                    txtIdPerfil.setText(String.valueOf(valorSeleccionado.getIdPerfil()));
                    txtPerfil.setText(valorSeleccionado.getPerfil());

                    btnGuardar.setDisable(true);
                    btnEliminar.setDisable(false);
                    btnActualizar.setDisable(false);
                }
            }
        });
    }

    @Override
    public void setScreenParent(ManejadorPantallas screenPage) {
        manejadorPantallas = screenPage;
    }

    @Override
    public void guardarRegistro() {
        // Crear una nueva instancia
        Perfil perfil = new Perfil(0, txtPerfil.getText());
        // Llamar al metodo insertarRegistro
        Connection conn = ConexionBaseDatos.getConnection();
        perfil.insertarRegistro(conn);
        listaPerfiles.clear();
        Perfil.getListaPerfiles(conn, listaPerfiles);
        //ConexionBaseDatos.cerrarConexion();

        Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
        mensaje.setTitle("Registro agregado");
        mensaje.setContentText("El registro ha sido agregado exitosamente");
        mensaje.setHeaderText("Resultado:");
        mensaje.show();
    }

    @Override
    public void actualizarRegistro() {
        Perfil perfil = new Perfil(Integer.valueOf(txtIdPerfil.getText()), txtPerfil.getText());
        Connection conn = ConexionBaseDatos.getConnection();
        perfil.actualizarRegistro(conn);
        //ConexionBaseDatos.cerrarConexion();
        listaPerfiles.set(tblView.getSelectionModel().getSelectedIndex(), perfil);

        Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
        mensaje.setTitle("Registro actualizado");
        mensaje.setContentText("El registro ha sido actualizado exitosamente");
        mensaje.setHeaderText("Resultado:");
        mensaje.show();
    }

    @Override
    public void eliminarRegistro() {
        Connection conn = ConexionBaseDatos.getConnection();
        boolean resultado = tblView.getSelectionModel().getSelectedItem().eliminarRegistro(conn);
        //ConexionBaseDatos.cerrarConexion();

        if (resultado) {
            listaPerfiles.remove(tblView.getSelectionModel().getSelectedIndex());

            Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
            mensaje.setTitle("Registro eliminado");
            mensaje.setContentText("El registro ha sido eliminado exitosamente");
            mensaje.setHeaderText("Resultado:");
            mensaje.show();
        } else {
            Alert mensaje = new Alert(Alert.AlertType.ERROR);
            mensaje.setTitle("Error al eliminar registro");
            mensaje.setContentText("No es posible eliminar el perfil, existen registros asociados");
            mensaje.setHeaderText("Resultado:");
            mensaje.show();
        }
    }

    @Override
    public void limpiarComponentes() {
        txtPerfil.setText(null);
        btnGuardar.setDisable(false);
        btnEliminar.setDisable(true);
        btnActualizar.setDisable(true);
    }

    @Override
    public void mostrarReporte() {
        // TODO
    }

    @FXML
    public void goToPantallaPrincipal(ActionEvent event) {
        manejadorPantallas.setScreen(Constantes.VENTANA_PRINCIPAL);
    }
}
