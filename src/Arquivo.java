
public class Arquivo {

	private String caminho;
	private String nome;
	
	public Arquivo(String caminho, String nome) {
		this.caminho = caminho;
		this.nome = nome;
	}

	public String getCaminho() {
		return caminho;
	}

	public String getNome() {
		return nome;
	}

	public String toString()
	{
		return (nome);
	}
}
