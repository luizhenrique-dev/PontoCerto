/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.eti.sw.pontocerto.bean;


import br.eti.sw.pontocerto.entidade.PontoDoDia;
import br.eti.sw.pontocerto.entidade.Usuario;
import br.eti.sw.pontocerto.negocio.PontoDoDiaRN;
import br.eti.sw.pontocerto.util.ContextoUtil;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.util.Calendar;
import java.util.Date;
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
    private PontoDoDia pontoDoDia;
    private List<PontoDoDia> lista;
    private Date horaEntrada;
    private Date horaSaidaAlmoco;
    private Date horaEntradaTarde;
    private Date horaSaida;

    @PostConstruct
    public void init() {
        PontoDoDiaRN pontoDoDiaRN = new PontoDoDiaRN();
        ContextoBean contextoBean;
        contextoBean = ContextoUtil.getContextoBean();
        Usuario usuario = contextoBean.getUsuarioLogado();
        pontoDoDia = pontoDoDiaRN.buscarPontoDoDia(usuario);

        if (pontoDoDia == null) {
            pontoDoDia = new PontoDoDia();
            this.pontoDoDia.setUsuario(usuario);
        } else {
            horaEntrada = this.pontoDoDia.getHoraEntrada().getTime();
            horaSaidaAlmoco = this.pontoDoDia.getHoraSaidaAlmoco().getTime();
            horaEntradaTarde = this.pontoDoDia.getHoraEntradaTarde().getTime();
            horaSaida = this.pontoDoDia.getHoraSaida().getTime();
        }
    }

    public void salvar() {
        PontoDoDiaRN pontoDoDiaRN = new PontoDoDiaRN();
        pontoDoDiaRN.salvar(this.pontoDoDia);

        enviaMensagemFaces(FacesMessage.SEVERITY_INFO, "Sucesso", "Ponto marcado com sucesso!");
    }

    public void excluir() {
        PontoDoDiaRN pontoDoDiaRN = new PontoDoDiaRN();
        pontoDoDiaRN.excluir(this.pontoDoDia);
        this.lista = null;
    }

    public List<PontoDoDia> getLista() {
        if (this.lista == null) {
            PontoDoDiaRN pontoDoDiaRN = new PontoDoDiaRN();
            ContextoBean contextoBean = ContextoUtil.getContextoBean();
            Usuario usuario = contextoBean.getUsuarioLogado();
            this.lista = pontoDoDiaRN.listar(usuario);

        }
        return this.lista;
    }

    public void marcarPontoDeEntrada() {
        this.pontoDoDia.setHoraEntrada(Calendar.getInstance());
        salvar();
        this.lista = null;
    }

    public void marcarPontoDeSaidaAlmoco() {
        this.pontoDoDia.setHoraSaidaAlmoco(Calendar.getInstance());
        salvar();
        this.lista = null;
    }

    public void marcarPontoDeEntradaTarde() {
        this.pontoDoDia.setHoraEntradaTarde(Calendar.getInstance());
        salvar();
        this.lista = null;
    }

    public void marcarPontoDeSaida() {
        this.pontoDoDia.setHoraSaida(Calendar.getInstance());
        salvar();
        this.lista = null;
    }

    public Date getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(Date horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public Date getHoraSaidaAlmoco() {
        return horaSaidaAlmoco;
    }

    public void setHoraSaidaAlmoco(Date horaSaidaAlmoco) {
        this.horaSaidaAlmoco = horaSaidaAlmoco;
    }

    public Date getHoraEntradaTarde() {
        return horaEntradaTarde;
    }

    public void setHoraEntradaTarde(Date horaEntradaTarde) {
        this.horaEntradaTarde = horaEntradaTarde;
    }

    public Date getHoraSaida() {
        return horaSaida;
    }

    public void setHoraSaida(Date horaSaida) {
        this.horaSaida = horaSaida;
    }

    public PontoDoDia getPontoDoDia() {
        return pontoDoDia;
    }

    public void setPontoDoDia(PontoDoDia pontoDoDia) {
        this.pontoDoDia = pontoDoDia;
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
