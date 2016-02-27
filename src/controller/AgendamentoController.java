package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import model.bean.MedicoModel;
import model.dao.MedicoDAO;
import util.ConverterDados;

/**
 * FXML Controller class
 *
 * @author jeff-
 */
public class AgendamentoController implements Initializable {

    @FXML
    private ComboBox<MedicoModel> cb_medico;
    @FXML
    private ComboBox cb_turno;
    @FXML
    private DatePicker dpData;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /*Utilizando a nossa Classe converter MedicoModel*/
        this.cb_medico.setConverter(new ConverterDados(ConverterDados.GET_MEDICO_NOME).getMedicoConverter());
    }

    /**
     * Executa as funções iniciais como preencher o comboBox do Medico
     * utilizando o Task já que pode ser um processo pesado
     */
    public void iniciarProcessos() {
        /*Para evitar uma exception de Thread temos que limpar o comboBox*/
        cb_medico.getItems().clear();

        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                cb_medico.setItems(MedicoDAO.executeQuery(null, MedicoDAO.QUERY_TODOS));
                return null;
            }
        };
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

}
