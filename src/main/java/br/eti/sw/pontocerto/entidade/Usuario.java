/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.eti.sw.pontocerto.entidade;

import br.com.caelum.stella.bean.validation.CPF;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Administrador
 */
@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1485158431899446299L;

    @Id
    @GeneratedValue
    private Integer id;

    private String nome;
    @Column(unique = true)
    private String email;
    @CPF
    @Column(unique = true)

    /**
     * Senha de acesso ao sistema.
     */
    private String senha;
    private boolean ativo;
    @Column(unique = true)
    /**
     * Login de acesso ao sistema.
     */
    private String login;
    @Column(name = "data_cadastro", nullable = false, columnDefinition = "DATETIME")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    /**
     * Data de cadastro do usuario no sistema.
     */
    private Calendar dataCadastro;

    @Column(name = "ultimo_login", nullable = false, columnDefinition = "DATETIME")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    /**
     * Data do ultimo login do usuario no sistema.
     */
    private Calendar dataUltimoLogin;

    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @JoinTable(
            name = "usuario_permissao",
            uniqueConstraints = {
                    @UniqueConstraint(columnNames = {"usuario", "permissao"})},
            joinColumns = @JoinColumn(name = "usuario"))

    @Column(name = "permissao", length = 50)
    /**
     * Lista com as permissões do usuário dentro do sistema.
     */
    private Set<String> permissao = new HashSet<String>();

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL,
            fetch = FetchType.EAGER)
    private Salario salario;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Set<String> getPermissao() {
        return permissao;
    }

    public Calendar getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Calendar dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Calendar getDataUltimoLogin() {
        return dataUltimoLogin;
    }

    public void setDataUltimoLogin(Calendar dataUltimoLogin) {
        this.dataUltimoLogin = dataUltimoLogin;
    }

    public void setPermissao(Set<String> permissao) {
        this.permissao = permissao;
    }

    public Salario getSalario() {
        return salario;
    }

    public void setSalario(Salario salario) {
        this.salario = salario;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 41 * hash + (this.email != null ? this.email.hashCode() : 0);
        hash = 41 * hash + (this.login != null ? this.login.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Usuario other = (Usuario) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        if ((this.email == null) ? (other.email != null) : !this.email.equals(other.email)) {
            return false;
        }
        if ((this.login == null) ? (other.login != null) : !this.login.equals(other.login)) {
            return false;
        }
        return true;
    }
}
