/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.eti.sw.pontocerto.bean;

import br.eti.sw.pontocerto.entidade.Usuario;
import br.eti.sw.pontocerto.negocio.UsuarioRN;
import br.eti.sw.pontocerto.util.ContextoUtil;
import br.eti.sw.pontocerto.util.RNException;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.util.Set;

/**
 * Classe respons�vel por ser uma ponte entre a regra de neg�cio da entidade
 * Usuario com as Views relacionadas a mesma. "Delegando" fun��es espec�ficas
 * para cada View.
 *
 * @author Luiz Henrique
 */
/**
 * Anota��o que reflete o nome a ser utilizado para chamar essa classe atrav�s
 * da View.
 */
@ManagedBean(name = "usuarioBean")
@SessionScoped
public class UsuarioBean {

    /**
     * Este � o objeto utilizado para manipular inser��es, edi��es e dele��es.
     */
    private Usuario usuario = new Usuario();
    private Set<Usuario> lista;
    private String confirmaSenha;
    private String emailRecuperacao;

    public String novo() {
        this.usuario = new Usuario();
        this.confirmaSenha = "";
        return "/publico/novoUsuario";
    }

    public void salvar() {
        String senha = this.usuario.getSenha();
        if (senha.equals(this.confirmaSenha)) {
            try {
                this.usuario.setAtivo(true);
                UsuarioRN usuarioRN = new UsuarioRN();
                usuarioRN.salvar(this.usuario);
                enviaMensagemFaces(FacesMessage.SEVERITY_INFO, "Clique em 'Voltar' e efetue o login.", "Usuário cadastrado com sucesso! Clique em 'Voltar' e efetue o login.");
            } catch (RNException ex) {
                enviaMensagemFaces(FacesMessage.SEVERITY_ERROR, "", "Erro: " + ex.getMessage());
            }
        } else {
            enviaMensagemFaces(FacesMessage.SEVERITY_ERROR, "Erro", "As senhas digitadas não conferem!");
        }
    }

    public void atualizar() {
        String senha = this.usuario.getSenha();
        if (senha.equals(this.confirmaSenha)) {
            try {
                this.usuario.setAtivo(true);
                UsuarioRN usuarioRN = new UsuarioRN();
                usuarioRN.salvar(this.usuario);
                ContextoBean contextoBean = ContextoUtil.getContextoBean();
                contextoBean.setUsuarioLogado(null);
                enviaMensagemFaces(FacesMessage.SEVERITY_INFO, "Sucesso.", "Perfil atualizado com sucesso! Clique em 'Voltar' para ir a página inicial.");
            } catch (RNException ex) {
                enviaMensagemFaces(FacesMessage.SEVERITY_ERROR, "", "Erro: " + ex.getMessage());
            }
        } else {
            enviaMensagemFaces(FacesMessage.SEVERITY_ERROR, "Erro", "As senhas digitadas não conferem!");
        }
    }

    public void recuperarCredenciais() {
        UsuarioRN usuarioRN = new UsuarioRN();
        Usuario usuarioRecuperacaoSenha = usuarioRN.buscaPorEmail(emailRecuperacao);
        FacesContext context = FacesContext.getCurrentInstance();
        if (usuarioRecuperacaoSenha != null) {
            try {
                usuarioRN.recuperarCredenciais(usuarioRecuperacaoSenha);
                FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, "Foi enviado um e-mail contendo as informações de suas credenciais! Clique em 'Voltar' e efetue o login.", "Sucesso");
                context.addMessage(null, facesMessage);
            } catch (RNException ex) {
                FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Não foi possível enviar o e-mail de recuperação das credenciais, por favor entre em contato com o suporte para resolver o problema.", "Desculpe");
                context.addMessage(null, facesMessage);
            }
        } else {
            FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Não existe nenhum usuário cadastrado com este e-mail!", "Erro");
            context.addMessage(null, facesMessage);
        }
    }

    public String editar() {
        this.lista = null;
        return "/restrito/usuario";
    }

    public void excluir() {
        UsuarioRN usuarioRN = new UsuarioRN();
        usuarioRN.excluir(this.usuario);
        this.lista = null;
    }

    public Set<Usuario> getLista() {
        if (this.lista == null) {
            UsuarioRN usuarioRN = new UsuarioRN();
            this.lista = usuarioRN.listar();
        }
        return this.lista;
    }

    public void verificaUnicidadeEmail() {
        UsuarioRN usuarioRN = new UsuarioRN();
        Usuario usuarioExistente = usuarioRN.buscaPorEmail(usuario.getEmail());
        if (usuarioExistente != null) {
            FacesContext.getCurrentInstance().addMessage("email", new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Já existe um usuário cadastrado com esse e-mail!"));
        }
    }

    public boolean isAdministrador() {
        UsuarioRN usuarioRN = new UsuarioRN();
        Usuario user = usuarioRN.carregar(this.usuario.getId());
        if (user.getPermissao().contains("ROLE_ADMINISTRADOR"))
            return true;

        return false;
    }

    public void verificaUnicidadeLogin() {
        UsuarioRN usuarioRN = new UsuarioRN();
        Usuario usuarioExistente = usuarioRN.buscaPorLogin(usuario.getLogin());
        if (usuarioExistente != null) {
            FacesContext.getCurrentInstance().addMessage("login", new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Já existe um usuário cadastrado com esse login!"));
        }
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getConfirmaSenha() {
        return confirmaSenha;
    }

    public void setConfirmaSenha(String confirmaSenha) {
        this.confirmaSenha = confirmaSenha;
    }

    public String getEmailRecuperacao() {
        return emailRecuperacao;
    }

    public void setEmailRecuperacao(String emailRecuperacao) {
        this.emailRecuperacao = emailRecuperacao;
    }

    /**
     * M�todo respons�vel por enviar mensagens para as views de acordo com as
     * opera��es realizadas.
     *
     * @param severidade � o grau da mensagem: erro, aviso, informativo.
     * @param titulo � o t�tulo da mensagem.
     * @param conteudo � o conte�do da mensagem.
     */
    private void enviaMensagemFaces(Severity severidade, String titulo, String conteudo) {
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage facesMessage = new FacesMessage(severidade, conteudo, titulo);
        context.addMessage(null, facesMessage);
    }
}
