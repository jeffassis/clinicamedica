/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testes;

import model.bean.MensalidadeModel;
import model.bean.PacienteModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import util.Relatorio;

/**
 *
 * @author jeand
 */
public class TesteRelatorio {
    public static void main(String[] args) throws JRException {
        PacienteModel paciente = new PacienteModel();
        paciente.setCodigo(2);
        MensalidadeModel mensalidade = new MensalidadeModel();
        mensalidade.setPacienteModel(paciente);
        Relatorio.gerarMensalidade(mensalidade);
    }
    
}
