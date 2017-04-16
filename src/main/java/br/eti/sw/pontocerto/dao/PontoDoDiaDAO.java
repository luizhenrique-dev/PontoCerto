/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.eti.sw.pontocerto.dao;

import br.eti.sw.pontocerto.entidade.PontoDoDia;
import br.eti.sw.pontocerto.entidade.Usuario;

import java.util.Date;
import java.util.List;

/**
 *
 * @author Luiz
 */
public interface PontoDoDiaDAO {

    public void salvar(PontoDoDia pontoDoDia);

    public void atualizar(PontoDoDia pontoDoDia);

    public void excluir(PontoDoDia pontoDoDia);

    public PontoDoDia carregar(Integer codigo);

    public List<PontoDoDia> listar(Usuario usuario);

    public PontoDoDia buscarPontoDoDia(Date data, Usuario usuario);
}
