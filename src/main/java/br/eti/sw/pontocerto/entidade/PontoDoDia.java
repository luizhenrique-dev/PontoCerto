/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.eti.sw.pontocerto.entidade;

import br.eti.sw.pontocerto.util.DataUtil;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar horaEntrada;

    @Column(name = "hora_saida_almoco")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar horaSaidaAlmoco;

    @Column(name = "hora_entrada_tarde")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar horaEntradaTarde;

    @Column(name = "hora_saida")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar horaSaida;

    @Column(name = "data")
    @Temporal(TemporalType.DATE)
    private Date dataRealizacao;

    @Column(name = "hora_extra")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar horaExtra;

    @Transient
    private String horaEntradaFormatada;

    @Transient
    private String horaSaidaAlmocoFormatada;

    @Transient
    private String horaEntradaTardeFormatada;

    @Transient
    private String horaSaidaFormatada;

    public String getHoraEntradaFormatada() {
        SimpleDateFormat s = new SimpleDateFormat("HH:mm:ss");
        horaEntradaFormatada = s.format(this.horaEntrada.getTime());
        return horaEntradaFormatada;
    }

    public void setHoraEntradaFormatada(String horaEntradaFormata) {
        this.horaEntradaFormatada = horaEntradaFormata;
    }

    public String getHoraSaidaAlmocoFormatada() {
        SimpleDateFormat s = new SimpleDateFormat("HH:mm:ss");
        horaSaidaAlmocoFormatada = s.format(this.horaSaidaAlmoco.getTime());
        return horaSaidaAlmocoFormatada;
    }

    public void setHoraSaidaAlmocoFormatada(String horaSaidaAlmocoFormatada) {
        this.horaSaidaAlmocoFormatada = horaSaidaAlmocoFormatada;
    }

    public String getHoraEntradaTardeFormatada() {
        SimpleDateFormat s = new SimpleDateFormat("HH:mm:ss");
        horaEntradaTardeFormatada = s.format(this.horaEntradaTarde.getTime());
        return horaEntradaTardeFormatada;
    }

    public void setHoraEntradaTardeFormatada(String horaEntradaTardeFormata) {
        this.horaEntradaTardeFormatada = horaEntradaTardeFormata;
    }

    public String getHoraSaidaFormatada() {
        SimpleDateFormat s = new SimpleDateFormat("HH:mm:ss");
        horaSaidaFormatada = s.format(this.horaSaida.getTime());
        return horaSaidaFormatada;
    }

    public void setHoraSaidaFormata(String horaSaidaFormata) {
        this.horaSaidaFormatada = horaSaidaFormata;
    }

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

    public Calendar getHoraExtra() {
        long tempoTrabalhadoNoDia = (horaSaidaAlmoco.getTimeInMillis() - horaEntrada.getTimeInMillis()) + (horaSaida.getTimeInMillis() - horaEntradaTarde.getTimeInMillis());
        long tempoTrabalhoObrigatorio = QTD_HORAS_TRABALHADAS_DIA * 3600000;
        horaExtra.setTimeInMillis(tempoTrabalhadoNoDia - tempoTrabalhoObrigatorio);
        return horaExtra;
    }

    public Calendar getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(Calendar horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public Calendar getHoraSaidaAlmoco() {
        return horaSaidaAlmoco;
    }

    public void setHoraSaidaAlmoco(Calendar horaSaidaAlmoco) {
        this.horaSaidaAlmoco = horaSaidaAlmoco;
    }

    public Calendar getHoraEntradaTarde() {
        return horaEntradaTarde;
    }

    public void setHoraEntradaTarde(Calendar horaEntradaTarde) {
        this.horaEntradaTarde = horaEntradaTarde;
    }

    public Calendar getHoraSaida() {
        return horaSaida;
    }

    public void setHoraSaida(Calendar horaSaida) {
        this.horaSaida = horaSaida;
    }

    public Date getDataRealizacao() {
        return dataRealizacao;
    }

    public String getDataRealizacaoFormatada() {
        return DataUtil.converterData(dataRealizacao, DataUtil.DataFormat.DDMMAAAA);
    }

    public void setDataRealizacao(Date dataRealizacao) {
        this.dataRealizacao = dataRealizacao;
    }

    public void setHoraExtra(Calendar horaExtra) {
        this.horaExtra = horaExtra;
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
        if (getDataRealizacao() != null ? !getDataRealizacao().equals(that.getDataRealizacao()) : that.getDataRealizacao() != null)
            return false;
        return getHoraExtra() != null ? getHoraExtra().equals(that.getHoraExtra()) : that.getHoraExtra() == null;
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
        result = 31 * result + (getHoraExtra() != null ? getHoraExtra().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PontoDoDia{" +
                "horaEntrada=" + horaEntrada +
                ", horaSaidaAlmoco=" + horaSaidaAlmoco +
                ", horaEntradaTarde=" + horaEntradaTarde +
                ", horaSaida=" + horaSaida +
                ", dataRealizacao=" + dataRealizacao +
                ", horaExtra=" + horaExtra +
                '}';
    }
}
