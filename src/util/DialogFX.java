/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Classe de Dialogs prontos.
 *
 * @author jeanderson
 */
public class DialogFX {

    /*Constante para apenas uso interno*/
    private static final int INFOR = 0;
    /*Constantes representado o tipo de Alert*/
    public static final int ATENCAO = 1;
    public static final int ERRO = 2;
    public static final int SUCESS = 3;

    /**
     * Exibi uma mensagem do tipo INFORMATION(informação)e não para a execução
     * de comandos.
     *
     * @param msg
     */
    public static void showMessage(String msg) {
        createDialog(msg, "Informação", "", INFOR).show();
    }

    /**
     * Exibi uma mensagem do tipo INFORMATION(informação)e não para a execução
     * de comandos.
     *
     * @param msg
     * @param title
     */
    public static void showMessage(String msg, String title) {
        createDialog(msg, title, "", INFOR).show();
    }

    /**
     * Exibi uma mensagem e não aguarda(não para execução de comandos).
     *
     * @param msg
     * @param title
     * @param dialogType
     */
    public static void showMessage(String msg, String title, int dialogType) {
        createDialog(msg, title, "", dialogType).show();
    }

    /**
     * Exibi uma mensagem do tipo INFORMATION(informação) e aguarda (Para a
     * execução de comandos até que seja fechado).
     *
     * @param msg
     */
    public static void showMessageAndWait(String msg) {
        createDialog(msg, "Informação", "", INFOR).showAndWait();
    }

    /**
     * Exibi uma mensagem do tipo INFORMATION(informação) e aguarda (Para a
     * execução de comandos até que seja fechado).
     *
     * @param msg
     * @param title
     */
    public static void showMessageAndWait(String msg, String title) {
        createDialog(msg, title, "", INFOR).showAndWait();
    }

    /**
     * Exibi uma mensagem e aguarda(Para execução de comandos).
     *
     * @param msg
     * @param title
     * @param dialogType
     */
    public static void showMessageAndWait(String msg, String title, int dialogType) {
        createDialog(msg, title, "", dialogType).showAndWait();
    }

    /**
     * Método criado para evitar repetições de código. Dúvidas olhe os commit
     * anteriores e compare.
     *
     * @param msg
     * @param title
     * @param header
     * @param dialogType
     * @return
     */
    private static Alert createDialog(String msg, String title, String header, int dialogType) {
        Alert dialog;
        Stage stage;
        switch (dialogType) {
            case INFOR:
                dialog = new Alert(Alert.AlertType.INFORMATION);
                stage = (Stage) dialog.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image(DialogFX.class.getResourceAsStream("/img/information-icon.png")));
                break;
            case ATENCAO:
                dialog = new Alert(Alert.AlertType.WARNING);
                stage = (Stage) dialog.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image(DialogFX.class.getResourceAsStream("/img/warning-icon.png")));
                break;
            case ERRO:
                dialog = new Alert(Alert.AlertType.ERROR);
                stage = (Stage) dialog.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image(DialogFX.class.getResourceAsStream("/img/error-icon.png")));
                break;
            case SUCESS:
                dialog = new Alert(Alert.AlertType.NONE);
                /*Alteramos o icone(Imagem) que aparece junto com o Dialog*/
                Image icon = new Image(DialogFX.class.getResourceAsStream("/img/sucess.png"));
                dialog.setGraphic(new ImageView(icon));
                /*Alteramos o icone da Janela*/
                stage = (Stage) dialog.getDialogPane().getScene().getWindow();
                stage.getIcons().add(icon);
            default:
                dialog = new Alert(Alert.AlertType.NONE);
                dialog.initStyle(StageStyle.UTILITY);
                break;
        }
        dialog.setTitle(title);
        dialog.setHeaderText(header);
        dialog.setContentText(msg);
        return dialog;
    }

}
