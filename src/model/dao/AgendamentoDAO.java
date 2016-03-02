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
import model.bean.AgendamentoModel;
import model.bean.MedicoModel;
import model.bean.PacienteModel;

/**
 *
 * @author jeff-
 */
public class AgendamentoDAO {

    /*Declaração das constantes estaticas para ser acessadas de qualquer lugar*/
    public static final int CREATE = 0;
    public static final int DELETE = 1;
    public static final int UPDATE = 2;
    public static final int QUERY_TODOS = 3;
    public static final int QUERY_NOME = 4;

    public static boolean executeUpdates(AgendamentoModel am, int operacao) {
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement ps;
        String sql;
        try {
            switch (operacao) {
                case CREATE:
                    sql = "insert into agendamento("
                            + "turno_agenda,"
                            + "motivo_agenda,"
                            + "horario_agenda,"
                            + "data_agenda,"
                            + "id_codigo_medico,"
                            + "id_codigo_paciente)"
                            + "values(?,?,?,?,?,?)";
                    ps = conexao.prepareStatement(sql);
                    ps.setString(1, am.getTurno());
                    ps.setString(2, am.getMotivo());
                    ps.setString(3, am.getHorario());
                    ps.setString(4, am.getData());
                    ps.setInt(5, am.getMedicoModel().getCodigo());
                    ps.setInt(6, am.getPacienteModel().getCodigo());
                    ps.executeUpdate();
                    ConnectionFactory.closeConnection(conexao, ps);
                    return true;
                case DELETE:
                    sql = "delete from agendamento where id_agenda=?";
                    ps = conexao.prepareStatement(sql);
                    ps.setInt(1, am.getCodigo());
                    ps.executeUpdate();
                    ConnectionFactory.closeConnection(conexao, ps);
                    return true;
                case UPDATE:
                    sql = "update agendamento set "
                            + "turno_agenda=?,"
                            + "motivo_agenda=?,"
                            + "horario_agenda=?,"
                            + "data_agenda=?,"
                            + "id_codigo_medico=?,"
                            + "id_codigo_paciente=? "
                            + "where id_agenda=?";
                    ps = conexao.prepareStatement(sql);
                    ps.setString(1, am.getTurno());
                    ps.setString(2, am.getMotivo());
                    ps.setString(3, am.getHorario());
                    ps.setString(4, am.getData());
                    ps.setInt(5, am.getMedicoModel().getCodigo());
                    ps.setInt(6, am.getPacienteModel().getCodigo());
                    ps.setInt(7, am.getCodigo());
                    ps.executeUpdate();
                    ConnectionFactory.closeConnection(conexao, ps);
                    return true;
                default:
                    conexao.close();
                    return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AgendamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public static ObservableList<AgendamentoModel> executeQuery(AgendamentoModel am, int operacao) {
        Connection conexao = ConnectionFactory.getConnection();
        PreparedStatement ps;
        ResultSet rs;
        String sql;
        ObservableList<AgendamentoModel> listaAgenda = FXCollections.observableArrayList();
        AgendamentoModel agendamentoModel;
        try {
            switch (operacao) {
                case QUERY_TODOS:
                    sql = "select * from agendamento "
                            + "left join medico on id_codigo_medico = id_medico "
                            + "left join paciente on id_codigo_paciente = id_paciente "
                            + "order by id_agenda";
                    ps = conexao.prepareStatement(sql);
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        /*Inicializamos o agendamento e colocamos os valores*/
                        agendamentoModel = new AgendamentoModel();
                        agendamentoModel.setCodigo(rs.getInt("id_agenda"));
                        agendamentoModel.setTurno(rs.getString("turno_agenda"));
                        agendamentoModel.setMotivo(rs.getString("motivo_agenda"));
                        agendamentoModel.setHorario(rs.getString("horario_agenda"));
                        agendamentoModel.setData(rs.getString("data_agenda"));
                        /*Colocamos a Medico*/
                        MedicoModel medico = new MedicoModel();
                        medico.setCodigo(rs.getInt("id_medico"));
                        medico.setNome(rs.getString("nome_medico"));
                        medico.setCrm(rs.getString("crm_medico"));
                        medico.setEspecialidade(rs.getString("especialidade_medico"));
                        /*Colocamos o Paciente*/
                        PacienteModel paciente = new PacienteModel();
                        paciente.setCodigo(rs.getInt("id_paciente"));
                        paciente.setNome(rs.getString("nome_paciente"));
                        paciente.setNascimento(rs.getString("nascimento_paciente"));
                        paciente.setEndereco(rs.getString("endereco_paciente"));
                        paciente.setTelefone(rs.getString("telefone_paciente"));
                        paciente.setCep(rs.getString("cep_paciente"));
                        paciente.setDocumento(rs.getString("documento_paciente"));
                        paciente.setSexo(rs.getString("sexo_paciente"));
                        paciente.setData_cliente(rs.getString("data_cliente_paciente"));
                        paciente.setTipo(rs.getString("tipo_paciente"));
                        paciente.setEmail(rs.getString("email_paciente"));
                        paciente.setObs(rs.getString("obs_paciente"));
                        /*Adicionamos no AgendamentoModel*/
                        agendamentoModel.setMedicoModel(medico);
                        agendamentoModel.setPacienteModel(paciente);
                        /*Adicionamos na Lista*/
                        listaAgenda.add(agendamentoModel);
                    }
                    ConnectionFactory.closeConnection(conexao, ps, rs);
                    return listaAgenda;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AgendamentoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaAgenda;
    }
}
