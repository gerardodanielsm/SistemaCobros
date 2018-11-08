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
import sistemacobros.modelo.Concepto;
import sistemacobros.modelo.Servicio;
import sistemacobros.modelo.Tarifa;
import sistemacobros.modelo.Uso;
import sistemacobros.vista.ManejadorPantallas;

/**
 *
 * @author Gerardo Solórzano
 */
public class TarifaControlador implements Initializable, ControlPantalla {

    ManejadorPantallas manejadorPantallas;

    // Columnas de tabla
    @FXML
    private TableColumn<Tarifa, Number> clmnIdTarifa;
    @FXML
    private TableColumn<Tarifa, Uso> clmnUso;
    @FXML
    private TableColumn<Tarifa, Servicio> clmnServicio;
    @FXML
    private TableColumn<Tarifa, Concepto> clmnConcepto;
    @FXML
    private TableColumn<Tarifa, Number> clmnCveDia;
    @FXML
    private TableColumn<Tarifa, Number> clmnAnio;
    @FXML
    private TableColumn<Tarifa, Float> clmnImporteBim;
    @FXML
    private TableColumn<Tarifa, Float> clmnImporteAnual;
    @FXML
    private TableColumn<Tarifa, Number> clmnAplicaIva;

    // Componentes GUI
    @FXML
    private TextField txtIdTarifa;
    @FXML
    private TextField txtCveDia;
    @FXML
    private TextField txtAnio;
    @FXML
    private TextField txtImporteBim;
    @FXML
    private TextField txtImporteAnual;
    @FXML
    private TextField txtAplicaIva;
    @FXML
    private ComboBox<Uso> cmbUso;
    @FXML
    private ComboBox<Servicio> cmbServicio;
    @FXML
    private ComboBox<Concepto> cmbConcepto;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnActualizar;
    @FXML
    private TableView<Tarifa> tblView;

    // Colecciones
    private ObservableList<Uso> listaUsos;
    private ObservableList<Servicio> listaServicios;
    private ObservableList<Concepto> listaConceptos;
    private ObservableList<Tarifa> listaTarifas;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Connection conn = ConexionBaseDatos.getConnection();

        // Inicializar lista
        listaUsos = FXCollections.observableArrayList();
        listaServicios = FXCollections.observableArrayList();
        listaConceptos = FXCollections.observableArrayList();
        listaTarifas = FXCollections.observableArrayList();

        // Llenar lista
        Uso.getListaUsos(conn, listaUsos);
        Servicio.getListaServicios(conn, listaServicios);
        Concepto.getListaConceptos(conn, listaConceptos);
        Tarifa.getListaTarifas(conn, listaTarifas);

        // Enlazar listas
        cmbUso.setItems(listaUsos);
        cmbServicio.setItems(listaServicios);
        cmbConcepto.setItems(listaConceptos);
        tblView.setItems(listaTarifas);

        // Enlazar columnas con atributos
        clmnIdTarifa.setCellValueFactory(new PropertyValueFactory<>("idTarifa"));
        clmnUso.setCellValueFactory(new PropertyValueFactory<>("uso"));
        clmnServicio.setCellValueFactory(new PropertyValueFactory<>("servicio"));
        clmnConcepto.setCellValueFactory(new PropertyValueFactory<>("concepto"));
        clmnCveDia.setCellValueFactory(new PropertyValueFactory<>("cveDia"));
        clmnAnio.setCellValueFactory(new PropertyValueFactory<>("anio"));
        clmnImporteBim.setCellValueFactory(new PropertyValueFactory<>("importeBim"));
        clmnImporteAnual.setCellValueFactory(new PropertyValueFactory<>("importeAnual"));
        clmnAplicaIva.setCellValueFactory(new PropertyValueFactory<>("aplicaIva"));
        gestionarEventos();
    }

    private void gestionarEventos() {
        tblView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tarifa>() {
            @Override
            public void changed(ObservableValue<? extends Tarifa> arg0, Tarifa valorAnterior,
                    Tarifa valorSeleccionado) {
                if (valorSeleccionado != null) {
                    txtIdTarifa.setText(String.valueOf(valorSeleccionado.getIdTarifa()));
                    cmbUso.setValue(valorSeleccionado.getUso());
                    cmbServicio.setValue(valorSeleccionado.getServicio());
                    cmbConcepto.setValue(valorSeleccionado.getConcepto());
                    txtCveDia.setText(String.valueOf(valorSeleccionado.getCveDia()));
                    txtAnio.setText(String.valueOf(valorSeleccionado.getAnio()));
                    txtImporteBim.setText(String.valueOf(valorSeleccionado.getImporteBim()));
                    txtImporteAnual.setText(String.valueOf(valorSeleccionado.getImporteAnual()));
                    txtAplicaIva.setText(String.valueOf(valorSeleccionado.getAplicaIva()));

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
        Tarifa tarifa = new Tarifa(0, cmbUso.getSelectionModel().getSelectedItem(), cmbServicio.getSelectionModel().getSelectedItem(), 
                cmbConcepto.getSelectionModel().getSelectedItem(), Integer.valueOf(txtCveDia.getText()), Integer.valueOf(txtAnio.getText()), Float.valueOf(txtImporteBim.getText()),
        Float.valueOf(txtImporteAnual.getText()), Integer.valueOf(txtAplicaIva.getText()));
        Connection conn = ConexionBaseDatos.getConnection();
        tarifa.insertarRegistro(conn);
        listaTarifas.clear();
        Tarifa.getListaTarifas(conn, listaTarifas);
        
        Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
        mensaje.setTitle("Registro agregado");
        mensaje.setContentText("El registro ha sido agregado exitosamente");
        mensaje.setHeaderText("Resultado:");
        mensaje.show();
    }

    @Override
    public void actualizarRegistro() {
        Tarifa tarifa = new Tarifa(Integer.valueOf(txtIdTarifa.getText()), cmbUso.getSelectionModel().getSelectedItem(), cmbServicio.getSelectionModel().getSelectedItem(), 
                cmbConcepto.getSelectionModel().getSelectedItem(), Integer.valueOf(txtCveDia.getText()), Integer.valueOf(txtAnio.getText()), Float.valueOf(txtImporteBim.getText()),
        Float.valueOf(txtImporteAnual.getText()), Integer.valueOf(txtAplicaIva.getText()));
        Connection conn = ConexionBaseDatos.getConnection();
        tarifa.actualizarRegistro(conn);
        listaTarifas.set(tblView.getSelectionModel().getSelectedIndex(), tarifa);
        
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
        listaTarifas.remove(tblView.getSelectionModel().getSelectedIndex());

        Alert mensaje = new Alert(Alert.AlertType.INFORMATION);
        mensaje.setTitle("Registro eliminado");
        mensaje.setContentText("El registro ha sido eliminado exitosamente");
        mensaje.setHeaderText("Resultado:");
        mensaje.show();
    }

    @Override
    public void limpiarComponentes() {
        txtIdTarifa.setText(null);
        cmbUso.setValue(null);
        cmbServicio.setValue(null);
        cmbConcepto.setValue(null);
        txtCveDia.setText(null);
        txtAnio.setText(null);
        txtImporteBim.setText(null);
        txtImporteAnual.setText(null);
        txtAplicaIva.setText(null);
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
