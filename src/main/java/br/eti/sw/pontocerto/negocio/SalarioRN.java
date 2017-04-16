/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.eti.sw.pontocerto.negocio;

import br.eti.sw.pontocerto.dao.SalarioDAO;
import br.eti.sw.pontocerto.entidade.Salario;
import br.eti.sw.pontocerto.entidade.Usuario;
import br.eti.sw.pontocerto.util.DAOFactory;

import java.math.BigDecimal;
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
            obtemValoresAliquitas(salario);
            this.salarioDAO.salvar(salario);
        } else {
            obtemValoresAliquitas(salario);
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

    private void obtemValoresAliquitas(Salario salario) {
        BigDecimal aliquotaINSS = salario.getImpostoINSS();
        BigDecimal aliquotaIRRF = salario.getImpostoIRRF();
        salario.setImpostoINSS(aliquotaINSS);
        salario.setImpostoIRRF(aliquotaIRRF);
    }

    public Salario buscarSalarioPorUsuario(Usuario usuario) {
        return this.salarioDAO.buscarSalarioPorUsuario(usuario);
    }
}
