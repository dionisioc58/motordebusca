import java.io.Serializable;
/**
 * The Class Value.
 * Contem as informações referentes as palavras como
 * arquivo de origem, linha e quantidade de vezes na linha
 */
public class Value implements Serializable{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The arquivo. */
	private Arquivo arquivo;
	
	/** The linha. */
	private int linha;
	
	/** The qtde. */
	private int qtde;
	
	/**
	 * Instantiates a new value.
	 *
	 * @param arquivo the arquivo
	 * @param linha the linha
	 */
	public Value(Arquivo arquivo, int linha) {
		this.arquivo = arquivo;
		this.linha = linha; 
		this.qtde = 1;
	}

	/**
	 * Gets the arquivo.
	 *
	 * @return the arquivo
	 */
	public Arquivo getArquivo() {
		return arquivo;
	}

	/**
	 * Gets the linha.
	 *
	 * @return the linha
	 */
	public int getLinha() {
		return linha;
	}

	/**
	 * Gets the qtde.
	 *
	 * @return the qtde
	 */
	public int getQtde() {
		return qtde;
	}

	/**
	 * Checks if is equal.
	 *
	 * @param value the value
	 * @return true, if is equal
	 */
	public boolean isEqual(Value value) {
		if(arquivo.getCaminho().equals(value.getArquivo().getCaminho()) && getLinha() == value.getLinha())
			return true;
		return false;
	}
	
	/**
	 * Incrementa qtde.
	 */
	public void incrementaQtde() {
		this.qtde += 1;
	}
	
	/**
	 * Converte o objeto em string
	 */
	@Override
	public String toString() {
		return arquivo.getNome() + ":" + linha + ":" + qtde;
	}
}
