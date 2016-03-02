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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.bean.AgendamentoModel;
import model.bean.MedicoModel;
import model.bean.PacienteModel;
import model.dao.AgendamentoDAO;
import model.dao.MedicoDAO;
import model.dao.PacienteDAO;
import util.ConverterDados;
import util.DialogFX;

/**
 * FXML Controller class
 *
 * @author jeff-
 */
public class AgendamentoController implements Initializable {

    @FXML
    private ComboBox<MedicoModel> cb_medico;
    @FXML
    private ComboBox<PacienteModel> cb_paciente;
    @FXML
    private ComboBox<String> cb_turno;
    ObservableList<String> listaTurno = FXCollections.observableArrayList("Manhã", "Tarde");
    @FXML
    private DatePicker dpData;
    @FXML
    private TextField txt_busca;
    @FXML
    private Button bt_buscar, bt_finalizar, bt_cancelar;
    @FXML
    private TextArea txt_motivo;
    @FXML
    private TableView<AgendamentoModel> tabela_agenda;
    @FXML
    private TableColumn<AgendamentoModel, String> pacienteColuna;
    @FXML
    private TableColumn<AgendamentoModel, String> medicoColuna;
    @FXML
    private TableColumn<AgendamentoModel, String> turnoColuna;
    @FXML
    private TableColumn<AgendamentoModel, String> dataColuna;
    @FXML
    private TableColumn<AgendamentoModel, String> motivoColuna;

    AgendamentoModel agendamentoModel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cb_turno.setItems(listaTurno);

        /**
         * Povoando a TableView
         */
        this.pacienteColuna.setCellValueFactory(cellData -> cellData.getValue().getPacienteModel().getNomeProperty());
        this.medicoColuna.setCellValueFactory(cellData -> cellData.getValue().getMedicoModel().getNomeProperty());
        this.turnoColuna.setCellValueFactory(cellData -> cellData.getValue().getTurnoProperty());
        this.dataColuna.setCellValueFactory(cellData -> cellData.getValue().getDataProperty());
        this.motivoColuna.setCellValueFactory(cellData -> cellData.getValue().getMotivoProperty());

        /*Utilizando a nossa Classe converterDados */
        this.cb_medico.setConverter(new ConverterDados(ConverterDados.GET_MEDICO_NOME).getMedicoConverter());
        this.cb_paciente.setConverter(new ConverterDados(ConverterDados.GET_PACIENTE_NOME).getPacienteConverter());
    }

    /**
     * Executa as funções iniciais como preencher o comboBox do Medico
     * utilizando o Task já que pode ser um processo pesado
     */
    public void iniciarProcessos() {
        /*Para evitar uma exception de Thread temos que limpar o comboBox*/
        cb_medico.getItems().clear();
        cb_paciente.getItems().clear();

        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                cb_medico.setItems(MedicoDAO.executeQuery(null, MedicoDAO.QUERY_TODOS));
                cb_paciente.setItems(PacienteDAO.executeQuery(null, PacienteDAO.QUERY_TODOS));
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
                return AgendamentoDAO.executeQuery(null, AgendamentoDAO.QUERY_TODOS);
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                tabela_agenda.setItems((ObservableList<AgendamentoModel>) getValue());
            }
        };
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    /**
     * Grava um agendamento na TableView
     */
    @FXML
    private void onSave() {
        if (this.cb_medico.getSelectionModel().getSelectedIndex() != -1
                && this.cb_paciente.getSelectionModel().getSelectedIndex() != -1) {
            this.agendamentoModel = new AgendamentoModel();
            agendamentoModel.setTurno((String) cb_turno.getSelectionModel().getSelectedItem());
            MedicoModel medico = cb_medico.getSelectionModel().getSelectedItem();
            agendamentoModel.setMedicoModel(medico);
            PacienteModel paciente = cb_paciente.getSelectionModel().getSelectedItem();
            agendamentoModel.setPacienteModel(paciente);
            agendamentoModel.setMotivo(txt_motivo.getText().trim());
            if (AgendamentoDAO.executeUpdates(agendamentoModel, AgendamentoDAO.CREATE)) {
                DialogFX.showMessage("Dados inseridos com sucesso!", "Sucesso", DialogFX.SUCESS);
                limparCampos();
                carregarTabela();
            }
        } else {
            DialogFX.showMessage("Por favor verifique se você selecionou um Médico, ou um Paciente.", "ERRO", DialogFX.ERRO);
        }
    }

    /**
     * Deleta um agendamento da TableView
     */
    @FXML
    private void onDelete() {
        if (tabela_agenda.getSelectionModel().getSelectedItem() != null) {
            if (DialogFX.showConfirmation("Deseja Excluir o Agendamento ?")) {
                this.agendamentoModel = tabela_agenda.getItems().get(tabela_agenda.getSelectionModel().getSelectedIndex());

                if (AgendamentoDAO.executeUpdates(agendamentoModel, AgendamentoDAO.DELETE)) {
                    tabela_agenda.getItems().remove(tabela_agenda.getSelectionModel().getSelectedIndex());
                    DialogFX.showMessage("Excluido com sucesso!", "Sucesso", DialogFX.SUCESS);
                } else {
                    DialogFX.showMessage("Não foi possivel excluir dados!", "ERRO", DialogFX.ERRO);
                }
            }
        } else {
            DialogFX.showMessage("Selecione um agendamento na Tabela!", "Atenção", DialogFX.ATENCAO);
        }
    }

    /**
     * Limpa as seleções dos Campos
     */
    @FXML
    private void onClear() {
        limparCampos();
        tabela_agenda.getSelectionModel().clearSelection();
    }

    /**
     * Método que limpa os campos
     */
    private void limparCampos() {
        cb_turno.getSelectionModel().clearSelection();
        cb_medico.getSelectionModel().clearSelection();
        cb_paciente.getSelectionModel().clearSelection();
        txt_motivo.setText("");
        dpData.getEditor().setText("");
    }

}
