package br.nom.penha.bruno.batch.tutorial.configuracao;

import javax.sql.DataSource;

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
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.transform.FixedLengthTokenizer;
import org.springframework.batch.item.file.transform.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import br.nom.penha.bruno.batch.tutorial.entidades.Sujeito;
import br.nom.penha.bruno.batch.tutorial.notificadores.OuvinteNotificacaoTarefaConcluida;
import br.nom.penha.bruno.batch.tutorial.processadores.SujeitoItemProcessor;

@Configuration
@EnableBatchProcessing
public class ConfiguradorBatch {
	
	@Autowired
	public JobBuilderFactory fabricaMontadorTarefas;
	
	@Autowired
	public StepBuilderFactory fabricaMontadorPassos;

	// Inicio do Reader Writer Processor
	 // tag::readerwriterprocessor[]
	@Bean
	public FlatFileItemReader<Sujeito> leitor(){
		return new FlatFileItemReaderBuilder<Sujeito>()
				.name("sujeitoItemReader")
				.resource(new ClassPathResource("dados-exemplo.txt"))
				.fixedLength()
				.addColumns(new Range(1,6)) // Vai ler da posicao 1 ate o final da 6
				.addColumns(new Range(7,12))// Vai ler da posicao 7 ate o final da 12
				.names(new String[] {"primeiroNome", "ultimoNome"})
				.fieldSetMapper(new BeanWrapperFieldSetMapper<Sujeito>() {
					{
						setTargetType(Sujeito.class);
					}
				})
				.build();
	}
	
	@Bean
	public SujeitoItemProcessor processador() {
		return new SujeitoItemProcessor();
	}
	
	@Bean
	public JdbcBatchItemWriter<Sujeito> escritor(DataSource dataSource){
		return new JdbcBatchItemWriterBuilder<Sujeito>()
					.itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
					.sql("INSERT INTO cidadaos (primeiro_nome,ultimo_nome) VALUES (:primeiroNome,:ultimoNome)")
					.dataSource(dataSource)
					.build();
				
				
	}
	 // tag::readerwriterprocessor[]
	// Fim do Reader Writer Processor
	
	// Inicio do Job Step
	
	@Bean
	public Job tarefaImportarSujeitos(OuvinteNotificacaoTarefaConcluida ouvinte, Step passo1) {
		
		return fabricaMontadorTarefas.get("tarefaImportarSujeitos")
				.incrementer(new RunIdIncrementer())
				.listener(ouvinte)
				.flow(passo1)
				.end()
				.build();
	}
	
	@Bean
	public Step passo1(JdbcBatchItemWriter<Sujeito> escritorParam) {
		return fabricaMontadorPassos.get("passo1")
				.<Sujeito, Sujeito> chunk(10)
				.reader(leitor())
				.processor(processador())
				.writer(escritorParam)
				.build();
	}
	
	// Fim do Job Step
}
