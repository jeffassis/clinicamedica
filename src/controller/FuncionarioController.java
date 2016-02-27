package controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import model.bean.FuncionarioModel;
import model.dao.FuncionarioDAO;

/**
 * FXML Controller class
 *
 * @author jeff-
 */
public class FuncionarioController implements Initializable {

    @FXML
    private TextField txt_codigo;
    @FXML
    private TextField txt_nome;
    @FXML
    private PasswordField pass_senha;
    @FXML
    private PasswordField pass_conf_senha;
    /*Preenchendo o ComboBox Manualmente */
    @FXML
    private ComboBox cb_permissao;
    ObservableList<String> listPermissao = FXCollections.observableArrayList("Administrador", "Comun");
    @FXML
    private TableView<FuncionarioModel> tabela_funcionario;
    @FXML
    private TableColumn<FuncionarioModel, String> nomeColuna;
    @FXML
    private TableColumn<FuncionarioModel, String> permissaoColuna;
    @FXML
    private Button bt_novo;
    @FXML
    private Button bt_salvar;
    @FXML
    private Button bt_editar;
    @FXML
    private Button bt_excluir;

    FuncionarioModel funcionarioModel;

    int flag = 1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /**
         * Preenchendo o ComboBox Especialidade
         */
        cb_permissao.setItems(listPermissao);

        /**
         * Carrega todos as colunas da tabela pegando os dados do BD
         */
        this.nomeColuna.setCellValueFactory(cellData -> cellData.getValue().getNomeProperty());
        this.permissaoColuna.setCellValueFactory(cellData -> cellData.getValue().getPermissaoProperty());
    }

    /**
     * Metodo para carregar o TableView da GUI com Thread
     */
    public void carregarTabela() {
        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                return FuncionarioDAO.executeQuery(null, FuncionarioDAO.QUERY_TODOS);
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                tabela_funcionario.setItems((ObservableList<FuncionarioModel>) getValue());
            }
        };
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    /**
     * Método da ação do Botão salvar da GUI do FXML
     */
    @FXML
    private void onSave() {
        /*Verifica se nome esta vazio*/
        if (txt_nome.getText().length() == 0) {
            alert("O campo nome não pode ser vazio");
            return;
        }
        /*Verifica se a senha esta vazia*/
        if (pass_senha.getText().length() == 0) {
            alert("O campo senha não pode ser vazio");
            return;
        }
        this.funcionarioModel = new FuncionarioModel();
        funcionarioModel.setNome(txt_nome.getText().trim());
        funcionarioModel.setSenha(pass_senha.getText().trim());
        funcionarioModel.setPermissao((String) cb_permissao.getSelectionModel().getSelectedItem());
        if (FuncionarioDAO.executeUpdates(funcionarioModel, FuncionarioDAO.CREATE)) {
            limparCampos();
            alert("Dados inseridos com sucesso!");
            carregarTabela();
            desabilitarCampos();
        } else {
            alert("Houve um erro ao inserir Dados");
        }
    }

    /**
     * Metodo criado para acao do botao Excluir
     */
    @FXML
    private void onDelete() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Mensagem");
        alert.setHeaderText("");
        alert.setContentText("Deseja excluir?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            this.funcionarioModel = tabela_funcionario.getItems().get(tabela_funcionario.getSelectionModel().getSelectedIndex());

            if (FuncionarioDAO.executeUpdates(funcionarioModel, FuncionarioDAO.DELETE)) {
                tabela_funcionario.getItems().remove(tabela_funcionario.getSelectionModel().getSelectedIndex());
                alert("Excluido com sucesso!");
                desabilitarCampos();
            } else {
                alert("Não foi possivel excluir dados");
            }
        }
    }

    /**
     * Método ação do Botao Novo
     */
    @FXML
    private void onNew() {
        bt_novo.setDisable(false);
        bt_salvar.setDisable(false);
        bt_editar.setDisable(true);
        bt_excluir.setDisable(true);
        habilitarCampos();
        txt_nome.requestFocus();
        flag = 1;
    }

    /**
     * Método que desativa os botoes editar e excluir
     */
    @FXML
    private void onCancel() {
        /* Desmarca qualquer registro que esteja selecionado na tabela*/
        tabela_funcionario.getSelectionModel().clearSelection();

        desabilitarCampos();
        limparCampos();
        bt_novo.setDisable(false);
        bt_salvar.setDisable(true);
        bt_editar.setDisable(true);
        bt_excluir.setDisable(true);

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
    private void limparCampos() {
        txt_codigo.setText("");
        txt_nome.setText("");
        pass_senha.setText("");
        pass_conf_senha.setText("");
        cb_permissao.getSelectionModel().clearSelection();
    }

    /**
     * Método para que seja acionado os botoes editar e excluir quando a tabela
     * for clickada
     */
    public void onClicked() {
        bt_novo.setDisable(false);
        bt_salvar.setDisable(true);
        bt_editar.setDisable(false);
        bt_excluir.setDisable(false);
    }

    /**
     * Método responsavel por habilitar os campos
     */
    private void habilitarCampos() {
        txt_nome.setDisable(false);
        pass_senha.setDisable(false);
        pass_conf_senha.setDisable(false);
        cb_permissao.setDisable(false);
    }

    /**
     * Método responsavel por desabilitar os campos e botôes
     */
    private void desabilitarCampos() {
        txt_nome.setDisable(true);
        pass_senha.setDisable(true);
        pass_conf_senha.setDisable(true);
        cb_permissao.setDisable(true);
        bt_salvar.setDisable(true);
        bt_editar.setDisable(true);
        bt_excluir.setDisable(true);
    }

}
