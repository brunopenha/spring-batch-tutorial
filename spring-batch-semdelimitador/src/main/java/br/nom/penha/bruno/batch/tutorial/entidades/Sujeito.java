package br.nom.penha.bruno.batch.tutorial.entidades;

public class Sujeito {

	private String primeiroNome;
	private String ultimoNome;
	
	public Sujeito() {
		
	}
	/**
	 * @param primeiroNome
	 * @param ultimoNome
	 */
	public Sujeito(String primeiroNome, String ultimoNome) {
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
	@Override
	public String toString() {
		return "Sujeito [primeiroNome=" + primeiroNome + ", ultimoNome=" + ultimoNome + "]";
	}
	
	
	
}
