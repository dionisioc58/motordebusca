import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;

public class RepositorioArquivos implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private HashSet<Arquivo> arquivos;
	
	public RepositorioArquivos() {
		arquivos = new HashSet<Arquivo>();
	}
	
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
	
	public void delArquivo(String nomeArquivo) {
		Iterator<Arquivo> i = arquivos.iterator();
		while (i.hasNext()) {
			Arquivo arquivo = i.next();
			String nome = arquivo.getNome() + " - " + arquivo.getQtdePalavras();
			if(nome.equals(nomeArquivo))
				i.remove();
		}
	}
	
	public HashSet<Arquivo> listaArquivos() {
		return arquivos;
	}
	
	public void printArquivos() {
		for(Arquivo arquivo : arquivos)
			System.out.println(arquivo.getNome());
	}
}
