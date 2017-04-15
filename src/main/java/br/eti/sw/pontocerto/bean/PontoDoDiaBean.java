/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.eti.sw.pontocerto.bean;


import br.eti.sw.pontocerto.entidade.PontoDoDia;
import br.eti.sw.pontocerto.entidade.Usuario;
import br.eti.sw.pontocerto.negocio.PontoDoDiaRN;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.util.List;

/**
 * Classe respons�vel por ser uma ponte entre a regra de neg�cio da entidade
 * PontoDoDia com as Views relacionadas a mesma. "Delegando" fun��es espec�ficas
 * para cada View.
 *
 * @author Luiz Henrique
 */

/**
 * Anota??o que reflete o nome a ser utilizado para chamar essa classe atrav?s
 * da View.
 */
@ManagedBean(name = "pontoDoDiaBean")
@SessionScoped
public class PontoDoDiaBean {

    /**
     * Este ? o objeto utilizado para manipular inser??es, edi??es e dele??es.
     */
    private PontoDoDia pontoDoDia = new PontoDoDia();
    private Usuario usuarioVinculado;
    private List<PontoDoDia> lista;

    @PostConstruct
    public void init() {

    }

    public String salvar() {
        PontoDoDiaRN pontoDoDiaRN = new PontoDoDiaRN();
        this.pontoDoDia.setUsuario(usuarioVinculado);
        pontoDoDiaRN.salvar(this.pontoDoDia);
        pontoDoDia = new PontoDoDia();
        enviaMensagemFaces(FacesMessage.SEVERITY_INFO, "Sucesso", "PontoDoDia enviado com sucesso!");
        return "sucessoFeedback";
    }

    public void excluir() {
        PontoDoDiaRN pontoDoDiaRN = new PontoDoDiaRN();
        pontoDoDiaRN.excluir(this.pontoDoDia);
        this.lista = null;
    }

    public List<PontoDoDia> getLista() {
        if (this.lista == null) {
            PontoDoDiaRN pontoDoDiaRN = new PontoDoDiaRN();
            this.lista = pontoDoDiaRN.listar();

        }
        return this.lista;
    }

    public PontoDoDia getFeedback() {
        return pontoDoDia;
    }

    public void setFeedback(PontoDoDia pontoDoDia) {
        this.pontoDoDia = pontoDoDia;
    }

    public Usuario getUsuarioVinculado() {
        return usuarioVinculado;
    }

    public void setWebVinculada(Usuario usuarioVinculado) {
        this.usuarioVinculado = usuarioVinculado;
    }

    /**
     * M?todo respons?vel por enviar mensagens para as views de acordo com as
     * opera??es realizadas.
     *
     * @param severidade ? o grau da mensagem: erro, aviso, informativo.
     * @param titulo     ? o t?tulo da mensagem.
     * @param conteudo   ? o conte?do da mensagem.
     */
    private void enviaMensagemFaces(Severity severidade, String titulo, String conteudo) {
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage facesMessage = new FacesMessage(severidade, conteudo, titulo);
        context.addMessage(null, facesMessage);
    }
}
