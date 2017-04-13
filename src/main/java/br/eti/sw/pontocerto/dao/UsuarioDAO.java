/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.eti.sw.pontocerto.dao;

import br.eti.sw.pontocerto.entidade.Usuario;

import java.util.List;

/**
 *
 * @author Luiz
 */
public interface UsuarioDAO {

    public void salvar(Usuario usuario);

    public void atualizar(Usuario usuario);

    public void excluir(Usuario usuario);

    public Usuario carregar(Integer codigo);

    public List<Usuario> listar();
    
    public Usuario buscarPorLogin(String login);

    public Usuario buscarPorEmail(String email);
}
