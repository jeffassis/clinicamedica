package controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import model.bean.PacienteModel;
import model.dao.PacienteDAO;
import util.DialogFX;

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
    /*Vamos precisar dele para chamar a tela paciente para editar os dados*/
    private HomeController homeController;
    private List<ComboBox> combos;

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

    /**
     * Método que pega a referencia de HomeController.
     *
     * @param homeController
     */
    public void pegarHomeReferencia(HomeController homeController) {
        this.homeController = homeController;
    }

    @FXML
    private void onMouseClick(MouseEvent evento) {
        if (tabela_paciente.getSelectionModel().getSelectedIndex() != -1) {
            if (evento.getClickCount() == 2) {
                /*Mandamos abrir a Tela paciente para editar*/
                this.homeController.cadPaciente(true, tabela_paciente);
            }
        } else {
            DialogFX.showMessage("Por favor selecione um Paciente!", "Atenção", DialogFX.ATENCAO);
        }
    }

    /**
     * Atualiza a tabela com os pacientes
     */
    @FXML
    private void onRefresh() {
        carregarTabela();
        tabela_paciente.getSelectionModel().clearSelection();
    }
}
