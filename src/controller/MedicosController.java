package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import model.bean.MedicoModel;
import model.dao.MedicoDAO;

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
    @FXML
    private Button bt_salvar;
    @FXML
    private Button bt_editar;
    @FXML
    private Button bt_excluir;
    @FXML
    private Button bt_novo;
    @FXML
    private Button bt_cancelar;

    /*Criação da variavel de Class */
    private MedicoModel medicoModel;

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

    @FXML
    private void onSave() {

        this.medicoModel = new MedicoModel();
        medicoModel.setNome(txt_nome.getText().trim());
        medicoModel.setCrm(txt_crm.getText().trim());
        medicoModel.setEspecialidade((String) cb_especialidade.getSelectionModel().getSelectedItem());
        if (MedicoDAO.executeUpdates(medicoModel, MedicoDAO.CREATE)) {
            limparCampos();
            alert("Dados inseridos com sucesso!");
            carregarTabela();
            desabilitarCampos();
        } else {
            alert("Houve um erro ao inserir Dados");
        }
    }

    /**
     * Método ação do Botao Novo
     */
    @FXML
    public void onNew() {
        bt_novo.setDisable(false);
        bt_salvar.setDisable(false);
        bt_editar.setDisable(true);
        bt_excluir.setDisable(true);
        habilitarCampos();
        txt_nome.requestFocus();
    }

    /**
     * Método que cria as Janelas de Dialog com Informação para usuario
     *
     * @param msg
     */
    private void alert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Mensagem");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    /**
     * Método que limpa os campos
     */
    public void limparCampos() {
        txt_codigo.setText("");
        txt_nome.setText("");
        txt_crm.setText("");
        cb_especialidade.getSelectionModel().clearSelection();
    }

    /**
     * Método responsavel por habilitar os campos
     */
    public void habilitarCampos() {
        txt_nome.setDisable(false);
        txt_crm.setDisable(false);
        cb_especialidade.setDisable(false);
    }

    /**
     * Método responsavel por desabilitar os campos e botôes
     */
    public void desabilitarCampos() {
        txt_nome.setDisable(true);
        txt_crm.setDisable(true);
        cb_especialidade.setDisable(true);
        bt_salvar.setDisable(true);
        bt_editar.setDisable(true);
        bt_excluir.setDisable(true);
    }
}
