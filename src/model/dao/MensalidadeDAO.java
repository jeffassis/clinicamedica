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
import model.bean.MensalidadeModel;
import model.bean.PacienteModel;
import util.Log;

/**
 *
 * @author jeff-
 */
public class MensalidadeDAO {

    public static final int CREATE = 0;
    public static final int DELETE = 1;
    public static final int UPDATE = 2;
    public static final int QUERY_TODOS = 3;
    public static final int QUERY_PACIENTE = 4;

    public static boolean executeUpdates(MensalidadeModel mm, int operacao) {
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement ps;
        String sql;
        try {
            switch (operacao) {
                case CREATE:
                    sql = "insert into mensalidade(valor_mensalidade,"
                            + "desconto_mensalidade,"
                            + "mes_mensalidade,"
                            + "data_mensalidade,"
                            + "id_codigo_paciente)"
                            + "values(?,?,?,?,?)";
                    ps = conexao.prepareStatement(sql);
                    ps.setDouble(1, mm.getValor());
                    ps.setDouble(2, mm.getDesconto());
                    ps.setString(3, mm.getMes());
                    ps.setString(4, mm.getData_pagto());
                    ps.setInt(5, mm.getPacienteModel().getCodigo());
                    ps.executeUpdate();
                    ConnectionFactory.closeConnection(conexao, ps);
                    return true;
                case DELETE:
                    sql = "delete from mensalidade where id_mensalidade=?";
                    ps = conexao.prepareStatement(sql);
                    ps.setInt(1, mm.getCodigo());
                    ps.executeUpdate();
                    ConnectionFactory.closeConnection(conexao, ps);
                    return true;
                case UPDATE:
                    sql = "update mensalidade set valor_mensalidade=?,"
                            + "desconto_mensalidade=?,"
                            + "mes_mensalidade=?,"
                            + "data_mensalidade=?,"
                            + "id_codigo_paciente=? "
                            + "where id_mensalidade=?";
                    ps = conexao.prepareStatement(sql);
                    ps.setDouble(1, mm.getValor());
                    ps.setDouble(2, mm.getDesconto());
                    ps.setString(3, mm.getMes());
                    ps.setString(4, mm.getData_pagto());
                    ps.setInt(5, mm.getPacienteModel().getCodigo());
                    ps.setInt(6, mm.getCodigo());
                    ps.executeUpdate();
                    ConnectionFactory.closeConnection(conexao, ps);
                    return true;
                default:
                    conexao.close();
                    return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(MensalidadeDAO.class.getName()).log(Level.SEVERE, null, ex);
            Log.relatarExcecao(MensalidadeDAO.class.getName(), ex);
            return false;
        }
    }

    public static ObservableList<MensalidadeModel> executeQuery(MensalidadeModel mm, int operacao) {
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement ps;
        ResultSet rs;
        String sql;
        ObservableList<MensalidadeModel> listaMensalidade = FXCollections.observableArrayList();
        MensalidadeModel mensalidadeModel;
        try {
            switch (operacao) {
                case QUERY_TODOS:
                    sql = "select * from mensalidade "
                            + "left join paciente on id_codigo_paciente = id_paciente "
                            + "order by id_mensalidade";
                    ps = conexao.prepareStatement(sql);
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        /*Atributos da mensalidade*/
                        mensalidadeModel = new MensalidadeModel();
                        mensalidadeModel.setCodigo(rs.getInt("id_mensalidade"));
                        mensalidadeModel.setValor(rs.getDouble("valor_mensalidade"));
                        mensalidadeModel.setDesconto(rs.getDouble("desconto_mensalidade"));
                        mensalidadeModel.setMes(rs.getString("mes_mensalidade"));
                        mensalidadeModel.setData_pagto(rs.getString("data_mensalidade"));

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

                        mensalidadeModel.setPacienteModel(pacienteModel);

                        listaMensalidade.add(mensalidadeModel);
                    }
                    ConnectionFactory.closeConnection(conexao, ps, rs);
                    return listaMensalidade;
                case QUERY_PACIENTE:
                    sql = "select * from mensalidade left join paciente on "
                            + "id_codigo_paciente = id_paciente where id_paciente=? order by id_mensalidade";
                    ps = conexao.prepareStatement(sql);
                    ps.setInt(1, mm.getPacienteModel().getCodigo());
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        /*Atributos da mensalidade*/
                        mensalidadeModel = new MensalidadeModel();
                        mensalidadeModel.setCodigo(rs.getInt("id_mensalidade"));
                        mensalidadeModel.setValor(rs.getDouble("valor_mensalidade"));
                        mensalidadeModel.setDesconto(rs.getDouble("desconto_mensalidade"));
                        mensalidadeModel.setMes(rs.getString("mes_mensalidade"));
                        mensalidadeModel.setData_pagto(rs.getString("data_mensalidade"));

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

                        mensalidadeModel.setPacienteModel(pacienteModel);

                        listaMensalidade.add(mensalidadeModel);
                    }
                    ConnectionFactory.closeConnection(conexao, ps, rs);
                    return listaMensalidade;
            }
        } catch (SQLException ex) {
            Logger.getLogger(MensalidadeDAO.class.getName()).log(Level.SEVERE, null, ex);
            Log.relatarExcecao(MensalidadeDAO.class.getName(), ex);
        }
        return listaMensalidade;
    }

}
