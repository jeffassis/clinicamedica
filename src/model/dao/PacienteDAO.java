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
                    ps.setString(3, pm.getEndereco());
                    ps.setString(4, pm.getTelefone());
                    ps.setString(5, pm.getCep());
                    ps.setString(6, pm.getDocumento());
                    ps.setString(7, pm.getSexo());
                    ps.setString(8, pm.getData_cliente());
                    ps.setString(9, pm.getTipo());
                    ps.setString(10, pm.getEmail());
                    ps.setString(11, pm.getObs());
                    ps.setInt(12, pm.getCidadeModel().getCodigo());
                    ps.setInt(13, pm.getBairroModel().getCodigo());
                    ps.executeUpdate();
                    ConnectionFactory.closeConnection(conexao, ps);
                    return true;
                case DELETE:
                    sql = "delete from paciente where id_paciente=?";
                    ps = conexao.prepareStatement(sql);
                    ps.setInt(1, pm.getCodigo());
                    ps.executeUpdate();
                    ConnectionFactory.closeConnection(conexao, ps);
                    return true;
                case UPDATE:
                    sql = "update paciente set "
                            + "nome_paciente=?,"
                            + "nascimento_paciente=?,"
                            + "endereco_paciente=?,"
                            + "telefone_paciente=?,"
                            + "cep_paciente=?,"
                            + "documento_paciente=?,"
                            + "sexo_paciente=?,"
                            + "data_cliente_paciente=?,"
                            + "tipo_paciente=?,"
                            + "email_paciente=?,"
                            + "obs_paciente=?,"
                            + "id_codigo_cidade=?,"
                            + "id_codigo_bairro=? "
                            + "where id_paciente=?";
                    ps = conexao.prepareStatement(sql);
                    ps.setString(1, pm.getNome());
                    ps.setString(2, pm.getNascimento());
                    ps.setString(3, pm.getEndereco());
                    ps.setString(4, pm.getTelefone());
                    ps.setString(5, pm.getCep());
                    ps.setString(6, pm.getDocumento());
                    ps.setString(7, pm.getSexo());
                    ps.setString(8, pm.getData_cliente());
                    ps.setString(9, pm.getTipo());
                    ps.setString(10, pm.getEmail());
                    ps.setString(11, pm.getObs());
                    ps.setInt(12, pm.getCidadeModel().getCodigo());
                    ps.setInt(13, pm.getBairroModel().getCodigo());
                    ps.setInt(14, pm.getCodigo());
                    ps.executeUpdate();
                    ConnectionFactory.closeConnection(conexao, ps);
                    return true;
                default:
                    conexao.close();
                    return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
