/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author jeanderson
 */
public class AutoCompleteComboBox implements EventHandler<KeyEvent> {

    /*Sera a referencia do Combo passado no construtor*/
    private ComboBox comboBox;
    /*Lista que vai conter os dados originais do comboBox*/
    private ObservableList dados;
    /*Variavel que vai dizer se o método saveData foi executado*/
    private boolean salvouDados;

    public AutoCompleteComboBox(ComboBox comboBox) {
        /*Pego a referencia*/
        this.comboBox = comboBox;
        /*Altero o evento para ele ouvir esta classe uso o KeyReleased
        pq o evento é chamado só apos a palavra está digitada*/
        this.comboBox.setOnKeyReleased(this);
        /*Informamos que o comboBox é editavel para o usuario digitar o nome*/
        this.comboBox.setEditable(true);

    }

    /**
     * Método que salvas os dados do comboBox, devera ser usado apos os dados no
     * comboBox serem populados.
     */
    public void saveData() {
        /*Salvamos os dados originais*/
        dados = comboBox.getItems();
        this.salvouDados = true;

    }

    /**
     * Evento é chamado toda vez que é alterado algum caractere no ComboBox.
     *
     * @param event
     */
    @Override
    public void handle(KeyEvent event) {
        /*So acontecera se o metodo saveData foi executado*/
        if (salvouDados) {
            /*Se foi apertado o botão UP(pra cima)*/
            if (event.getCode() == KeyCode.UP) {
                /*Move o digitador para a ultima opção*/
                moveCaret(comboBox.getEditor().getText().length());
                /*Para aqui*/
                return;
            } else if (event.getCode() == KeyCode.DOWN) {
                /*Se o comboBox não estiver mostrando a lista*/
                if (!comboBox.isShowing()) {
                    comboBox.show();
                }
                moveCaret(comboBox.getEditor().getText().length());
                /*Para aqui*/
                return;
            }
            if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.LEFT
                    || event.isControlDown() || event.getCode() == KeyCode.HOME
                    || event.getCode() == KeyCode.END || event.getCode() == KeyCode.TAB) {
                /*Se for utilizado qualquer uma dessas Teclas paramos o código aqui*/
                return;
            }
            /*Essa lista vai conter os dados que vai ser exibido conforma o usuario digitar*/
            ObservableList lista = FXCollections.observableArrayList();
            for (int i = 0; i < dados.size(); i++) {
                /*Como não sabemos qual o tipo do combobox, usamos o getConverter para retornar a StringConverter que foi adicionado nele
                por meio da Classe ConverterDados, tendo essa String podemos comparar as Inicias dos nome, com o nome digitado no comboBox
                utilizamos o metodo toLowerCase que transforma todas as letras em maiusculas.*/
                if (comboBox.getConverter().toString(dados.get(i)).toLowerCase().startsWith(comboBox.getEditor().getText().trim().toLowerCase())) {
                    /*os Nome que tiveram as mesmas inicias serao adicionadas a lista*/
                    lista.add(dados.get(i));
                }
            }
            /*Salva o texto digitado no comboBox pq vamos utilizar o setItens ai o texto seria perdido*/
            String textoDigitado = comboBox.getEditor().getText();

            /*Alteramos para os itens dessa nova lista, assim ficando somente os que foram encontrados na condição acima (if)*/
            comboBox.setItems(lista);
            /*Colocamos o texto que tinha sido digitado*/
            comboBox.getEditor().setText(textoDigitado);
            /*Movemos o digitador para a ultima posição*/
            moveCaret(textoDigitado.length());
            /*Se a lista não estiver vazia*/
            if (!lista.isEmpty()) {
                comboBox.show();
            }

        }
    }

    /**
     * Move o digitador para determinada posicação.
     *
     * @param length
     */
    private void moveCaret(int length) {
        this.comboBox.getEditor().positionCaret(length);
    }

    /**
     * Adiciona outro comboBox. Obs: Utilizar somente apos o comboBox está com
     * todos os Dados populados.
     *
     * @param comboBox
     */
    public void addComboBox(ComboBox comboBox) {
        AutoCompleteComboBox box = new AutoCompleteComboBox(comboBox);
        box.saveData();
    }

}
