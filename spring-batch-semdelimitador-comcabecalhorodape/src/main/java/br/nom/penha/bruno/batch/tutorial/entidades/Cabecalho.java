package br.nom.penha.bruno.batch.tutorial.entidades;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Cabecalho {

	private String tipo;
	private Date data;
	private String descricao;

	public Cabecalho() {
		
	}
	
	public Cabecalho(String tipoParam, Date dataParam, String descricaoParam) {
		tipo = tipoParam;
		data = dataParam;
		descricao = descricaoParam;
	}
	
	public Cabecalho(String tipoParam, String dataParam, String descricaoParam) {
		tipo = tipoParam;
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		try {
			data = sdf.parse(dataParam);
			
		} catch (ParseException e) {
			//LOG.error("Ocorreu ao tratar a data no cabecalho ->" + e);
		}
		
		descricao = descricaoParam;
	}
	

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String toString() {
		return "Cabecalho [tipo=" + tipo + ", data=" + data + ", descricao=" + descricao + "]";
	}

}
