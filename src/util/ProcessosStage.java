/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author jeanderson
 */
public class ProcessosStage {

    private boolean abriuTela, editar, modality;
    private final String urlFxml;
    private String urlIcon, titleStage;
    private Stage palco, primaryStage;
    private Object controller;

    public ProcessosStage(String urlFXML, Object controller) {
        this.urlFxml = urlFXML;
        this.controller = controller;
    }

    /**
     * Exibi a Tela, deve preencher algumas configurações nos parâmetros, Obs:
     * os campos podem ser null.
     *
     * @param dados
     * @param referencia1
     * @param referencia2
     */
    public void show(Object dados, Object referencia1, Object referencia2) {
        if (!abriuTela) {
            try {
                FXMLLoader carregar = new FXMLLoader(getClass().getResource(urlFxml));
                palco = new Stage();
                Parent root = carregar.load();
                Scene cena = new Scene(root);
                palco.setMaximized(false);
                palco.setResizable(false);
                palco.setScene(cena);
                if (modality) {
                    palco.initModality(Modality.APPLICATION_MODAL);
                }
                if(titleStage != null){
                palco.setTitle(titleStage);
                }
                if (primaryStage != null) {
                    palco.initOwner(primaryStage);
                }
                if (urlIcon == null) {
                    urlIcon = "/img/coracao_icon.png";
                }
                palco.getIcons().add(new Image(getClass().getResourceAsStream(urlIcon)));
                
                controller = carregar.getController();
                if (controller instanceof Funcionalidades) {
                    /*Se for uma instacia de Funcionalidades, então ele vai chamar os metodos da Classe
                    se a classe controller nao tiver implementado algum ou nenhum desses metodos, então
                    nada acontecerá, pois na Classe Pai esses metodos nao tem nenhuma função implementada*/
                    Funcionalidades funcoes = (Funcionalidades) controller;
                    funcoes.carregarTabela();
                    funcoes.iniciarProcessos();
                    funcoes.iniciarProcessos(referencia1);
                    funcoes.iniciarProcessos(referencia1, referencia2);
                    funcoes.pegarReferencia(referencia1);
                    funcoes.iniciarProcessos(editar, dados);
                }
                palco.show();
                abriuTela = true;
            } catch (IOException ex) {
                Logger.getLogger(ProcessosStage.class.getName()).log(Level.SEVERE, null, ex);
                Log.relatarExcecao(ProcessosStage.class.getName(), ex);
            }
        } else {
            if (controller instanceof Funcionalidades) {
                Funcionalidades funcao = (Funcionalidades) controller;
                funcao.refresh();
                funcao.carregarTabela();
                funcao.iniciarProcessos();
                funcao.iniciarProcessos(referencia1);
                funcao.iniciarProcessos(referencia1, referencia2);
                funcao.iniciarProcessos(editar, dados);
            }
            palco.show();
            palco.requestFocus();
        }
    }

    /**
     * Método retorna o Stage da Classe
     *
     * @return
     */
    public Stage getStage() {
        return this.palco;
    }

    /**
     * Retorna um Controller.
     *
     * @return
     */
    public Object getController() {
        return this.controller;
    }

    /**
     * Método adiciona o primaryStage como initOwner.
     *
     * @param primaryStage
     */
    public void addPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    /**
     * Adiciona um Icone a Janela.
     *
     * @param urlIcon
     */
    public void addIcon(String urlIcon) {
        this.urlIcon = urlIcon;
    }
    
    /**
     * Adiciona um Titulo a Tela.
     * @param titleStage 
     */
    public void addTitle(String titleStage){
        this.titleStage = titleStage;
    }
    
    /**
     * Exibir a Tela para editar dados.
     * @param editar 
     */
    public void setEditar(boolean editar){
        this.editar = editar;
    }
    /**
     * Exibir a Tela com a configuração de APLICATION_MODAL.
     * @param aplicationModal 
     */
    public void setModality(boolean aplicationModal){
        this.modality = aplicationModal;
    }
    
}
