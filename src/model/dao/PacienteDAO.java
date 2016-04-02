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
import model.bean.PacienteModel;
import util.Log;

/**
 *
 * @author jeff-
 */
public class PacienteDAO {

    /*Declaração das constantes estaticas para ser acessadas de qualquer lugar*/
    public static final int CREATE = 0;
    public static final int DELETE = 1;
    public static final int UPDATE = 2;
    public static final int UPDATE_STATUS = 3;
    public static final int QUERY_TODOS = 4;
    public static final int QUERY_NOME = 5;

    public static boolean executeUpdates(PacienteModel pm, int operacao) {
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement ps;
        String sql;
        try {
            switch (operacao) {
                case CREATE:
                    sql = "insert into paciente("
                            + "nome_paciente,"
                            + "nascimento_paciente,"
                            + "endereco_paciente,"
                            + "telefone_paciente,"
                            + "cep_paciente,"
                            + "documento_paciente,"
                            + "sexo_paciente,"
                            + "data_cliente_paciente,"
                            + "tipo_paciente,"
                            + "email_paciente,"
                            + "obs_paciente,"
                            + "id_codigo_cidade,"
                            + "id_codigo_bairro,"
                            + "status)"
                            + "values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
                    ps = conexao.prepareStatement(sql);
                    ps.setString(1, pm.getNome());
                    ps.setString(2, pm.getNascimento());
                    ps.setString(3, pm.getEndereco());
                    ps.setString(4, pm.getTelefone());
                    ps.setString(5, pm.getCep());
                    ps.setString(6, pm.getDocumento());
                    ps.setString(7, pm.getSexo());
                    ps.setString(8, pm.getData_cliente());
                    ps.setString(9, pm.getTipo());
                    ps.setString(10, pm.getEmail());
                    ps.setString(11, pm.getObs());
                    ps.setInt(12, pm.getCidadeModel().getCodigo());
                    ps.setInt(13, pm.getBairroModel().getCodigo());
                    ps.setBoolean(14, pm.getStatus());
                    ps.executeUpdate();
                    ConnectionFactory.closeConnection(conexao, ps);
                    return true;
                case DELETE:
                    sql = "delete from paciente where id_paciente=?";
                    ps = conexao.prepareStatement(sql);
                    ps.setInt(1, pm.getCodigo());
                    ps.executeUpdate();
                    ConnectionFactory.closeConnection(conexao, ps);
                    return true;
                case UPDATE:
                    sql = "update paciente set "
                            + "nome_paciente=?,"
                            + "nascimento_paciente=?,"
                            + "endereco_paciente=?,"
                            + "telefone_paciente=?,"
                            + "cep_paciente=?,"
                            + "documento_paciente=?,"
                            + "sexo_paciente=?,"
                            + "data_cliente_paciente=?,"
                            + "tipo_paciente=?,"
                            + "email_paciente=?,"
                            + "obs_paciente=?,"
                            + "id_codigo_cidade=?,"
                            + "id_codigo_bairro=?,"
                            + "status = ?"
                            + "where id_paciente=?";
                    ps = conexao.prepareStatement(sql);
                    ps.setString(1, pm.getNome());
                    ps.setString(2, pm.getNascimento());
                    ps.setString(3, pm.getEndereco());
                    ps.setString(4, pm.getTelefone());
                    ps.setString(5, pm.getCep());
                    ps.setString(6, pm.getDocumento());
                    ps.setString(7, pm.getSexo());
                    ps.setString(8, pm.getData_cliente());
                    ps.setString(9, pm.getTipo());
                    ps.setString(10, pm.getEmail());
                    ps.setString(11, pm.getObs());
                    ps.setInt(12, pm.getCidadeModel().getCodigo());
                    ps.setInt(13, pm.getBairroModel().getCodigo());
                    ps.setInt(14, pm.getCodigo());
                    ps.setBoolean(15,pm.getStatus());
                    ps.executeUpdate();
                    ConnectionFactory.closeConnection(conexao, ps);
                    return true;
                case UPDATE_STATUS:
                    sql = "update paciente set status = ?";
                    ps = conexao.prepareStatement(sql);
                    ps.setBoolean(1, pm.getStatus());
                    ps.executeUpdate();
                    ConnectionFactory.closeConnection(conexao, ps);
                    return true;
                default:
                    conexao.close();
                    return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            Log.relatarExcecao(PacienteDAO.class.getName(), ex);
        }
        return false;
    }

    public static ObservableList<PacienteModel> executeQuery(PacienteModel pm, int operacao) {
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement ps;
        ResultSet rs;
        String sql;
        ObservableList<PacienteModel> listaPaciente = FXCollections.observableArrayList();
        PacienteModel pacienteModel;
        try {
            switch (operacao) {
                case QUERY_TODOS:
                    sql = "select * from paciente "
                            + "left join cidade on id_codigo_cidade = id_cidade "
                            + "left join bairro on id_codigo_bairro = id_bairro "
                            + "order by id_paciente";
                    ps = conexao.prepareStatement(sql);
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        /*Inicializamos o paciente e colocamos os valores*/
                        pacienteModel = new PacienteModel();
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
                        pacienteModel.setStatus(rs.getBoolean("status"));
                        /*Colocamos a Cidade*/
                        CidadeModel cidadeModel = new CidadeModel();
                        cidadeModel.setCodigo(rs.getInt("id_cidade"));
                        cidadeModel.setNome(rs.getString("nome_cidade"));
                        cidadeModel.setSigla(rs.getString("sigla_cidade"));
                        /*Colocamos o Bairro*/
                        BairroModel bairroModel = new BairroModel();
                        bairroModel.setCodigo(rs.getInt("id_bairro"));
                        bairroModel.setNome(rs.getString("nome_bairro"));
                        bairroModel.setCidadeModel(cidadeModel);
                        /*Adicionamos no PacienteModel*/
                        pacienteModel.setCidadeModel(cidadeModel);
                        pacienteModel.setBairroModel(bairroModel);
                        /*Adicionamos na Lista*/
                        listaPaciente.add(pacienteModel);
                    }
                    ConnectionFactory.closeConnection(conexao, ps, rs);
                    return listaPaciente;
                case QUERY_NOME:
                    sql = "select * from paciente "
                            + "left join cidade on id_codigo_cidade = id_cidade "
                            + "left join bairro on id_codigo_bairro = id_bairro "
                            + "where nome_paciente = ?";
                    ps = conexao.prepareStatement(sql);
                    ps.setString(1, pm.getNome());
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        /*Inicializamos o paciente e colocamos os valores*/
                        pacienteModel = new PacienteModel();
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
                        pacienteModel.setStatus(rs.getBoolean("status"));
                        /*Colocamos a Cidade*/
                        CidadeModel cidadeModel = new CidadeModel();
                        cidadeModel.setCodigo(rs.getInt("id_cidade"));
                        cidadeModel.setNome(rs.getString("nome_cidade"));
                        cidadeModel.setSigla(rs.getString("sigla_cidade"));
                        /*Colocamos o Bairro*/
                        BairroModel bairroModel = new BairroModel();
                        bairroModel.setCodigo(rs.getInt("id_bairro"));
                        bairroModel.setNome(rs.getString("nome_bairro"));
                        bairroModel.setCidadeModel(cidadeModel);
                        /*Adicionamos no PacienteModel*/
                        pacienteModel.setCidadeModel(cidadeModel);
                        pacienteModel.setBairroModel(bairroModel);
                        /*Adicionamos na Lista*/
                        listaPaciente.add(pacienteModel);
                    }
                    ConnectionFactory.closeConnection(conexao, ps, rs);
                    return listaPaciente;
            }
        } catch (SQLException ex) {
            Logger.getLogger(PacienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            Log.relatarExcecao(PacienteDAO.class.getName(), ex);
        }
        return listaPaciente;
    }
}
