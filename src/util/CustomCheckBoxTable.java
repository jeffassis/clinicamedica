/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import javafx.beans.property.BooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;

/**
 *
 * @author jeanderson
 * @param <S>
 * @param <T>
 */
public class CustomCheckBoxTable<S,T> extends TableCell<S, T> {
    private CheckBox checkBox;
    private TableColumn<S,T> coluna;

    /**
     * Obs: Não utilize esse construtor padrão.
     */
    public CustomCheckBoxTable() {
    }
    
    /**
     * Passe a Coluna da Tabela onde está o checkBox.
     * @param coluna 
     */
    public CustomCheckBoxTable(final TableColumn<S, T> coluna) {
        /*Instanciamos o checkBox*/
        this.checkBox = new CheckBox();
        /*Informamos que a celula deve ficar no centro da coluna*/
        setAlignment(Pos.CENTER);
        this.coluna = coluna;
    }

    

    /**
     * Método é chamado a cada alteração na Celula da Coluna.
     * @param item
     * @param empty 
     */
    @Override
    protected void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
        if(item == null || empty){
            setGraphic(null);
        }else{
            /*Criamos uma variavel do tipo Boolean, mas property pois precisamos saber quando
            algum valor do checkBox e alterado. O getIndex retorna em qual linha da coluna foi alterado*/
            BooleanProperty checado = (BooleanProperty)this.coluna.getCellObservableValue(getIndex());
            /*Colocamos o checkBox para ficar selecionado ou nao de acordo com o clique do usuario*/
            this.checkBox.setSelected(checado.getValue());
            /*Colocamos o checkBox na celula*/
            setGraphic(this.checkBox);
        }
    }
    /**
     * retorna o CheckBox da Classe.
     * @return 
     */
    public CheckBox getCheckBox(){
        return this.checkBox;
    }
    
}
