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
import sistemacobros.vista.ManejadorPantallas;

/**
 *
 * @author Gerardo Solórzano
 */
public class ConceptoControlador implements Initializable, ControlPantalla {

    ManejadorPantallas manejadorPantallas;

    // Columnas de tabla
    @FXML
    private TableColumn<Concepto, Number> clmnIdConcepto;
    @FXML
    private TableColumn<Concepto, String> clmnConcepto;
    @FXML
    private TableColumn<Concepto, String> clmnPartida;
    @FXML
    private TableColumn<Concepto, Number> clmnReciboConcepto;
    @FXML
    private TableColumn<Concepto, Number> clmnMuestraBoton;
    @FXML
    private TableColumn<Concepto, Number> clmnColor;
    @FXML
    private TableColumn<Concepto, Number> clmnPedirImporte;

    // Componentes GUI
    @FXML
    private TextField txtIdConcepto;
    @FXML
    private TextField txtConcepto;
    @FXML
    private TextField txtPartida;
    @FXML
    private TextField txtReciboConcepto;
    @FXML
    private TextField txtMuestraBoton;
    @FXML
    private TextField txtColor;
    @FXML
    private TextField txtPedirImporte;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnActualizar;
    @FXML
    private TableView<Concepto> tblView;

    // Colecciones
    private ObservableList<Concepto> listaConceptos;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Connection conn = ConexionBaseDatos.getConnection();

        // Inicializar lista
        listaConceptos = FXCollections.observableArrayList();

        // Llenar lista
        Concepto.getListaConceptos(conn, listaConceptos);

        // Enlazar listas
        tblView.setItems(listaConceptos);

        // Enlazar columnas con atributos
        clmnIdConcepto.setCellValueFactory(new PropertyValueFactory<>("idConcepto"));
        clmnConcepto.setCellValueFactory(new PropertyValueFactory<>("concepto"));
        clmnPartida.setCellValueFactory(new PropertyValueFactory<>("partida"));
        clmnReciboConcepto.setCellValueFactory(new PropertyValueFactory<>("reciboConcepto"));
        clmnMuestraBoton.setCellValueFactory(new PropertyValueFactory<>("muestraBoton"));
        clmnColor.setCellValueFactory(new PropertyValueFactory<>("color"));
        clmnPedirImporte.setCellValueFactory(new PropertyValueFactory<>("PedirImporte"));
        gestionarEventos();
    }

    private void gestionarEventos() {
        tblView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Concepto>() {
            @Override
            public void changed(ObservableValue<? extends Concepto> arg0, Concepto valorAnterior,
                    Concepto valorSeleccionado) {
                if (valorSeleccionado != null) {
                    txtIdConcepto.setText(String.valueOf(valorSeleccionado.getIdConcepto()));
                    txtConcepto.setText(valorSeleccionado.getConcepto());
                    txtPartida.setText(valorSeleccionado.getPartida());
                    txtReciboConcepto.setText(String.valueOf(valorSeleccionado.getReciboConcepto()));
                    txtMuestraBoton.setText(String.valueOf(valorSeleccionado.getMuestraBoton()));
                    txtColor.setText(String.valueOf(valorSeleccionado.getColor()));
                    txtPedirImporte.setText(String.valueOf(valorSeleccionado.getPedirImporte()));

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
        Concepto concepto = new Concepto(0, txtConcepto.getText(), txtPartida.getText(),
                Integer.valueOf(txtReciboConcepto.getText()), Integer.valueOf(txtMuestraBoton.getText()), Integer.valueOf(txtColor.getText()),
                Integer.valueOf(txtPedirImporte.getText()));
        // Llamar al metodo insertarRegistro
        Connection conn = ConexionBaseDatos.getConnection();
        concepto.insertarRegistro(conn);
        listaConceptos.clear();
        Concepto.getListaConceptos(conn, listaConceptos);
        //ConexionBaseDatos.cerrarConexion();

        Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
        mensaje.setTitle("Registro agregado");
        mensaje.setContentText("El registro ha sido agregado exitosamente");
        mensaje.setHeaderText("Resultado:");
        mensaje.show();
    }

    @Override
    public void actualizarRegistro() {
        Concepto concepto = new Concepto(Integer.valueOf(txtIdConcepto.getText()), txtConcepto.getText(),
                txtPartida.getText(), Integer.valueOf(txtReciboConcepto.getText()), Integer.valueOf(txtMuestraBoton.getText()), Integer.valueOf(txtColor.getText()),
                Integer.valueOf(txtPedirImporte.getText()));
        Connection conn = ConexionBaseDatos.getConnection();
        concepto.actualizarRegistro(conn);
        //ConexionBaseDatos.cerrarConexion();
        listaConceptos.set(tblView.getSelectionModel().getSelectedIndex(), concepto);

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
            listaConceptos.remove(tblView.getSelectionModel().getSelectedIndex());

            Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
            mensaje.setTitle("Registro eliminado");
            mensaje.setContentText("El registro ha sido eliminado exitosamente");
            mensaje.setHeaderText("Resultado:");
            mensaje.show();
        } else {
            Alert mensaje = new Alert(Alert.AlertType.ERROR);
            mensaje.setTitle("Error al eliminar registro");
            mensaje.setContentText("No es posible eliminar el concepto, existen registros asociados");
            mensaje.setHeaderText("Resultado:");
            mensaje.show();
        }
    }

    @Override
    public void limpiarComponentes() {
        txtIdConcepto.setText(null);
        txtConcepto.setText(null);
        txtPartida.setText(null);
        txtReciboConcepto.setText(null);
        txtMuestraBoton.setText(null);
        txtColor.setText(null);
        txtPedirImporte.setText(null);
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
