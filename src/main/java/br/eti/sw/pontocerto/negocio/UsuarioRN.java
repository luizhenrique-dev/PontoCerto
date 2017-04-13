/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.eti.sw.pontocerto.negocio;

import br.eti.sw.pontocerto.dao.UsuarioDAO;
import br.eti.sw.pontocerto.entidade.Mensagem;
import br.eti.sw.pontocerto.entidade.Usuario;
import br.eti.sw.pontocerto.util.DAOFactory;
import br.eti.sw.pontocerto.util.RNException;
import br.eti.sw.pontocerto.util.UtilException;

import java.util.Calendar;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 *
 * @author Luiz
 */
public class UsuarioRN {

    private UsuarioDAO usuarioDAO;
    private String senhaCriptografada;

    public UsuarioRN() {
        this.usuarioDAO = DAOFactory.criarUsuarioDAO();
    }

    public Usuario carregar(Integer id) {
        return this.usuarioDAO.carregar(id);
    }

    public Usuario buscaPorLogin(String login) {
        return this.usuarioDAO.buscarPorLogin(login);
    }

    public Usuario buscaPorEmail(String email) {
        return this.usuarioDAO.buscarPorEmail(email);
    }

    public String getEmailUsuario(Usuario usuario) {
        return this.usuarioDAO.carregar(usuario.getId()).getEmail();
    }

    public void salvar(Usuario usuario) throws RNException {
        Integer codigo = usuario.getId();

        if (codigo == null || codigo == 0) {
            usuario.setSenha(criptografaSenha(usuario.getSenha()));
            usuario.setNome(usuario.getNome().toUpperCase());
            usuario.getPermissao().add("ROLE_USUARIO");

            Calendar data = Calendar.getInstance();
            usuario.setDataCadastro(data);
            usuario.setDataUltimoLogin(data);
            this.usuarioDAO.salvar(usuario);
//            try {
//                enviarEmailCadastro(usuario);
//            } catch (RNException ex) {
//                throw new RNException(ex.getMessage());
//            }
        } else {
            if (buscaPorEmail(usuario.getEmail()) == null || getEmailUsuario(usuario).equals(usuario.getEmail())) {
                usuario.setSenha(criptografaSenha(usuario.getSenha()));
                usuario.setNome(usuario.getNome().toUpperCase());
                usuario.setDataCadastro(buscaPorLogin(usuario.getLogin()).getDataCadastro());
                usuario.setDataUltimoLogin(buscaPorLogin(usuario.getLogin()).getDataUltimoLogin());
                this.usuarioDAO.atualizar(usuario);
            } else {
                throw new RNException("Já existe um usuário cadastrado com este e-mail!");
            }
        }
    }

    public void atualizarUsuario(Usuario usuario) {
        this.usuarioDAO.atualizar(usuario);
    }

    public void excluir(Usuario usuario) {
        this.usuarioDAO.excluir(usuario);
    }

    public Set<Usuario> listar() {
        Set<Usuario> listaRetorno = new LinkedHashSet<Usuario>(this.usuarioDAO.listar());
        return listaRetorno;
    }

    private void enviarEmailCadastro(Usuario usuario) throws RNException {
        Mensagem mensagem = new Mensagem();
        mensagem.setDestino(usuario.getEmail());
        mensagem.setTitulo("Confirmação de cadastro - PontoCerto");
        mensagem.setMensagem("Prezado(a) " + usuario.getNome() + ", <br /><br />"
                + "Bem-vindo ao sistema PontoCerto desenvolvido por Luiz Henrique Fernandes da Silva. "
                + "Agora você pode verificar horas trabalhadas no dia, cálculo de horas extras, cálculo de salário e etc pelo sistema! "
                + "<br /><br />Seu login é: " + usuario.getLogin()
                + "<br /><br />Atenciosamente, <br /><br />"
                + "Equipe PontoCerto");

        MensagemRN mensagemRN = new MensagemRN();
        try {
            mensagemRN.enviaEmail(mensagem);
        } catch (UtilException ex) {
            throw new RNException("Não foi possível enviar o e-mail de boas vindas para seu usuário.");
        }
    }

    private void enviarEmailRecuperarCredenciais(Usuario usuario, String novaSenha) throws RNException {
        Mensagem mensagem = new Mensagem();
        mensagem.setDestino(usuario.getEmail());
        mensagem.setTitulo("Recuperação de login/senha - PontoCerto");
        mensagem.setMensagem("Prezado(a) " + usuario.getNome() + ", <br /><br />"
                + "Recebemos uma solicitação de recuperação de login/senha do seu usuário. <br /><br />"
                + "Criamos uma senha temporária para você poder acessar o sistema. Assim que for possível troque-a por uma senha de seu interesse.<br /><br />"
                + "Seguem os dados: <br /><br />"
                + "<b>Login:</b> " + usuario.getLogin() + "<br />"
                + "<b>Senha:</b> " + novaSenha + "<br />"
                + "<br /><br />Atenciosamente, <br /><br />"
                + "Equipe PontoCerto");

        MensagemRN mensagemRN = new MensagemRN();
        try {
            mensagemRN.enviaEmail(mensagem);
        } catch (UtilException ex) {
            throw new RNException(ex.getMessage());
        }
    }

    public void recuperarCredenciais(Usuario usuario) throws RNException {
        try {
            String novaSenha = criarSenhaAleatoria();
            String senhaCripto = org.apache.commons.codec.digest.DigestUtils.sha256Hex(novaSenha);
            enviarEmailRecuperarCredenciais(usuario, novaSenha);
            usuario.setSenha(senhaCripto);
            this.usuarioDAO.atualizar(usuario);
        } catch (RNException ex) {
            throw new RNException(ex.getMessage());
        }
    }

    private String criarSenhaAleatoria() {
        int qtdeMaximaCaracteres = 8;
        String[] caracteres = {"a", "1", "b", "2", "4", "5", "6", "7", "8",
            "9", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k",
            "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w",
            "x", "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
            "V", "W", "X", "Y", "Z"};

        StringBuilder senha = new StringBuilder();

        for (int i = 0; i < qtdeMaximaCaracteres; i++) {
            int posicao = (int) (Math.random() * caracteres.length);
            senha.append(caracteres[posicao]);
        }
        return senha.toString();
    }

    /**
     * M�todo respons�vel por criptografar a senha informada pelo usu�rio.
     *
     * @param senha � a senha em texto plano.
     * @return senha criptografada ap�s execu��o do algoritmo SHA256.
     */
    private String criptografaSenha(String senha) {
        if (senha != null && senha.trim().length() == 0) {
            return this.senhaCriptografada;
        } else {
            String senhaCripto = org.apache.commons.codec.digest.DigestUtils.sha256Hex(senha);
            return senhaCripto;
        }
    }
}
