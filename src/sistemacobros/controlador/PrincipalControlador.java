package sistemacobros.controlador;

import sistemacobros.vista.ManejadorPantallas;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import sistemacobros.constantes.Constantes;

/**
 *
 * @author Gerardo Solórzano
 */
public class PrincipalControlador implements Initializable, ControlPantalla {

    ManejadorPantallas manejadorPantallas;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO
    }

    @Override
    public void setScreenParent(ManejadorPantallas screenPage) {
        manejadorPantallas = screenPage;
    }

    @FXML
    public void goToPantallaUsuarios(ActionEvent event) {
        manejadorPantallas.loadScreen(Constantes.VENTANA_USUARIO, Constantes.VENTANA_USUARIO_ARCHIVO);
        manejadorPantallas.setScreen(Constantes.VENTANA_USUARIO);
    }
    
    @FXML
    public void goToPantallaConceptos(ActionEvent event) {
        manejadorPantallas.loadScreen(Constantes.VENTANA_CONCEPTO, Constantes.VENTANA_CONCEPTO_ARCHIVO);
        manejadorPantallas.setScreen(Constantes.VENTANA_CONCEPTO);
    }
    
    @FXML
    public void goToPantallaRangos(ActionEvent event) {
        manejadorPantallas.loadScreen(Constantes.VENTANA_RANGO, Constantes.VENTANA_RANGO_ARCHIVO);
        manejadorPantallas.setScreen(Constantes.VENTANA_RANGO);
    }
    
    @FXML
    public void goToPantallaPerfiles(ActionEvent event) {
        manejadorPantallas.loadScreen(Constantes.VENTANA_PERFIL, Constantes.VENTANA_PERFIL_ARCHIVO);
        manejadorPantallas.setScreen(Constantes.VENTANA_PERFIL);
    }
    
    @FXML
    public void goToPantallaServicios(ActionEvent event) {
        manejadorPantallas.loadScreen(Constantes.VENTANA_SERVICIO, Constantes.VENTANA_SERVICIO_ARCHIVO);
        manejadorPantallas.setScreen(Constantes.VENTANA_SERVICIO);
    }
    
    @FXML
    public void goToPantallaUsos(ActionEvent event) {
        manejadorPantallas.loadScreen(Constantes.VENTANA_USO, Constantes.VENTANA_USO_ARCHIVO);
        manejadorPantallas.setScreen(Constantes.VENTANA_USO);
    }
    
    @FXML
    public void goToPantallaTarifas(ActionEvent event) {
        manejadorPantallas.loadScreen(Constantes.VENTANA_TARIFA, Constantes.VENTANA_TARIFA_ARCHIVO);
        manejadorPantallas.setScreen(Constantes.VENTANA_TARIFA);
    }

    @Override
    public void guardarRegistro() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void actualizarRegistro() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void eliminarRegistro() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void limpiarComponentes() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mostrarReporte() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
