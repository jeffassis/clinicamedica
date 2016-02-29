/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
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
    private static final int CONFIRMACAO = 1;
    /*Constantes representado o tipo de Alert*/
    public static final int ATENCAO = 2;
    public static final int ERRO = 3;
    public static final int SUCESS = 4;

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

                dialog.getButtonTypes().add(new ButtonType("OK", ButtonBar.ButtonData.CANCEL_CLOSE));
                break;
            case CONFIRMACAO:
                dialog = new Alert(Alert.AlertType.CONFIRMATION);
                stage = (Stage) dialog.getDialogPane().getScene().getWindow();
                stage.getIcons().add(new Image(DialogFX.class.getResourceAsStream("/img/confirmation-icon.png")));
                break;
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
     * Exibi uma Tela de confirmação. Retorna true caso seja selecionado sim
     *
     * @param question
     * @return
     */
    public static boolean showConfirmation(String question) {
        Alert dialog = createDialog(question, "Mensagem", "", CONFIRMACAO);
        /*Criamos os botões personalizados*/
        ButtonType btnSim = new ButtonType("Sim", ButtonBar.ButtonData.YES);
        ButtonType btnNao = new ButtonType("Não", ButtonBar.ButtonData.NO);
        ButtonType btnCancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
        /*Substituimos todos os botões do Dialog pelo o nosso*/
        dialog.getButtonTypes().setAll(btnSim, btnNao, btnCancelar);
        Optional<ButtonType> resultado = dialog.showAndWait();
        return resultado.get() == btnSim;
    }

    /**
     * Exibi uma Tela de confirmação. Retorna true caso seja selecionado sim
     *
     * @param question
     * @param title
     * @return
     */
    public static boolean showConfirmation(String question, String title) {
        Alert dialog = createDialog(question, title, "", CONFIRMACAO);
        ButtonType btnSim = new ButtonType("Sim", ButtonBar.ButtonData.YES);
        ButtonType btnNao = new ButtonType("Não", ButtonBar.ButtonData.NO);
        ButtonType btnCancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getButtonTypes().setAll(btnSim, btnNao, btnCancelar);
        Optional<ButtonType> resultado = dialog.showAndWait();
        return resultado.get() == btnSim;
    }

}
