package util;

import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.util.StringConverter;
import model.bean.BairroModel;
import model.bean.CategoriaModel;
import model.bean.CidadeModel;
import model.bean.DependenteModel;
import model.bean.MedicoModel;
import model.bean.PacienteModel;
import model.dao.DependentesDAO;
import model.dao.MedicoDAO;
import model.dao.PacienteDAO;

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
     * Retorna o código da Paciente
     */
    public static final int GET_CATEGORIA_CODIGO = 8;
    public static final int GET_CATEGORIA_DESCRICAO = 9;
    /**
     * Retornar o código do Dependente.
     */
    public static final int GET_DEPENDENTE_CODIGO = 10;
    public static final int GET_DEPENDENTE_NOME = 11;

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
    public StringConverter<MedicoModel> getMedicoConverter(ComboBox<MedicoModel> comboBox) {
        StringConverter<MedicoModel> convertido = new StringConverter<MedicoModel>() {
            @Override
            public String toString(MedicoModel object) {
                if (object != null) {
                    switch (retorno) {
                        case GET_MEDICO_CODIGO:
                            return object.getCodigoProperty().getValue().toString();
                        case GET_MEDICO_NOME:
                            return object.getNome();
                        default:
                            return "";
                    }
                } else {
                    return "";
                }

            }

            @Override
            public MedicoModel fromString(String nomeDoMedico) {
                return comboBox.getItems().stream().filter(medico -> medico.getNome().equals(nomeDoMedico)).findFirst().get();
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
                if (object != null) {
                    switch (retorno) {
                        case GET_CIDADE_CODIGO:
                            return object.getCodigoProperty().getValue().toString();
                        case GET_CIDADE_NOME:
                            return object.getNome();
                        default:
                            return "";
                    }
                } else {
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
                if (object != null) {
                    switch (retorno) {
                        case GET_BAIRRO_CODIGO:
                            return object.getCodigoProperty().getValue().toString();
                        case GET_BAIRRO_NOME:
                            return object.getNome();
                        default:
                            return "";
                    }
                } else {
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
     * @param comboBox
     * @return
     */
    public StringConverter<PacienteModel> getPacienteConverter(ComboBox<PacienteModel> comboBox) {
        StringConverter<PacienteModel> convertido = new StringConverter<PacienteModel>() {
            @Override
            public String toString(PacienteModel object) {
                if (object != null) {
                    switch (retorno) {
                        case GET_PACIENTE_CODIGO:
                            return object.getCodigoProperty().getValue().toString();
                        case GET_PACIENTE_NOME:
                            return object.getNome();
                        default:
                            return "";
                    }
                } else {
                    return "";
                }
            }

            @Override
            public PacienteModel fromString(String nomeDoPaciente) {
                return comboBox.getItems().stream().filter(paciente -> paciente.getNome().equals(nomeDoPaciente)).findFirst().get();
            }

        };
        return convertido;
    }

    /**
     * Retorna um toString proprio de PacienteModel
     *
     * @return
     */
    public StringConverter<CategoriaModel> getCategoriaConverter() {
        StringConverter<CategoriaModel> convertido = new StringConverter<CategoriaModel>() {
            @Override
            public String toString(CategoriaModel object) {
                if (object != null) {
                    switch (retorno) {
                        case GET_CATEGORIA_CODIGO:
                            return object.getCodigoProperty().getValue().toString();
                        case GET_CATEGORIA_DESCRICAO:
                            return object.getDescricao();
                        default:
                            return "";
                    }
                } else {
                    return "";
                }
            }

            @Override
            public CategoriaModel fromString(String string) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
        return convertido;
    }

    public StringConverter<DependenteModel> getDependenteConverter(ComboBox<DependenteModel> comboBox) {
        StringConverter<DependenteModel> converter = new StringConverter<DependenteModel>() {
            @Override
            public String toString(DependenteModel object) {
                if (object != null) {
                    switch (retorno) {
                        case GET_DEPENDENTE_CODIGO:
                            return object.getCodigoProperty().getValue().toString();
                        case GET_DEPENDENTE_NOME:
                            return object.getNome();
                        default:
                            return "";
                    }
                } else {
                    return "";
                }
            }

            @Override
            public DependenteModel fromString(String nomeDoDependente) {
                return comboBox.getItems().stream().filter(dependente -> dependente.getNome().equals(nomeDoDependente)).findFirst().get();
            }
        };
        return converter;
    }
}
