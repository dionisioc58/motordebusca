import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.Normalizer;
import java.text.Normalizer.Form;
/**
 * The Class Importar.
 * Serve para realizar as tarefas de importacao
 * dos arquivos
 */
public class Importar {
	
	/** The caracteres invalidos. */
	final private static String[] caracteresInvalidos = {";", ",", ".", "/", "<", ">", ":", "]", "[", "{", "}",
												   		 "'", "`", "+", "*", "\"", "(", ")", "0", "1", "2", "3",
												   		 "4", "5", "6", "7", "8", "9"} ;
	
	/**
	 * Importar.
	 *
	 * @param trie the trie
	 * @param arquivo the arquivo
	 * @return true, if successful
	 */
	public static boolean importar(Trie trie, Arquivo arquivo) {
		BufferedReader buffer = null;
		String textoLinha;
		try {
			buffer = new BufferedReader(new FileReader(arquivo.getCaminho()));
			int linha = 1;
			int qtdePalavras = 0;
            while ((textoLinha = buffer.readLine()) != null) {
                textoLinha = limpar(textoLinha);
                String[] arr = textoLinha.split(" ");
                for(String ss : arr) {
                	if(!ss.trim().equals("")) {
                		trie.insertWord(ss, arquivo, linha);
                		qtdePalavras++;
                	}
                 }
                linha++;
            }
            buffer.close();
            arquivo.setQtdePalavras(qtdePalavras);
		} catch (IOException e) {
			System.out.println(e.getMessage());
			return false;
		}
		return true;
	}
	
	/**
	 * Limpar para remover os caracteres inválidos, acentuações etc
	 *
	 * @param texto the texto
	 * @return O texto limpo
	 */
	private static String limpar(String texto) {
		texto = Normalizer.normalize(texto, Form.NFD).replaceAll("[^\\p{ASCII}]", "");
		for(int i = 0; i < caracteresInvalidos.length; i++)
			texto = texto.replace(caracteresInvalidos[i], " ");
		return texto;
	}
}
