import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Importar {
	
	private static String[] caracteresInvalidos = {";", ",", ".", "/", "<", ">", ":", "]", "[", "{", "}", "'", "`"} ;
	
	public static boolean importar(Trie trie, Arquivo arquivo) {
		BufferedReader buffer = null;
		String textoLinha;
		try {
			buffer = new BufferedReader(new FileReader(arquivo.getCaminho()));
			int linha = 1;
            while ((textoLinha = buffer.readLine()) != null) {
                //System.out.println(textoLinha);
                textoLinha = limpar(textoLinha);
                //System.out.println(textoLinha);
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
		String retorno = texto;
		for(int i = 0; i < caracteresInvalidos.length; i++)
			retorno = retorno.replace(caracteresInvalidos[i], " ");
		return retorno;
	}
}
