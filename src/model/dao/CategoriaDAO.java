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
import model.bean.CategoriaModel;

/**
 * Class de Acesso ao banco de dados do Objeto Categoria
 *
 * @author jeff-
 */
public class CategoriaDAO {

    public static final int CREATE = 0;
    public static final int DELETE = 1;
    public static final int UPDATE = 2;
    public static final int QUERY_TODOS = 3;

    public static boolean executeUpdates(CategoriaModel cm, int operacao) {
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement ps;
        String sql;
        try {
            switch (operacao) {
                case CREATE:
                    sql = "insert into categoria("
                            + "descricao_categoria)"
                            + "values(?)";
                    ps = conexao.prepareStatement(sql);
                    ps.setString(1, cm.getDescricao());
                    ps.executeUpdate();
                    ConnectionFactory.closeConnection(conexao, ps);
                    return true;
                case DELETE:
                    sql = "delete from categoria where id_categoria=?";
                    ps = conexao.prepareStatement(sql);
                    ps.setInt(1, cm.getCodigo());
                    ps.executeUpdate();
                    ConnectionFactory.closeConnection(conexao, ps);
                    return true;
                case UPDATE:
                    sql = "update categoria set"
                            + "decricao_categoria=? "
                            + "where id_categoria=?";
                    ps = conexao.prepareStatement(sql);
                    ps.setString(1, cm.getDescricao());
                    ps.setInt(2, cm.getCodigo());
                    ps.executeUpdate();
                    ConnectionFactory.closeConnection(conexao, ps);
                    return true;
                default:
                    conexao.close();
                    return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CategoriaDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public static ObservableList executeQuery(CategoriaModel cm, int operacao) {
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement ps;
        ResultSet rs;
        String sql;
        ObservableList<CategoriaModel> listaCategoria = FXCollections.observableArrayList();
        CategoriaModel categoriaModel;
        try {
            switch (operacao) {
                case QUERY_TODOS:
                    sql = "select * from categoria order by id_categoria";
                    ps = conexao.prepareStatement(sql);
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        categoriaModel = new CategoriaModel();
                        categoriaModel.setCodigo(rs.getInt("id_categoria"));
                        categoriaModel.setDescricao(rs.getString("descricao_categoria"));
                        listaCategoria.add(categoriaModel);
                    }
                    ConnectionFactory.closeConnection(conexao, ps, rs);
                    return listaCategoria;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CategoriaDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaCategoria;
    }

}
