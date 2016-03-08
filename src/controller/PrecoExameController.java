package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import model.bean.CategoriaModel;
import model.bean.ExameModel;
import model.bean.ValorExameModel;
import model.dao.CategoriaDAO;
import model.dao.ExameDAO;
import model.dao.ValorExameDAO;
import util.ConverterDados;
import util.DialogFX;

/**
 * FXML Controller class
 *
 * @author jeff-
 */
public class PrecoExameController implements Initializable {

    @FXML
    private TextField txt_codigo;
    @FXML
    private TextField txt_descricao;
    @FXML
    private TextField txt_valor_categoria;
    @FXML
    private TextField txt_valor_exame;
    @FXML
    private TableView<ExameModel> tabela_exame;
    @FXML
    private TableColumn<ExameModel, Integer> codigoColuna;
    @FXML
    private TableColumn<ExameModel, String> exameColuna;
    @FXML
    private ComboBox<CategoriaModel> cb_categoria;

    private ExameModel exameModel;

    private ValorExameModel valorExameModel;

    int flag = 1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /**
         * Carrega todos as colunas da tabela pegando os dados do BD
         */
        this.codigoColuna.setCellValueFactory(cellData -> cellData.getValue().getCodigoProperty().asObject());
        this.exameColuna.setCellValueFactory(cellData -> cellData.getValue().getDescricaoProperty());

        /*Utilizando a nossa Classe converter CategoriaModel*/
        this.cb_categoria.setConverter(new ConverterDados(ConverterDados.GET_CATEGORIA_DESCRICAO).getCategoriaConverter());
    }

    /**
     * Executa as funções iniciais como preencher o comboBox do Bairro
     * utilizando o Task já que pode ser um processo pesado
     */
    public void iniciarProcessos() {
        /*Para evitar uma exception de Thread temos que limpar o comboBox*/
        cb_categoria.getItems().clear();

        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                cb_categoria.setItems(CategoriaDAO.executeQuery(null, CategoriaDAO.QUERY_TODOS));
                return null;
            }
        };
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    /**
     * Metodo para carregar o TableView da GUI com Thread
     */
    public void carregarTabela() {
        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                return ExameDAO.executeQuery(null, ExameDAO.QUERY_TODOS);
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                tabela_exame.setItems((ObservableList<ExameModel>) getValue());
            }
        };
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    /**
     * Método que
     *
     */
    @FXML
    public void clickEdit(TableView<ValorExameModel> tabela) {
        if (tabela.getSelectionModel().getSelectedIndex() != -1) {
            valorExameModel = tabela.getSelectionModel().getSelectedItem();
            this.exameModel = new ExameModel();
            txt_codigo.setText(Integer.toString(valorExameModel.getCodigo()));
            //txt_descricao.setText(valorExameModel.getDescricao());
        }
    }

    @FXML
    private void onSave(ActionEvent event) {
        if (txt_valor_categoria.getText().isEmpty()) {
            DialogFX.showMessage("O valor de categoria não pode ser vazio!", "Campo Vazio", DialogFX.ATENCAO);
            return;
        } else if (txt_valor_exame.getText().isEmpty()) {
            DialogFX.showMessage("O valor de exame não pode ser vazio!", "Campo Vazio", DialogFX.ATENCAO);
            return;
        }
        if (this.cb_categoria.getSelectionModel().getSelectedIndex() != -1
                && tabela_exame.getSelectionModel().getSelectedIndex() != -1) {
            this.exameModel = new ExameModel();
            exameModel.setCodigo(Integer.valueOf(txt_codigo.getText().trim()));
            exameModel.setDescricao(txt_descricao.getText().trim());

            this.valorExameModel = new ValorExameModel();
            valorExameModel.setExameModel(exameModel);
            valorExameModel.setValor_categoria(Double.valueOf(txt_valor_categoria.getText().trim()));
            valorExameModel.setValor_exame(Double.valueOf(txt_valor_exame.getText().trim()));
            CategoriaModel categoria = cb_categoria.getSelectionModel().getSelectedItem();
            valorExameModel.setCategoriaModel(categoria);
            if (ValorExameDAO.executeUpdates(valorExameModel, ValorExameDAO.CREATE)) {
                ((Node) event.getSource()).getScene().getWindow().hide();
                limparCampos();
                DialogFX.showMessage("Preço inserido com sucesso!", "Sucesso", DialogFX.SUCESS);
            }
        } else {
            DialogFX.showMessage("Por favor verifique se você selecionou uma categoria ou exame.", "Atenção", DialogFX.ATENCAO);
        }
    }

    /**
     * Método que limpa os campos
     */
    private void limparCampos() {
        txt_codigo.setText("");
        txt_descricao.setText("");
        cb_categoria.getSelectionModel().clearSelection();
        txt_valor_categoria.setText("");
        txt_valor_exame.setText("");
    }

    /**
     * Método para ação do botão cancelar
     */
    @FXML
    private void onCancel(ActionEvent event) {
        limparCampos();
        ((Node) event.getSource()).getScene().getWindow().hide();
    }
}
