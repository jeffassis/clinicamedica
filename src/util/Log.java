/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Classe responsavel pelo Log's.
 *
 * @author jeanderson
 */
public class Log {

    /**
     * Gera um arquivo log dentro da Pasta Logger que fica na Pasta principal do
     * Usuario.
     *
     * @param className
     * @param ex
     */
    public static void relatarExcecao(String className, Exception ex) {
        try {
            /*Informamos qual o nome do Logger, que no caso vai ser o nome da Classe
           que acontecerá a exceção*/
            Logger log = Logger.getLogger(className);
            /*Variavel que vai conter qual a pasta do sistema que liga ao usuario,
            por padrão será do sistema operacional Windows*/
            String systemPath = "/Users/";
            /*Se for outro sistema operacional*/
            if (System.getProperty("os.name").startsWith("Linux")) {
                systemPath = "/home/";
            }
            /*Pasta onde vamos colocar os Logs*/
            File pastaLog = new File(systemPath + System.getProperty("user.name") + "/Logger");
            if (!pastaLog.exists()) {
                pastaLog.mkdir();
            }
            /*vamos formatar a data de quando ocorreu a exceção.*/
            SimpleDateFormat dataFormato = new SimpleDateFormat("d-M_HHmmss");
            /*variavel que vai conter o diretorio e o arquivo. o nome do arquivo será gerado com a Data da execução*/
            String arquivoDir = pastaLog.getAbsolutePath() + "/My_log_" + dataFormato.format(Calendar.getInstance().getTime()) + ".log";
            /*Classe responsavel por escrever o arquivo*/
            FileHandler escrever = new FileHandler(arquivoDir, true);
            /*Precisamos informar como será escrito(formato) as exceções, Vamos Utilizar uma Classe já pronta para isso
            a Classe SimpleFormatter*/
            escrever.setFormatter(new SimpleFormatter());
            /*Adicionamos ao nosso log a Classe que vai escrever a exceção que for gerada*/
            log.addHandler(escrever);
            
            /*Geramos o Log, passamos que será de Nivel Severe(Alto), e passamos a exceção para ele*/
            log.log(Level.SEVERE, null, ex);

            /*Finalizamos a escrita*/
            escrever.flush();
            escrever.close();

        } catch (IOException | SecurityException e) {
            Logger.getLogger(Log.class.getName()).log(Level.SEVERE, null, e);
        }
    }

}
