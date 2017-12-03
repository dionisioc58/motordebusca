import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
/**
 * The Class RepositorioArquivos.
 * Guarda a lista de arquivos que compoem a base
 */
public class RepositorioArquivos implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The arquivos. */
	private HashSet<Arquivo> arquivos;
	
	/**
	 * Instantiates a new repositorio arquivos.
	 */
	public RepositorioArquivos() {
		arquivos = new HashSet<Arquivo>();
	}
	
	/**
	 * Adds the arquivo.
	 *
	 * @param arquivo the arquivo
	 * @return true, if successful
	 */
	public boolean addArquivo(Arquivo arquivo) {
		boolean contem = false;
		for(Arquivo arq : arquivos) {
			if(arq.getCaminho().equals(arquivo.getCaminho())) {
				contem = true;
			}
		}
		if(!contem)
			arquivos.add(arquivo);
		
		return !contem;
	}
	
	/**
	 * Del arquivo.
	 *
	 * @param nomeArquivo the nome arquivo
	 */
	public void delArquivo(String nomeArquivo) {
		Iterator<Arquivo> i = arquivos.iterator();
		while (i.hasNext()) {
			Arquivo arquivo = i.next();
			String nome = arquivo.getNome() + " - " + arquivo.getQtdePalavras();
			if(nome.equals(nomeArquivo))
				i.remove();
		}
	}
	
	/**
	 * Lista arquivos.
	 *
	 * @return the hash set
	 */
	public HashSet<Arquivo> listaArquivos() {
		return arquivos;
	}
	
	/**
	 * Prints the arquivos.
	 */
	public void printArquivos() {
		for(Arquivo arquivo : arquivos)
			System.out.println(arquivo.getNome());
	}
}
