/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
import model.bean.DependenteModel;
import model.bean.PacienteModel;
import model.dao.DependentesDAO;
import model.dao.PacienteDAO;
import util.AutoCompleteComboBox;
import util.ConverterDados;
import util.DialogFX;
import util.MaskFormatter;

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
    /*Mascara para os campos*/
    private MaskFormatter formatter;

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
        /*Adicionando as mascaras*/
        formatter = new MaskFormatter(txt_telefone1);
        formatter.setMask(MaskFormatter.TEL_8DIG);
        formatter.showMask();
        formatter.addComponente(txt_telefone2, MaskFormatter.TEL_8DIG, true);
        formatter.addComponente(txt_telefone3, MaskFormatter.TEL_8DIG, true);
        formatter.addComponente(txt_telefone4, MaskFormatter.TEL_8DIG, true);
        formatter.addComponente(txt_telefone5, MaskFormatter.TEL_8DIG, true);
        formatter.addComponente(dp_nascimento1, MaskFormatter.DATA_BARRA, true);
        formatter.addComponente(dp_nascimento2, MaskFormatter.DATA_BARRA, true);
        formatter.addComponente(dp_nascimento3, MaskFormatter.DATA_BARRA, true);
        formatter.addComponente(dp_nascimento4, MaskFormatter.DATA_BARRA, true);
        formatter.addComponente(dp_nascimento5, MaskFormatter.DATA_BARRA, true);
        

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
     * Evento do botão salvar.
     */
    @FXML
    private void onSave() {
        if (verificarCampos()) {
            DialogFX.showMessage("Por favor verifique se os campos estão preenchidos corretamente", "Campo não preenchido", DialogFX.ATENCAO);
        } else {
            /*Lista que vai conter nossos dependentes*/
            List<DependenteModel> lista = new ArrayList<>();
            /*Para evitar repetições de código usaremos um for, assim como o verificar Campos*/
            TextField[] textFields = {txt_nome1, txt_nome2, txt_nome3, txt_nome4, txt_nome5};
            TextField[] telefonesFields = {txt_telefone1, txt_telefone2, txt_telefone3, txt_telefone4, txt_telefone5};
            ComboBox[] boxs = {cb_parent1, cb_parent2, cb_parent3, cb_parent4, cb_parent5};
            DatePicker[] pickers = {dp_nascimento1, dp_nascimento2, dp_nascimento3, dp_nascimento4, dp_nascimento5};
            for (int i = 0; i < textFields.length; i++) {
                /*Se o campo nome não estiver vazio*/
                if (!textFields[i].getText().isEmpty()) {
                    DependenteModel dependenteModel = new DependenteModel();
                    /*Adicionamos o paciente selecionado ao dependente*/
                    dependenteModel.setPacienteModel(cb_paciente.getSelectionModel().getSelectedItem());
                    dependenteModel.setNome(textFields[i].getText().trim());
                    dependenteModel.setTelefone(telefonesFields[i].getText().trim());
                    dependenteModel.setParentesco(boxs[i].getSelectionModel().getSelectedItem().toString());
                    dependenteModel.setNascimento(pickers[i].getEditor().getText());
                    /*adicionamos a nossa lista*/
                    lista.add(dependenteModel);
                }
            }
            /*Utilizando o metodo MultiUpdates adicionamos todos eles ao banco de dados*/
            if (DependentesDAO.executeMultiUpdates(lista)) {
                limparCampos();
                DialogFX.showMessage("Dependentes Adicionados com sucesso!", "Sucesso", DialogFX.SUCESS);
            } else {
                DialogFX.showMessage("Houve um erro ao adicionar os dependentes", "ERRO", DialogFX.ERRO);
            }

        }
    }

    /**
     * Método reiniciar os dados da tela.
     */
    public void refresh() {
        limparCampos();
    }

    /**
     * Método que verifica todos os campos.
     *
     * @return
     */
    private boolean verificarCampos() {
        /*Verificamos se o paciente foi selecionado, se não foi retornamos true*/
        if (cb_paciente.getSelectionModel().isSelected(-1)) {
            return true;
        }
        /*Verificação do primeiro dependente*/
        if (txt_nome1.getText().isEmpty()) {
            return true;
        } else if (txt_telefone1.getText().isEmpty() && cb_parent1.getSelectionModel().isSelected(-1)
                && dp_nascimento1.getEditor().getText().length() < 10) {
            return true;
        }
        /*Adicionamos nossos componentes a um array do mesmo tipo*/
        TextField[] textFields = {txt_nome2, txt_nome3, txt_nome4, txt_nome5};
        TextField[] telefonesFields = {txt_telefone2, txt_telefone3, txt_telefone4, txt_telefone5};
        ComboBox[] boxs = {cb_parent2, cb_parent3, cb_parent4, cb_parent5};
        DatePicker[] pickers = {dp_nascimento2, dp_nascimento3, dp_nascimento4, dp_nascimento5};
        /*Vamos usar o for para verificar os campos*/
        for (int i = 0; i < textFields.length; i++) {
            /*Se o campo Nome não esta vazio, então vamos verificar seus campos*/
            if (!textFields[i].getText().isEmpty()) {
                /*Verificamos da mesma maneira que o primeiro, veja q usando essa maneira
                evitamos repetições de código*/
                if (telefonesFields[i].getText().isEmpty() && boxs[i].getSelectionModel().isSelected(-1)
                        && pickers[i].getEditor().getText().length() < 10) {
                    return true;
                }
            }
        }
        return false;

    }

}
