package model.dao;

import factory.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.bean.PacienteModel;

/**
 *
 * @author jeff-
 */
public class PacienteDAO {

    /*Declaração das constantes estaticas para ser acessadas de qualquer lugar*/
    public static final int CREATE = 0;
    public static final int DELETE = 1;
    public static final int UPDATE = 2;
    public static final int QUERY_TODOS = 3;
    public static final int QUERY_NOME = 4;

    public static boolean executeUpdates(PacienteModel pm, int operacao) {
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement ps;
        String sql;
        try {
            switch (operacao) {
                case CREATE:
                    sql = "insert into paciente("
                            + "nome_paciente,"
                            + "nascimento_paciente,"
                            + "endereco_paciente,"
                            + "telefone_paciente,"
                            + "cep_paciente,"
                            + "documento_paciente,"
                            + "sexo_paciente,"
                            + "data_cliente_paciente,"
                            + "tipo_paciente,"
                            + "email_paciente,"
                            + "obs_paciente,"
                            + "id_codigo_cidade,"
                            + "id_codigo_bairro)"
                            + "values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
                    ps = conexao.prepareStatement(sql);
                    ps.setString(1, pm.getNome());
                    ps.setString(2, pm.getNascimento());
                    
            }
        } catch (SQLException ex) {
            Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

}
