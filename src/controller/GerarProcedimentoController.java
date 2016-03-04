package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.bean.ProcedimentosModel;
import model.dao.ProcedimentoDAO;

/**
 * FXML Controller class
 *
 * @author jeff-
 */
public class GerarProcedimentoController implements Initializable {

    @FXML
    private TableView<ProcedimentosModel> tabela_procedimento;
    @FXML
    private TableColumn<ProcedimentosModel, Integer> codigoColuna;
    @FXML
    private TableColumn<ProcedimentosModel, String> procedimentoColuna;

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

}
