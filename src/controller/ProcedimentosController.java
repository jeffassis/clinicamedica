package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import model.bean.CidadeModel;
import model.bean.ProcedimentosModel;
import model.dao.CidadeDAO;
import model.dao.ProcedimentoDAO;
import util.DialogFX;

/**
 * FXML Controller class
 *
 * @author jeff-
 */
public class ProcedimentosController implements Initializable {

    @FXML
    private TextField txt_codigo;
    @FXML
    private TextField txt_descricao;
    @FXML
    private TableView<ProcedimentosModel> tabela_procedimento;
    @FXML
    private TableColumn<ProcedimentosModel, Integer> codigoColuna;
    @FXML
    private TableColumn<ProcedimentosModel, String> procedimentoColuna;
    @FXML
    private Button bt_novo;
    @FXML
    private Button bt_salvar;
    @FXML
    private Button bt_editar;
    @FXML
    private Button bt_excluir;

    ProcedimentosModel procedimentosModel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /**
         * Carrega todos as colunas da tabela pegando os dados do BD
         */
        this.codigoColuna.setCellValueFactory(cellData -> cellData.getValue().getCodigoProperty().asObject());
        this.procedimentoColuna.setCellValueFactory(cellData -> cellData.getValue().getDescricaoProperty());
    }

    /**
     * Metodo para carregar o TableView da GUI com Thread
     */
    public void carregarTabela() {
        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                return ProcedimentoDAO.executeQuery(null, ProcedimentoDAO.QUERY_TODOS);
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                tabela_procedimento.setItems((ObservableList<ProcedimentosModel>) getValue());
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
        procedimentosModel = new ProcedimentosModel();
        procedimentosModel.setDescricao(txt_descricao.getText().trim());
        if (ProcedimentoDAO.executeUpdates(procedimentosModel, ProcedimentoDAO.CREATE)) {
            DialogFX.showMessage("Dados inseridos com sucesso!", "Sucesso", DialogFX.SUCESS);
            limparCampos();
            carregarTabela();
            desabilitarCampos();
        }
    }

    /**
     * Método para ação do botão editar
     *
     */
    @FXML
    private void onEdit() {

    }

    /**
     * Metodo criado para acao do botao Excluir
     */
    @FXML
    private void onDelete() {
        if (DialogFX.showConfirmation("Deseja Excluir ?")) {
            this.procedimentosModel = tabela_procedimento.getItems().get(tabela_procedimento.getSelectionModel().getSelectedIndex());

            if (ProcedimentoDAO.executeUpdates(procedimentosModel, ProcedimentoDAO.DELETE)) {
                tabela_procedimento.getItems().remove(tabela_procedimento.getSelectionModel().getSelectedIndex());
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
        txt_descricao.requestFocus();
    }

    /**
     * Método que desativa os botoes editar e excluir
     */
    @FXML
    private void onCancel() {
        /* Desmarca qualquer registro que esteja selecionado na tabela*/
        tabela_procedimento.getSelectionModel().clearSelection();

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
        txt_descricao.setText("");
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
        txt_descricao.setDisable(true);
    }

    /**
     * Método responsavel por habilitar os campos
     */
    private void habilitarCampos() {
        txt_descricao.setDisable(false);
    }

    /**
     * Método responsavel por desabilitar os campos e botôes
     */
    private void desabilitarCampos() {
        txt_descricao.setDisable(true);
        bt_salvar.setDisable(true);
        bt_editar.setDisable(true);
        bt_excluir.setDisable(true);
    }
}
