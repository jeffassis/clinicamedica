package model.dao;

import factory.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.bean.MedicoModel;

/**
 * Class de Acesso aos Dados do Objeto Medico
 *
 * @author jeff-
 */
public class MedicoDAO {

    /*Declaração das constantes estaticas para ser acessadas de qualquer lugar*/
    public static final int CREATE = 0;
    public static final int DELETE = 1;
    public static final int UPDATE = 2;
    public static final int QUERY_TODOS = 3;
    public static final int QUERY_NOME = 4;

    /**
     * Metodo booleano para CREATE, DELETE e UPDATE
     *
     * @param mm
     * @param operacao
     * @return
     */
    public boolean executeUpdates(MedicoModel mm, int operacao) {
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement ps;
        String sql;
        try {
            switch (operacao) {
                case CREATE:
                    sql = "insert into medico("
                            + "nome_medico,"
                            + "crm_medico,"
                            + "especialidade_medico)"
                            + "values(?,?,?)";
                    ps = conexao.prepareStatement(sql);
                    ps.setString(1, mm.getNome());
                    ps.setString(2, mm.getCrm());
                    ps.setString(3, mm.getEspecialidade());
                    ps.executeUpdate();
                    ps.close();
                    conexao.close();
                    return true;
                case DELETE:
                    sql = "delete from medico where id_medico=?";
                    ps = conexao.prepareStatement(sql);
                    ps.setInt(1, mm.getCodigo());
                    ps.executeUpdate();
                    ps.close();
                    conexao.close();
                    return true;
                case UPDATE:
                    sql = "update medico set "
                            + "nome_medico=?, "
                            + "crm_medico=?, "
                            + "especialidade_medico=? "
                            + "where id_medico=?";
                    ps = conexao.prepareStatement(sql);
                    ps.setString(1, mm.getNome());
                    ps.setString(2, mm.getCrm());
                    ps.setString(3, mm.getEspecialidade());
                    ps.setInt(4, mm.getCodigo());
                    ps.executeUpdate();
                    ps.close();
                    conexao.close();
                    return true;
                default:
                    conexao.close();
                    return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(MedicoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}
