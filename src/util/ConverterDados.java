package util;

import javafx.util.StringConverter;
import model.bean.MedicoModel;

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
}
