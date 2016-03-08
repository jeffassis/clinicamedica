/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import model.bean.DependenteModel;
import util.Log;

/**
 *
 * @author jeanderson
 */
public class DependentesDAO {

    public static final int CREATE = 0;
    public static final int UPDATE = 1;
    public static final int DELETE = 2;

    public static final int QUERY_TODOS = 3;

    /**
     * Método executa operações de CREATE,UPDATE e DELETE no banco de Dados.
     *
     * @param dm
     * @param operacao
     * @return
     */
    public static boolean executeUpdates(DependenteModel dm, int operacao) {
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement ps;
        String sql;
        try {
            switch (operacao) {
                case CREATE:
                    sql = "insert into dependentes(nome_dependente,telefone_dependente,"
                            + "nascimento_dependente, parentesco_dependente)"
                            + "values(?,?,?,?)";
                    ps = conexao.prepareStatement(sql);
                    ps.setString(1, dm.getNome());
                    ps.setString(2, dm.getTelefone());
                    ps.setString(3, dm.getNascimento());
                    ps.setString(4, dm.getParentesco());
                    ps.executeUpdate();
                    ConnectionFactory.closeConnection(conexao, ps);
                    return true;
                case UPDATE:
                    sql = "update dependentes set nome_dependente = ?"
                            + "telefone_dependente = ?"
                            + "nascimento_dependente = ?"
                            + "parentesco_dependente = ?"
                            + "where id_dependente = ?";
                    ps = conexao.prepareStatement(sql);
                    ps.setString(1, dm.getNome());
                    ps.setString(2, dm.getTelefone());
                    ps.setString(3, dm.getNascimento());
                    ps.setString(4, dm.getParentesco());
                    ps.setInt(5, dm.getCodigo());
                    ps.executeUpdate();
                    ConnectionFactory.closeConnection(conexao, ps);
                    return true;
                case DELETE:
                    sql = "delete from dependentes where id_dependente = ?";
                    ps = conexao.prepareStatement(sql);
                    ps.setInt(1, dm.getCodigo());
                    ps.executeUpdate();
                    ConnectionFactory.closeConnection(conexao, ps);
                    return true;
                default:
                    ConnectionFactory.closeConnection(conexao);
                    return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DependentesDAO.class.getName()).log(Level.SEVERE, null, ex);
            Log.relatarExcecao(DependentesDAO.class.getName(), ex);
            return false;
        }
    }

    public static ObservableList<DependenteModel> executeQuery(DependenteModel dm, int operacao) {
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql;
        ObservableList<DependenteModel> lista = FXCollections.observableArrayList();
        DependenteModel dependente;
        try {
            switch (operacao) {
                case QUERY_TODOS:
                    sql = "select * from dependente left join paciente_dependente"
                            + "on id_dependente = id_codigo_dependente left join paciente"
                            + "on id_codigo_paciente = id_paciente";
                    ps = conexao.prepareStatement(sql);
                    rs = ps.executeQuery();
                    while(rs.next()){
                        dependente = new DependenteModel();
                        dependente.setCodigo(rs.getInt("id_dependente"));
                        dependente.setNome(rs.getString("nome_dependente"));
                    }
                    ConnectionFactory.closeConnection(conexao, ps, rs);
                    return lista;
                default:
                    ConnectionFactory.closeConnection(conexao, ps, rs);
                    return lista;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DependentesDAO.class.getName()).log(Level.SEVERE, null, ex);
            Log.relatarExcecao(DependentesDAO.class.getName(), ex);
            return lista;
        }
    }

}
