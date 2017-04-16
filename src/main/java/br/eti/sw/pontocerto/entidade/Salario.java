/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.eti.sw.pontocerto.entidade;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Administrador
 */
@Entity
@Table(name = "salario")
public class Salario implements Serializable {

    private static final long serialVersionUID = 5434514464269156251L;
    public static final BigDecimal ALIQUOTA_INSS_11 = BigDecimal.valueOf(0.11);
    public static final BigDecimal ALIQUOTA_INSS_9 = BigDecimal.valueOf(0.09);
    public static final BigDecimal ALIQUOTA_IRRF_15 = BigDecimal.valueOf(0.15);
    public static final BigDecimal ALIQUOTA_IRRF_22 = BigDecimal.valueOf(0.225);
    public static final BigDecimal VALOR_MAX_TAXA_15porc_IRRF = new BigDecimal("3751.05");
    public static final BigDecimal VALOR_MAX_TAXA_9porc_INSS = new BigDecimal("2765.66");

    @Id
    @GeneratedValue
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @Column(name = "salario_base", nullable = false)
    private BigDecimal salarioBase = BigDecimal.ZERO;

    @Column(name = "porcentagem_irrf")
    private BigDecimal impostoIRRF;

    @Column(name = "porcentagem_inss")
    private BigDecimal impostoINSS;

    @Column(name = "outros_descontos")
    private BigDecimal outrosDescontos = BigDecimal.ZERO;

    @Column(name = "vencimento_adicional")
    private BigDecimal vencimentoAdicional;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public BigDecimal getSalarioBase() {
        return salarioBase.setScale(2);
    }

    public void setSalarioBase(BigDecimal salarioBase) {
        this.salarioBase = salarioBase;
    }

    public BigDecimal getImpostoIRRF() {
        if ((this.salarioBase.subtract(this.salarioBase.multiply(this.impostoINSS))).compareTo(VALOR_MAX_TAXA_15porc_IRRF) == 1) {
            return Salario.ALIQUOTA_IRRF_22;
        } else {
            return Salario.ALIQUOTA_IRRF_15;
        }
    }

    public void setImpostoIRRF(BigDecimal impostoIRRF) {
        this.impostoIRRF = impostoIRRF;
    }

    public BigDecimal getImpostoINSS() {
        if ((this.salarioBase.subtract(outrosDescontos)).compareTo(VALOR_MAX_TAXA_9porc_INSS) == 1) {
            return Salario.ALIQUOTA_INSS_11;
        } else {
            return Salario.ALIQUOTA_INSS_9;
        }
    }

    public void setImpostoINSS(BigDecimal impostoINSS) {
        this.impostoINSS = impostoINSS;
    }

    public BigDecimal getOutrosDescontos() {
        return outrosDescontos;
    }

    public void setOutrosDescontos(BigDecimal outrosDescontos) {
        this.outrosDescontos = outrosDescontos;
    }

    public BigDecimal getVencimentoAdicional() {
        return vencimentoAdicional;
    }

    public void setVencimentoAdicional(BigDecimal vencimentoAdicional) {
        this.vencimentoAdicional = vencimentoAdicional;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Salario)) return false;

        Salario salario = (Salario) o;

        if (getImpostoIRRF() != salario.getImpostoIRRF()) return false;
        if (getImpostoINSS() != salario.getImpostoINSS()) return false;
        if (getId() != null ? !getId().equals(salario.getId()) : salario.getId() != null) return false;
        if (getUsuario() != null ? !getUsuario().equals(salario.getUsuario()) : salario.getUsuario() != null)
            return false;
        if (getSalarioBase() != null ? !getSalarioBase().equals(salario.getSalarioBase()) : salario.getSalarioBase() != null)
            return false;
        if (getOutrosDescontos() != null ? !getOutrosDescontos().equals(salario.getOutrosDescontos()) : salario.getOutrosDescontos() != null)
            return false;
        return getVencimentoAdicional() != null ? getVencimentoAdicional().equals(salario.getVencimentoAdicional()) : salario.getVencimentoAdicional() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getUsuario() != null ? getUsuario().hashCode() : 0);
        result = 31 * result + (getSalarioBase() != null ? getSalarioBase().hashCode() : 0);
        result = 31 * result + (getImpostoIRRF() != null ? getImpostoIRRF().hashCode() : 0);
        result = 31 * result + (getImpostoINSS() != null ? getImpostoINSS().hashCode() : 0);
        result = 31 * result + (getOutrosDescontos() != null ? getOutrosDescontos().hashCode() : 0);
        result = 31 * result + (getVencimentoAdicional() != null ? getVencimentoAdicional().hashCode() : 0);
        return result;
    }
}
