/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.eti.sw.pontocerto.bean;


import br.eti.sw.pontocerto.entidade.Salario;
import br.eti.sw.pontocerto.entidade.Usuario;
import br.eti.sw.pontocerto.negocio.SalarioRN;
import br.eti.sw.pontocerto.util.ContextoUtil;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.math.BigDecimal;
import java.util.List;

/**
 * Classe respons�vel por ser uma ponte entre a regra de neg�cio da entidade
 * Salario com as Views relacionadas a mesma. "Delegando" fun��es espec�ficas
 * para cada View.
 *
 * @author Luiz Henrique
 */

/**
 * Anota��o que reflete o nome a ser utilizado para chamar essa classe atrav�s
 * da View.
 */
@ManagedBean(name = "salarioBean")
@SessionScoped
public class SalarioBean {

    /**
     * Este � o objeto utilizado para manipular inser��es, edi��es e dele��es.
     */
    private Salario salario;
    private List<Salario> lista;
    private BigDecimal deducaoIRRF;

    @PostConstruct
    public void init() {
        SalarioRN salarioRN = new SalarioRN();
        ContextoBean contextoBean = ContextoUtil.getContextoBean();
        Usuario usuario = contextoBean.getUsuarioLogado();
        this.salario = salarioRN.buscarSalarioPorUsuario(usuario);

        if (this.salario == null) {
            this.salario = new Salario();
            this.salario.setUsuario(usuario);
        }
    }

    public String salvar() {
        SalarioRN salarioRN = new SalarioRN();
        salarioRN.salvar(this.salario);
        enviaMensagemFaces(FacesMessage.SEVERITY_INFO, "Sucesso", "Salário enviado com sucesso!");
        return "sucessoFeedback";
    }

    public void excluir() {
        SalarioRN salarioRN = new SalarioRN();
        salarioRN.excluir(this.salario);
        this.lista = null;
    }

    public List<Salario> getLista() {
        if (this.lista == null) {
            SalarioRN salarioRN = new SalarioRN();
            this.lista = salarioRN.listar();

        }
        return this.lista;
    }

    public BigDecimal obtenhaDeducaoIRRF() {
        SalarioRN salarioRN = new SalarioRN();
        return salarioRN.obtenhaDeducaoIRRF(this.salario);
    }

    public Salario getSalario() {
        return salario;
    }

    public void setSalario(Salario salario) {
        this.salario = salario;
    }

    public BigDecimal getDeducaoIRRF() {
        SalarioRN salarioRN = new SalarioRN();
        deducaoIRRF =  salarioRN.obtenhaDeducaoIRRF(this.salario);
        return deducaoIRRF;
    }

    public void setDeducaoIRRF(BigDecimal deducaoIRRF) {
        this.deducaoIRRF = deducaoIRRF;
    }

    /**
     * M�todo respons�vel por enviar mensagens para as views de acordo com as
     * opera��es realizadas.
     *
     * @param severidade � o grau da mensagem: erro, aviso, informativo.
     * @param titulo     � o t�tulo da mensagem.
     * @param conteudo   � o conte�do da mensagem.
     */
    private void enviaMensagemFaces(Severity severidade, String titulo, String conteudo) {
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage facesMessage = new FacesMessage(severidade, conteudo, titulo);
        context.addMessage(null, facesMessage);
    }
}
