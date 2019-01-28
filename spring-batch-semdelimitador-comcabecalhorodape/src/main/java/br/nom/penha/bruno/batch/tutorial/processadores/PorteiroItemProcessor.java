package br.nom.penha.bruno.batch.tutorial.processadores;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import br.nom.penha.bruno.batch.tutorial.entidades.Cabecalho;
import br.nom.penha.bruno.batch.tutorial.entidades.CabecalhoArquivo;
import br.nom.penha.bruno.batch.tutorial.entidades.Sujeito;

public class PorteiroItemProcessor implements ItemProcessor<Object,Object>{

	private static final Logger LOG = LoggerFactory.getLogger(PorteiroItemProcessor.class);
	
	private ItemProcessor<Sujeito, Sujeito> processadorCorpo;
	private ItemProcessor<CabecalhoArquivo, Cabecalho> processadorCabecalho;
	
	@Override
	public Object process(final Object item) throws Exception {
		
		if(item instanceof SujeitoItemProcessor) { // Trata o corpo
			return processadorCorpo.process((Sujeito) item);			
		}else if(item instanceof CabecalhoItemProcessor) {
			return processadorCabecalho.process((CabecalhoArquivo) item);
		}
		
		final String msg = "Nao consigo tratar o item: " + item;
		LOG.error(msg);
		throw new RuntimeException(msg);
	}

}
