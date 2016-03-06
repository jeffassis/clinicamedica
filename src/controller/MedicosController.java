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
import model.bean.MedicoModel;
import model.dao.MedicoDAO;
import util.DialogFX;

/**
 * FXML Controller class
 *
 * @author jeff-
 */
public class MedicosController implements Initializable {

    @FXML
    private TextField txt_codigo;
    @FXML
    private TextField txt_nome;
    @FXML
    private TextField txt_crm;
    /*Preenchendo o ComboBox Manualmente */
    @FXML
    private ComboBox cb_especialidade;
    ObservableList<String> listEspecialidade = FXCollections.observableArrayList("Cardiologista", "Ortopedista", "Pediatra", "Clinico Geral");
    @FXML
    private TableView<MedicoModel> tabela_medico;
    @FXML
    private TableColumn<MedicoModel, Integer> codigoColuna;
    @FXML
    private TableColumn<MedicoModel, String> nomeColuna;
    @FXML
    private TableColumn<MedicoModel, String> crmColuna;
    @FXML
    private TableColumn<MedicoModel, String> especialidadeColuna;
    @FXML
    private Button bt_salvar;
    @FXML
    private Button bt_editar;
    @FXML
    private Button bt_excluir;
    @FXML
    private Button bt_novo;
    @FXML
    private Button bt_cancelar;

    /*Criação da variavel de Class */
    private MedicoModel medicoModel;

    int flag = 1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /**
         * Preenchendo o ComboBox Especialidade
         */
        cb_especialidade.setItems(listEspecialidade);

