package br.nom.penha.bruno.batch.tutorial.configuracao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineCallbackHandler;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.transform.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import br.nom.penha.bruno.batch.tutorial.configuracao.leitor.MultiLineSujeitoItemReader;
import br.nom.penha.bruno.batch.tutorial.entidades.Cabecalho;
import br.nom.penha.bruno.batch.tutorial.entidades.CabecalhoArquivo;
import br.nom.penha.bruno.batch.tutorial.entidades.Sujeito;
import br.nom.penha.bruno.batch.tutorial.notificadores.OuvinteNotificadorTarefaConcluida;
import br.nom.penha.bruno.batch.tutorial.processadores.CabecalhoItemProcessor;
import br.nom.penha.bruno.batch.tutorial.processadores.SujeitoItemProcessor;

@Configuration
@EnableBatchProcessing
public class ConfiguradorBatch {
	
	private static final Logger LOG = LoggerFactory.getLogger(ConfiguradorBatch.class);
	
	private Cabecalho cabecalhoCarga;
	
	@Autowired
	public JobBuilderFactory fabricaMontadorTarefas;
	
	@Autowired
	public StepBuilderFactory fabricaMontadorPassos;

	// Inicio do Reader Writer Processor
	
	// Tratamento do cabeçalho
	
	
	//
//	@Bean
//	public FlatFileItemReader<CabecalhoArquivo> leitor(){
//		return new MultiLineSujeitoItemReader()
//				.name("cabecalhoItemReader")
//				.resource(new ClassPathResource("dados-exemplo.txt"))
//				.fixedLength()
//				.addColumns(new Range(1,1))  // tipo
//				.addColumns(new Range(2,10)) // data
//				.addColumns(new Range(11,26))  // descricao
//				.names(new String[] {"tipo", "data", "descricao"})
//				.fieldSetMapper(new BeanWrapperFieldSetMapper<CabecalhoArquivo>() {
//					{
//						setTargetType(CabecalhoArquivo.class);
//					}
//				})
//				.build();
//	}
	
	@Bean
	public FlatFileItemReader<CabecalhoArquivo> leitorCabecalho(){
		return new FlatFileItemReaderBuilder<CabecalhoArquivo>()
				.name("cabecalhoItemReader")
				.resource(new ClassPathResource("dados-exemplo.txt"))
				.fixedLength()
				.addColumns(new Range(1,1))  // tipo
				.addColumns(new Range(2,10)) // data
				.addColumns(new Range(11,26))  // descricao
				.names(new String[] {"tipo", "data", "descricao"})
				.fieldSetMapper(new BeanWrapperFieldSetMapper<CabecalhoArquivo>() {
					{
						setTargetType(CabecalhoArquivo.class);
					}
				})
				.build();
	}
	
	@Bean
	public CabecalhoItemProcessor processadorCabecalho() {
		return new CabecalhoItemProcessor();
	}
	
	@Bean
	public JdbcBatchItemWriter<Cabecalho> escritorCacecalho(DataSource dataSource){
		return new JdbcBatchItemWriterBuilder<Cabecalho>()
					.itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
					.sql("INSERT INTO remessa (tipo,data,descricao) VALUES (:tipo,:data,:descricao)")
					.dataSource(dataSource)
					.build();
				
				
	}
	// Fim do tratamento do cabeçalho
	
	// Tratamento do Corpo
	@Bean
	public FlatFileItemReader<Sujeito> leitorCorpo(){
		LineCallbackHandler callback = new LineCallbackHandler() {
			
			@Override
			public void handleLine(String line) {
				
				int indice = 0;
				
				final String tipo = line.substring(indice, indice + 1);
				indice += 1;
				
				final String data = line.substring(indice, indice + 8);
				indice += 8;
				
				final String descricao = line.substring(indice, indice + 17);
				
				cabecalhoCarga = new Cabecalho(tipo, data, descricao);
				
				
			}
		};
		return new FlatFileItemReaderBuilder<Sujeito>()
				.name("sujeitoItemReader")
				.linesToSkip(1)
				.resource(new ClassPathResource("dados-exemplo.txt"))
				.fixedLength()
				.addColumns(new Range(1,1)) // Vai ler da posicao 1 ate o final da 1
				.addColumns(new Range(2,7)) // Vai ler da posicao 2 ate o final da 7
				.addColumns(new Range(7,13))// Vai ler da posicao 7 ate o final da 13
				.names(new String[] {"tipo","primeiroNome", "ultimoNome"})
				.fieldSetMapper(new BeanWrapperFieldSetMapper<Sujeito>() {
					{
						setTargetType(Sujeito.class);
					}
				})
				.skippedLinesCallback(callback)
				.build();
	}
	
	@Bean
	public SujeitoItemProcessor processadorCorpo() {
		return new SujeitoItemProcessor();
	}
	
	@Bean
	public JdbcBatchItemWriter<Sujeito> escritorCorpo(DataSource dataSource){
		
		JdbcBatchItemWriter<Cabecalho> banco = new JdbcBatchItemWriter<Cabecalho>();
		banco.setDataSource(dataSource);
		banco.setSql("INSERT INTO remessa (tipo,data,descricao) VALUES (:tipo,:data,:descricao)");
		
		
		return new JdbcBatchItemWriterBuilder<Sujeito>()
					.itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
					.sql("INSERT INTO cidadaos (tipo,primeiro_nome,ultimo_nome) VALUES (:tipo,:primeiroNome,:ultimoNome)")
					.dataSource(dataSource)
					.build();
				
				
	}
	// Fim tratamento do Corpo
	
	// Fim do Reader Writer Processor
	
	// Inicio do Job Step
	
	@Bean
	public Job tarefaImportarSujeitos(OuvinteNotificadorTarefaConcluida ouvinte, Step passo1) {
		
		return fabricaMontadorTarefas.get("tarefaImportarSujeitos")
				.incrementer(new RunIdIncrementer())
				.listener(ouvinte)
				.flow(passo1)
				.end()
				.build();
	}
	
	@Bean
	public Step passo99(JdbcBatchItemWriter<Cabecalho> escritorParam) {
		return fabricaMontadorPassos.get("passo99")
				.<CabecalhoArquivo, Cabecalho> chunk(1)
				.reader(leitorCabecalho())
				.processor(processadorCabecalho())
				.writer(escritorParam)
				.build();
	}
	
	@Bean
	public Step passo1(JdbcBatchItemWriter<Sujeito> escritorParam) {
		return fabricaMontadorPassos.get("passo1")
				.<Sujeito, Sujeito> chunk(10)
				.reader(leitorCorpo())
				.processor(processadorCorpo())
				.writer(escritorParam)
				.build();
	}
	
	// Fim do Job Step
}
