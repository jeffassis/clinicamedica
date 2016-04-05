package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.BooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.EventDispatchChain;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.util.Callback;
import model.bean.MensalidadeModel;
import model.bean.PacienteModel;
import model.dao.MensalidadeDAO;
import model.dao.PacienteDAO;
import util.CustomCheckBoxTable;
import util.DialogFX;

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
    private TableColumn<MensalidadeModel, Boolean> pagoColuna;
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
        this.pagoColuna.setCellValueFactory(cellData -> cellData.getValue().getStatusProperty());

        /*Veja como ficou simples*/
        pagoColuna.setCellFactory(coluna -> new CustomCheckBoxTable<>(coluna));
        /*Botão salvar começa desativado*/
        this.bt_salvar.setDisable(true);
        /*Evento na TableView utilizando a property acontece até quando a Tabela e populado ou dando um clear*/
        tabela_mensalidade.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (tabela_mensalidade.getSelectionModel().getSelectedIndex() == -1) {
                this.bt_salvar.setDisable(true);
            } else {
                this.bt_salvar.setDisable(false);
            }
        });

        /**
         * Carregar os dados do Paciente na TableView
         */
        this.pacienteCodigoColuna.setCellValueFactory(cellData -> cellData.getValue().getCodigoProperty().asObject());
        this.pacienteColuna.setCellValueFactory(cellData -> cellData.getValue().getNomeProperty());
    }

    /**
     * Método que carrega a tabela Mensalidade
     */
    public void preencheTabela() {
        PacienteModel paciente = tabela_paciente.getSelectionModel().getSelectedItem();

        MensalidadeModel mm = new MensalidadeModel(paciente);

        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                return MensalidadeDAO.executeQuery(mm, MensalidadeDAO.QUERY_PACIENTE);
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

    /**
     * Evento do clique no CheckBox da Coluna.
     *
     * @param box
     * @param customCheck
     * @deprecated - Não vai ser mais utilizado.
     */
    private void checkBoxOnAction(CheckBox box, CustomCheckBoxTable customCheck) {
        /*Falta implementar*/
        System.out.println("Situação do Combo: " + box.isSelected());
        System.out.println("Linha selecionada:" + customCheck.getIndex());
    }

    /**
     * Evento do botão salvar.
     */
    @FXML
    private void onSave() {
        boolean valorDaColuna = pagoColuna.getCellData(tabela_mensalidade.getSelectionModel().getSelectedIndex());
        MensalidadeModel mensalidade = tabela_mensalidade.getSelectionModel().getSelectedItem();
        mensalidade.setStatus(valorDaColuna);
        if (MensalidadeDAO.executeUpdates(mensalidade, MensalidadeDAO.UPDATE_STATUS)) {
            DialogFX.showMessage("Status da mensalidade alterada com sucesso!", "Sucesso!", DialogFX.SUCESS);
        } else {
            DialogFX.showMessage("Houve um erro ao alterar os status da mensalidade", "Erro!", DialogFX.ERRO);
        }
    }

}
