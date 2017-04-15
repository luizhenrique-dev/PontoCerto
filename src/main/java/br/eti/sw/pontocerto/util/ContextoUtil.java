/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.eti.sw.pontocerto.util;

import br.eti.sw.pontocerto.bean.ContextoBean;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 * Classe respons�vel por obter as informa��es do contexto do sistema no momento da requisi��o.
 * @author Luiz Henrique
 */
public class ContextoUtil {

    public static ContextoBean getContextoBean() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext external = context.getExternalContext();
        HttpSession session = (HttpSession) external.getSession(true);
        ContextoBean contextoBean = (ContextoBean) session.getAttribute("contextoBean");
        return contextoBean;
    }
}
