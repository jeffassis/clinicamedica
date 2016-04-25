package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import model.bean.BairroModel;
import model.bean.CidadeModel;
import model.bean.PacienteModel;
import model.dao.BairroDAO;
import model.dao.CidadeDAO;
import model.dao.PacienteDAO;
import util.ConverterDados;
import util.DialogFX;
import util.Funcionalidades;
import util.MaskFormatter;

/**
 * FXML Controller class
 *
 * @author jeff-
 */
public class PacientesController extends Funcionalidades implements Initializable {

    @FXML
    private DatePicker dp_nascimento, dp_cliente;
    @FXML
    private TextField txt_matricula, txt_nome, txt_endereco, txt_telefone;
    @FXML
    private TextField txt_cep, txt_documento, txt_tipo, txt_email, txt_observacoes;
    @FXML
    private Button bt_novo, bt_salvar, bt_localizar, bt_imprimir, bt_cancelar;
    @FXML
    private ComboBox<CidadeModel> cb_cidade;
    @FXML
    private ComboBox<BairroModel> cb_bairro;
    @FXML
    private CheckBox checkStatus;
    @FXML
    private ComboBox<String> cb_sexo;
    ObservableList<String> listaSexo = FXCollections.observableArrayList("Feminino", "Masculino");
    /*Essa variavel será nossa flag para saber se a tela foi aberta para editar ou não*/
    private boolean editar;
    /*Vamos precisar dessa Classe para atualizar o dados quando for usar o Paciente para editar*/
    private TableView<PacienteModel> tabela;
    /*Representante da Classe HomeControle*/
    private HomeController controller;

    PacienteModel pacienteModel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        MaskFormatter formatter = new MaskFormatter();
        /*Adicionamos outros componentes*/
        formatter.addComponente(txt_telefone, MaskFormatter.TEL_DIG, true);
        formatter.addComponente(dp_cliente, MaskFormatter.DATA_BARRA, true);
        formatter.addComponente(dp_nascimento, MaskFormatter.DATA_BARRA, true);
        this.checkStatus.setSelected(true);

        /*Utilizando a nossa Classe converter CidadeModel*/
        this.cb_cidade.setConverter(new ConverterDados(ConverterDados.GET_CIDADE_NOME).getCidadeConverter());
        this.cb_bairro.setConverter(new ConverterDados(ConverterDados.GET_BAIRRO_NOME).getBairroConverter());

