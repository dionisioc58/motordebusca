import java.io.Serializable;

/**
 * The Class Arquivo.
 */
public class Arquivo implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The caminho. */
	private String caminho;
	
	/** The nome. */
	private String nome;
	
	/** The qtde palavras. */
	private int qtdePalavras;
	
	/**
	 * Instantiates a new arquivo.
	 *
	 * @param caminho the caminho
	 * @param nome the nome
	 */
	public Arquivo(String caminho, String nome) {
		this.caminho = caminho;
		this.nome = nome;
		this.qtdePalavras = 0;
	}

	/**
	 * Gets the caminho.
	 *
	 * @return the caminho
	 */
	public String getCaminho() {
		return caminho;
	}

	/**
	 * Gets the nome.
	 *
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * Converte o objeto em string
	 */
	public String toString() {
		return (nome);
	}

	/**
	 * Gets the qtde palavras.
	 *
	 * @return the qtde palavras
	 */
	public int getQtdePalavras() {
		return qtdePalavras;
	}

	/**
	 * Sets the qtde palavras.
	 *
	 * @param qtdePalavras the new qtde palavras
	 */
	public void setQtdePalavras(int qtdePalavras) {
		this.qtdePalavras = qtdePalavras;
	}
}
