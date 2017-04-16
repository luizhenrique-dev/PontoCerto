/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.eti.sw.pontocerto.dao.impl;

import br.eti.sw.pontocerto.dao.PontoDoDiaDAO;
import br.eti.sw.pontocerto.entidade.PontoDoDia;
import br.eti.sw.pontocerto.entidade.Usuario;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Date;
import java.util.List;

/**
 *
 * @author Luiz Henrique
 */
public class PontoDoDiaDAOHibernate implements PontoDoDiaDAO {

    private Session session;

    public void setSession(Session session) {
        this.session = session;
    }

    @Override
    public void salvar(PontoDoDia pontoDoDia) {
        this.session.save(pontoDoDia);
    }

    @Override
    public void excluir(PontoDoDia pontoDoDia) {
        this.session.delete(pontoDoDia);
    }

    @Override
    public PontoDoDia carregar(Integer codigo) {
        return (PontoDoDia) this.session.get(PontoDoDia.class, codigo);
    }

    @Override
    public void atualizar(PontoDoDia pontoDoDia) {
        this.session.update(pontoDoDia);
    }

    @Override
    public List<PontoDoDia> listar(Usuario usuario) {
        String hql = "from PontoDoDia ponto where ponto.usuario = :usuario";
        Query consulta = this.session.createQuery(hql);
        consulta.setInteger("usuario", usuario.getId());
        return consulta.list();
    }

    @Override
    public PontoDoDia buscarPontoDoDia(Date data, Usuario usuario) {
        String hql = "from PontoDoDia ponto where ponto.dataRealizacao = :data and ponto.usuario = :usuario";
        Query consulta = this.session.createQuery(hql);
        consulta.setDate("data", data);
        consulta.setInteger("usuario", usuario.getId());
        return (PontoDoDia) consulta.uniqueResult();
    }
}
