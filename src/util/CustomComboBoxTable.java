/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.BooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;

/**
 *
 * @author jeanderson
 * @param <S>
 * @param <T>
 */
public class CustomComboBoxTable<S, T> extends TableCell<S, T> {

    private ComboBox comboBox;
    private TableColumn<S, T> coluna;
    private ObservableList<String> valores;

    public CustomComboBoxTable() {
    }

    public CustomComboBoxTable(final TableColumn<S, T> coluna) {
        this.comboBox = new ComboBox<>();
        this.coluna = coluna;
        setAlignment(Pos.CENTER);
        this.valores = FXCollections.observableArrayList();
        valores.add("Ativo");
        valores.add("Inativo");
        this.comboBox.setItems(valores);
    }

    @Override
    protected void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
        if (item == null || empty) {
            setGraphic(null);
        } else {
            BooleanProperty valor = (BooleanProperty) this.coluna.getCellObservableValue(getIndex());
            if (valor.get()) {
                comboBox.setValue("Ativo");
            } else {
                comboBox.setValue("Inativo");
            }
            setGraphic(comboBox);
        }
    }

    public ComboBox getComboBox() {
        return this.comboBox;
    }

}