        /**
         * Carrega todos as colunas da tabela pegando os dados do BD
         */
        this.codigoColuna.setCellValueFactory(cellData -> cellData.getValue().getCodigoProperty().asObject());
        this.nomeColuna.setCellValueFactory(cellData -> cellData.getValue().getNomeProperty());
        this.crmColuna.setCellValueFactory(cellData -> cellData.getValue().getCrmProperty());
        this.especialidadeColuna.setCellValueFactory(cellData -> cellData.getValue().getEspecialidadeProperty());
    }

    /**
     * Metodo para carregar o TableView da GUI com Thread
     */
    public void carregarTabela() {
        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                return MedicoDAO.executeQuery(null, MedicoDAO.QUERY_TODOS);
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                tabela_medico.setItems((ObservableList<MedicoModel>) getValue());
            }
        };
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    /**
     * Método da ação do Botão salvar da GUI do FXMl do Médico
     */
    @FXML
    private void onSave() {
        /*Verifica a flag. Se for 1 ela salva dados*/
        if (flag == 1) {
            /*Verifica se nome do Medico esta vazia*/
            if (txt_nome.getText().isEmpty()) {
                DialogFX.showMessage("O campo não pode ser vazio!", "Campo Vazio", DialogFX.ATENCAO);
                return;
            }

            this.medicoModel = new MedicoModel();
            medicoModel.setNome(txt_nome.getText().trim());
            medicoModel.setCrm(txt_crm.getText().trim());
            medicoModel.setEspecialidade((String) cb_especialidade.getSelectionModel().getSelectedItem());
            if (MedicoDAO.executeUpdates(medicoModel, MedicoDAO.CREATE)) {
                limparCampos();
                DialogFX.showMessage("Dados inseridos com sucesso!", "Sucesso", DialogFX.SUCESS);
                carregarTabela();
                desabilitarCampos();
            } else {
                DialogFX.showMessage("Houve um erro ao inserir Dados", "ERRO", DialogFX.ERRO);
            }
        } else {
            /*Se a flag for 2 edita os dados do banco de dados*/
            this.medicoModel = new MedicoModel();
            medicoModel.setCodigo(Integer.parseInt(txt_codigo.getText().trim()));
            medicoModel.setNome(txt_nome.getText().trim());
            medicoModel.setCrm(txt_crm.getText().trim());
            medicoModel.setEspecialidade((String) cb_especialidade.getSelectionModel().getSelectedItem());
            if (MedicoDAO.executeUpdates(medicoModel, MedicoDAO.UPDATE)) {
                limparCampos();
                DialogFX.showMessage("Dados Atualizados com sucesso!", "Sucesso", DialogFX.SUCESS);
                carregarTabela();
                flag = 1;
                desabilitarCampos();
            } else {
                DialogFX.showMessage("Não foi possivel atualizar dados", "ERROR", DialogFX.ERRO);
            }
        }
    }

    /**
     * Método para ação do botão editar
     *
     */
    @FXML
    private void onEdit() {
        /*Verificamos se a tabela foi selecionada*/
        if (tabela_medico.getSelectionModel().getSelectedIndex() != -1) {
            /*Habilito o botão salvar*/
            this.bt_salvar.setDisable(false);
            this.medicoModel = tabela_medico.getSelectionModel().getSelectedItem();
            txt_codigo.setText(Integer.toString(medicoModel.getCodigo()));
            txt_nome.setText(medicoModel.getNome());
            txt_crm.setText(medicoModel.getCrm());
            /*Utilizamos o for para descobrir qual posição é igual ao dado retornado
             de especialidade*/
            for (int i = 0; i < cb_especialidade.getItems().size(); i++) {
                if (((String) cb_especialidade.getItems().get(i)).equals(medicoModel.getEspecialidade())) {
                    cb_especialidade.getSelectionModel().select(i);
                    /*agora q ja achamos paramos o for*/
                    break;
                }
            }
            flag = 2;
            /*Desabilita os botões excluir e editar */
            this.bt_editar.setDisable(true);
            this.bt_excluir.setDisable(true);
            habilitarCampos();
        }
    }

    /**
     * Metodo criado para acao do botao Excluir
     */
    @FXML
    private void onDelete() {
        if (DialogFX.showConfirmation("Deseja Excluir ?")) {
            this.medicoModel = tabela_medico.getItems().get(tabela_medico.getSelectionModel().getSelectedIndex());

            if (MedicoDAO.executeUpdates(medicoModel, MedicoDAO.DELETE)) {
                tabela_medico.getItems().remove(tabela_medico.getSelectionModel().getSelectedIndex());
                DialogFX.showMessage("Excluido com sucesso", "Sucesso", DialogFX.SUCESS);
                desabilitarCampos();
            } else {
                DialogFX.showMessage("Não foi possivel excluir dados", "ERRO", DialogFX.ERRO);
            }
        }
    }

    /**
     * Método ação do Botao Novo
     */
    @FXML
    private void onNew() {
        bt_novo.setDisable(false);
        bt_salvar.setDisable(false);
        bt_editar.setDisable(true);
        bt_excluir.setDisable(true);
        habilitarCampos();
        txt_nome.requestFocus();
        flag = 1;
    }

    /**
     * Método que desativa os botoes editar e excluir
     */
    @FXML
    private void onCancel() {
        /* Desmarca qualquer registro que esteja selecionado na tabela*/
        tabela_medico.getSelectionModel().clearSelection();

        desabilitarCampos();
        limparCampos();
        bt_novo.setDisable(false);
        bt_salvar.setDisable(true);
        bt_editar.setDisable(true);
        bt_excluir.setDisable(true);
    }

    /**
     * Método que limpa os campos
     */
    private void limparCampos() {
        txt_codigo.setText("");
        txt_nome.setText("");
        txt_crm.setText("");
        cb_especialidade.getSelectionModel().clearSelection();
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
        txt_nome.setDisable(true);
        txt_crm.setDisable(true);
        cb_especialidade.setDisable(true);
    }

    /**
     * Método responsavel por habilitar os campos
     */
    private void habilitarCampos() {
        txt_nome.setDisable(false);
        txt_crm.setDisable(false);
        cb_especialidade.setDisable(false);
    }

    /**
     * Método responsavel por desabilitar os campos e botôes
     */
    private void desabilitarCampos() {
        txt_nome.setDisable(true);
        txt_crm.setDisable(true);
        cb_especialidade.setDisable(true);
        bt_salvar.setDisable(true);
        bt_editar.setDisable(true);
        bt_excluir.setDisable(true);
    }
    /**
     * Método reiniciar os dados da tela.
     */
    public void refresh(){
        limparCampos();
        desabilitarCampos();
        flag = 1;
    }
}
