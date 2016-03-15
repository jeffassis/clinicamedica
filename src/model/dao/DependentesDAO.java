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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.bean.DependenteModel;
import model.bean.PacienteModel;
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
     * Passe o ID do paciente e retornarar o Dependente relacionado a ele.
     */
    public static final int QUERY_PACIENTE = 4;
    /**
     * Passe o ID do dependente e retornarar o paciente Relacionado a ele.
     */
    public static final int QUERY_DEPENDENTE = 5;
    public static final int QUERY_NOME = 6;

    /**
     * Método executa operações de CREATE,UPDATE e DELETE no banco de Dados.
     * Obs: não adiciona o Dependente a um Paciente.
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
                    sql = "insert into dependente(nome_dependente,telefone_dependente,"
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
                    sql = "update dependente set nome_dependente = ?"
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
                    sql = "delete from dependente where id_dependente = ?";
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

    /**
     * Método executa operações de QUERY no banco de Dados.
     *
     * @param dm
     * @param operacao
     * @return
     */
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
                            + " on id_dependente = id_codigo_dependente left join paciente"
                            + " on id_codigo_paciente = id_paciente";
                    ps = conexao.prepareStatement(sql);
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        dependente = new DependenteModel();
                        dependente.setCodigo(rs.getInt("id_dependente"));
                        dependente.setNome(rs.getString("nome_dependente"));
                        dependente.setNascimento(rs.getString("nascimento_dependente"));
                        dependente.setTelefone(rs.getString("telefone_dependente"));
                        dependente.setParentesco(rs.getString("parentesco_dependente"));
                        /*Paciente que esta relacionado com os dependentes*/
                        PacienteModel pacienteModel = new PacienteModel();
                        pacienteModel.setCodigo(rs.getInt("id_paciente"));
                        pacienteModel.setNome(rs.getString("nome_paciente"));
                        pacienteModel.setNascimento(rs.getString("nascimento_paciente"));
                        pacienteModel.setEndereco(rs.getString("endereco_paciente"));
                        pacienteModel.setTelefone(rs.getString("telefone_paciente"));
                        pacienteModel.setCep(rs.getString("cep_paciente"));
                        pacienteModel.setDocumento(rs.getString("documento_paciente"));
                        pacienteModel.setSexo(rs.getString("sexo_paciente"));
                        pacienteModel.setData_cliente(rs.getString("data_cliente_paciente"));
                        pacienteModel.setTipo(rs.getString("tipo_paciente"));
                        pacienteModel.setEmail(rs.getString("email_paciente"));
                        pacienteModel.setObs(rs.getString("obs_paciente"));

                        dependente.setPacienteModel(pacienteModel);

                        lista.add(dependente);
                    }
                    ConnectionFactory.closeConnection(conexao, ps, rs);
                    return lista;
                case QUERY_PACIENTE:
                    sql = "select * from dependente left join paciente_dependente"
                            + " on id_dependente = id_codigo_dependente left join paciente"
                            + " on id_codigo_paciente = id_paciente where id_paciente = ?";
                    ps = conexao.prepareStatement(sql);
                    ps.setInt(1, dm.getPacienteModel().getCodigo());
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        dependente = new DependenteModel();
                        dependente.setCodigo(rs.getInt("id_dependente"));
                        dependente.setNome(rs.getString("nome_dependente"));
                        dependente.setNascimento(rs.getString("nascimento_dependente"));
                        dependente.setTelefone(rs.getString("telefone_dependente"));
                        dependente.setParentesco(rs.getString("parentesco_dependente"));
                        /*Paciente que esta relacionado com os dependentes*/
                        PacienteModel pacienteModel = new PacienteModel();
                        pacienteModel.setCodigo(rs.getInt("id_paciente"));
                        pacienteModel.setNome(rs.getString("nome_paciente"));
                        pacienteModel.setNascimento(rs.getString("nascimento_paciente"));
                        pacienteModel.setEndereco(rs.getString("endereco_paciente"));
                        pacienteModel.setTelefone(rs.getString("telefone_paciente"));
                        pacienteModel.setCep(rs.getString("cep_paciente"));
                        pacienteModel.setDocumento(rs.getString("documento_paciente"));
                        pacienteModel.setSexo(rs.getString("sexo_paciente"));
                        pacienteModel.setData_cliente(rs.getString("data_cliente_paciente"));
                        pacienteModel.setTipo(rs.getString("tipo_paciente"));
                        pacienteModel.setEmail(rs.getString("email_paciente"));
                        pacienteModel.setObs(rs.getString("obs_paciente"));

                        dependente.setPacienteModel(pacienteModel);

                        lista.add(dependente);
                    }
                    ConnectionFactory.closeConnection(conexao, ps, rs);
                    return lista;
                case QUERY_DEPENDENTE:
                    sql = "select * from dependente left join paciente_dependente"
                            + " on id_dependente = id_codigo_dependente left join paciente"
                            + " on id_codigo_paciente = id_paciente where id_dependente = ?";
                    ps = conexao.prepareStatement(sql);
                    ps.setInt(1, dm.getCodigo());
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        dependente = new DependenteModel();
                        dependente.setCodigo(rs.getInt("id_dependente"));
                        dependente.setNome(rs.getString("nome_dependente"));
                        dependente.setNascimento(rs.getString("nascimento_dependente"));
                        dependente.setTelefone(rs.getString("telefone_dependente"));
                        dependente.setParentesco(rs.getString("parentesco_dependente"));
                        /*Paciente que esta relacionado com os dependentes*/
                        PacienteModel pacienteModel = new PacienteModel();
                        pacienteModel.setCodigo(rs.getInt("id_paciente"));
                        pacienteModel.setNome(rs.getString("nome_paciente"));
                        pacienteModel.setNascimento(rs.getString("nascimento_paciente"));
                        pacienteModel.setEndereco(rs.getString("endereco_paciente"));
                        pacienteModel.setTelefone(rs.getString("telefone_paciente"));
                        pacienteModel.setCep(rs.getString("cep_paciente"));
                        pacienteModel.setDocumento(rs.getString("documento_paciente"));
                        pacienteModel.setSexo(rs.getString("sexo_paciente"));
                        pacienteModel.setData_cliente(rs.getString("data_cliente_paciente"));
                        pacienteModel.setTipo(rs.getString("tipo_paciente"));
                        pacienteModel.setEmail(rs.getString("email_paciente"));
                        pacienteModel.setObs(rs.getString("obs_paciente"));

                        dependente.setPacienteModel(pacienteModel);

                        lista.add(dependente);
                    }
                    ConnectionFactory.closeConnection(conexao, ps, rs);
                    return lista;
                case QUERY_NOME:
                    sql = "select * from dependente left join paciente_dependente"
                            + " on id_dependente = id_codigo_dependente left join paciente"
                            + " on id_codigo_paciente = id_paciente where nome_dependente = ?";
                    ps = conexao.prepareStatement(sql);
                    ps.setString(1, dm.getNome());
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        dependente = new DependenteModel();
                        dependente.setCodigo(rs.getInt("id_dependente"));
                        dependente.setNome(rs.getString("nome_dependente"));
                        dependente.setNascimento(rs.getString("nascimento_dependente"));
                        dependente.setTelefone(rs.getString("telefone_dependente"));
                        dependente.setParentesco(rs.getString("parentesco_dependente"));
                        /*Paciente que esta relacionado com os dependentes*/
                        PacienteModel pacienteModel = new PacienteModel();
                        pacienteModel.setCodigo(rs.getInt("id_paciente"));
                        pacienteModel.setNome(rs.getString("nome_paciente"));
                        pacienteModel.setNascimento(rs.getString("nascimento_paciente"));
                        pacienteModel.setEndereco(rs.getString("endereco_paciente"));
                        pacienteModel.setTelefone(rs.getString("telefone_paciente"));
                        pacienteModel.setCep(rs.getString("cep_paciente"));
                        pacienteModel.setDocumento(rs.getString("documento_paciente"));
                        pacienteModel.setSexo(rs.getString("sexo_paciente"));
                        pacienteModel.setData_cliente(rs.getString("data_cliente_paciente"));
                        pacienteModel.setTipo(rs.getString("tipo_paciente"));
                        pacienteModel.setEmail(rs.getString("email_paciente"));
                        pacienteModel.setObs(rs.getString("obs_paciente"));

                        dependente.setPacienteModel(pacienteModel);

                        lista.add(dependente);
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

    /**
     * Adiciona vários dependentes no banco e atribuir eles a um paciente.
     *
     * @param list
     * @return
     */
    public static boolean executeMultiUpdates(List<DependenteModel> list) {
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement ps;
        ResultSet rs;
        final String sql_insert = "insert into dependente(nome_dependente,telefone_dependente,"
                + "nascimento_dependente, parentesco_dependente)"
                + "values(?,?,?,?)";
        final String sql_query = "select id_dependente from dependente where nome_dependente = ?";
        final String sql_insert_paciente = "insert into paciente_dependente(id_codigo_dependente, id_codigo_paciente) values(?,?)";
        try {
            for (int i = 0; i < list.size(); i++) {
                /*Adicionamos um dependente*/
                ps = conexao.prepareStatement(sql_insert);
                ps.setString(1, list.get(i).getNome());
                ps.setString(2, list.get(i).getTelefone());
                ps.setString(3, list.get(i).getNascimento());
                ps.setString(4, list.get(i).getParentesco());
                ps.executeUpdate();
                ps.close();

                /*Fazemos uma query para descobrir o ID desse dependente recem-adicionado*/
                ps = conexao.prepareStatement(sql_query);
                ps.setString(1, list.get(i).getNome());
                rs = ps.executeQuery();
                rs.next();
                int id_dependente = rs.getInt("id_dependente");
                rs.close();
                ps.close();
                /*Agora que ja sabemos o ID do dependente recem-adicionado vamos atribuir ele a um paciente*/
                ps = conexao.prepareStatement(sql_insert_paciente);
                ps.setInt(1, id_dependente);
                ps.setInt(2, list.get(i).getPacienteModel().getCodigo());
                ps.executeUpdate();
                ps.close();

            }
            ConnectionFactory.closeConnection(conexao);
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DependentesDAO.class.getName()).log(Level.SEVERE, null, ex);
            Log.relatarExcecao(DependentesDAO.class.getName(), ex);
            return false;
        }
    }
}
