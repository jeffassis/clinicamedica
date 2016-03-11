package model.dao;

import factory.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.bean.MedicoModel;
import util.Log;

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
    public static boolean executeUpdates(MedicoModel mm, int operacao) {
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement ps;
        String sql;
        try {
            switch (operacao) {
                case CREATE:
                    sql = "insert into medico("
                            + "nome_medico,"
                            + "crm_medico,"
                            + "especialidade_medico,"
                            + "telefone_medico,"
                            + "celular_medico)"
                            + "values(?,?,?,?,?)";
                    ps = conexao.prepareStatement(sql);
                    ps.setString(1, mm.getNome());
                    ps.setString(2, mm.getCrm());
                    ps.setString(3, mm.getEspecialidade());
                    ps.setString(4, mm.getTelefone());
                    ps.setString(5, mm.getCelular());
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
                            + "especialidade_medico=?,"
                            + "telefone_medico=?,"
                            + "celular_medico=? "
                            + "where id_medico=?";
                    ps = conexao.prepareStatement(sql);
                    ps.setString(1, mm.getNome());
                    ps.setString(2, mm.getCrm());
                    ps.setString(3, mm.getEspecialidade());
                    ps.setString(4, mm.getTelefone());
                    ps.setString(5, mm.getCelular());
                    ps.setInt(6, mm.getCodigo());
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
            Log.relatarExcecao(MedicoDAO.class.getName(), ex);
            return false;
        }
    }

    /**
     * Método estático que retorna uma observableList com uma pesquisa
     *
     * @param mm
     * @param operacao
     * @return
     */
    public static ObservableList<MedicoModel> executeQuery(MedicoModel mm, int operacao) {
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement ps;
        ResultSet rs;
        String sql;
        ObservableList<MedicoModel> listaMedico = FXCollections.observableArrayList();
        MedicoModel medicoModel;
        try {
            switch (operacao) {
                case QUERY_TODOS:
                    sql = "select * from medico order by id_medico";
                    ps = conexao.prepareStatement(sql);
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        medicoModel = new MedicoModel();
                        medicoModel.setCodigo(rs.getInt("id_medico"));
                        medicoModel.setNome(rs.getString("nome_medico"));
                        medicoModel.setCrm(rs.getString("crm_medico"));
                        medicoModel.setEspecialidade(rs.getString("especialidade_medico"));
                        medicoModel.setTelefone(rs.getString("telefone_medico"));
                        medicoModel.setCelular(rs.getString("celular_medico"));
                        listaMedico.add(medicoModel);
                    }
                    rs.close();
                    ps.close();
                    conexao.close();
                    return listaMedico;
                case QUERY_NOME:
                    sql = "select * from medico where nome_medico = ?";
                    ps = conexao.prepareStatement(sql);
                    ps.setString(1, mm.getNome());
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        medicoModel = new MedicoModel();
                        medicoModel.setCodigo(rs.getInt("id_medico"));
                        medicoModel.setNome(rs.getString("nome_medico"));
                        medicoModel.setCrm(rs.getString("crm_medico"));
                        medicoModel.setEspecialidade(rs.getString("especialidade_medico"));
                        medicoModel.setTelefone(rs.getString("telefone_medico"));
                        medicoModel.setCelular(rs.getString("celular_medico"));
                        listaMedico.add(medicoModel);
                    }
                    rs.close();
                    ps.close();
                    conexao.close();
                    return listaMedico;
            }
        } catch (SQLException ex) {
            Logger.getLogger(MedicoDAO.class.getName()).log(Level.SEVERE, null, ex);
            Log.relatarExcecao(MedicoDAO.class.getName(), ex);
        }
        return listaMedico;
    }
}
