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
import sistemacobros.modelo.Concepto;
import sistemacobros.modelo.Uso;
import sistemacobros.vista.ManejadorPantallas;

/**
 *
 * @author Gerardo Solórzano
 */
public class UsoControlador implements Initializable, ControlPantalla {

    ManejadorPantallas manejadorPantallas;

    // Columnas de tabla
    @FXML
    private TableColumn<Uso, Number> clmnIdUso;
    @FXML
    private TableColumn<Concepto, String> clmnUso;
    @FXML
    private TableColumn<Concepto, Number> clmnEsDomestico;
    @FXML
    private TableColumn<Concepto, String> clmnPrefijo;
    @FXML
    private TableColumn<Concepto, String> clmnAbrevia;
    @FXML
    private TableColumn<Concepto, Number> clmnCalculaRec;

    // Componentes GUI
    @FXML
    private TextField txtIdUso;
    @FXML
    private TextField txtUso;
    @FXML
    private TextField txtEsDomestico;
    @FXML
    private TextField txtPrefijo;
    @FXML
    private TextField txtAbrevia;
    @FXML
    private TextField txtCalculaRec;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnActualizar;
    @FXML
    private TableView<Uso> tblView;

    // Colecciones
    private ObservableList<Uso> listaUsos;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Connection conn = ConexionBaseDatos.getConnection();

        // Inicializar lista
        listaUsos = FXCollections.observableArrayList();

        // Llenar lista
        Uso.getListaUsos(conn, listaUsos);

        // Enlazar listas
        tblView.setItems(listaUsos);

        // Enlazar columnas con atributos
        clmnIdUso.setCellValueFactory(new PropertyValueFactory<>("idUso"));
        clmnUso.setCellValueFactory(new PropertyValueFactory<>("uso"));
        clmnEsDomestico.setCellValueFactory(new PropertyValueFactory<>("esDomestico"));
        clmnPrefijo.setCellValueFactory(new PropertyValueFactory<>("prefijo"));
        clmnAbrevia.setCellValueFactory(new PropertyValueFactory<>("abrevia"));
        clmnCalculaRec.setCellValueFactory(new PropertyValueFactory<>("calculaRec"));
        gestionarEventos();
    }

    private void gestionarEventos() {
        tblView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Uso>() {
            @Override
            public void changed(ObservableValue<? extends Uso> arg0, Uso valorAnterior,
                    Uso valorSeleccionado) {
                if (valorSeleccionado != null) {
                    txtIdUso.setText(String.valueOf(valorSeleccionado.getIdUso()));
                    txtUso.setText(valorSeleccionado.getUso());
                    txtEsDomestico.setText(String.valueOf(valorSeleccionado.getEsDomestico()));
                    txtPrefijo.setText(valorSeleccionado.getPrefijo());
                    txtAbrevia.setText(valorSeleccionado.getAbrevia());
                    txtCalculaRec.setText(String.valueOf(valorSeleccionado.getCalculaRec()));

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
        Uso uso = new Uso(0, txtUso.getText(), Integer.valueOf(txtEsDomestico.getText()),
                txtPrefijo.getText(), txtAbrevia.getText(), Integer.valueOf(txtCalculaRec.getText()));
        // Llamar al metodo insertarRegistro
        Connection conn = ConexionBaseDatos.getConnection();
        uso.insertarRegistro(conn);
        listaUsos.clear();
        Uso.getListaUsos(conn, listaUsos);
        //ConexionBaseDatos.cerrarConexion();

        Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
        mensaje.setTitle("Registro agregado");
        mensaje.setContentText("El registro ha sido agregado exitosamente");
        mensaje.setHeaderText("Resultado:");
        mensaje.show();
    }

    @Override
    public void actualizarRegistro() {
        Uso uso = new Uso(Integer.valueOf(txtIdUso.getText()), txtUso.getText(), Integer.valueOf(txtEsDomestico.getText()),
                txtPrefijo.getText(), txtAbrevia.getText(), Integer.valueOf(txtCalculaRec.getText()));
        Connection conn = ConexionBaseDatos.getConnection();
        uso.actualizarRegistro(conn);
        //ConexionBaseDatos.cerrarConexion();
        listaUsos.set(tblView.getSelectionModel().getSelectedIndex(), uso);

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
            listaUsos.remove(tblView.getSelectionModel().getSelectedIndex());

            Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
            mensaje.setTitle("Registro eliminado");
            mensaje.setContentText("El registro ha sido eliminado exitosamente");
            mensaje.setHeaderText("Resultado:");
            mensaje.show();
        } else {
            Alert mensaje = new Alert(Alert.AlertType.ERROR);
            mensaje.setTitle("Error al eliminar registro");
            mensaje.setContentText("No es posible eliminar el uso, existen registros asociados");
            mensaje.setHeaderText("Resultado:");
            mensaje.show();
        }
    }

    @Override
    public void limpiarComponentes() {
        txtIdUso.setText(null);
        txtUso.setText(null);
        txtEsDomestico.setText(null);
        txtPrefijo.setText(null);
        txtAbrevia.setText(null);
        txtCalculaRec.setText(null);
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
