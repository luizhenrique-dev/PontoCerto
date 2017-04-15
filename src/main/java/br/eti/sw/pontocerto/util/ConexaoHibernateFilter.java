/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.eti.sw.pontocerto.util;

import org.hibernate.SessionFactory;

import javax.servlet.*;
import java.io.IOException;

/**
 * Classe utilit�ria para filtrar e centralizar todas as requisi��es Hibernate com o
 * objetivo: Abrir Sessions do Hibernate; Associar Sessions a ThreadsLocais;
 * Fechar Sessions associadas.
 *
 * @author Luiz Henrique
 */
public class ConexaoHibernateFilter implements Filter {

    private SessionFactory sf;

    @Override
    public void init(FilterConfig config) throws ServletException {
        this.sf = HibernateUtil.getSessionFactory();
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
            ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {
        try {
            this.sf.getCurrentSession().beginTransaction();
            chain.doFilter(servletRequest, servletResponse);
            this.sf.getCurrentSession().getTransaction().commit();
            this.sf.getCurrentSession().close();
        } catch (Throwable ex) {
            try {
                if (this.sf.getCurrentSession().getTransaction().isActive()) {
                    this.sf.getCurrentSession().getTransaction().rollback();
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
            throw new ServletException(ex);
        }
    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub

    }
}
