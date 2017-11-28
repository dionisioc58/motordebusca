import java.util.ArrayList;
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
	 * Print the trie
	 * @param nivel A profundidade
	 */
	public void print(int nivel) {
		//Imprime | para identificar a posicao
		for(int i = 0; i < nivel; i++)
			System.out.print("|");
			
		//Imprime um asterisco para indicar o fim de uma sequencia 
		System.out.print(valor);
		if(isFim)
			System.out.println("*");
		else
			System.out.println("");
		
		//Imprime os filhos recursivamente
		for(TrieNode filho : filhos) {
			filho.print(nivel+1);
		}
	}
	
	public void printOrigens() {
		for(Value lista : this.values) {
			System.out.println(lista.getArquivo().getNome() + ": " + lista.getQtde() + " ocorrência(s) na linha " + lista.getLinha());
		}
	}

	public List<Value> getValues() {
		return values;
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
}
