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
import model.bean.ExameModel;

/**
 *
 * @author jeff-
 */
public class ExameDAO {

    /*Declaração das constantes estaticas para ser acessadas de qualquer lugar*/
    public static final int CREATE = 0;
    public static final int DELETE = 1;
    public static final int UPDATE = 2;
    public static final int QUERY_TODOS = 3;

    public static boolean executeUpdates(ExameModel pm, int operacao) {
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement ps;
        String sql;
        try {
            switch (operacao) {
                case CREATE:
                    sql = "insert into exame(descricao_exame)values(?)";
                    ps = conexao.prepareStatement(sql);
                    ps.setString(1, pm.getDescricao());
                    ps.executeUpdate();
                    ConnectionFactory.closeConnection(conexao, ps);
                    return true;
                case DELETE:
                    sql = "delete from exame where id_exame=?";
                    ps = conexao.prepareStatement(sql);
                    ps.setInt(1, pm.getCodigo());
                    ps.executeUpdate();
                    ConnectionFactory.closeConnection(conexao, ps);
                    return true;
                case UPDATE:
                    sql = "update exame set "
                            + "descricao_exame=? "
                            + "where id_exame=?";
                    ps = conexao.prepareStatement(sql);
                    ps.setString(1, pm.getDescricao());
                    ps.setInt(2, pm.getCodigo());
                    ps.executeUpdate();
                    ConnectionFactory.closeConnection(conexao, ps);
                    return true;
                default:
                    conexao.close();
                    return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ExameDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public static ObservableList<ExameModel> executeQuery(ExameModel pm, int operacao) {
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement ps;
        ResultSet rs;
        String sql;
        ObservableList<ExameModel> listaProcedimento = FXCollections.observableArrayList();
        ExameModel procedimentosModel;
        try {
            switch (operacao) {
                case QUERY_TODOS:
                    sql = "select * from exame order by id_exame";
                    ps = conexao.prepareStatement(sql);
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        procedimentosModel = new ExameModel();
                        procedimentosModel.setCodigo(rs.getInt("id_exame"));
                        procedimentosModel.setDescricao(rs.getString("descricao_exame"));
                        listaProcedimento.add(procedimentosModel);
                    }
                    ConnectionFactory.closeConnection(conexao, ps, rs);
                    return listaProcedimento;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ExameDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaProcedimento;
    }
}
