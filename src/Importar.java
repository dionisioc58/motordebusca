import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.Normalizer;
import java.text.Normalizer.Form;

public class Importar {
	
	private static String[] caracteresInvalidos = {";", ",", ".", "/", "<", ">", ":", "]", "[", "{", "}",
												   "'", "`", "+", "*", "\"", "(", ")"} ;
	
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
            buffer.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
			return false;
		}
		return true;
	}
	
	private static String limpar(String texto) {
		texto = Normalizer.normalize(texto, Form.NFD).replaceAll("[^\\p{ASCII}]", "");
		for(int i = 0; i < caracteresInvalidos.length; i++)
			texto = texto.replace(caracteresInvalidos[i], " ");
		return texto;
		
		
		/*texto = Normalizer.normalize(texto, Form.NFD).replaceAll("[^\\p{Alpha}]", " ");
		//String alphaOnly = input.replaceAll("[^\\p{Alpha}]+","");
		//Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
		return texto;//pattern.matcher(texto).replaceAll("");
		//String retorno = texto;
		//for(int i = 0; i < caracteresInvalidos.length; i++)
			//texto = texto.replace(caracteresInvalidos[i], " ");
		//return texto;*/
	}
}
