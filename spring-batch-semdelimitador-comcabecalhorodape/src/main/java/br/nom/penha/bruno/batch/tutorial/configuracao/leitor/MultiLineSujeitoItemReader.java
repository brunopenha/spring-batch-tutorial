package br.nom.penha.bruno.batch.tutorial.configuracao.leitor;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemStream;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.util.Assert;

import br.nom.penha.bruno.batch.tutorial.entidades.Sujeito;

public class MultiLineSujeitoItemReader implements ItemReader<Sujeito>, ItemStream {
	private FlatFileItemReader<FieldSet> delegate;

	/**
	 * @see org.springframework.batch.item.ItemReader#read()
	 */
	@Override
	public Sujeito read() throws Exception {
		Sujeito t = null;

		boolean lendoSujeito = true;
		
		for (FieldSet line; (line = this.delegate.read()) != null;) {
			String prefix = line.readString(0);
			if (prefix.equals("1")) {
				
				t = new Sujeito(); // Record must start with 'BEGIN'
				//Assert.notNull(t, "No '1' was found.");
				t.setTipo(line.readInt(2));
				t.setPrimeiroNome(line.readString(2));
				t.setUltimoNome(line.readString(3));
				lendoSujeito = false;
			} else if (prefix.equals("2")) {
				//Assert.notNull(t, "No '1' was found.");
				t.setTipo(line.readInt(2));
				t.setPrimeiroNome(line.readString(2));
				t.setUltimoNome(line.readString(3));
			} else if (prefix.equals("3")) {
				
			} else if (prefix.equals("1") && lendoSujeito) {
				return t; // Record must end with 'END'
			}
		}
		Assert.isNull(t, "No 'END' was found.");
		return null;
	}

	public void setDelegate(FlatFileItemReader<FieldSet> delegate) {
		this.delegate = delegate;
	}

	@Override
	public void close() throws ItemStreamException {
		this.delegate.close();
	}

	@Override
	public void open(ExecutionContext executionContext) throws ItemStreamException {
		this.delegate.open(executionContext);
	}

	@Override
	public void update(ExecutionContext executionContext) throws ItemStreamException {
		this.delegate.update(executionContext);
	}
}
