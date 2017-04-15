/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.eti.sw.pontocerto.dao;

import br.eti.sw.pontocerto.entidade.Salario;

import java.util.List;

/**
 *
 * @author Luiz
 */
public interface SalarioDAO {

    public void salvar(Salario salario);

    public void atualizar(Salario salario);

    public void excluir(Salario salario);

    public Salario carregar(Integer codigo);

    public List<Salario> listar();
}
