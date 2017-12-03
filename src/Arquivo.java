import java.io.Serializable;

public class Arquivo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String caminho;
	private String nome;
	private int qtdePalavras;
	
	public Arquivo(String caminho, String nome) {
		this.caminho = caminho;
		this.nome = nome;
		this.qtdePalavras = 0;
	}

	public String getCaminho() {
		return caminho;
	}

	public String getNome() {
		return nome;
	}

	public String toString() {
		return (nome);
	}

	public int getQtdePalavras() {
		return qtdePalavras;
	}

	public void setQtdePalavras(int qtdePalavras) {
		this.qtdePalavras = qtdePalavras;
	}
}
