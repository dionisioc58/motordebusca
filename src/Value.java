/**
 * 
 * @author dionisio
 *
 */
public class Value {
	private Arquivo arquivo;
	
	private int linha;
	
	private int qtde;
	
	public Value(Arquivo arquivo, int linha) {
		this.arquivo = arquivo;
		this.linha = linha; 
		this.qtde = 1;
	}

	public Arquivo getArquivo() {
		return arquivo;
	}

	public int getLinha() {
		return linha;
	}

	public int getQtde() {
		return qtde;
	}

	public boolean isEqual(Value value) {
		if(arquivo.getCaminho().equals(value.getArquivo().getCaminho()) && getLinha() == value.getLinha())
			return true;
		return false;
	}
	
	public void incrementaQtde() {
		this.qtde += 1;
	}
}
