import java.util.Scanner;


/**
 * The Class Main.
 */
public class Main {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {

		Scanner scan = new Scanner(System.in);
		Trie t = new Trie();
		RepositorioArquivos repositorio = new RepositorioArquivos();
		System.out.println("Testar a árvore.");
		boolean sair = false;
		
		do {
			System.out.println("Operações");
			System.out.println("1 - Inserir palavras de um arquivo");
			//System.out.println("2 - Inserir várias palavras");
			System.out.println("3 - Remover uma palavra");
			System.out.println("4 - Procurar uma palavra");
			System.out.println("5 - Imprimir a árvore");
			System.out.println("6 - Listar arquivos");
			System.out.println("7 - Sair");

			int opcao = scan.nextInt();
			switch (opcao) {
			case 1:
				IOArquivos selecionar = new IOArquivos();
				Arquivo novo = selecionar.addArquivo();
				if(novo != null) {
					if(repositorio.addArquivo(novo))
						Importar.importar(t, novo);
				}
				break;
			/*case 2:
				String palavra = "";
				do {
					System.out.println("Digite uma string a inserir");
					palavra = scan.next();
					//if(!palavra.equalsIgnoreCase("sair"))
						//t.insertWord(palavra, "merda", 1);
				} while(!palavra.equalsIgnoreCase("sair"));
				t.print();
				break;*/
			case 3:
				System.out.println("Digite a string a remover");
				try {
					t.removeWord(scan.next());
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				break;
			case 4:
				System.out.println("Digite a string a pesquisar");
				t.search(scan.next(), true);
				break;
			case 5:
				System.out.println("Imprimir a árvore inteira.");
				t.print();
				break;
			case 6:
				repositorio.printArquivos();
				break;
			case 7:
				System.out.println("Tchau!");
				sair = true;
				break;
			default:
				System.out.println("Entrada inválida!");
				break;
			}
		} while(!sair);
		scan.close();
	}
}