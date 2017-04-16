/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.eti.sw.pontocerto.negocio;

import br.eti.sw.pontocerto.dao.PontoDoDiaDAO;
import br.eti.sw.pontocerto.entidade.PontoDoDia;
import br.eti.sw.pontocerto.entidade.Usuario;
import br.eti.sw.pontocerto.util.DAOFactory;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author Administrador
 */
public class PontoDoDiaRN {

    private PontoDoDiaDAO pontoDoDiaDAO;

    public PontoDoDiaRN() {
        this.pontoDoDiaDAO = DAOFactory.criarPontoDoDiaDAO();
    }

    public PontoDoDia carregar(Integer id) {
        return this.pontoDoDiaDAO.carregar(id);
    }

    public void salvar(PontoDoDia pontoDoDia) {
        Integer codigo = pontoDoDia.getId();
        if (codigo == null || codigo == 0) {
            pontoDoDia.setDataRealizacao(Calendar.getInstance().getTime());
            this.pontoDoDiaDAO.salvar(pontoDoDia);
        } else {
            this.pontoDoDiaDAO.atualizar(pontoDoDia);
        }
    }

    public void excluir(PontoDoDia pontoDoDia) {
        this.pontoDoDiaDAO.excluir(pontoDoDia);
    }

    public List<PontoDoDia> listar(Usuario usuario) {
        List<PontoDoDia> pontosDoDia = new ArrayList<PontoDoDia>(this.pontoDoDiaDAO.listar(usuario));
        return pontosDoDia;
    }

    public PontoDoDia buscarPontoDoDia(Usuario usuario) {
        return this.pontoDoDiaDAO.buscarPontoDoDia(Calendar.getInstance().getTime(), usuario);
    }
}
