package br.nom.penha.bruno.batch.tutorial.entidades;

public class Sujeito {

	private int tipo;
	private String primeiroNome;
	private String ultimoNome;
	
	public Sujeito() {
		
	}
	
	/**
	 * @param tipo
	 * @param primeiroNome
	 * @param ultimoNome
	 */
	public Sujeito(String tipoParam, String primeiroNome, String ultimoNome) {
		this.tipo = Integer.parseInt(tipoParam);
		this.primeiroNome = primeiroNome;
		this.ultimoNome = ultimoNome;
	}



	public String getPrimeiroNome() {
		return primeiroNome;
	}
	public void setPrimeiroNome(String primeiroNome) {
		this.primeiroNome = primeiroNome;
	}
	public String getUltimoNome() {
		return ultimoNome;
	}
	public void setUltimoNome(String ultimoNome) {
		this.ultimoNome = ultimoNome;
	}	
	
	public int getTipo() {
		return tipo;
	}
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	@Override
	public String toString() {
		return "Sujeito [tipo=" + tipo + ", primeiroNome=" + primeiroNome + ", ultimoNome=" + ultimoNome + "]";
	}	
	
}
