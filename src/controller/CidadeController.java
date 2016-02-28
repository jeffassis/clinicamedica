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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import model.bean.CidadeModel;
import model.bean.MedicoModel;
import model.dao.CidadeDAO;
import model.dao.MedicoDAO;

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

    }
}
