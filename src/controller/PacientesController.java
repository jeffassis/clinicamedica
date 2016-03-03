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
import javafx.scene.control.TextField;
import model.bean.BairroModel;
import model.bean.CidadeModel;
import model.bean.PacienteModel;
import model.dao.BairroDAO;
import model.dao.CidadeDAO;
import model.dao.PacienteDAO;
import util.ConverterDados;
import util.DialogFX;
import util.MaskFormatter;

/**
 * FXML Controller class
 *
 * @author jeff-
 */
public class PacientesController implements Initializable {
    
    @FXML
    private DatePicker dp_nascimento, dp_cliente;
    @FXML
    private TextField txt_matricula, txt_nome, txt_endereco, txt_telefone;
    @FXML
    private TextField txt_cep, txt_documento, txt_tipo, txt_email, txt_observacoes;
    @FXML
    private Button bt_novo, bt_salvar, bt_excluir, bt_localizar, bt_imprimir, bt_cancelar;
    @FXML
    private ComboBox<CidadeModel> cb_cidade;
    @FXML
    private ComboBox<BairroModel> cb_bairro;
    @FXML
    private ComboBox<String> cb_sexo;
    ObservableList<String> listaSexo = FXCollections.observableArrayList("Feminino", "Masculino");
    
    PacienteModel pacienteModel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        MaskFormatter formatter = new MaskFormatter(txt_telefone);
        formatter.setMask(MaskFormatter.TEL_8DIG);
        /*Caso vc queria mostrar a mascara no TextField*/
        formatter.showMask();
        /*Adicionamos outros componentes*/
        formatter.addComponente(dp_cliente, MaskFormatter.DATA_BARRA, true);
        formatter.addComponente(dp_nascimento, MaskFormatter.DATA_BARRA, true);

        /*Utilizando a nossa Classe converter CidadeModel*/
        this.cb_cidade.setConverter(new ConverterDados(ConverterDados.GET_CIDADE_NOME).getCidadeConverter());
        this.cb_bairro.setConverter(new ConverterDados(ConverterDados.GET_BAIRRO_NOME).getBairroConverter());
        
        cb_sexo.setItems(listaSexo);
    }

    /**
     * Executa as funções iniciais como preencher o comboBox do Paciente
     * utilizando o Task já que pode ser um processo pesado
     */
    public void iniciarProcessos() {
        /*Para evitar uma exception de Thread temos que limpar o comboBox*/
        cb_cidade.getItems().clear();
        cb_bairro.getItems().clear();
        
        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                cb_cidade.setItems(CidadeDAO.executeQuery(null, CidadeDAO.QUERY_TODOS));
                cb_bairro.setItems(BairroDAO.executeQuery(null, BairroDAO.QUERY_TODOS));
                return null;
            }
        };
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    /**
     * Método para acao do botao salvar
     */
    @FXML
    private void onSave() {
        this.pacienteModel = new PacienteModel();
        pacienteModel.setNome(txt_nome.getText().trim());
        /*Não sei usar a conversão nova*/
        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        //pacienteModel.setNascimento(dp_nascimento.getValue().format(formatter));
        pacienteModel.setEndereco(txt_endereco.getText().trim());
        pacienteModel.setTelefone(txt_telefone.getText().trim());
        pacienteModel.setCep(txt_cep.getText().trim());
        pacienteModel.setDocumento(txt_documento.getText().trim());
        pacienteModel.setSexo((String) cb_sexo.getSelectionModel().getSelectedItem());
        //pacienteModel.setData_cliente(dp_cliente.getValue().format(formatter));
        pacienteModel.setTipo(txt_tipo.getText().trim());
        pacienteModel.setEmail(txt_email.getText().trim());
        pacienteModel.setObs(txt_observacoes.getText().trim());
        CidadeModel cidade = cb_cidade.getSelectionModel().getSelectedItem();
        pacienteModel.setCidadeModel(cidade);
        BairroModel bairro = cb_bairro.getSelectionModel().getSelectedItem();
        pacienteModel.setBairroModel(bairro);
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
        if (PacienteDAO.executeUpdates(pacienteModel, PacienteDAO.CREATE)) {
            limparCampos();
            DialogFX.showMessage("Dados inseridos com sucesso!", "Sucesso", DialogFX.SUCESS);
            
            desabilitarCampos();
        }
    }

    /**
     * Método ação do Botao Novo
     */
    @FXML
    private void onNew() {
        bt_novo.setDisable(false);
        bt_salvar.setDisable(false);
        bt_excluir.setDisable(true);
        habilitarCampos();
        txt_nome.requestFocus();
    }

    /**
     * Método que desativa os botoes editar e excluir
     */
    @FXML
    private void onCancel() {
        desabilitarCampos();
        limparCampos();
        bt_novo.setDisable(false);
        bt_salvar.setDisable(true);
        bt_excluir.setDisable(true);
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
        bt_excluir.setDisable(true);
    }
}
