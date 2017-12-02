import java.util.HashMap;
import java.util.Iterator;

/**
 * The Class Trie.
 */
public class Trie {
	
	/** The raiz. */
	private TrieNode raiz;

	/**
	 * Instantiates a new trie.
	 */
	public Trie() {
		raiz = new TrieNode(' ');
	}

	/**
	 * Insert word.
	 *
	 * @param s the string
	 */
	public void insertWord(String s, Arquivo arquivo, int linha) {
		TrieNode retorno = search(s, false);
		Value value = new Value(arquivo, linha);
		if (retorno != null) {
			retorno.addValue(value);
			return;
		}
		TrieNode atual = raiz;
		for (char ch : s.toCharArray()) {
			TrieNode filho = atual.subNode(ch);
			if (filho != null)
				atual = filho;
			else {
				atual.addFilho(new TrieNode(ch));
				atual = atual.subNode(ch);
			}
			atual.setContador(atual.getContador() + 1);
		}
		atual.setFim(true);
		atual.addValue(value);
		atual.setPalavra(s);
	}

	/**
	 * Search.
	 *
	 * @param s the string
	 * @return true, if successful
	 */
	public TrieNode search(String s, boolean imprimir) {
		TrieNode atual = raiz;
		for (char ch : s.toCharArray()) {
			if (atual.subNode(ch) == null) {
				if(imprimir)
					System.out.println("Não encontrado!");
				return null;
			} else
				atual = atual.subNode(ch);
		}
		if (atual.isFim()) {
			if(imprimir)
				atual.printOrigens();
			return atual;
		}
		return null;
	}

	/**
	 * Removes the word.
	 *
	 * @param s the string
	 */
	public void removeWord(String s) {
		if (search(s, false) == null) {
			System.out.println(s + " não existe na Trie.");
			return;
		}
		TrieNode atual = raiz;
		for (char ch : s.toCharArray()) {
			TrieNode filho = atual.subNode(ch);
			if (filho.getContador() == 1) {
				atual.delFilho(filho);
				return;
			} else {
				filho.setContador(filho.getContador() - 1);
				atual = filho;
			}
		}
		atual.setFim(false);
	}
	
	@Override
	public String toString() {
		String retorno = "[Raiz]\n";
		for (TrieNode filho : raiz.getFilhos()) {
			retorno += filho.toString();
		}
		return retorno;
	}
	
	public HashMap<String, Value> getIndice() {
		HashMap<String, Value> retorno = new HashMap<>();
		for (TrieNode filho : raiz.getFilhos()) {
			retorno.putAll(filho.getIndice());
		}
		return retorno;
	}

	/**
	 * Remove um livro da árvore
	 * @param nomeArquivo Nome do arquivo
	 * @return True se conseguiu remover
	 */
	public boolean removeBook(String nomeArquivo) {
		Iterator<TrieNode> i = raiz.getFilhos().iterator();
		while (i.hasNext()) {
			TrieNode filho = i.next();
			filho.removeBook(nomeArquivo);
			if((filho.getValues().size() == 0) && (filho.getFilhos().size() == 0))
				i.remove();
		}
		return true;
	}
}