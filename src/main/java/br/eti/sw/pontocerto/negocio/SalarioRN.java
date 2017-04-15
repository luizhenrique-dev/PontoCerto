/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.eti.sw.pontocerto.negocio;

import br.eti.sw.pontocerto.dao.SalarioDAO;
import br.eti.sw.pontocerto.entidade.Salario;
import br.eti.sw.pontocerto.util.DAOFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrador
 */
public class SalarioRN {

    private SalarioDAO salarioDAO;

    public SalarioRN() {
        this.salarioDAO = DAOFactory.criarSalarioDAO();
    }

    public Salario carregar(Integer id) {
        return this.salarioDAO.carregar(id);
    }

    public void salvar(Salario salario) {
        Integer codigo = salario.getId();
        if (codigo == null || codigo == 0) {
            this.salarioDAO.salvar(salario);
        } else {
            this.salarioDAO.atualizar(salario);
        }
    }

    public void excluir(Salario salario) {
        this.salarioDAO.excluir(salario);
    }

    public List<Salario> listar() {
        List<Salario> feedbacksOrdenadas = new ArrayList<Salario>(this.salarioDAO.listar());
        return feedbacksOrdenadas;
    }
}
