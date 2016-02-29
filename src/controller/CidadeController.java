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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import model.bean.CidadeModel;
import model.dao.CidadeDAO;
import util.DialogFX;

/**
 * FXML Controller class
 *
 * @author jeff-
 */
public class CidadeController implements Initializable {

    @FXML
    private TextField txt_codigo;
    @FXML
    private TextField txt_nome;
    @FXML
    private ComboBox cb_sigla;
    ObservableList<String> listaSigla = FXCollections.observableArrayList("RJ", "SP");
    @FXML
    private TableView<CidadeModel> tabela_cidade;
    @FXML
    private TableColumn<CidadeModel, Integer> codigoColuna;
    @FXML
    private TableColumn<CidadeModel, String> nomeColuna;
    @FXML
    private TableColumn<CidadeModel, String> siglaColuna;
    @FXML
    private Button bt_novo;
    @FXML
    private Button bt_salvar;
    @FXML
    private Button bt_editar;
    @FXML
    private Button bt_excluir;

    CidadeModel cidadeModel;

    int flag = 1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /**
         * Preenchendo o ComboBox Sigla
         */
        cb_sigla.setItems(listaSigla);

        /**
         * Carrega todos as colunas da tabela pegando os dados do BD
         */
        this.codigoColuna.setCellValueFactory(cellData -> cellData.getValue().getCodigoProperty().asObject());
        this.nomeColuna.setCellValueFactory(cellData -> cellData.getValue().getNomeProperty());
        this.siglaColuna.setCellValueFactory(cellData -> cellData.getValue().getSiglaProperty());
    }

    /**
     * Metodo para carregar o TableView da GUI com Thread
     */
    public void carregarTabela() {
        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                return CidadeDAO.executeQuery(null, CidadeDAO.QUERY_TODOS);
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                tabela_cidade.setItems((ObservableList<CidadeModel>) getValue());
            }
        };
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    /**
     * Método da ação do Botão salvar da GUI do FXMl
     */
    @FXML
    private void onSave() {
        if (flag == 1) {
            /*Verifica se nome esta vazia*/
            /*Trocamos o length por isEmpty, pois o isEmpty verifica se está vázio*/
            if (txt_nome.getText().isEmpty()) {
                DialogFX.showMessage("O campo não pode ser vazio!");
                return;
            }
            this.cidadeModel = new CidadeModel();
            cidadeModel.setNome(txt_nome.getText().trim());
            cidadeModel.setSigla((String) cb_sigla.getSelectionModel().getSelectedItem());
            if (CidadeDAO.executeUpdates(cidadeModel, CidadeDAO.CREATE)) {
                limparCampos();
                alert("Dados inseridos com sucesso!");
                carregarTabela();
                desabilitarCampos();
            } else {
                alert("Houve um erro ao inserir Dados");
            }
        } else {
            /*Se a flag for 2 edita os dados do banco de dados*/
            this.cidadeModel = new CidadeModel();
            cidadeModel.setCodigo(Integer.parseInt(txt_codigo.getText().trim()));
            cidadeModel.setNome(txt_nome.getText().trim());
            cidadeModel.setSigla((String) cb_sigla.getSelectionModel().getSelectedItem());
            if (CidadeDAO.executeUpdates(cidadeModel, CidadeDAO.UPDATE)) {
                limparCampos();
                alert("Dados Atualizados com sucesso!");
                carregarTabela();
                flag = 1;
                desabilitarCampos();
            } else {
                alert("Não foi possivel atualizar dados");
            }
        }
    }

    /**
     * Método para ação do botão editar
     *
     */
    @FXML
    private void onEdit() {
        /*Verificamos se a tabela foi selecionada*/
        if (tabela_cidade.getSelectionModel().getSelectedIndex() != -1) {
            /*Habilito o botão salvar*/
            this.bt_salvar.setDisable(false);
            this.cidadeModel = tabela_cidade.getSelectionModel().getSelectedItem();
            txt_codigo.setText(Integer.toString(cidadeModel.getCodigo()));
            txt_nome.setText(cidadeModel.getNome());
            /*Utilizamos o for para descobrir qual posição é igual ao dado retornado
             de especialidade*/
            for (int i = 0; i < cb_sigla.getItems().size(); i++) {
                if (((String) cb_sigla.getItems().get(i)).equals(cidadeModel.getSigla())) {
                    cb_sigla.getSelectionModel().select(i);
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
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Mensagem");
        alert.setHeaderText("");
        alert.setContentText("Deseja excluir?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            this.cidadeModel = tabela_cidade.getItems().get(tabela_cidade.getSelectionModel().getSelectedIndex());

            if (CidadeDAO.executeUpdates(cidadeModel, CidadeDAO.DELETE)) {
                tabela_cidade.getItems().remove(tabela_cidade.getSelectionModel().getSelectedIndex());
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
        tabela_cidade.getSelectionModel().clearSelection();

        desabilitarCampos();
        limparCampos();
        bt_novo.setDisable(false);
        bt_salvar.setDisable(true);
        bt_editar.setDisable(true);
        bt_excluir.setDisable(true);
    }

    /**
     * Método que cria as Janelas de Dialog com Informação para usuario
     * @deprecated Está sendo substituido pelo DialogFX.
     * @see DialogFX
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
        cb_sigla.getSelectionModel().clearSelection();
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
        cb_sigla.setDisable(true);
    }

    /**
     * Método responsavel por habilitar os campos
     */
    private void habilitarCampos() {
        txt_nome.setDisable(false);
        cb_sigla.setDisable(false);
    }

    /**
     * Método responsavel por desabilitar os campos e botôes
     */
    private void desabilitarCampos() {
        txt_nome.setDisable(true);
        cb_sigla.setDisable(true);
        bt_salvar.setDisable(true);
        bt_editar.setDisable(true);
        bt_excluir.setDisable(true);
    }
}
