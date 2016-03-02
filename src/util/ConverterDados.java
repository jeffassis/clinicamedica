package util;

import javafx.util.StringConverter;
import model.bean.BairroModel;
import model.bean.CidadeModel;
import model.bean.MedicoModel;
import model.bean.PacienteModel;

/**
 *
 * @author jeff-
 */
public class ConverterDados {

    /*Declaração da variavel retorno*/
    private int retorno;

    /**
     * Retorna o código do Médico
     */
    public static final int GET_MEDICO_CODIGO = 0;
    public static final int GET_MEDICO_NOME = 1;

    /**
     * Retorna o código da Cidade
     */
    public static final int GET_CIDADE_CODIGO = 2;
    public static final int GET_CIDADE_NOME = 3;

    /**
     * Retorna o código da Bairro
     */
    public static final int GET_BAIRRO_CODIGO = 4;
    public static final int GET_BAIRRO_NOME = 5;
    /**
     * Retorna o código da Paciente
     */
    public static final int GET_PACIENTE_CODIGO = 6;
    public static final int GET_PACIENTE_NOME = 7;

    /**
     * Passe um int estatico da Classe ConverterDados informando o tipo da
     * operação, ex: ConverterDados dados = new
     * ConverterDados(ConverterDados.GET_MEDICO_NOME).
     *
     * @param retorno
     */
    public ConverterDados(int retorno) {
        this.retorno = retorno;
    }

    /**
     * Retorna um toString proprio de MedicoModel
     *
     * @return
     */
    public StringConverter<MedicoModel> getMedicoConverter() {
        StringConverter<MedicoModel> convertido = new StringConverter<MedicoModel>() {
            @Override
            public String toString(MedicoModel object) {
                switch (retorno) {
                    case GET_MEDICO_CODIGO:
                        return object.getCodigoProperty().getValue().toString();
                    case GET_MEDICO_NOME:
                        return object.getNome();
                    default:
                        return "";
                }
            }

            @Override
            public MedicoModel fromString(String string) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
        return convertido;
    }

    /**
     * Retorna um toString proprio de CidadeModel
     *
     * @return
     */
    public StringConverter<CidadeModel> getCidadeConverter() {
        StringConverter<CidadeModel> convertido = new StringConverter<CidadeModel>() {
            @Override
            public String toString(CidadeModel object) {
                switch (retorno) {
                    case GET_CIDADE_CODIGO:
                        return object.getCodigoProperty().getValue().toString();
                    case GET_CIDADE_NOME:
                        return object.getNome();
                    default:
                        return "";
                }
            }

            @Override
            public CidadeModel fromString(String string) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
        return convertido;
    }

    /**
     * Retorna um toString proprio de BairroModel
     *
     * @return
     */
    public StringConverter<BairroModel> getBairroConverter() {
        StringConverter<BairroModel> convertido = new StringConverter<BairroModel>() {
            @Override
            public String toString(BairroModel object) {
                switch (retorno) {
                    case GET_BAIRRO_CODIGO:
                        return object.getCodigoProperty().getValue().toString();
                    case GET_BAIRRO_NOME:
                        return object.getNome();
                    default:
                        return "";
                }
            }

            @Override
            public BairroModel fromString(String string) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
        return convertido;
    }

    /**
     * Retorna um toString proprio de PacienteModel
     *
     * @return
     */
    public StringConverter<PacienteModel> getPacienteConverter() {
        StringConverter<PacienteModel> convertido = new StringConverter<PacienteModel>() {
            @Override
            public String toString(PacienteModel object) {
                switch (retorno) {
                    case GET_PACIENTE_CODIGO:
                        return object.getCodigoProperty().getValue().toString();
                    case GET_PACIENTE_NOME:
                        return object.getNome();
                    default:
                        return "";
                }
            }

            @Override
            public PacienteModel fromString(String string) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
        return convertido;
    }
}
