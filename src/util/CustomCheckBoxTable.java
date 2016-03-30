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

    public CustomCheckBoxTable() {
    }
    
    public CustomCheckBoxTable(final TableColumn<S, T> coluna) {
        this.checkBox = new CheckBox();
        this.checkBox.setAlignment(Pos.CENTER);
        setAlignment(Pos.CENTER);
        this.coluna = coluna;
    }

    

    @Override
    protected void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
        if(item == null || empty){
            setGraphic(null);
        }else{
            BooleanProperty checado = (BooleanProperty)this.coluna.getCellObservableValue(getIndex());
            this.checkBox.setSelected(checado.getValue());
            this.checkBox.selectedProperty().bindBidirectional(checado);
            setGraphic(this.checkBox);
        }
    }
    
    public CheckBox getCheckBox(){
        return this.checkBox;
    }
    
}
