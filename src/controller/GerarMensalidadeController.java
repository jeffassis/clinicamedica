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
import javafx.scene.control.TextField;
import model.bean.MensalidadeModel;
import model.bean.PacienteModel;
import model.dao.MensalidadeDAO;
import model.dao.PacienteDAO;
import util.AutoCompleteComboBox;
import util.ConverterDados;
import util.DialogFX;
import util.Funcionalidades;
import util.MaskFormatter;

/**
 * FXML Controller class
 *
 * @author jeff-
 */
public class GerarMensalidadeController extends Funcionalidades implements Initializable {

    @FXML
    private TextField txt_codigo, txt_valor, txt_desconto;
    @FXML
    private DatePicker dp_data;
    @FXML
    private ComboBox<PacienteModel> cb_paciente;
    @FXML
    private ComboBox cb_mes;
    ObservableList<String> listaMes = FXCollections.observableArrayList("Janeiro", "Fevereiro", "Março",
            "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro");
    @FXML
    private TableView<MensalidadeModel> tabela_mensalidade;
    @FXML
    private TableColumn<MensalidadeModel, Integer> codigoColuna;
    @FXML
    private TableColumn<MensalidadeModel, String> mesColuna;
    @FXML
    private TableColumn<MensalidadeModel, String> dataColuna;
    @FXML
    private TableColumn<MensalidadeModel, Double> mensalColuna;
    @FXML
    private TableColumn<MensalidadeModel, Double> descontoColuna;
    @FXML
    private TableColumn<MensalidadeModel, Double> valor_finalColuna;
    @FXML
    private Button bt_salvar;
    @FXML
    private Button bt_excluir;
    @FXML
    private Button bt_editar;
    @FXML
    private Button bt_novo;

    /*Declaração da Class*/
    private MensalidadeModel mensalidadeModel;
    private AutoCompleteComboBox autoCompleteComboBox;
    private MaskFormatter formatter;

    int flag = 1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /**
         * Preenchendo o combo Mês
         */
        cb_mes.setItems(listaMes);

        /**
         * Carregar todas as colunas da tabela pegando os dados do BD
         */
        this.codigoColuna.setCellValueFactory(cellData -> cellData.getValue().getCodigoProperty().asObject());
        this.mesColuna.setCellValueFactory(cellData -> cellData.getValue().getMesProperty());
        this.dataColuna.setCellValueFactory(cellData -> cellData.getValue().getData_pagtoProperty());
        this.mensalColuna.setCellValueFactory(cellData -> cellData.getValue().getValorProperty().asObject());
        this.descontoColuna.setCellValueFactory(cellData -> cellData.getValue().getDescontoProperty().asObject());
        /*Apenas mostro qual dados vai popular a coluna*/
        this.valor_finalColuna.setCellValueFactory(cellData -> cellData.getValue().getValorComDesconto().asObject());

        /*Utilizando a nossa Classe ConverterDados*/
        this.cb_paciente.setConverter(new ConverterDados(ConverterDados.GET_PACIENTE_NOME).getPacienteConverter(cb_paciente));
        /*Utilizando o autoComplete do ComboBox*/
        this.autoCompleteComboBox = new AutoCompleteComboBox(cb_paciente);

