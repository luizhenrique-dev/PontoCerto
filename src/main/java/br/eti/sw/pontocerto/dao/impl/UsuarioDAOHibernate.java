/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.eti.sw.pontocerto.dao.impl;

import br.eti.sw.pontocerto.dao.UsuarioDAO;
import br.eti.sw.pontocerto.entidade.Usuario;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import java.util.List;

/**
 *
 * @author Luiz Henrique
 */
public class UsuarioDAOHibernate implements UsuarioDAO {

    private Session session;

    public void setSession(Session session) {
        this.session = session;
    }

    @Override
    public void salvar(Usuario usuario) {
        this.session.save(usuario);
    }

    @Override
    public void excluir(Usuario usuario) {
        this.session.delete(usuario);
    }

    @Override
    public Usuario carregar(Integer codigo) {
        return (Usuario) this.session.get(Usuario.class, codigo);
    }

    @Override
    public void atualizar(Usuario usuario) {
        if (usuario.getPermissao() == null || usuario.getPermissao().size() == 0) {
            Usuario usuarioPermissao = this.carregar(usuario.getId());
            usuario.setPermissao(usuarioPermissao.getPermissao());
            this.session.evict(usuarioPermissao);
        }
        this.session.merge(usuario);
    }

    @Override
    public Usuario buscarPorEmail(String email) {
        String hql = "from Usuario u where u.email = :email";
        Query consulta = this.session.createQuery(hql);
        consulta.setString("email", email);
        return (Usuario) consulta.uniqueResult();
    }

    @Override
    public Usuario buscarPorLogin(String login) {
        String hql = "from Usuario u where u.login = :login";
        Query consulta = this.session.createQuery(hql);
        consulta.setString("login", login);
        return (Usuario) consulta.uniqueResult();
    }

    @Override
    public List<Usuario> listar() {
        Criteria criteria = this.session.createCriteria(Usuario.class);
        criteria.addOrder(Order.asc("nome"));
        return criteria.list();
    }
}
