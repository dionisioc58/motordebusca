import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * The Class TrieNode.
 */
public class TrieNode {
	
	/** The valor. */
	private char valor;
	
	/** The is fim. */
	private boolean isFim;
	
	/** The contador. */
	private int contador;
	
	/** The filhos. */
	private LinkedList<TrieNode> filhos;
	
	private List<Value> values;
	
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
	
	public HashMap<String, Value> getIndice() {
		HashMap<String, Value> retorno = new HashMap<>();
		if(this.isFim) {
			for(Value value : values)
				retorno.put(palavra, value);
		}
		for (TrieNode filho : this.getFilhos()) {
			retorno.putAll(filho.getIndice());
		}
		return retorno;
	}
	
	public void printOrigens() {
		for(Value lista : this.values) {
			System.out.println(lista.getArquivo().getNome() + ": " + lista.getQtde() + " ocorrência(s) na linha " + lista.getLinha());
		}
	}
	
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
				if(value.getArquivo().getNome().equals(nomeArquivo)) {
					ii.remove();
				}
			}
			if(this.getValues().size() == 0) {
				this.setFim(false);
			}
		}
	}

	public List<Value> getValues() {
		return values;
	}
	
	public Value getValue(int index)
	{
		return values.get(index);
	}

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
	
	public int getContador() {
		return contador;
	}

	public void setContador(int contador) {
		this.contador = contador;
	}

	public LinkedList<TrieNode> getFilhos() {
		return filhos;
	}
	
	public void addFilho(TrieNode node) {
		filhos.add(node);
	}
	
	public void delFilho(TrieNode node) {
		filhos.remove(node);
	}

	public void setFim(boolean isFim) {
		this.isFim = isFim;
	}
	
	public boolean isFim() {
		return isFim;
	}

	public String getPalavra() {
		return palavra;
	}

	public void setPalavra(String palavra) {
		this.palavra = palavra;
	}
}
