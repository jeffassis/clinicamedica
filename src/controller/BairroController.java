package controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
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
import model.bean.BairroModel;
import model.bean.CidadeModel;
import model.dao.BairroDAO;
import model.dao.CidadeDAO;
import util.ConverterDados;
import util.DialogFX;

/**
 * FXML Controller class
 *
 * @author jeff-
 */
public class BairroController implements Initializable {

    @FXML
    private TextField txt_codigo;
    @FXML
    private TextField txt_nome;
    @FXML
    private ComboBox<CidadeModel> cb_cidade;
    @FXML
    private TableView<BairroModel> tabela_bairro;
    @FXML
    private TableColumn<BairroModel, Integer> codigoColuna;
    @FXML
    private TableColumn<BairroModel, String> nomeColuna;
    @FXML
    private TableColumn<BairroModel, String> cidadeColuna;
    @FXML
    private Button bt_novo;
    @FXML
    private Button bt_salvar;
    @FXML
    private Button bt_excluir;

    BairroModel bairroModel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /**
         * Carrega todos as colunas da tabela pegando os dados do BD
         */
        this.codigoColuna.setCellValueFactory(cellData -> cellData.getValue().getCodigoProperty().asObject());
        this.nomeColuna.setCellValueFactory(cellData -> cellData.getValue().getNomeProperty());
        this.cidadeColuna.setCellValueFactory(cellData -> cellData.getValue().getCidadeModel().getNomeProperty());

        /*Utilizando a nossa Classe converter BairroModel*/
        this.cb_cidade.setConverter(new ConverterDados(ConverterDados.GET_CIDADE_NOME).getCidadeConverter());
    }

    /**
     * Metodo para carregar o TableView da GUI com Thread
     */
    public void carregarTabela() {
        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                return BairroDAO.executeQuery(null, BairroDAO.QUERY_TODOS);

            }

            @Override
            protected void succeeded() {
                super.succeeded();
                tabela_bairro.setItems((ObservableList<BairroModel>) getValue());
            }
        };
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    /**
     * Executa as funções iniciais como preencher o comboBox do Bairro
     * utilizando o Task já que pode ser um processo pesado
     */
    public void iniciarProcessos() {
        /*Para evitar uma exception de Thread temos que limpar o comboBox*/
        cb_cidade.getItems().clear();

        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                cb_cidade.setItems(CidadeDAO.executeQuery(null, CidadeDAO.QUERY_TODOS));
                return null;
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
        /*Verifica se nome esta vazia*/
        if (txt_nome.getText().length() == 0) {
            DialogFX.showMessage("O campo nome não pode ser vazio", "Atenção", DialogFX.ATENCAO);
            return;
        }
        this.bairroModel = new BairroModel();
        bairroModel.setNome(txt_nome.getText().trim());
        CidadeModel cidade = cb_cidade.getSelectionModel().getSelectedItem();
        bairroModel.setCidadeModel(cidade);
        if (BairroDAO.executeUpdates(bairroModel, BairroDAO.CREATE)) {
            limparCampos();
            DialogFX.showMessage("Dados inseridos com sucesso!", "Sucesso", DialogFX.SUCESS);
            carregarTabela();
            desabilitarCampos();
        } else {
            DialogFX.showMessage("Houve um erro ao inserir Dados", "ERRO", DialogFX.ERRO);
        }
    }

    /**
     * Metodo criado para acao do botao Excluir
     */
    @FXML
    private void onDelete() {
        if (DialogFX.showConfirmation("Deseja Excluir?")) {
            this.bairroModel = tabela_bairro.getItems().get(tabela_bairro.getSelectionModel().getSelectedIndex());

            if (BairroDAO.executeUpdates(bairroModel, BairroDAO.DELETE)) {
                tabela_bairro.getItems().remove(tabela_bairro.getSelectionModel().getSelectedIndex());
                DialogFX.showMessage("Dados excluidos com sucesso!", "Sucesso", DialogFX.SUCESS);
                desabilitarCampos();
            } else {
                DialogFX.showMessage("Houve um erro ao excluir Dados", "ERRO", DialogFX.ERRO);
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
        bt_excluir.setDisable(true);
        habilitarCampos();
        txt_nome.requestFocus();
    }

    /**
     * Método que desativa os botoes editar e excluir
     */
    @FXML
    private void onCancel() {
        /* Desmarca qualquer registro que esteja selecionado na tabela*/
        tabela_bairro.getSelectionModel().clearSelection();

        desabilitarCampos();
        limparCampos();
        bt_novo.setDisable(false);
        bt_salvar.setDisable(true);
        bt_excluir.setDisable(true);
    }

    /**
     * Método que limpa os campos
     */
    private void limparCampos() {
        txt_codigo.setText("");
        txt_nome.setText("");
        cb_cidade.getSelectionModel().clearSelection();
    }

    /**
     * Método para que seja acionado os botoes editar e excluir quando a tabela
     * for clickada
     */
    public void onClicked() {
        bt_novo.setDisable(false);
        bt_salvar.setDisable(true);
        bt_excluir.setDisable(false);
        limparCampos();
        txt_nome.setDisable(true);
        cb_cidade.setDisable(true);
    }

    /**
     * Método responsavel por habilitar os campos
     */
    private void habilitarCampos() {
        txt_nome.setDisable(false);
        cb_cidade.setDisable(false);
    }

    /**
     * Método responsavel por desabilitar os campos e botôes
     */
    private void desabilitarCampos() {
        txt_nome.setDisable(true);
        cb_cidade.setDisable(true);
        bt_salvar.setDisable(true);
        bt_excluir.setDisable(true);
    }
}
