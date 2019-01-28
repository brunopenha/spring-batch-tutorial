package br.nom.penha.bruno.batch.tutorial.entidades;

public class CabecalhoArquivo {

	private String tipo;
	private String data;
	private String descricao;

	public CabecalhoArquivo() {
		
	}
	
	public CabecalhoArquivo(String tipoParam, String dataParam, String descricaoParam) {
		tipo = tipoParam;
		data = dataParam;
		descricao = descricaoParam;
	}
	
	

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
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
