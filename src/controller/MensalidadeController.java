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
import model.bean.MensalidadeModel;
import model.bean.PacienteModel;
import model.dao.MensalidadeDAO;
import model.dao.PacienteDAO;

/**
 * FXML Controller class
 *
 * @author jeff-
 */
public class MensalidadeController implements Initializable {

    @FXML
    private TextField txt_paciente;
    @FXML
    private TableView<PacienteModel> tabela_paciente;
    @FXML
    private TableColumn<PacienteModel, Integer> pacienteCodigoColuna;
    @FXML
    private TableColumn<PacienteModel, String> pacienteColuna;
    @FXML
    private TableView<MensalidadeModel> tabela_mensalidade;
    @FXML
    private TableColumn<MensalidadeModel, Integer> codigoColuna;
    @FXML
    private TableColumn<MensalidadeModel, String> mesColuna;
    @FXML
    private TableColumn<MensalidadeModel, String> dataColuna;
    @FXML
    private TableColumn<MensalidadeModel, Double> descontoColuna;
    @FXML
    private TableColumn<MensalidadeModel, Double> valorColuna;
    @FXML
    private Button bt_salvar;

    private HomeController homeController;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /**
         * Carregar os dados da TableView com os dados do BD
         */
        this.codigoColuna.setCellValueFactory(cellData -> cellData.getValue().getCodigoProperty().asObject());
        this.mesColuna.setCellValueFactory(cellData -> cellData.getValue().getMesProperty());
        this.dataColuna.setCellValueFactory(cellData -> cellData.getValue().getData_pagtoProperty());
        this.descontoColuna.setCellValueFactory(cellData -> cellData.getValue().getDescontoProperty().asObject());
        this.valorColuna.setCellValueFactory(cellData -> cellData.getValue().getValorProperty().asObject());

        /**
         * Carregar os dados do Paciente na TableView
         */
        this.pacienteCodigoColuna.setCellValueFactory(cellData -> cellData.getValue().getCodigoProperty().asObject());
        this.pacienteColuna.setCellValueFactory(cellData -> cellData.getValue().getNomeProperty());
    }

    /**
     * Método que carrega a tabela Mensalidade
     */
    public void carregarTabela() {
        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                return MensalidadeDAO.executeQuery(null, MensalidadeDAO.QUERY_TODOS);
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                tabela_mensalidade.setItems((ObservableList<MensalidadeModel>) getValue());
            }
        };
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    /**
     * Método que carrega a tabela Paciente
     *
     * Eu fiz 2 task diferente pois a Table de Paciente vai ficar fixa, mas o
     * povoamento dos dados da TableView de Mensalidade vai ser preenchido
     * conforme for selecionado o paciente.
     */
    public void carregarTabela2() {
        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                return PacienteDAO.executeQuery(null, PacienteDAO.QUERY_TODOS);
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                tabela_paciente.setItems((ObservableList<PacienteModel>) getValue());
            }
        };
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    /**
     * Ação para chamar a GUI GerarMensalidade
     */
    @FXML
    private void gerarCobranca() {
        this.homeController.gerarMensalidade();
    }

    /**
     * Método que pega a referencia de HomeController.
     *
     * @param homeController
     */
    public void pegarHomeReferencia(HomeController homeController) {
        this.homeController = homeController;
    }

}
