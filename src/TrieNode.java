import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;
/**
 * The Class TrieNode.
 */
public class TrieNode implements Serializable{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The valor. */
	private char valor;
	
	/** The is fim. */
	private boolean isFim;
	
	/** The contador. */
	private int contador;
	
	/** The filhos. */
	private LinkedList<TrieNode> filhos;
	
	/** The values. */
	private List<Value> values;
	
	/** The palavra. */
	private String palavra;

	/**
	 * Instantiates a new trie node.
	 *
	 * @param c the character
	 */
	public TrieNode(char c) {
		filhos = new LinkedList<TrieNode>();
		isFim = false;
		valor = c;
		contador = 0;
		values = new ArrayList<Value>();
	}

	/**
	 * Sub node.
	 *
	 * @param c the character
	 * @return the trie node
	 */
	public TrieNode subNode(char c) {
		if (filhos != null)
			for (TrieNode filho : filhos)
				if (Character.toLowerCase(filho.valor) == Character.toLowerCase(c))
					return filho;
		return null;
	}
	
	/**
	 * Converte o objeto em string
	 */
	@Override
	public String toString() {
		String retorno = "";
		for(TrieNode filho : filhos) {
			retorno += filho.toString();
		}
		if(isFim) {
			retorno += palavra + "\n";
			for(Value value : values)
				retorno += "  " + value.getArquivo().getNome() + ":" + value.getLinha() + ":" + value.getQtde();
			retorno += "\n";
		}
		return retorno;
	}
	
	/**
	 * Gets the indice.
	 *
	 * @return the indice
	 */
	public TreeMap<String, Value> getIndice() {
		TreeMap<String, Value> retorno = new TreeMap<>();
		if(this.isFim) {
			for(Value value : values)
				retorno.put(palavra, value);
		}
		for (TrieNode filho : this.getFilhos()) {
			retorno.putAll(filho.getIndice());
		}
		return retorno;
	}
	
	/**
	 * Prints the origens.
	 */
	public void printOrigens() {
		for(Value lista : this.values) {
			System.out.println(lista.getArquivo().getNome() + ": " + lista.getQtde() + " ocorrência(s) na linha " + lista.getLinha());
		}
	}
	
	/**
	 * Removes the book.
	 *
	 * @param nomeArquivo the nome arquivo
	 */
	public void removeBook(String nomeArquivo) {
		Iterator<TrieNode> i = this.getFilhos().iterator();
		while (i.hasNext()) {
			TrieNode filho = i.next();
			filho.removeBook(nomeArquivo);
			if((filho.getValues().size() == 0) && (filho.getFilhos().size() == 0)) {
				i.remove();
			}
		}
		if(this.isFim()) {
			Iterator<Value> ii = this.getValues().iterator();
			while (ii.hasNext()) {
				Value value = ii.next();
				String nome = value.getArquivo().getNome() + " - " + value.getArquivo().getQtdePalavras();
				if(nome.equals(nomeArquivo)) {
					ii.remove();
				}
			}
			if(this.getValues().size() == 0) {
				this.setFim(false);
			}
		}
	}

	/**
	 * Gets the values.
	 *
	 * @return the values
	 */
	public List<Value> getValues() {
		return values;
	}
	
	/**
	 * Gets the value.
	 *
	 * @param index the index
	 * @return the value
	 */
	public Value getValue(int index)
	{
		return values.get(index);
	}

	/**
	 * Adds the value.
	 *
	 * @param value the value
	 */
	public void addValue(Value value) {
		boolean encontrado = false;
		for(Value lista : this.values) {
			if(lista.isEqual(value)) {
				lista.incrementaQtde();
				encontrado = true;
			}
		}
		if(!encontrado)
			this.values.add(value);
	}
	
	/**
	 * Gets the contador.
	 *
	 * @return the contador
	 */
	public int getContador() {
		return contador;
	}

	/**
	 * Sets the contador.
	 *
	 * @param contador the new contador
	 */
	public void setContador(int contador) {
		this.contador = contador;
	}

	/**
	 * Gets the filhos.
	 *
	 * @return the filhos
	 */
	public LinkedList<TrieNode> getFilhos() {
		return filhos;
	}
	
	/**
	 * Adds the filho.
	 *
	 * @param node the node
	 */
	public void addFilho(TrieNode node) {
		filhos.add(node);
	}
	
	/**
	 * Del filho.
	 *
	 * @param node the node
	 */
	public void delFilho(TrieNode node) {
		filhos.remove(node);
	}

	/**
	 * Sets the fim.
	 *
	 * @param isFim the new fim
	 */
	public void setFim(boolean isFim) {
		this.isFim = isFim;
	}
	
	/**
	 * Checks if is fim.
	 *
	 * @return true, if is fim
	 */
	public boolean isFim() {
		return isFim;
	}

	/**
	 * Gets the palavra.
	 *
	 * @return the palavra
	 */
	public String getPalavra() {
		return palavra;
	}

	/**
	 * Sets the palavra.
	 *
	 * @param palavra the new palavra
	 */
	public void setPalavra(String palavra) {
		this.palavra = palavra;
	}
}
