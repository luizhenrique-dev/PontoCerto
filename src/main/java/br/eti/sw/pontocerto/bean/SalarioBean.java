/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.eti.sw.pontocerto.bean;


import br.eti.sw.pontocerto.entidade.Salario;
import br.eti.sw.pontocerto.entidade.Usuario;
import br.eti.sw.pontocerto.negocio.SalarioRN;
import br.eti.sw.pontocerto.negocio.UsuarioRN;
import br.eti.sw.pontocerto.util.RNException;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
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
    private Salario salario = new Salario();
    private Usuario usuarioVinculado;
    private List<Salario> lista;

    @PostConstruct
    public void init() {

    }

    public String salvar() {
        SalarioRN salarioRN = new SalarioRN();
        this.salario.setUsuario(usuarioVinculado);
        salarioRN.salvar(this.salario);

        UsuarioRN usuarioRN = new UsuarioRN();
        this.usuarioVinculado.setSalario(salario);
        try {
            usuarioRN.salvar(usuarioVinculado);
        } catch (RNException ex) {
            enviaMensagemFaces(FacesMessage.SEVERITY_INFO, "Erro", ex.getMessage());
        }
        salario = new Salario();
        enviaMensagemFaces(FacesMessage.SEVERITY_INFO, "Sucesso", "Salario enviado com sucesso!");
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

    public Salario getFeedback() {
        return salario;
    }

    public void setFeedback(Salario salario) {
        this.salario = salario;
    }

    public Usuario getUsuarioVinculado() {
        return usuarioVinculado;
    }

    public void setWebVinculada(Usuario usuarioVinculado) {
        this.usuarioVinculado = usuarioVinculado;
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
