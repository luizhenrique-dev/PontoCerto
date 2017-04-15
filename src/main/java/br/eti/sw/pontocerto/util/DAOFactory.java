/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.eti.sw.pontocerto.util;

import br.eti.sw.pontocerto.dao.PontoDoDiaDAO;
import br.eti.sw.pontocerto.dao.SalarioDAO;
import br.eti.sw.pontocerto.dao.UsuarioDAO;
import br.eti.sw.pontocerto.dao.impl.PontoDoDiaDAOHibernate;
import br.eti.sw.pontocerto.dao.impl.SalarioDAOHibernate;
import br.eti.sw.pontocerto.dao.impl.UsuarioDAOHibernate;

/**
 * Classe respons�vel por criar as inst�ncias das classes Hibernate da camada de
 * acesso aos dados.
 *
 * @author Luiz Henrique
 */
public class DAOFactory {

    public static UsuarioDAO criarUsuarioDAO() {
        UsuarioDAOHibernate usuarioDAO = new UsuarioDAOHibernate();
        usuarioDAO.setSession(HibernateUtil.getSessionFactory().getCurrentSession());
        return usuarioDAO;
    }

    public static SalarioDAO criarSalarioDAO() {
        SalarioDAOHibernate salarioDAO = new SalarioDAOHibernate();
        salarioDAO.setSession(HibernateUtil.getSessionFactory().getCurrentSession());
        return salarioDAO;
    }

    public static PontoDoDiaDAO criarPontoDoDiaDAO() {
        PontoDoDiaDAOHibernate pontoDoDiaDAO = new PontoDoDiaDAOHibernate();
        pontoDoDiaDAO.setSession(HibernateUtil.getSessionFactory().getCurrentSession());
        return pontoDoDiaDAO;
    }
}
