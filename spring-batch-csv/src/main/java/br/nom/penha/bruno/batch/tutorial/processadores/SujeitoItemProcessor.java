package br.nom.penha.bruno.batch.tutorial.processadores;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import br.nom.penha.bruno.batch.tutorial.entidades.Sujeito;

public class SujeitoItemProcessor implements ItemProcessor<Sujeito,Sujeito>{

	private static final Logger LOG = LoggerFactory.getLogger(SujeitoItemProcessor.class);
	
	@Override
	public Sujeito process(final Sujeito item) throws Exception {
		
		final String primeiroNome = item.getPrimeiroNome().toUpperCase();
		final String ultimoNome = item.getUltimoNome().toUpperCase();
		
		final Sujeito sujeitoAlterado = new Sujeito(primeiroNome,ultimoNome);
		
		LOG.info("Convertendo (" + item + ") em (" + sujeitoAlterado + ")");
		
		
		return sujeitoAlterado;
	}

}
