package sistemacobros.reporte;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.collections.ObservableList;
import javax.swing.JFrame;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.swing.JRViewer;
import sistemacobros.modelo.Usuario;

/**
 *
 * @author Gerardo Solórzano
 */
public class UsuarioReporte extends JFrame {

    private static final long serialVersionUID = 1L;

    public void mostrarReporte(ObservableList<Usuario> listaUsuarios) throws JRException {

        String reportSrcFile = "resources/ConnectionInformation.jrxml";

        // Compilar reporte
        JasperReport jasperReport = JasperCompileManager.compileReport(reportSrcFile);

        HashMap<String, Object> parameters;
        ArrayList<HashMap<String, Object>> list = new ArrayList<>();
        for (Usuario usuario : listaUsuarios) {
            parameters = new HashMap<>();
            parameters.put("id", usuario.getIdUsuario());
            parameters.put("nombre", usuario.getNombre());
            parameters.put("apellido_paterno", usuario.getApellidoPaterno());
            parameters.put("apellido_materno", usuario.getApellidoMaterno());
            parameters.put("usuario", usuario.getUsuario());
            parameters.put("id_perfil", usuario.getPerfil().getIdPerfil());
            parameters.put("fecha_alta", new Timestamp(usuario.getFechaAlta().getTime()));
            list.add(parameters);
        }

        JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(list);
        JasperPrint print = JasperFillManager.fillReport(jasperReport, null, beanColDataSource);
        JRViewer viewer = new JRViewer(print);
        viewer.setOpaque(true);
        viewer.setVisible(true);
        this.add(viewer);
        this.setSize(700, 500);
        this.setVisible(true);
    }
}
