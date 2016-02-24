package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import model.bean.MedicoModel;

/**
 * FXML Controller class
 *
 * @author jeff-
 */
public class MedicosController implements Initializable {

    @FXML
    private TextField txt_codigo;
    @FXML
    private TextField txt_nome;
    @FXML
    private TextField txt_crm;
    @FXML
    private ComboBox cb_especialidade;
    @FXML
    private TableView<MedicoModel> tabela_medico;
    @FXML
    private TableColumn<MedicoModel, Integer> codigoColuna;
    @FXML
    private TableColumn<MedicoModel, String> nomeColuna;
    @FXML
    private TableColumn<MedicoModel, String> crmColuna;
    @FXML
    private TableColumn<MedicoModel, String> especialidadeColuna;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    /**
     * Metodo para carregar o TableView da GUI com Thread
     */
    public void carregarTabela() {
        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            protected void succeeded() {
                super.succeeded();
            }
        };
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }
}
