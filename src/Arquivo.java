
public class Arquivo {

	private String caminho;
	private String nome;
	private String dataImportacao;
	
	public Arquivo(String caminho, String nome, String dataImportacao) {
		this.caminho = caminho;
		this.nome = nome;
		this.dataImportacao = dataImportacao;
	}

	public String getCaminho() {
		return caminho;
	}

	public String getNome() {
		return nome;
	}

	public String getDataImportacao() {
		return dataImportacao;
	}
}
