/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.eti.sw.pontocerto.bean;

import br.eti.sw.pontocerto.entidade.Usuario;
import br.eti.sw.pontocerto.negocio.UsuarioRN;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.util.Calendar;

//import br.com.dbserver.lunchtime.negocio.UsuarioRN;

/**
 * Classe respons�vel por obter o usu�rio logado no sistema no momento da
 * requisi��o.
 *
 * @author Luiz Henrique
 */
@ManagedBean(name = "contextoBean")
@SessionScoped
public class ContextoBean {

    private Usuario usuarioLogado = null;
    private String tipoFormulario;
    private String tipoFormularioAberturaSala;

    @PostConstruct
    public void init() {
    }

    public Usuario getUsuarioLogado() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext external = context.getExternalContext();
        String login = external.getRemoteUser();
        if (this.usuarioLogado == null || !login.equals(usuarioLogado.getLogin())) {
            if (login != null) {
                UsuarioRN usuarioRN = new UsuarioRN();
                this.usuarioLogado = usuarioRN.buscaPorLogin(login);
                this.usuarioLogado.setDataUltimoLogin(Calendar.getInstance());
                usuarioRN.atualizarUsuario(usuarioLogado);
            }
        }
        return usuarioLogado;
    }

    public void setUsuarioLogado(Usuario usuario) {
        this.usuarioLogado = usuario;
    }

    public String getTipoFormulario() {
        return tipoFormulario;
    }

    public void setTipoFormulario(String tipoFormulario) {
        this.tipoFormulario = tipoFormulario;
    }

    public String getTipoFormularioAberturaSala() {
        return tipoFormularioAberturaSala;
    }

    public void setTipoFormularioAberturaSala(String tipoFormularioAberturaSala) {
        this.tipoFormularioAberturaSala = tipoFormularioAberturaSala;
    }
}
