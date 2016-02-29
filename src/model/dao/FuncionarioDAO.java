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
import model.bean.FuncionarioModel;
import util.Log;

/**
 * Class de Acesso aos Dados do Objeto Funcionario
 *
 * @author jeff-
 */
public class FuncionarioDAO {

    /*Declaração das constantes estaticas para ser acessadas de qualquer lugar*/
    public static final int CREATE = 0;
    public static final int DELETE = 1;
    public static final int UPDATE = 2;
    public static final int QUERY_TODOS = 3;
    public static final int QUERY_NOME = 4;

    /**
     * Metodo booleano para CREATE, DELETE e UPDATE
     *
     * @param fm
     * @param operacao
     * @return
     */
    public static boolean executeUpdates(FuncionarioModel fm, int operacao) {
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement ps;
        String sql;
        try {
            switch (operacao) {
                case CREATE:
                    sql = "insert into funcionario("
                            + "nome_funcionario,"
                            + "senha_funcionario,"
                            + "permissao_funcionario)"
                            + "values(?,?,?)";
                    ps = conexao.prepareStatement(sql);
                    ps.setString(1, fm.getNome());
                    ps.setString(2, fm.getSenha());
                    ps.setString(3, fm.getPermissao());
                    ps.executeUpdate();
                    ps.close();
                    conexao.close();
                    return true;
                case DELETE:
                    sql = "delete from funcionario where id_funcionario=?";
                    ps = conexao.prepareStatement(sql);
                    ps.setInt(1, fm.getCodigo());
                    ps.executeUpdate();
                    ps.close();
                    conexao.close();
                    return true;
                case UPDATE:
                    sql = "update funcionario set "
                            + "nome_funcionario=?,"
                            + "senha_funcionario=?,"
                            + "permissao_funcionario=? "
                            + "where id_funcionario = ?";
                    ps = conexao.prepareStatement(sql);
                    ps.setString(1, fm.getNome());
                    ps.setString(2, fm.getSenha());
                    ps.setString(3, fm.getPermissao());
                    ps.setInt(4, fm.getCodigo());
                    ps.executeUpdate();
                    ps.close();
                    conexao.close();
                    return true;
                default:
                    conexao.close();
                    return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(FuncionarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            Log.relatarExcecao(FuncionarioDAO.class.getName(), ex);
            return false;
        }
    }

    public static ObservableList<FuncionarioModel> executeQuery(FuncionarioModel fm, int operacao) {
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement ps;
        ResultSet rs;
        String sql;
        ObservableList<FuncionarioModel> listaFunc = FXCollections.observableArrayList();
        FuncionarioModel funcionarioModel;
        try {
            switch (operacao) {
                case QUERY_TODOS:
                    sql = "select * from funcionario order by id_funcionario";
                    ps = conexao.prepareStatement(sql);
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        funcionarioModel = new FuncionarioModel();
                        funcionarioModel.setCodigo(rs.getInt("id_funcionario"));
                        funcionarioModel.setNome(rs.getString("nome_funcionario"));
                        funcionarioModel.setSenha(rs.getString("senha_funcionario"));
                        funcionarioModel.setPermissao(rs.getString("permissao_funcionario"));
                        listaFunc.add(funcionarioModel);
                    }
                    ConnectionFactory.closeConnection(conexao, ps, rs);
                    return listaFunc;
                case QUERY_NOME:
                    sql = "select * from funcionario order by nome_funcionario";
                    ps = conexao.prepareStatement(sql);
                    ps.setString(1, fm.getNome());
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        funcionarioModel = new FuncionarioModel();
                        funcionarioModel.setCodigo(rs.getInt("id_funcionario"));
                        funcionarioModel.setNome(rs.getString("nome_funcionario"));
                        funcionarioModel.setSenha(rs.getString("senha_funcionario"));
                        funcionarioModel.setPermissao(rs.getString("permissao_funcionario"));
                        listaFunc.add(funcionarioModel);
                    }
                    ConnectionFactory.closeConnection(conexao, ps, rs);
                    return listaFunc;
            }
        } catch (SQLException ex) {
            Logger.getLogger(FuncionarioDAO.class.getName()).log(Level.SEVERE, null, ex);
            Log.relatarExcecao(FuncionarioDAO.class.getName(), ex);
        }
        return listaFunc;
    }
}
