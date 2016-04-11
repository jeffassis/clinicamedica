package controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javax.swing.text.TabableView;
import model.bean.PacienteModel;
import model.dao.PacienteDAO;
import util.DialogFX;
import util.Funcionalidades;

/**
 * FXML Controller class
 *
 * @author jeff-
 */
public class MeusPacientesController extends Funcionalidades implements Initializable {

    @FXML
    private TableView<PacienteModel> tabela_paciente;
    @FXML
    private TableColumn<PacienteModel, String> nomeColuna;
    @FXML
    private TableColumn<PacienteModel, Integer> codigoColuna;
    @FXML
    private TableColumn<PacienteModel, String> enderecoColuna;
    @FXML
    private TableColumn<PacienteModel, String> telefoneColuna;
    @FXML
    private TableColumn<PacienteModel, String> emailColuna;
    /*Vamos precisar dele para chamar a tela paciente para editar os dados*/
    private HomeController homeController;
    @FXML
    private MenuItem mnAtivo, mnInativo;
    private ObservableList<PacienteModel> listaDePacientes;
    private SortedList<PacienteModel> listaDeAtivos;
    private SortedList<PacienteModel> listaDeInativos;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /**
         * Implementação da TableView
         */
        this.codigoColuna.setCellValueFactory(cellData -> cellData.getValue().getCodigoProperty().asObject());
        this.nomeColuna.setCellValueFactory(cellData -> cellData.getValue().getNomeProperty());
        this.enderecoColuna.setCellValueFactory(cellData -> cellData.getValue().getEnderecoProperty());
        this.telefoneColuna.setCellValueFactory(cellData -> cellData.getValue().getTelefoneProperty());
        this.emailColuna.setCellValueFactory(cellData -> cellData.getValue().getEmailProperty());
    }

    @Override
    public void carregarTabela() {
        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                listaDePacientes = PacienteDAO.executeQuery(null, PacienteDAO.QUERY_TODOS);
                return null;
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                tabela_paciente.setItems(listaDePacientes);
                /*Filtro de pacientes ativos, passamos como parametro uma lista de pacientes e no segundo fazemos a condição*/
                FilteredList<PacienteModel> filtroDeAtivos = new FilteredList<>(listaDePacientes, paciente -> paciente.getStatus());
                FilteredList<PacienteModel> filtroDeInativos = new FilteredList<>(listaDePacientes, paciente -> paciente.getStatus() == false);
                
                /*Adicionamos o dados a essa lista, passando o filtro ou seja ela so terar os dados de acordo com o filtro*/
                listaDeAtivos = new SortedList<>(filtroDeAtivos);
                listaDeInativos = new SortedList<>(filtroDeInativos);
            }
        };
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }
    
    /**
     * Método que pega a referencia de HomeController.
     * @param referencia 
     */
    @Override
    public void pegarReferencia(Object referencia) {
        super.pegarReferencia(referencia);
        this.homeController = (HomeController) referencia;
    }

    @FXML
    private void onMouseClick(MouseEvent evento) {
        if (tabela_paciente.getSelectionModel().getSelectedIndex() != -1) {
            if (evento.getClickCount() == 2) {
                /*Mandamos abrir a Tela paciente para editar*/
                this.homeController.cadPaciente(true, tabela_paciente);
            }
        } else {
            DialogFX.showMessage("Por favor selecione um Paciente!", "Atenção", DialogFX.ATENCAO);
        }
    }

    /**
     * Atualiza a tabela com os pacientes
     */
    @FXML
    private void onRefresh() {
        carregarTabela();
        tabela_paciente.getSelectionModel().clearSelection();
    }

    /**
     * Evento do menuItem ativo.
     */
    @FXML
    private void mnAtivoAction() {
        if(listaDeAtivos != null){
            tabela_paciente.setItems(listaDeAtivos);
        }
    }

    /**
     * Evento do menuItem inativo.
     */
    @FXML
    private void mnInativoAction() {
       if(listaDeInativos != null){
           tabela_paciente.setItems(listaDeInativos);
       }
    }
}
