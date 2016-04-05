package controller;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
import model.bean.AgendamentoModel;
import model.bean.MedicoModel;
import model.bean.PacienteModel;
import model.dao.AgendamentoDAO;
import model.dao.MedicoDAO;
import model.dao.PacienteDAO;
import util.AutoCompleteComboBox;
import util.ConverterDados;
import util.DialogFX;
import util.MaskFormatter;

/**
 * FXML Controller Class Agendamento
 *
 * @author jeff-
 */
public class AgendamentoController implements Initializable {

    @FXML
    private ComboBox<MedicoModel> cb_medico;
    @FXML
    private ComboBox<PacienteModel> cb_paciente;
    @FXML
    private ComboBox<String> cb_horario;
    ObservableList<String> listaHorario = FXCollections.observableArrayList("09:00", "09:30", "10:00", "10:30", "11:00", "11:30",
            "12:00", "12:30", "13:00", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "17:00");
    @FXML
    private ComboBox<String> cb_turno;
    ObservableList<String> listaTurno = FXCollections.observableArrayList("Manhã", "Tarde");
    @FXML
    private DatePicker dpData;
    @FXML
    private DatePicker dp_localiza;
    @FXML
    private Button bt_finalizar, bt_cancelar;
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
    private TableColumn<AgendamentoModel, String> horarioColuna;
    @FXML
    private TableColumn<AgendamentoModel, String> dataColuna;
    @FXML
    private TableColumn<AgendamentoModel, String> motivoColuna;

    private AgendamentoModel agendamentoModel;
    
    private AutoCompleteComboBox autoCompleteComboBox;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cb_turno.setItems(listaTurno);
        cb_horario.setItems(listaHorario);

        /**
         * Povoando a TableView
         */
        this.pacienteColuna.setCellValueFactory(cellData -> cellData.getValue().getPacienteModel().getNomeProperty());
        this.medicoColuna.setCellValueFactory(cellData -> cellData.getValue().getMedicoModel().getNomeProperty());
        this.turnoColuna.setCellValueFactory(cellData -> cellData.getValue().getTurnoProperty());
        this.horarioColuna.setCellValueFactory(cellData -> cellData.getValue().getHorarioProperty());
        this.dataColuna.setCellValueFactory(cellData -> cellData.getValue().getDataProperty());
        this.motivoColuna.setCellValueFactory(cellData -> cellData.getValue().getMotivoProperty());

        /*Utilizando a nossa Classe converterDados */
        this.cb_medico.setConverter(new ConverterDados(ConverterDados.GET_MEDICO_NOME).getMedicoConverter(cb_medico));
        this.cb_paciente.setConverter(new ConverterDados(ConverterDados.GET_PACIENTE_NOME).getPacienteConverter(cb_paciente));
        /*Utilizando o autocomplete do comboBox*/
        this.autoCompleteComboBox = new AutoCompleteComboBox(cb_medico);
        

        /**
         * Colocando a Mascara no DatePicker
         */
        MaskFormatter formatter = new MaskFormatter(dpData);
        formatter.setMask(MaskFormatter.DATA_BARRA);
        formatter.showMask();
        formatter.addComponente(dp_localiza, MaskFormatter.DATA_BARRA, true);
    }

    /**
     * Executa as funções iniciais como preencher o comboBox do Medico
     * utilizando o Task já que pode ser um processo pesado
     */
    public void iniciarProcessos() {
        /*Para evitar uma exception de Thread temos que limpar o comboBox*/
        cb_medico.getItems().clear();
        cb_paciente.getItems().clear();
        /*Precisa formatar a data atual para o padrão que queremos*/
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dp_localiza.getEditor().setText(dateFormat.format(Calendar.getInstance().getTime()));
        onDateSelected();

        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                cb_medico.setItems(MedicoDAO.executeQuery(null, MedicoDAO.QUERY_TODOS));
                cb_paciente.setItems(PacienteDAO.executeQuery(null, PacienteDAO.QUERY_TODOS));
                return null;
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                autoCompleteComboBox.saveData();
                autoCompleteComboBox.addComboBox(cb_paciente);
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
                return AgendamentoDAO.executeQuery(null, AgendamentoDAO.QUERY_DATA);
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
                && this.cb_paciente.getSelectionModel().getSelectedIndex() != -1
                && this.cb_turno.getSelectionModel().getSelectedIndex() != -1) {
            this.agendamentoModel = new AgendamentoModel();
            agendamentoModel.setTurno((String) cb_turno.getSelectionModel().getSelectedItem());
            agendamentoModel.setHorario((String) cb_horario.getSelectionModel().getSelectedItem());
            MedicoModel medico = cb_medico.getSelectionModel().getSelectedItem();
            agendamentoModel.setMedicoModel(medico);
            PacienteModel paciente = cb_paciente.getSelectionModel().getSelectedItem();
            agendamentoModel.setPacienteModel(paciente);
            /*Adicionado a Mascara no DatePicker e validando o mesmo*/
            if (dpData.getEditor().getText().length() == 10) {
                agendamentoModel.setData(dpData.getEditor().getText());
            } else {
                DialogFX.showMessage("Data do cliente não foi preenchida corretamente.", "Erro encontrado", DialogFX.ERRO);
                return;
            }
            agendamentoModel.setMotivo(txt_motivo.getText().trim());
            if (AgendamentoDAO.executeUpdates(agendamentoModel, AgendamentoDAO.CREATE)) {
                DialogFX.showMessage("Dados inseridos com sucesso!", "Sucesso", DialogFX.SUCESS);
                limparCampos();
                iniciarProcessos();
            }
        } else {
            DialogFX.showMessage("Por favor verifique se você selecionou Médico, Turno e Paciente.", "Atenção", DialogFX.ATENCAO);
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
        carregarTabela();
    }

    /**
     * Evento ao selecionar data.
     */
    @FXML
    private void onDateSelected() {
        /*Caso o cara preferiu digitar a digitar a data em vez de selecionar*/
        if (dp_localiza.getEditor().getText().length() == 10) {
            String dataSelecionada = dp_localiza.getEditor().getText();
            AgendamentoModel am = new AgendamentoModel();
            am.setData(dataSelecionada);
            Task task = new Task() {
                @Override
                protected Object call() throws Exception {
                    return AgendamentoDAO.executeQuery(am, AgendamentoDAO.QUERY_DATA);
                }

                @Override
                protected void succeeded() {
                    super.succeeded();
                    tabela_agenda.getItems().clear();
                    tabela_agenda.setItems((ObservableList<AgendamentoModel>) getValue());
                }

            };
            Thread t = new Thread(task);
            t.setDaemon(true);
            t.start();
        } else {
            DialogFX.showMessage("Data não foi preenchida corretamente", "Atenção", DialogFX.ATENCAO);
        }
    }

    /**
     * Método que limpa os campos
     */
    private void limparCampos() {
        cb_turno.getSelectionModel().clearSelection();
        cb_medico.getSelectionModel().clearSelection();
        cb_paciente.getSelectionModel().clearSelection();
        cb_horario.getSelectionModel().clearSelection();
        txt_motivo.setText("");
        dpData.getEditor().setText("");
    }
    
    /**
     * Método reiniciar os dados da tela.
     */
    public void refresh(){
        limparCampos();
    }
}
