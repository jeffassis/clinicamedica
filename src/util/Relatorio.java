/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import factory.ConnectionFactory;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.bean.MensalidadeModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

/**
 *
 * @author jeanderson
 */
public class Relatorio {

    /**
     * Gera um relatório da Mensalidade de determinado paciente.
     *
     * @param mensalidade
     * @return - Retorna a Path e o arquivo PDF gerado
     */
    public static String gerarMensalidade(MensalidadeModel mensalidade) {
        try {
            String pasta = Relatorio.class.getClassLoader().getResource("").getPath() + "jasper/";
            String pastaEArquivo = pasta + "Paciente_mensalidade.jrxml";
            Connection conexao = ConnectionFactory.getConnection();
            JasperCompileManager.compileReportToFile(pastaEArquivo);
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("ID_PACIENTE", mensalidade.getPacienteModel().getCodigo());
            JasperPrint print = JasperFillManager.fillReport(pasta + "Paciente_mensalidade.jasper", parametros, conexao);
            String pastaDoPdf = "/Users/" + System.getProperty("user.name") + "/Documents/" + mensalidade.getPacienteModel().getNome() + ".pdf";
            JasperExportManager.exportReportToPdfFile(print, pastaDoPdf);
            conexao.close();
            return pastaDoPdf;
        } catch (JRException | SQLException ex) {
            Logger.getLogger(Relatorio.class.getName()).log(Level.SEVERE, null, ex);
            Log.relatarExcecao(Relatorio.class.getName(), ex);
            return null;
        }
    }

}
