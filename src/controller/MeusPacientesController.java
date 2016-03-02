package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.bean.PacienteModel;
import model.dao.PacienteDAO;

/**
 * FXML Controller class
 *
 * @author jeff-
 */
public class MeusPacientesController implements Initializable {

    @FXML
    private TableView<PacienteModel> tabela_paciente;
    @FXML
    private TableColumn<PacienteModel, String> nomeColuna;
    @FXML
    private TableColumn<PacienteModel, Integer> codigoColuna;
    @FXML
    private TableColumn<PacienteModel, String> enderecoColuna;
    @FXML
    private TableColumn<PacienteModel, String> telefoneColuna;
    @FXML
    private TableColumn<PacienteModel, String> emailColuna;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /**
         * Implementação da TableView
         */
        this.codigoColuna.setCellValueFactory(cellData -> cellData.getValue().getCodigoProperty().asObject());
        this.nomeColuna.setCellValueFactory(cellData -> cellData.getValue().getNomeProperty());
        this.enderecoColuna.setCellValueFactory(cellData -> cellData.getValue().getEnderecoProperty());
        this.telefoneColuna.setCellValueFactory(cellData -> cellData.getValue().getTelefoneProperty());
        this.emailColuna.setCellValueFactory(cellData -> cellData.getValue().getEmailProperty());

    }

    public void carregarTabela() {
        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                return PacienteDAO.executeQuery(null, PacienteDAO.QUERY_TODOS);
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                tabela_paciente.setItems((ObservableList<PacienteModel>) getValue());
            }
        };
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

}
