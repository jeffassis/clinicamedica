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
import model.bean.BairroModel;
import model.bean.CidadeModel;

/**
 *
 * @author jeff-
 */
public class BairroDAO {

    /*Declaração das constantes estaticas para ser acessadas de qualquer lugar*/
    public static final int CREATE = 0;
    public static final int DELETE = 1;
    public static final int UPDATE = 2;
    public static final int QUERY_TODOS = 3;
    public static final int QUERY_NOME = 4;

    public static boolean executeUpdates(BairroModel bm, int operacao) {
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement ps;
        String sql;
        try {
            switch (operacao) {
                case CREATE:
                    sql = "insert into bairro("
                            + "nome_bairro,"
                            + "id_codigo_cidade)"
                            + "values(?,?)";
                    ps = conexao.prepareStatement(sql);
                    ps.setString(1, bm.getNome());
                    ps.setInt(2, bm.getCidadeModel().getCodigo());
                    ps.executeUpdate();
                    ConnectionFactory.closeConnection(conexao, ps);
                    return true;
                case DELETE:
                    sql = "delete from bairro where id_bairro";
                    ps = conexao.prepareStatement(sql);
                    ps.setInt(1, bm.getCodigo());
                    ps.executeUpdate();
                    ConnectionFactory.closeConnection(conexao, ps);
                    return true;
                case UPDATE:
                    sql = "update bairro set "
                            + "nome_bairro=?,"
                            + "id_codigo_cidade=? "
                            + "where id_bairro=?";
                    ps = conexao.prepareStatement(sql);
                    ps.setString(1, bm.getNome());
                    ps.setInt(2, bm.getCidadeModel().getCodigo());
                    ps.setInt(3, bm.getCodigo());
                    ps.executeUpdate();
                    ConnectionFactory.closeConnection(conexao, ps);
                    return true;
                default:
                    conexao.close();
                    return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(BairroDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public static ObservableList<BairroModel> executeQuery(BairroModel bm, int operacao) {
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement ps;
        ResultSet rs;
        String sql;
        ObservableList<BairroModel> listaBairro = FXCollections.observableArrayList();
        BairroModel bairroModel;
        try {
            switch (operacao) {
                case QUERY_TODOS:
                    sql = "select * from bairro left join cidade on id_codigo_cidade = id_cidade order by id_bairro";
                    ps = conexao.prepareStatement(sql);
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        /*Inicializamos ele aqui e atribuimos os valores a ele*/
                        bairroModel = new BairroModel();
                        bairroModel.setCodigo(rs.getInt("id_bairro"));
                        bairroModel.setNome(rs.getString("nome_bairro"));

                        CidadeModel cidadeModel = new CidadeModel();
                        cidadeModel.setCodigo(rs.getInt("id_cidade"));
                        cidadeModel.setNome(rs.getString("nome_cidade"));
                        cidadeModel.setSigla(rs.getString("sigla_cidade"));
                        /*Colocamos a cidade*/
                        bairroModel.setCidadeModel(cidadeModel);
                        /*adicionamos ele*/
                        listaBairro.add(bairroModel);
                    }
                    ConnectionFactory.closeConnection(conexao, ps, rs);
                    return listaBairro;
            }
        } catch (SQLException ex) {
            Logger.getLogger(BairroDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaBairro;
    }

}
