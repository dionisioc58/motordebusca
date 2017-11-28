import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.Normalizer;
import java.text.Normalizer.Form;

public class Importar {
	
	private static String[] caracteresInvalidos = {";", ",", ".", "/", "<", ">", ":", "]", "[", "{", "}", "'", "`", "+", "*"} ;
	
	public static boolean importar(Trie trie, Arquivo arquivo) {
		BufferedReader buffer = null;
		String textoLinha;
		try {
			buffer = new BufferedReader(new FileReader(arquivo.getCaminho()));
			int linha = 1;
            while ((textoLinha = buffer.readLine()) != null) {
                textoLinha = limpar(textoLinha);
                String[] arr = textoLinha.split(" ");
                for(String ss : arr) {
                	if(!ss.trim().equals(""))
                      trie.insertWord(ss, arquivo, linha);
                 }
                linha++;
            }

		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return true;
	}
	
	private static String limpar(String texto) {
		texto = Normalizer.normalize(texto, Form.NFD).replaceAll("[^\\p{ASCII}]", "");
		for(int i = 0; i < caracteresInvalidos.length; i++)
			texto = texto.replace(caracteresInvalidos[i], " ");
		return texto;
	}
}
