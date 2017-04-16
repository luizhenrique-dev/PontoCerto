/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.eti.sw.pontocerto.dao;

import br.eti.sw.pontocerto.entidade.Salario;
import br.eti.sw.pontocerto.entidade.Usuario;

import java.util.List;

/**
 * @author Luiz
 */
public interface SalarioDAO {

    void salvar(Salario salario);

    void atualizar(Salario salario);

    void excluir(Salario salario);

    Salario carregar(Integer codigo);

    Salario buscarSalarioPorUsuario(Usuario usuario);

    List<Salario> listar();
}
