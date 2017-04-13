/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.eti.sw.pontocerto.entidade;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

/**
 * @author Administrador
 */
@Entity
@Table(name = "ponto_dia")
public class PontoDoDia implements Serializable {

    private static final long serialVersionUID = 1485158431899446299L;
    private static final long QTD_HORAS_TRABALHADAS_DIA = 8;

    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Usuario.class)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(name = "hora_entrada")
    private Time horaEntrada;

    @Column(name = "hora_saida_almoco")
    private Time horaSaidaAlmoco;

    @Column(name = "hora_entrada_tarde")
    private Time horaEntradaTarde;

    @Column(name = "hora_saida")
    private Time horaSaida;

    @Column(name = "data")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataRealizacao;

    @Column(name = "hora_extra")
    private Time horaExtra;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Time getHoraEntrada() {
        return horaEntrada;
    }

    public Time getHoraExtra() {
        long tempoTrabalhadoNoDia = (horaSaidaAlmoco.getTime() - horaEntrada.getTime()) + (horaSaida.getTime() - horaEntradaTarde.getTime());
        long tempoTrabalhoObrigatorio = QTD_HORAS_TRABALHADAS_DIA * 3600000;
        horaExtra.setTime(tempoTrabalhadoNoDia - tempoTrabalhoObrigatorio);
        return horaExtra;
    }

    public void setHoraExtra(Time horaExtra) {
        this.horaExtra = horaExtra;
    }

    public void setHoraEntrada(Time horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public Time getHoraSaidaAlmoco() {
        return horaSaidaAlmoco;
    }

    public void setHoraSaidaAlmoco(Time horaSaidaAlmoco) {
        this.horaSaidaAlmoco = horaSaidaAlmoco;
    }

    public Time getHoraEntradaTarde() {
        return horaEntradaTarde;
    }

    public void setHoraEntradaTarde(Time horaEntradaTarde) {
        this.horaEntradaTarde = horaEntradaTarde;
    }

    public Time getHoraSaida() {
        return horaSaida;
    }

    public void setHoraSaida(Time horaSaida) {
        this.horaSaida = horaSaida;
    }

    public Date getDataRealizacao() {
        return dataRealizacao;
    }

    public void setDataRealizacao(Date dataRealizacao) {
        this.dataRealizacao = dataRealizacao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PontoDoDia)) return false;

        PontoDoDia that = (PontoDoDia) o;

        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;
        if (getUsuario() != null ? !getUsuario().equals(that.getUsuario()) : that.getUsuario() != null) return false;
        if (getHoraEntrada() != null ? !getHoraEntrada().equals(that.getHoraEntrada()) : that.getHoraEntrada() != null)
            return false;
        if (getHoraSaidaAlmoco() != null ? !getHoraSaidaAlmoco().equals(that.getHoraSaidaAlmoco()) : that.getHoraSaidaAlmoco() != null)
            return false;
        if (getHoraEntradaTarde() != null ? !getHoraEntradaTarde().equals(that.getHoraEntradaTarde()) : that.getHoraEntradaTarde() != null)
            return false;
        if (getHoraSaida() != null ? !getHoraSaida().equals(that.getHoraSaida()) : that.getHoraSaida() != null)
            return false;
        return getDataRealizacao() != null ? getDataRealizacao().equals(that.getDataRealizacao()) : that.getDataRealizacao() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getUsuario() != null ? getUsuario().hashCode() : 0);
        result = 31 * result + (getHoraEntrada() != null ? getHoraEntrada().hashCode() : 0);
        result = 31 * result + (getHoraSaidaAlmoco() != null ? getHoraSaidaAlmoco().hashCode() : 0);
        result = 31 * result + (getHoraEntradaTarde() != null ? getHoraEntradaTarde().hashCode() : 0);
        result = 31 * result + (getHoraSaida() != null ? getHoraSaida().hashCode() : 0);
        result = 31 * result + (getDataRealizacao() != null ? getDataRealizacao().hashCode() : 0);
        return result;
    }
}
