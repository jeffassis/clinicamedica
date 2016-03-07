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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import model.bean.PacienteModel;
import model.dao.PacienteDAO;
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
    }

    public void iniciarProcessos() {
        /*Para evitar exceções*/
        cb_paciente.getItems().clear();

        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                cb_paciente.setItems(PacienteDAO.executeQuery(null, PacienteDAO.QUERY_TODOS));
                return null;
            }
        };
        Thread t = new Thread(task);
        t.setDaemon(true);
        t.start();

        /*Adicionamos um evento que vai ser chamado quando o usuario digitar, esse evento
        é apenas mostrar o valor que nem está na imagem 1 - depentende 50,00 2 - 70,00*/
        txt_nome1.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            /*Se digitar uma letra*/
            if (newValue.length() == 1) {
                txt_valor.setText("50,00");
            } else if (newValue.length() == 0) {
                txt_valor.setText("0,00");
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

}
