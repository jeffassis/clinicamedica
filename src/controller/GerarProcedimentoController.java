package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.bean.ExameModel;
import model.dao.ExameDAO;

/**
 * FXML Controller class
 *
 * @author jeff-
 */
public class GerarProcedimentoController implements Initializable {

    @FXML
    private TableView<ExameModel> tabela_procedimento;
    @FXML
    private TableColumn<ExameModel, Integer> codigoColuna;
    @FXML
    private TableColumn<ExameModel, String> procedimentoColuna;

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
                return ExameDAO.executeQuery(null, ExameDAO.QUERY_TODOS);
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                tabela_procedimento.setItems((ObservableList<ExameModel>) getValue());
            }
        };
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

}