        /**
         * Mascara da Data
         */
        formatter = new MaskFormatter(dp_data);
        formatter.setMask(MaskFormatter.DATA_BARRA);
        formatter.addComponente(dp_data, MaskFormatter.DATA_BARRA, true);
        formatter.showMask();
    }

    /**
     * Executa a função iniciais como preencher o comboBox
     */
    @Override
    public void iniciarProcessos() {
        /*Para evitar Exception de thread temos que limpar o comboBox*/
        cb_paciente.getItems().clear();

        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
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
     * Metodo para carregar o TableView da GUI sendo controlado pelo ComboBox
     */
    public void carregandoTabela() {

        PacienteModel paciente = cb_paciente.getSelectionModel().getSelectedItem();
        
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
     * Método da ação do Botão salvar da GUI do FXML
     */
    @FXML
    private void onSave() {
        /**
         * TODO Validação dos campos
         */
        if (flag == 1) {
            /*Flag igual a 1 salva os dados*/
            this.mensalidadeModel = new MensalidadeModel();
            mensalidadeModel.setValor(Double.valueOf(txt_valor.getText().trim().replace(",", ".")));
            mensalidadeModel.setDesconto(Double.valueOf(txt_desconto.getText().trim().replace(",", ".")));
            mensalidadeModel.setMes((String) cb_mes.getSelectionModel().getSelectedItem());

            if (dp_data.getEditor().getText().length() == 10) {
                mensalidadeModel.setData_pagto(dp_data.getEditor().getText());
            } else {
                DialogFX.showMessage("Há data não foi preenchida corretamente", "Erro encontrado", DialogFX.ATENCAO);
                return;
            }
            PacienteModel paciente = cb_paciente.getSelectionModel().getSelectedItem();
            mensalidadeModel.setPacienteModel(paciente);
            if (MensalidadeDAO.executeUpdates(mensalidadeModel, MensalidadeDAO.CREATE)) {
                DialogFX.showMessage("Dados inseridos com sucesso", "Sucesso", DialogFX.SUCESS);
                limparCampos();
                desabilitarCampos();
            }
        } else {
            /*Flag igual a 2 ele atualiza os dados do BD*/
            this.mensalidadeModel = new MensalidadeModel();
            mensalidadeModel.setCodigo(Integer.parseInt(txt_codigo.getText().trim()));
            mensalidadeModel.setValor(Double.valueOf(txt_valor.getText().trim().replace(",", ".")));
            mensalidadeModel.setDesconto(Double.valueOf(txt_desconto.getText().trim().replace(",", ".")));
            mensalidadeModel.setMes((String) cb_mes.getSelectionModel().getSelectedItem());

            if (dp_data.getEditor().getText().length() == 10) {
                mensalidadeModel.setData_pagto(dp_data.getEditor().getText());
            } else {
                DialogFX.showMessage("Há data não foi preenchida corretamente", "Erro encontrado", DialogFX.ATENCAO);
                return;
            }
            PacienteModel paciente = cb_paciente.getSelectionModel().getSelectedItem();
            mensalidadeModel.setPacienteModel(paciente);
            if (MensalidadeDAO.executeUpdates(mensalidadeModel, MensalidadeDAO.UPDATE)) {
                DialogFX.showMessage("Dados Atualizados com sucesso", "Sucesso", DialogFX.SUCESS);
                limparCampos();
                desabilitarCampos();
                flag = 1;
            }
        }
    }

    /**
     * Metodo para a ação do botão Editar
     */
    @FXML
    private void onEdit() {
        if (tabela_mensalidade.getSelectionModel().getSelectedIndex() != -1) {
            this.bt_salvar.setDisable(false);
            this.mensalidadeModel = tabela_mensalidade.getSelectionModel().getSelectedItem();
            txt_codigo.setText(Integer.toString(mensalidadeModel.getCodigo()));
            txt_valor.setText(mensalidadeModel.getValorProperty().getValue().toString());
            txt_desconto.setText(mensalidadeModel.getDescontoProperty().getValue().toString());
            cb_paciente.setValue(mensalidadeModel.getPacienteModel());
            dp_data.getEditor().setText(mensalidadeModel.getData_pagto());

            for (int i = 0; i < cb_mes.getItems().size(); i++) {
                if (((String) cb_mes.getItems().get(i)).equals(mensalidadeModel.getMes())) {
                    cb_mes.getSelectionModel().select(i);
                    break;
                }
            }
            bt_editar.setDisable(true);
            bt_excluir.setDisable(true);
            habilitarCampos();
            //cb_paciente.requestFocus();
            flag = 2;
        }
    }

    /**
     * Metodo para a ação do botão excluir
     */
    @FXML
    private void onDelete() {
        if (tabela_mensalidade.getSelectionModel().getSelectedItem() != null) {
            if (DialogFX.showConfirmation("Deseja Excluir ?")) {
                this.mensalidadeModel = tabela_mensalidade.getItems().get(
                        tabela_mensalidade.getSelectionModel().getSelectedIndex());
                if (MensalidadeDAO.executeUpdates(mensalidadeModel, MensalidadeDAO.DELETE)) {
                    tabela_mensalidade.getItems().remove(
                            tabela_mensalidade.getSelectionModel().getSelectedIndex());
                    DialogFX.showMessage("Excluido com sucesso!", "Sucesso", DialogFX.SUCESS);
                    desabilitarCampos();
                } else {
                    DialogFX.showMessage("Não foi possivel excluir dados", "Erro", DialogFX.ERRO);
                }
            }
        } else {
            DialogFX.showMessage("Selecione uma mensalidade na Tabela!", "Atenção", DialogFX.ATENCAO);
        }
    }

    /**
     * Metodo para ação do botão Novo
     */
    @FXML
    private void onNew() {
        habilitarCampos();
        bt_novo.setDisable(false);
        bt_salvar.setDisable(false);
        bt_excluir.setDisable(true);
        bt_editar.setDisable(true);
        cb_paciente.requestFocus();
        flag = 1;
    }

    /**
     * Metodo para ação do botão Cancelar
     */
    @FXML
    private void onCancel() {
        tabela_mensalidade.getSelectionModel().clearSelection();
        limparCampos();
        desabilitarCampos();
        bt_novo.setDisable(false);
        bt_salvar.setDisable(true);
        bt_editar.setDisable(true);
        bt_excluir.setDisable(true);
    }

    /**
     * Método para que seja acionado os botoes editar e excluir quando a tabela
     * for clickada
     */
    public void onClicked() {
        bt_novo.setDisable(false);
        bt_salvar.setDisable(true);
        bt_editar.setDisable(false);
        bt_excluir.setDisable(false);
        limparCampos();
        desabilitarCampos();
    }

    /**
     * Metodos que limpa os campos
     */
    private void limparCampos() {
        txt_codigo.setText("");
        txt_valor.setText("");
        txt_desconto.setText("");
        cb_mes.getSelectionModel().clearSelection();
        cb_paciente.getEditor().setText("");
        dp_data.getEditor().clear();
    }

    /**
     * Metodo para Desabilitar os campos
     */
    private void desabilitarCampos() {
        dp_data.setDisable(true);
        txt_valor.setDisable(true);
        txt_desconto.setDisable(true);
        cb_mes.setDisable(true);
        bt_salvar.setDisable(true);
    }

    /**
     * Metodo para habilitar os campos
     */
    private void habilitarCampos() {
        dp_data.setDisable(false);
        txt_valor.setDisable(false);
        txt_desconto.setDisable(false);
        cb_mes.setDisable(false);
        bt_salvar.setDisable(false);
    }
}
