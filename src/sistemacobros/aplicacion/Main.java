package sistemacobros.aplicacion;

import sistemacobros.vista.ManejadorPantallas;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sistemacobros.constantes.Constantes;

/**
 *
 * @author Gerardo Solórzano
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        ManejadorPantallas manejadorPantallas = new ManejadorPantallas();
        manejadorPantallas.loadScreen(Constantes.VENTANA_PRINCIPAL, Constantes.VENTANA_PRINCIPAL_ARCHIVO);
        manejadorPantallas.setScreen(Constantes.VENTANA_PRINCIPAL);

        Group root = new Group();
        root.getChildren().addAll(manejadorPantallas);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
