import java.io.Serializable;

public class Arquivo implements Serializable {

	private static final long serialVersionUID = 1L;
	
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
