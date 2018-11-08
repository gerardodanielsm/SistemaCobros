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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import sistemacobros.bd.ConexionBaseDatos;
import sistemacobros.constantes.Constantes;
import sistemacobros.modelo.Rango;
import sistemacobros.modelo.Uso;
import sistemacobros.vista.ManejadorPantallas;

/**
 *
 * @author Gerardo Solórzano
 */
public class RangoControlador implements Initializable, ControlPantalla {

    ManejadorPantallas manejadorPantallas;

    // Columnas de tabla
    @FXML
    private TableColumn<Rango, Number> clmnIdRango;
    @FXML
    private TableColumn<Rango, Uso> clmnUso;
    @FXML
    private TableColumn<Rango, Float> clmnInicio;
    @FXML
    private TableColumn<Rango, Float> clmnFin;
    @FXML
    private TableColumn<Rango, Float> clmnImporte;
    @FXML
    private TableColumn<Rango, Float> clmnImpExtra;
    @FXML
    private TableColumn<Rango, Number> clmnAnio;

    // Componentes GUI
    @FXML
    private TextField txtIdRango;
    @FXML
    private TextField txtInicio;
    @FXML
    private TextField txtFin;
    @FXML
    private TextField txtImporte;
    @FXML
    private TextField txtImpExtra;
    @FXML
    private TextField txtAnio;
    @FXML
    private ComboBox<Uso> cmbUso;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnActualizar;
    @FXML
    private TableView<Rango> tblView;

    // Colecciones
    private ObservableList<Uso> listaUsos;
    private ObservableList<Rango> listaRangos;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Connection conn = ConexionBaseDatos.getConnection();

        // Inicializar lista
        listaUsos = FXCollections.observableArrayList();
        listaRangos = FXCollections.observableArrayList();

        // Llenar lista
        Uso.getListaUsos(conn, listaUsos);
        Rango.getListaRangos(conn, listaRangos);

        // Enlazar listas
        cmbUso.setItems(listaUsos);
        tblView.setItems(listaRangos);

        // Enlazar columnas con atributos
        clmnIdRango.setCellValueFactory(new PropertyValueFactory<>("idRango"));
        clmnUso.setCellValueFactory(new PropertyValueFactory<>("uso"));
        clmnInicio.setCellValueFactory(new PropertyValueFactory<>("inicio"));
        clmnFin.setCellValueFactory(new PropertyValueFactory<>("fin"));
        clmnImporte.setCellValueFactory(new PropertyValueFactory<>("importe"));
        clmnImpExtra.setCellValueFactory(new PropertyValueFactory<>("ImpExtra"));
        clmnAnio.setCellValueFactory(new PropertyValueFactory<>("anio"));
        gestionarEventos();
    }

    private void gestionarEventos() {
        tblView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Rango>() {
            @Override
            public void changed(ObservableValue<? extends Rango> arg0, Rango valorAnterior,
                    Rango valorSeleccionado) {
                if (valorSeleccionado != null) {
                    txtIdRango.setText(String.valueOf(valorSeleccionado.getIdRango()));
                    cmbUso.setValue(valorSeleccionado.getUso());
                    txtInicio.setText(String.valueOf(valorSeleccionado.getInicio()));
                    txtFin.setText(String.valueOf(valorSeleccionado.getFin()));
                    txtImporte.setText(String.valueOf(valorSeleccionado.getImporte()));
                    txtImpExtra.setText(String.valueOf(valorSeleccionado.getImpExtra()));
                    txtAnio.setText(String.valueOf(valorSeleccionado.getAnio()));

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
        Rango rango = new Rango(0, cmbUso.getSelectionModel().getSelectedItem(), Float.valueOf(txtInicio.getText()),
                Float.valueOf(txtFin.getText()), Float.valueOf(txtImporte.getText()), Float.valueOf(txtImpExtra.getText()),
                Integer.valueOf(txtAnio.getText()));
        // Llamar al metodo insertarRegistro
        Connection conn = ConexionBaseDatos.getConnection();
        rango.insertarRegistro(conn);
        listaRangos.clear();
        Rango.getListaRangos(conn, listaRangos);
        //ConexionBaseDatos.cerrarConexion();

        Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
        mensaje.setTitle("Registro agregado");
        mensaje.setContentText("El registro ha sido agregado exitosamente");
        mensaje.setHeaderText("Resultado:");
        mensaje.show();
    }

    @Override
    public void actualizarRegistro() {
        Rango rango = new Rango(Integer.valueOf(txtIdRango.getText()), cmbUso.getSelectionModel().getSelectedItem(), Float.valueOf(txtInicio.getText()),
                Float.valueOf(txtFin.getText()), Float.valueOf(txtImporte.getText()), Float.valueOf(txtImpExtra.getText()),
                Integer.valueOf(txtAnio.getText()));
        Connection conn = ConexionBaseDatos.getConnection();
        rango.actualizarRegistro(conn);
        //ConexionBaseDatos.cerrarConexion();
        listaRangos.set(tblView.getSelectionModel().getSelectedIndex(), rango);

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
        listaRangos.remove(tblView.getSelectionModel().getSelectedIndex());

        Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
        mensaje.setTitle("Registro eliminado");
        mensaje.setContentText("El registro ha sido eliminado exitosamente");
        mensaje.setHeaderText("Resultado:");
        mensaje.show();
    }

    @Override
    public void limpiarComponentes() {
        txtIdRango.setText(null);
        cmbUso.setValue(null);
        txtInicio.setText(null);
        txtFin.setText(null);
        txtImporte.setText(null);
        txtImpExtra.setText(null);
        txtAnio.setText(null);
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