        cb_sexo.setItems(listaSexo);
    }

    /**
     * Executa as funções iniciais como preencher o comboBox do Paciente
     * utilizando o Task já que pode ser um processo pesado
     *
     * @param editar - Passar um boolean informando se a tela foi aberta para
     * editar ou não.
     * @param tableView - Passa uma Tabela de Pacientes
     */
    @Override
    public void iniciarProcessos(boolean editar, Object tableView) {
        /*Para evitar uma exception de Thread temos que limpar o comboBox*/
        cb_cidade.getItems().clear();
        cb_bairro.getItems().clear();
        /*Fazemos isso pois vamos precisar saber se no onSave estamos alterando ou salvando dados*/
        this.editar = editar;
        this.tabela = (TableView<PacienteModel>) tableView;
        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                cb_cidade.setItems(CidadeDAO.executeQuery(null, CidadeDAO.QUERY_TODOS));
                cb_bairro.setItems(BairroDAO.executeQuery(null, BairroDAO.QUERY_TODOS));
                return null;
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                /*Colocamos dentro do succeeded pq vamos precisar que os comboBox já estejam populados
                pq se não ao comparar os dados que nem fazemos a baixo poderia da erro já que o banco pode demorar para responder.*/
                /*Se foi aberto para editar*/
                if (editar) {
                    /*Para evitar qualquer tipo de exceção*/
                    if (tabela != null) {
                        /*Pegamos a referencia da Tabela*/
                        habilitarCampos();
                        /*Pegamos o paciente selecionado em outra tabela*/
                        PacienteModel model = tabela.getSelectionModel().getSelectedItem();
                        bt_novo.setDisable(true);
                        bt_salvar.setDisable(false);
                        /*Utilizei o Property pq ele tem o toString*/
                        txt_matricula.setText(model.getCodigoProperty().getValue().toString());
                        txt_nome.setText(model.getNome());
                        /*Perceba como vamos adicionar a Data. É como se fosse um TextField*/
                        dp_nascimento.getEditor().setText(model.getNascimento());
                        txt_endereco.setText(model.getEndereco());
                        txt_telefone.setText(model.getTelefone());
                        /*removidos os for*/
                        cb_cidade.setValue(model.getCidadeModel());
                        cb_bairro.setValue(model.getBairroModel());
                        cb_sexo.setValue(model.getSexo());
                        dp_cliente.getEditor().setText(model.getData_cliente());
                        txt_tipo.setText(model.getTipo());
                        txt_email.setText(model.getEmail());
                        txt_observacoes.setText(model.getObs());
                        txt_documento.setText(model.getDocumento());
                        txt_cep.setText(model.getCep());
                        checkStatus.setSelected(model.getStatus());

                    }
                }
            }

        };
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();

    }

    /**
     * Método que pega a referencia de HomeController.
     *
     * @param controller - Passe a Classe HomeController
     */
    public void getHomeController(HomeController controller) {
        this.controller = controller;
    }

    /**
     * Método para acao do botao salvar
     */
    @FXML
    private void onSave() {
        this.pacienteModel = new PacienteModel();
        pacienteModel.setNome(txt_nome.getText().trim());
        pacienteModel.setEndereco(txt_endereco.getText().trim());
        pacienteModel.setTelefone(txt_telefone.getText().trim());
        pacienteModel.setCep(txt_cep.getText().trim());
        pacienteModel.setDocumento(txt_documento.getText().trim());
        pacienteModel.setSexo((String) cb_sexo.getSelectionModel().getSelectedItem());
        pacienteModel.setTipo(txt_tipo.getText().trim());
        pacienteModel.setEmail(txt_email.getText().trim());
        pacienteModel.setObs(txt_observacoes.getText().trim());
        CidadeModel cidade = cb_cidade.getSelectionModel().getSelectedItem();
        pacienteModel.setCidadeModel(cidade);
        BairroModel bairro = cb_bairro.getSelectionModel().getSelectedItem();
        pacienteModel.setBairroModel(bairro);
        pacienteModel.setStatus(checkStatus.isSelected());
        /*Para pegamos a data ficou mais fácil, Imagina que o datepicker virou um TextField
        primeiro vamos verificar se a datas fornecidas estão corretas*/
        if (dp_nascimento.getEditor().getText().length() == 10) {
            /*esse igual a 10 e a quantidade de caracteres que a data possui, se for 10
            então a data está completa*/
            if (dp_cliente.getEditor().getText().length() == 10) {
                /*Se chegou aqui então ambos estão corretos, eu fiz por camada(if dentro de if) para que mostre onde está o erro de cada um.
                Veja que pegamos como se fosse um texto digitado em um TextField, smp utilizando GetEditor primeiro*/
                pacienteModel.setData_cliente(dp_cliente.getEditor().getText());
                pacienteModel.setNascimento(dp_nascimento.getEditor().getText());
            } else {
                DialogFX.showMessage("Data do cliente não foi preenchida corretamente", "Erro encontrado", DialogFX.ATENCAO);
                /*Paramos a execução dessa linha usando o return, ou seja a proxima linha não será executada*/
                return;
            }
        } else {
            DialogFX.showMessage("Data de nascimento não foi preenchida corretamente", "Erro encontrado", DialogFX.ATENCAO);
            /*Paramos a execução dessa linha usando o return, ou seja a proxima linha não será executada*/
            return;
        }
        /*Verificamos se é para salvar como edição ou apenas adicionado um dado.*/
        if (this.editar == true) {
            /*Setamos o código já que é um paciente editado*/
            this.pacienteModel.setCodigo(Integer.parseInt(txt_matricula.getText()));
            if (PacienteDAO.executeUpdates(pacienteModel, PacienteDAO.UPDATE)) {
                DialogFX.showMessage("Dados Alterados com sucesso com sucesso!", "Sucesso", DialogFX.SUCESS);
                /*Trocamos pelo o editado, assim não precisamos buscar dnv no banco de Dados*/
                tabela.getItems().set(pacienteModel.getCodigo() - 1, pacienteModel);

            } else {
                DialogFX.showMessage("Não foi possivel alterar dados", "ERRO", DialogFX.ERRO);
            }

        } else if (editar == false) {
            /*Muito cuidado com o if ao formatar o texto ele ficava jutando o else com o if, então tive q fazer dessa
            maneira para não dá problema*/
            if (PacienteDAO.executeUpdates(pacienteModel, PacienteDAO.CREATE)) {
                limparCampos();
                DialogFX.showMessage("Dados inseridos com sucesso!", "Sucesso", DialogFX.SUCESS);

                desabilitarCampos();
            } else {
                DialogFX.showMessage("Não foi possivel inserir dados", "ERRO", DialogFX.ERRO);
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
        habilitarCampos();
        txt_nome.requestFocus();
    }

    /**
     * Método que desativa os botoes editar e excluir
     */
    @FXML
    private void onCancel() {
        /*Ao fazer isso desativamos o editar, ou seja ele só vai add*/
        this.editar = false;
        desabilitarCampos();
        limparCampos();
        bt_novo.setDisable(false);
        bt_salvar.setDisable(true);
    }

    /**
     * Método de ação do botao localizar
     */
    @FXML
    private void onLocalizar(ActionEvent event) {
        /*Fecha a Janela*/
        ((Node) event.getSource()).getScene().getWindow().hide();
        /*Apenas pedimos para o Home exibir a Janela*/
        this.controller.meusPacientes();
    }

    /**
     * Método que limpa os campos
     */
    private void limparCampos() {
        txt_matricula.setText("");
        txt_nome.setText("");
        dp_nascimento.getEditor().setText("");
        txt_endereco.setText("");
        txt_telefone.setText("");
        txt_cep.setText("");
        txt_documento.setText("");
        cb_sexo.getSelectionModel().clearSelection();
        dp_cliente.getEditor().setText("");
        txt_tipo.setText("");
        txt_email.setText("");
        txt_observacoes.setText("");
        cb_cidade.getSelectionModel().clearSelection();
        cb_bairro.getSelectionModel().clearSelection();
    }

    /**
     * Método responsavel por habilitar os campos
     */
    private void habilitarCampos() {
        txt_nome.setDisable(false);
        dp_nascimento.setDisable(false);
        txt_endereco.setDisable(false);
        txt_telefone.setDisable(false);
        txt_cep.setDisable(false);
        txt_documento.setDisable(false);
        cb_sexo.setDisable(false);
        dp_cliente.setDisable(false);
        txt_tipo.setDisable(false);
        txt_email.setDisable(false);
        txt_observacoes.setDisable(false);
        cb_cidade.setDisable(false);
        cb_bairro.setDisable(false);
    }

    /**
     * Método responsavel por desabilitar os campos e botôes
     */
    private void desabilitarCampos() {
        txt_nome.setDisable(true);
        dp_nascimento.setDisable(true);
        txt_endereco.setDisable(true);
        txt_telefone.setDisable(true);
        txt_cep.setDisable(true);
        txt_documento.setDisable(true);
        cb_sexo.setDisable(true);
        dp_cliente.setDisable(true);
        txt_tipo.setDisable(true);
        txt_email.setDisable(true);
        txt_observacoes.setDisable(true);
        cb_cidade.setDisable(true);
        cb_bairro.setDisable(true);
        bt_salvar.setDisable(true);
    }

    /**
     * Método reiniciar os dados da tela.
     */
    @Override
    public void refresh() {
        limparCampos();
        desabilitarCampos();
        this.dp_cliente.getEditor().setText("");
        this.dp_nascimento.getEditor().setText("");
        /*Volta os comboBox para nenhuma seleção*/
        this.cb_sexo.getSelectionModel().clearSelection();
        this.cb_bairro.getSelectionModel().clearSelection();
        this.cb_cidade.getSelectionModel().clearSelection();
        this.checkStatus.setSelected(true);
    }
}
