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
import model.bean.CategoriaModel;
import model.dao.CategoriaDAO;
import util.DialogFX;
import util.Funcionalidades;

/**
 * FXML Controller class
 *
 * @author jeff-
 */
public class CategoriasController extends Funcionalidades implements Initializable {

    @FXML
    private TextField txt_codigo;
    @FXML
    private TextField txt_descricao;
    @FXML
    private TableView<CategoriaModel> tabela_categoria;
    @FXML
    private TableColumn<CategoriaModel, Integer> codigoColuna;
    @FXML
    private TableColumn<CategoriaModel, String> descricaoColuna;
    @FXML
    private Button bt_novo;
    @FXML
    private Button bt_salvar;
    @FXML
    private Button bt_editar;
    @FXML
    private Button bt_excluir;

    CategoriaModel categoriaModel;

    int flag = 1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /**
         * Populando TableView
         */
        this.codigoColuna.setCellValueFactory(cellData -> cellData.getValue().getCodigoProperty().asObject());
        this.descricaoColuna.setCellValueFactory(cellData -> cellData.getValue().getDescricaoProperty());

    }

    /**
     * Metodo para carregar o TableView da GUI com Thread
     */
    @Override
    public void carregarTabela() {
        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                return CategoriaDAO.executeQuery(null, CategoriaDAO.QUERY_TODOS);
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                tabela_categoria.setItems((ObservableList<CategoriaModel>) getValue());
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
        if (txt_descricao.getText().isEmpty()) {
            DialogFX.showMessage("O campo descrição não pode ser vazio!", "Campo vazio", DialogFX.ATENCAO);
            return;
        }
        if (flag == 1) {
            /*Salva os dados de categoria*/
            categoriaModel = new CategoriaModel();
            categoriaModel.setDescricao(txt_descricao.getText().trim());
            if (CategoriaDAO.executeUpdates(categoriaModel, CategoriaDAO.CREATE)) {
                DialogFX.showMessage("Dados inseridos com sucesso!", "Sucesso", DialogFX.SUCESS);
                limparCampos();
                carregarTabela();
                desabilitarCampos();
            } else {
                DialogFX.showMessage("Não foi possivel inserir dados", "ERROR", DialogFX.ERRO);
            }
        } else {
            /*Edita os dados de categoria*/
            categoriaModel = new CategoriaModel();
            categoriaModel.setCodigo(Integer.parseInt(txt_codigo.getText().trim()));
            categoriaModel.setDescricao(txt_descricao.getText().trim());
            if (CategoriaDAO.executeUpdates(categoriaModel, CategoriaDAO.UPDATE)) {
                limparCampos();
                DialogFX.showMessage("Dados Atualizados com sucesso!", "Sucesso", DialogFX.SUCESS);
                carregarTabela();
                flag = 1;
                desabilitarCampos();
            } else {
                DialogFX.showMessage("Não foi possivel atualizar dados", "ERROR", DialogFX.ERRO);
            }
        }
    }

    /**
     * Método para ação do botão editar
     *
     */
    @FXML
    private void onEdit() {
        if (tabela_categoria.getSelectionModel().getSelectedIndex() != -1) {
            this.bt_salvar.setDisable(false);
            this.categoriaModel = tabela_categoria.getSelectionModel().getSelectedItem();
            txt_codigo.setText(Integer.toString(categoriaModel.getCodigo()));
            txt_descricao.setText(categoriaModel.getDescricao());
        }
        flag = 2;
        /*Desabilita os botões excluir e editar */
        this.bt_editar.setDisable(true);
        this.bt_excluir.setDisable(true);
        habilitarCampos();
    }

    /**
     * Metodo criado para acao do botao Excluir
     */
    @FXML
    private void onDelete() {
        if (DialogFX.showConfirmation("Deseja Excluir ?")) {
            this.categoriaModel = tabela_categoria.getItems().get(tabela_categoria.getSelectionModel().getSelectedIndex());

            if (CategoriaDAO.executeUpdates(categoriaModel, CategoriaDAO.DELETE)) {
                tabela_categoria.getItems().remove(tabela_categoria.getSelectionModel().getSelectedIndex());
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
        flag = 1;
        txt_descricao.requestFocus();
    }

    /**
     * Método que desativa os botoes editar e excluir
     */
    @FXML
    public void onCancel() {
        /* Desmarca qualquer registro que esteja selecionado na tabela*/
        tabela_categoria.getSelectionModel().clearSelection();

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
