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
import model.bean.ExameModel;
import model.bean.ValorExameModel;

/**
 *
 * @author jeff-
 */
public class ValorExameDAO {

    public static final int CREATE = 0;
    public static final int DELETE = 1;
    public static final int UPDATE = 2;
    public static final int QUERY_TODOS = 3;

    public static boolean executeUpdates(ValorExameModel vem, int operacao) {
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement ps;
        String sql;
        try {
            switch (operacao) {
                case CREATE:
                    sql = "insert into valor_exame("
                            + "valor_categoria,"
                            + "valor_exame,"
                            + "id_codigo_exame,"
                            + "id_codigo_categoria)"
                            + "values(?,?,?,?)";
                    ps = conexao.prepareStatement(sql);
                    ps.setDouble(1, vem.getValor_categoria());
                    ps.setDouble(2, vem.getValor_exame());
                    ps.setInt(3, vem.getExameModel().getCodigo());
                    ps.setInt(4, vem.getCategoriaModel().getCodigo());
                    ps.executeUpdate();
                    ConnectionFactory.closeConnection(conexao, ps);
                    return true;
                case DELETE:
                    sql = "delete from valor_exame where id_valor=?";
                    ps = conexao.prepareStatement(sql);
                    ps.setInt(1, vem.getCodigo());
                    ps.executeUpdate();
                    ConnectionFactory.closeConnection(conexao, ps);
                    return true;
                case UPDATE:
                    sql = "update valor_exame set "
                            + "valor_categoria=?,"
                            + "valor_exame=?,"
                            + "id_codigo_exame=?,"
                            + "id_codigo_categoria=? "
                            + "where id_valor=?";
                    ps = conexao.prepareStatement(sql);
                    ps.setDouble(1, vem.getValor_categoria());
                    ps.setDouble(2, vem.getValor_exame());
                    ps.setInt(3, vem.getExameModel().getCodigo());
                    ps.setInt(4, vem.getCategoriaModel().getCodigo());
                    ps.setInt(5, vem.getCodigo());
                    ps.executeUpdate();
                    ConnectionFactory.closeConnection(conexao, ps);
                    return true;
                default:
                    conexao.close();
                    return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ValorExameDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public static ObservableList<ValorExameModel> executeQuery(ValorExameModel vem, int operacao) {
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement ps;
        ResultSet rs;
        String sql;
        ObservableList<ValorExameModel> listaValorExame = FXCollections.observableArrayList();
        ValorExameModel valorExameModel;
        try {
            switch (operacao) {
                case QUERY_TODOS:
                    sql = "select * from valor_exame "
                            + "left join exame on id_codigo_exame = id_exame "
                            + "left join categoria on id_codigo_categoria = id_categoria "
                            + "order by id_valor";
                    ps = conexao.prepareStatement(sql);
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        valorExameModel = new ValorExameModel();
                        valorExameModel.setCodigo(rs.getInt("id_valor"));
                        valorExameModel.setValor_categoria(rs.getDouble("valor_categoria"));
                        valorExameModel.setValor_exame(rs.getDouble("valor_exame"));

                        ExameModel exameModel = new ExameModel();
                        exameModel.setCodigo(rs.getInt("id_exame"));
                        exameModel.setDescricao(rs.getString("descricao_exame"));

                        CategoriaModel categoriaModel = new CategoriaModel();
                        categoriaModel.setCodigo(rs.getInt("id_categoria"));
                        categoriaModel.setDescricao(rs.getString("descricao_categoria"));

                        valorExameModel.setExameModel(exameModel);
                        valorExameModel.setCategoriaModel(categoriaModel);

                        listaValorExame.add(valorExameModel);
                    }
                    ConnectionFactory.closeConnection(conexao, ps, rs);
                    return listaValorExame;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ValorExameDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaValorExame;
    }

}
