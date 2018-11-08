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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import sistemacobros.modelo.Servicio;
import sistemacobros.vista.ManejadorPantallas;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import sistemacobros.bd.ConexionBaseDatos;
import sistemacobros.constantes.Constantes;

/**
 *
 * @author Gerardo Solórzano
 */
public class ServicioControlador implements Initializable, ControlPantalla {

    ManejadorPantallas manejadorPantallas;

    // Columnas de tabla
    @FXML
    private TableColumn<Servicio, Number> clmnIdServicio;
    @FXML
    private TableColumn<Servicio, String> clmnServicio;
    @FXML
    private TableColumn<Servicio, String> clmnAbrevia;
    @FXML
    private TableColumn<Servicio, Number> clmnUsaRango;
    @FXML
    private TableColumn<Servicio, Number> clmnBimestral;

    // Componentes GUI
    @FXML
    private TextField txtIdServicio;
    @FXML
    private TextField txtServicio;
    @FXML
    private TextField txtAbrevia;
    @FXML
    private TextField txtUsaRango;
    @FXML
    private TextField txtBimestral;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnActualizar;
    @FXML
    private TableView<Servicio> tblView;

    // Colecciones
    private ObservableList<Servicio> listaServicios;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Connection conn = ConexionBaseDatos.getConnection();

        // Inicializar lista
        listaServicios = FXCollections.observableArrayList();

        // Llenar lista
        Servicio.getListaServicios(conn, listaServicios);

        // Enlazar listas
        tblView.setItems(listaServicios);

        // Enlazar columnas con atributos
        clmnIdServicio.setCellValueFactory(new PropertyValueFactory<>("idServicio"));
        clmnServicio.setCellValueFactory(new PropertyValueFactory<>("servicio"));
        clmnAbrevia.setCellValueFactory(new PropertyValueFactory<>("abrevia"));
        clmnUsaRango.setCellValueFactory(new PropertyValueFactory<>("usaRango"));
        clmnBimestral.setCellValueFactory(new PropertyValueFactory<>("bimestral"));
        gestionarEventos();
    }

    private void gestionarEventos() {
        tblView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Servicio>() {
            @Override
            public void changed(ObservableValue<? extends Servicio> arg0, Servicio valorAnterior,
                    Servicio valorSeleccionado) {
                if (valorSeleccionado != null) {
                    txtIdServicio.setText(String.valueOf(valorSeleccionado.getIdServicio()));
                    txtServicio.setText(valorSeleccionado.getServicio());
                    txtAbrevia.setText(valorSeleccionado.getAbrevia());
                    txtUsaRango.setText(String.valueOf(valorSeleccionado.getUsaRango()));
                    txtBimestral.setText(String.valueOf(valorSeleccionado.getBimestral()));

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
        Servicio servicio = new Servicio(0, txtServicio.getText(), txtAbrevia.getText(),
                Integer.valueOf(txtUsaRango.getText()), Integer.valueOf(txtBimestral.getText()));
        // Llamar al metodo insertarRegistro
        Connection conn = ConexionBaseDatos.getConnection();
        servicio.insertarRegistro(conn);
        listaServicios.clear();
        Servicio.getListaServicios(conn, listaServicios);
        //ConexionBaseDatos.cerrarConexion();

        Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
        mensaje.setTitle("Registro agregado");
        mensaje.setContentText("El registro ha sido agregado exitosamente");
        mensaje.setHeaderText("Resultado:");
        mensaje.show();
    }

    @Override
    public void actualizarRegistro() {
        Servicio servicio = new Servicio(Integer.valueOf(txtIdServicio.getText()), txtServicio.getText(), txtAbrevia.getText(),
                Integer.valueOf(txtUsaRango.getText()), Integer.valueOf(txtBimestral.getText()));
        Connection conn = ConexionBaseDatos.getConnection();
        servicio.actualizarRegistro(conn);
        //ConexionBaseDatos.cerrarConexion();
        listaServicios.set(tblView.getSelectionModel().getSelectedIndex(), servicio);

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
            listaServicios.remove(tblView.getSelectionModel().getSelectedIndex());

            Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
            mensaje.setTitle("Registro eliminado");
            mensaje.setContentText("El registro ha sido eliminado exitosamente");
            mensaje.setHeaderText("Resultado:");
            mensaje.show();
        } else {
            Alert mensaje = new Alert(Alert.AlertType.ERROR);
            mensaje.setTitle("Error al eliminar registro");
            mensaje.setContentText("No es posible eliminar el servicio, existen registros asociados");
            mensaje.setHeaderText("Resultado:");
            mensaje.show();
        }
    }

    @Override
    public void limpiarComponentes() {
        txtIdServicio.setText(null);
        txtServicio.setText(null);
        txtAbrevia.setText(null);
        txtUsaRango.setText(null);
        txtBimestral.setText(null);
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
