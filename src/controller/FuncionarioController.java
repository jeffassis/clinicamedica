package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import model.bean.FuncionarioModel;
import model.dao.FuncionarioDAO;
import util.DialogFX;

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
        /*Verifica a flag. Se for 1 ela salva dados*/
        if (flag == 1) {
            /*Verifica se nome esta vazio*/
            if (txt_nome.getText().isEmpty()) {
                DialogFX.showMessage("O campo nome não pode ser vazio", "Atenção", DialogFX.ATENCAO);
                return;
            }
            /*Verifica se a senha sao iguais*/
            if (pass_senha.getText().equals(pass_conf_senha.getText())) {
                /*Verifica se a senha esta vazio*/
                if (pass_senha.getText().isEmpty()) {
                    DialogFX.showMessage("O campos senha não pode ser vazio!", "Atenção", DialogFX.ATENCAO);
                    return;
                }
                this.funcionarioModel = new FuncionarioModel();
                funcionarioModel.setNome(txt_nome.getText().trim());
                funcionarioModel.setSenha(pass_senha.getText().trim());
                funcionarioModel.setPermissao((String) cb_permissao.getSelectionModel().getSelectedItem());
                if (FuncionarioDAO.executeUpdates(funcionarioModel, FuncionarioDAO.CREATE)) {
                    limparCampos();
                    DialogFX.showMessage("Dados inseridos com sucesso!", "Sucesso", DialogFX.SUCESS);
                    carregarTabela();
                    desabilitarCampos();
                } else {
                    DialogFX.showMessage("Houve um erro ao inserir Dados!", "Erro", DialogFX.ERRO);
                }
            } else {
                DialogFX.showMessage("As senhas não correspondem", "Erro", DialogFX.ERRO);
            }
        } else if (pass_senha.getText().equals(pass_conf_senha.getText())) {
            /*Se a flag for 2 edita os dados do banco de dados*/
            this.funcionarioModel = new FuncionarioModel();
            funcionarioModel.setCodigo(Integer.parseInt(txt_codigo.getText().trim()));
            funcionarioModel.setNome(txt_nome.getText().trim());
            funcionarioModel.setSenha(pass_senha.getText().trim());
            funcionarioModel.setPermissao((String) cb_permissao.getSelectionModel().getSelectedItem());
            if (FuncionarioDAO.executeUpdates(funcionarioModel, FuncionarioDAO.UPDATE)) {
                limparCampos();
                DialogFX.showMessage("Dados Atualizados com sucesso!", "Sucesso", DialogFX.SUCESS);
                carregarTabela();
                flag = 1;
                desabilitarCampos();
            } else {
                DialogFX.showMessage("Não foi possivel atualizar dados!", "Erro", DialogFX.ERRO);
            }
        } else {
            DialogFX.showMessage("As senhas não correspondem", "Erro", DialogFX.ERRO);
        }
    }

    /**
     * Método para ação do botão editar
     *
     */
    @FXML
    private void onEdit() {
        /*Verificamos se a tabela foi selecionada*/
        if (tabela_funcionario.getSelectionModel().getSelectedIndex() != -1) {
            /*Habilito o botão salvar*/
            this.bt_salvar.setDisable(false);
            this.funcionarioModel = tabela_funcionario.getSelectionModel().getSelectedItem();
            txt_codigo.setText(Integer.toString(funcionarioModel.getCodigo()));
            txt_nome.setText(funcionarioModel.getNome());
            pass_senha.setText(funcionarioModel.getSenha());
            /*Utilizamos o for para descobrir qual posição é igual ao dado retornado
             de especialidade*/
            for (int i = 0; i < cb_permissao.getItems().size(); i++) {
                if (((String) cb_permissao.getItems().get(i)).equals(funcionarioModel.getPermissao())) {
                    cb_permissao.getSelectionModel().select(i);
                    /*agora q ja achamos paramos o for*/
                    break;
                }
            }
            flag = 2;
            /*Desabilita os botões excluir e editar */
            this.bt_editar.setDisable(true);
            this.bt_excluir.setDisable(true);
            habilitarCampos();
        }
    }

    /**
     * Metodo criado para acao do botao Excluir
     */
    @FXML
    private void onDelete() {
        if (DialogFX.showConfirmation("Deseja Excluir ?")) {
            this.funcionarioModel = tabela_funcionario.getItems().get(tabela_funcionario.getSelectionModel().getSelectedIndex());

            if (FuncionarioDAO.executeUpdates(funcionarioModel, FuncionarioDAO.DELETE)) {
                tabela_funcionario.getItems().remove(tabela_funcionario.getSelectionModel().getSelectedIndex());
                DialogFX.showMessage("Excluido com sucesso", "Sucesso", DialogFX.SUCESS);
                desabilitarCampos();
            } else {
                DialogFX.showMessage("Não foi possivel excluir dados", "ERRO", DialogFX.ERRO);
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
        limparCampos();
        txt_nome.setDisable(true);
        pass_conf_senha.setDisable(true);
        pass_senha.setDisable(true);
        cb_permissao.setDisable(true);
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
