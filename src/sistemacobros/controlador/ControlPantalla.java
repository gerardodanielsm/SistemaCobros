package sistemacobros.controlador;

import javafx.fxml.FXML;
import sistemacobros.vista.ManejadorPantallas;

/**
 *
 * @author Gerardo Solórzano
 */
public interface ControlPantalla {

    public void setScreenParent(ManejadorPantallas screenPage);

    @FXML
    public void guardarRegistro();

    @FXML
    public void actualizarRegistro();

    @FXML
    public void eliminarRegistro();

    @FXML
    public void limpiarComponentes();

    @FXML
    public void mostrarReporte();
}
