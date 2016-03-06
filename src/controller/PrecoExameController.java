package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import model.bean.CategoriaModel;
import model.bean.ExameModel;
import model.dao.CategoriaDAO;
import model.dao.ExameDAO;
import util.ConverterDados;

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
    private TableView<ExameModel> tabela_exame;
    @FXML
    private TableColumn<ExameModel, Integer> codigoColuna;
    @FXML
    private TableColumn<ExameModel, String> exameColuna;
    @FXML
    private ComboBox<CategoriaModel> cb_categoria;

    private ExameModel exameModel;

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

    @FXML
    private void clickEdit() {
        if (tabela_exame.getSelectionModel().getSelectedIndex() != -1) {
            exameModel = tabela_exame.getSelectionModel().getSelectedItem();
            txt_codigo.setText(Integer.toString(exameModel.getCodigo()));
            txt_descricao.setText(exameModel.getDescricao());
        }
    }

}
