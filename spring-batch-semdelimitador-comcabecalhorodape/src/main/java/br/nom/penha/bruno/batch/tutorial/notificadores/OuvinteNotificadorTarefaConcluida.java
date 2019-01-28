package br.nom.penha.bruno.batch.tutorial.notificadores;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import br.nom.penha.bruno.batch.tutorial.entidades.Sujeito;

@Component
public class OuvinteNotificadorTarefaConcluida extends JobExecutionListenerSupport {

	private static final Logger LOG = LoggerFactory.getLogger(OuvinteNotificadorTarefaConcluida.class);
	
	private final JdbcTemplate modeloJdbc;
	
	public OuvinteNotificadorTarefaConcluida(JdbcTemplate jdbcTemplate) {
		
		this.modeloJdbc = jdbcTemplate;
	}
	
	@Override
	public void afterJob(JobExecution execucaoTarefa) {
		
		if(execucaoTarefa.getStatus() == BatchStatus.COMPLETED) {
			LOG.info("FIM da Tarefa (Job) !!!");
						
			modeloJdbc.query("SELECT tipo, primeiro_nome, ultimo_nome FROM cidadaos", 
					(resultado,linha) -> new Sujeito(
												resultado.getString(1), 
												resultado.getString(2),
												resultado.getString(3))
							) // resultado da consulta
			.forEach( sujeito -> LOG.info("Encontrei <" + sujeito + "> no banco"));
			
//			modeloJdbc.query("SELECT primeiro_nome, ultimo_nome FROM remessas", 
//					(resultado,linha) -> new Cabecalho(
//												resultado.getString(1), 
//												resultado.getString(2),
//												resultado.getString(3))
//							) // resultado da consulta
//			.forEach( cabecalho -> LOG.info("Encontrei <" + cabecalho + "> no banco"));
		}
		
	}
	
}
