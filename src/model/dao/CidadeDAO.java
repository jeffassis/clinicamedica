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
import model.bean.CidadeModel;

/**
 *
 * @author jeff-
 */
public class CidadeDAO {

    /*Declaração das constantes estaticas para ser acessadas de qualquer lugar*/
    public static final int CREATE = 0;
    public static final int DELETE = 1;
    public static final int UPDATE = 2;
    public static final int QUERY_TODOS = 3;
    public static final int QUERY_NOME = 4;

    public static boolean executeUpdates(CidadeModel cm, int operacao) {
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement ps;
        String sql;
        try {
            switch (operacao) {
                case CREATE:
                    sql = "insert into cidade("
                            + "nome_cidade,"
                            + "sigla_cidade)"
                            + "values(?,?)";
                    ps = conexao.prepareStatement(sql);
                    ps.setString(1, cm.getNome());
                    ps.setString(2, cm.getSigla());
                    ps.executeUpdate();
                    ConnectionFactory.closeConnection(conexao, ps);
                    return true;
                case DELETE:
                    sql = "delete from cidade where id_cidade=?";
                    ps = conexao.prepareStatement(sql);
                    ps.setInt(1, cm.getCodigo());
                    ps.executeUpdate();
                    ConnectionFactory.closeConnection(conexao, ps);
                    return true;
                case UPDATE:
                    sql = "update cidade set "
                            + "nome_cidade=?,"
                            + "sigla_cidade=? "
                            + "where id_cidade=?";
                    ps = conexao.prepareStatement(sql);
                    ps.setString(1, cm.getNome());
                    ps.setString(2, cm.getSigla());
                    ps.setInt(3, cm.getCodigo());
                    ps.executeUpdate();
                    ConnectionFactory.closeConnection(conexao, ps);
                    return true;
                default:
                    conexao.close();
                    return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CidadeDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public static ObservableList<CidadeModel> executeQuery(CidadeModel cm, int operacao) {
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement ps;
        ResultSet rs;
        String sql;
        ObservableList<CidadeModel> listaCidade = FXCollections.observableArrayList();
        CidadeModel cidadeModel;
        try {
            switch (operacao) {
                case QUERY_TODOS:
                    sql = "select * from cidade order by id_cidade";
                    ps = conexao.prepareStatement(sql);
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        cidadeModel = new CidadeModel();
                        cidadeModel.setCodigo(rs.getInt("id_cidade"));
                        cidadeModel.setNome(rs.getString("nome_cidade"));
                        cidadeModel.setSigla(rs.getString("sigla_cidade"));
                        listaCidade.add(cidadeModel);
                    }
                    ConnectionFactory.closeConnection(conexao, ps, rs);
                    return listaCidade;
                case QUERY_NOME:
                    sql = "select * from cidade order by nome_cidade";
                    ps = conexao.prepareStatement(sql);
                    ps.setString(1, cm.getNome());
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        cidadeModel = new CidadeModel();
                        cidadeModel.setCodigo(rs.getInt("id_cidade"));
                        cidadeModel.setNome(rs.getString("nome_cidade"));
                        cidadeModel.setSigla(rs.getString("sigla_cidade"));
                        listaCidade.add(cidadeModel);
                    }
                    ConnectionFactory.closeConnection(conexao, ps, rs);
                    return listaCidade;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CidadeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaCidade;
    }
}
