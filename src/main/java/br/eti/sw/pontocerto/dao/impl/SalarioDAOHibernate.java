/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.eti.sw.pontocerto.dao.impl;

import br.eti.sw.pontocerto.dao.SalarioDAO;
import br.eti.sw.pontocerto.entidade.Salario;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import java.util.List;

/**
 *
 * @author Luiz Henrique
 */
public class SalarioDAOHibernate implements SalarioDAO {

    private Session session;

    public void setSession(Session session) {
        this.session = session;
    }

    @Override
    public void salvar(Salario salario) {
        this.session.save(salario);
    }

    @Override
    public void excluir(Salario salario) {
        this.session.delete(salario);
    }

    @Override
    public Salario carregar(Integer codigo) {
        return (Salario) this.session.get(Salario.class, codigo);
    }

    @Override
    public void atualizar(Salario salario) {
        this.session.update(salario);
    }

    @Override
    public List<Salario> listar() {
        Criteria criteria = this.session.createCriteria(Salario.class);
        criteria.addOrder(Order.asc("salarioBase"));
        return criteria.list();
    }
}
