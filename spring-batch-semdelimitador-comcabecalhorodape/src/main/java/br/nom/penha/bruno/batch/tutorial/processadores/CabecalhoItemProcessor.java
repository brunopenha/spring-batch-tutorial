package br.nom.penha.bruno.batch.tutorial.processadores;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import br.nom.penha.bruno.batch.tutorial.entidades.Cabecalho;
import br.nom.penha.bruno.batch.tutorial.entidades.CabecalhoArquivo;

public class CabecalhoItemProcessor implements ItemProcessor<CabecalhoArquivo,Cabecalho>{

	private static final Logger LOG = LoggerFactory.getLogger(CabecalhoItemProcessor.class);
	
	@Override
	public Cabecalho process(final CabecalhoArquivo item) throws Exception {
		
		final String tipo = item.getTipo().toUpperCase();
		
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		final Date data = sdf.parse(item.getData());
		
		final String descricao = item.getDescricao().toUpperCase();
		
		final Cabecalho sujeitoAlterado = new Cabecalho(tipo,data,descricao);
		
		LOG.info("Convertendo (" + item + ") em (" + sujeitoAlterado + ")");
		
		
		return sujeitoAlterado;
	}

}
