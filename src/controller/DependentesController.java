/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import model.bean.PacienteModel;
import model.dao.PacienteDAO;
import util.AutoCompleteComboBox;
import util.ConverterDados;

/**
 *
 * @author jeanderson
 */
public class DependentesController implements Initializable {

    /*Declaração dos componentes*/
    @FXML
    private TextField txt_nome1, txt_nome2, txt_nome3, txt_nome4, txt_nome5, txt_valor;
    @FXML
    private TextField txt_telefone1, txt_telefone2, txt_telefone3, txt_telefone4, txt_telefone5;
    @FXML
    private ComboBox<String> cb_parent1, cb_parent2, cb_parent3, cb_parent4, cb_parent5;
    @FXML
    private ComboBox<PacienteModel> cb_paciente;
    @FXML
    private DatePicker dp_nascimento1, dp_nascimento2, dp_nascimento3, dp_nascimento4, dp_nascimento5;
    @FXML
    private Button bt_salvar, bt_limpar, bt_cancelar;
    /*Classe que vai transforma o comboBox em autoComplete*/
    private AutoCompleteComboBox autoComplete;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> parentesco = FXCollections.observableArrayList();
        /*Adicionei no masculino e feminino se quiser alterar pode arrochar :)*/
        parentesco.addAll("Pai", "Mãe", "Filho", "Filha", "Irmão", "Irmã", "Primo", "Prima", "Tio", "Tia", "Sobrinho", "Sobrinha");
        cb_parent1.setItems(parentesco);
        cb_parent2.setItems(parentesco);
        cb_parent3.setItems(parentesco);
        cb_parent4.setItems(parentesco);
        cb_parent5.setItems(parentesco);
        /*Qual o dado que será mostrado no combo*/
        cb_paciente.setConverter(new ConverterDados(ConverterDados.GET_PACIENTE_NOME).getPacienteConverter());
        /*Passamos o ComboBox para o construtor, mas falta utilizar o método saveDate() para ter efeito*/
        this.autoComplete = new AutoCompleteComboBox(cb_paciente);
        /*Botão salvar inicia bloqueado*/
        bt_salvar.setDisable(true);
        
        /*Adicionamos um evento que vai ser chamado quando o usuario digitar, esse evento
        é apenas mostrar o valor que nem está na imagem 1 - depentende 50,00 2 - 70,00*/
        txt_nome1.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            /*Se digitar uma letra*/
            if (newValue.length() == 1) {
                txt_valor.setText("50,00");
                /*Ativa o botão novamente*/
                bt_salvar.setDisable(false);
            } else if (newValue.length() == 0) {
                txt_valor.setText("0,00");
                bt_salvar.setDisable(true);
            }
        });
        txt_nome2.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            /*Se digitar uma letra*/
            if (newValue.length() == 1) {
                txt_valor.setText("70,00");
            } else if (newValue.length() == 0) {
                txt_valor.setText("50,00");
            }
        });
        txt_nome5.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            /*Se digitar uma letra*/
            if (newValue.length() == 1) {
                txt_valor.setText("90,00");
            } else if (newValue.length() == 0) {
                txt_valor.setText("70,00");
            }
        });

    }

    /**
     * Inicializa Processos importantes.
     */
    public void iniciarProcessos() {
        /*Para evitar exceções*/
        cb_paciente.getItems().clear();

        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                cb_paciente.setItems(PacienteDAO.executeQuery(null, PacienteDAO.QUERY_TODOS));
                /*Esse método que coloca o autoComplete em ação so deve ser chamado apos os dados estarem no comboBox*/
                autoComplete.saveData();
                return null;
            }
        };
        Thread t = new Thread(task);
        t.setDaemon(true);
        t.start();
    }

    /**
     * Método limpa todos os campos.
     */
    @FXML
    private void limparCampos() {
        txt_nome1.setText("");
        txt_nome2.setText("");
        txt_nome3.setText("");
        txt_nome4.setText("");
        txt_nome5.setText("");
        dp_nascimento1.getEditor().setText("");
        dp_nascimento2.getEditor().setText("");
        dp_nascimento3.getEditor().setText("");
        dp_nascimento4.getEditor().setText("");
        dp_nascimento5.getEditor().setText("");
        cb_paciente.getSelectionModel().clearSelection();
        cb_parent1.getSelectionModel().clearSelection();
        cb_parent2.getSelectionModel().clearSelection();
        cb_parent3.getSelectionModel().clearSelection();
        cb_parent4.getSelectionModel().clearSelection();
        cb_parent5.getSelectionModel().clearSelection();
        txt_valor.setText("0,00");
        txt_telefone1.setText("");
        txt_telefone2.setText("");
        txt_telefone3.setText("");
        txt_telefone4.setText("");
        txt_telefone5.setText("");

    }

    /**
     * Método fecha a janela.
     *
     * @param evento
     */
    @FXML
    private void onCancelar(ActionEvent evento) {
        /*Fecha a janela*/
        ((Node) evento.getSource()).getScene().getWindow().hide();
    }
    /**
     * Método reiniciar os dados da tela.
     */
    public void refresh(){
        limparCampos();
    }

}
