import java.util.HashSet;

public class RepositorioArquivos {
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
	
	public void delArquivo(Arquivo arquivo) {
		arquivos.remove(arquivo);
	}
	
	public HashSet<Arquivo> listaArquivos() {
		return arquivos;
	}
	
	public void printArquivos() {
		for(Arquivo arquivo : arquivos)
			System.out.println(arquivo.getNome() + " - " + arquivo.getDataImportacao());
	}
}
