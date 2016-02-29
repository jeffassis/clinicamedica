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
 *
 * @author jeanderson
 */
public class DialogFX {

    /*Constantes representado o tipo de Alert*/
    public static final int ATENCAO = 0;
    public static final int ERRO = 1;

    /**
     * Exibi uma mensagem do tipo INFORMATION(informação)e não para a execução
     * de comandos.
     *
     * @param msg
     */
    public static void showMessage(String msg) {
        Alert dialog = new Alert(Alert.AlertType.INFORMATION);
        dialog.setTitle("Informação");
        dialog.setHeaderText("");
        dialog.setContentText(msg);
        /*Trocamos o Icone Padrão*/
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(DialogFX.class.getResourceAsStream("/img/information-icon.png")));

        dialog.show();
    }

    /**
     * Exibi uma mensagem do tipo INFORMATION(informação)e não para a execução
     * de comandos.
     *
     * @param msg
     * @param title
     */
    public static void showMessage(String msg, String title) {
        Alert dialog = new Alert(Alert.AlertType.INFORMATION);
        dialog.setTitle(title);
        dialog.setHeaderText("");
        dialog.setContentText(msg);
        /*Trocamos o Icone Padrão*/
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(DialogFX.class.getResourceAsStream("/img/information-icon.png")));

        dialog.show();
    }

    /**
     * Exibi uma mensagem e não aguarda(não para execução de comandos).
     *
     * @param msg
     * @param title
     * @param dialogType
     */
    public static void showMessage(String msg, String title, int dialogType) {
        Alert dialog;
        Stage stage;
        switch (dialogType) {
            case ATENCAO:
                dialog = new Alert(Alert.AlertType.WARNING);
                break;
            case ERRO:
                dialog = new Alert(Alert.AlertType.ERROR);
                break;
            default:
                dialog = new Alert(Alert.AlertType.INFORMATION);
                stage = (Stage) dialog.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image(DialogFX.class.getResourceAsStream("/img/information-icon.png")));
                break;
        }
        dialog.setTitle(title);
        dialog.setHeaderText("");
        dialog.setContentText(msg);
        dialog.show();

    }

    /**
     * Exibi uma mensagem do tipo INFORMATION(informação) e aguarda (Para a
     * execução de comandos até que seja fechado).
     *
     * @param msg
     */
    public static void showMessageAndWait(String msg) {
        Alert dialog = new Alert(Alert.AlertType.INFORMATION);
        dialog.setTitle("Informação");
        dialog.setHeaderText("");
        dialog.setContentText(msg);
        /*Trocamos o Icone Padrão*/
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(DialogFX.class.getResourceAsStream("/img/information-icon.png")));

        dialog.showAndWait();
    }

    /**
     * Exibi uma mensagem do tipo INFORMATION(informação) e aguarda (Para a
     * execução de comandos até que seja fechado).
     *
     * @param msg
     * @param title
     */
    public static void showMessageAndWait(String msg, String title) {
        Alert dialog = new Alert(Alert.AlertType.INFORMATION);
        dialog.setTitle(title);
        dialog.setHeaderText("");
        dialog.setContentText(msg);
        /*Trocamos o Icone Padrão*/
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(DialogFX.class.getResourceAsStream("/img/information-icon.png")));

        dialog.showAndWait();
    }
    
    /**
     * Exibi uma mensagem e aguarda(Para execução de comandos).
     *
     * @param msg
     * @param title
     * @param dialogType
     */
    public static void showMessageAndWait(String msg, String title, int dialogType) {
        Alert dialog;
        Stage stage;
        switch (dialogType) {
            case ATENCAO:
                dialog = new Alert(Alert.AlertType.WARNING);
                break;
            case ERRO:
                dialog = new Alert(Alert.AlertType.ERROR);
                break;
            default:
                dialog = new Alert(Alert.AlertType.INFORMATION);
                stage = (Stage) dialog.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image(DialogFX.class.getResourceAsStream("/img/information-icon.png")));
                break;
        }
        dialog.setTitle(title);
        dialog.setHeaderText("");
        dialog.setContentText(msg);
        dialog.showAndWait();

    }

}
