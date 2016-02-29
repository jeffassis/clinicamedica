/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Classe de Dialogs prontos.
 * @author jeanderson
 */
public class DialogFX {
    
    /*Constantes representado o tipo de Alert*/
    public static final int ATENCAO = 0;
    public static final int ERRO = 1;
    
    /**
     * Exibi uma mensagem do tipo INFORMATION(informação)e não para a execução de comandos.
     * @param msg 
     */
    public static void showMessage(String msg){
        Alert dialog = new Alert(Alert.AlertType.INFORMATION);
        dialog.setTitle("Informação");
        dialog.setHeaderText("");
        dialog.setContentText(msg);
        /*Trocamos o Icone Padrão*/
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(DialogFX.class.getResourceAsStream("/img/information-icon.png")));
        
        dialog.show();
    }
    
    
    
}
