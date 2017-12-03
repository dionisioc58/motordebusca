import java.awt.Button;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

/**
 * Classe principal do Motor de Busca
 */
public class MotorBusca {

	private JFrame frame, frameIndexacao, frameBusca;
	private JTextField textField;
	Trie t;
	RepositorioArquivos repositorio;
	private TreeMap<String, Value> indice;
	private Trie blackList;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MotorBusca window = new MotorBusca();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MotorBusca() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		t = new Trie();
		blackList = new Trie();
		repositorio = new RepositorioArquivos();
		indice = new TreeMap<String, Value>();
		recarrega();
		
		frame = new JFrame();
		frame.setBounds(150, 100, 450, 362);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		frameIndexacao = new JFrame();
		frameIndexacao.setBounds(300, 150, 450, 362);
		frameIndexacao.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frameIndexacao.getContentPane().setLayout(null);
		
		JCheckBox ativaBL = new JCheckBox("Ativa");
		ativaBL.setSelected(true);
		ativaBL.setBounds(220, 10, 70, 22);
		frame.getContentPane().add(ativaBL);
		
		Button btnBlack = new Button("Arquivo Black List");
		btnBlack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				IOArquivos selecionar = new IOArquivos();
				Arquivo novo = selecionar.addArquivo();
				if(novo != null) {
					blackList = new Trie();
					Importar.importar(blackList, novo);
					salvar(t, blackList, repositorio);
				}
			}
		});
		btnBlack.setBounds(90, 10, 120, 22);
		frame.getContentPane().add(btnBlack);
		
		textField = new JTextField();
		textField.setBounds(73, 122, 259, 29);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JRadioButton rdbtnAnd = new JRadioButton("AND");
		rdbtnAnd.setBounds(172, 158, 63, 23);
		frame.getContentPane().add(rdbtnAnd);
		
		JRadioButton rdbtnOr = new JRadioButton("OR");
		rdbtnOr.setBounds(237, 158, 63, 23);
		frame.getContentPane().add(rdbtnOr);
		
		JButton btnBusca = new JButton("Busca");
		btnBusca.setEnabled(false);
		btnBusca.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(textField.getText().isEmpty() == false)
				{	
					frameBusca = new JFrame();
					frameBusca.setBounds(150, 200, 450, 362);
					frameBusca.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
					frameBusca.getContentPane().setLayout(null);
					
					List listBusca = new List();
					listBusca.setBounds(0, 0, 434, 323);
					frameBusca.getContentPane().add(listBusca);
				
				
					String busca = textField.getText();
					String palavrasDigitadas[];
					ArrayList<TrieNode> palavrasEncontradas = new ArrayList<TrieNode>();
					
					palavrasDigitadas = busca.split(" ");
					
					if(ativaBL.isSelected()) {
						for(int i = 0; i < palavrasDigitadas.length; i++) {
							if(blackList.search(palavrasDigitadas[i], false) != null) {
								JOptionPane.showMessageDialog(btnBusca, "Pesquisa com palavra proibida.", "ATENÇÃO", 0);
								frameBusca.setVisible(false);
								return;
							}
						}
					}
					
					frameBusca.setVisible(true);
					
					if(rdbtnAnd.isSelected())
					{		
						for(int i = 0; i < palavrasDigitadas.length; i++)
						{
							TrieNode adicionar = t.search(palavrasDigitadas[i], false);
							if(adicionar != null)
								palavrasEncontradas.add(adicionar);
							else
							{
								listBusca.add("Não Encontrado!!");
								return;
							}
						}
						
						boolean contem = true;
						ArrayList<String> arquivosValidos = new ArrayList<String>();
						
						/**
						 * Este for serve para varrer todas as palavras digitadas no geral
						 */
						for(int i = 0; i < palavrasEncontradas.size(); i++)
						{
							/**
							 * Este for serve para varrer todos os values da palavra(i)
							 */
							for(int j = 0; j < palavrasEncontradas.get(i).getValues().size(); j++)
							{
								/**
								 * Este if serve para guardar todos arquivos validos, rodando apenas na primeira palavra
								 */
								if(i == 0)
								{
									String arquivoAtual = palavrasEncontradas.get(i).getValue(j).getArquivo().getNome();
									
									/**
									 * Este for serve para percorrer as outras palavras a partir da palavra(i)
									 */
									for(int y = i + 1; y < palavrasEncontradas.size(); y++)
									{
										contem = false;
										
										/**
										 * Este for serve para percorrer todos os values das outras palavras que estão sendo percorridas
										 */
										for(int q = 0; q < palavrasEncontradas.get(y).getValues().size(); q++)
										{
											if(palavrasEncontradas.get(y).getValue(q).getArquivo().getNome().equals(arquivoAtual))
											{
												contem = true;
											}
											
											if(contem)
												break;
										}
									}
									
									/**
									 * Ele só entra nesse IF se em todas as palavras ele encontrou pelo menos um value com o mesmo nome de arquivo
									 */
									if(contem == true)
									{										
										arquivosValidos.add(palavrasEncontradas.get(i).getValue(j).getArquivo().getNome());
									
										String nome = palavrasDigitadas[i];
										String nomeArquivo = palavrasEncontradas.get(i).getValue(j).getArquivo().getNome();
										int qtd = palavrasEncontradas.get(i).getValue(j).getQtde();
										int linha = palavrasEncontradas.get(i).getValue(j).getLinha(); 
										String retorno = nomeArquivo + ": '" + nome + "' teve " + qtd + " ocorrência(s) na linha " + linha;
										listBusca.add(retorno);
									}
								}
								
								/**
								 * Serve para as outras palavras, fica mais rapido, pois já teremos uma lista com todos os arquivos validos
								 */
								else
								{
									for(int x = 0; x < arquivosValidos.size(); x++)
									{
										if(palavrasEncontradas.get(i).getValue(j).getArquivo().getNome().equals(arquivosValidos.get(x)))
										{
											String nome = palavrasDigitadas[i];
											String nomeArquivo = palavrasEncontradas.get(i).getValue(j).getArquivo().getNome();
											int qtd = palavrasEncontradas.get(i).getValue(j).getQtde();
											int linha = palavrasEncontradas.get(i).getValue(j).getLinha(); 
											String retorno = nomeArquivo + ": '" + nome + "' teve " + qtd + " ocorrência(s) na linha " + linha;
											listBusca.add(retorno);
											
											break;
										}
									}
								}
							}
						}
					}
					
					else if(rdbtnOr.isSelected())
					{
						boolean achouUm = false;
						
						for(int i = 0; i < palavrasDigitadas.length; i++)
						{
							TrieNode encontrado = t.search(palavrasDigitadas[i], false);
							
							if(encontrado != null)
							{
								achouUm = true;
								
								for(int j = 0; j < encontrado.getValues().size(); j++)
								{
									String nome = palavrasDigitadas[i];
									String nomeArquivo = encontrado.getValue(j).getArquivo().getNome();
									int qtd = encontrado.getValue(j).getQtde();
									int linha = encontrado.getValue(j).getLinha(); 
									
									String retorno = nomeArquivo + ": '" + nome + "' teve " + qtd + " ocorrência(s) na linha " + linha;
									listBusca.add(retorno);
								}								
							}
						}
						
						if(achouUm == false)
						{
							listBusca.add("Não Encontrado!");
						}
					}
				}
			}
		});
		btnBusca.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnBusca.setBounds(335, 125, 68, 23);
		frame.getContentPane().add(btnBusca);
		
		JLabel lblMOT = new JLabel("M O T O R  D E  B U S C A");
		lblMOT.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
		lblMOT.setBounds(106, 41, 236, 29);
		frame.getContentPane().add(lblMOT);
		
		Button button = new Button("Indexação");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frameIndexacao.setVisible(true);
				
				List listArquivos = new List();
				atualizaLista(listArquivos);
				
				listArquivos.setBounds(37, 41, 233, 272);
				frameIndexacao.getContentPane().add(listArquivos);
				
				JButton btnAdicionar = new JButton("Adicionar");
				btnAdicionar.setBounds(276, 41, 129, 23);
				frameIndexacao.getContentPane().add(btnAdicionar);
				btnAdicionar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {						
						IOArquivos selecionar = new IOArquivos();
						Arquivo novo = selecionar.addArquivo();
						if(novo != null) {
							if(repositorio.addArquivo(novo))
							{
								Importar.importar(t, novo);
								atualizaLista(listArquivos);
								salvar(t, blackList, repositorio);
							}
						}
					}
				});
				
				JButton btnRemover = new JButton("Remover");
				btnRemover.setBounds(276, 75, 129, 23);
				frameIndexacao.getContentPane().add(btnRemover);
				btnRemover.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						if(listArquivos.getSelectedIndex() > -1) {
							if(t.removeBook(listArquivos.getSelectedItem())) {
								repositorio.delArquivo(listArquivos.getSelectedItem());
								listArquivos.remove(listArquivos.getSelectedIndex());
								salvar(t, blackList, repositorio);
							}
						} else
							JOptionPane.showMessageDialog(btnRemover, "Selecione um arquivo para remover.", "ATENÇÃO", 0);
					}
				});
				
				JButton btnAtualizar = new JButton("Atualizar");
				btnAtualizar.setBounds(276, 109, 129, 23);
				btnAtualizar.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						t = new Trie();
						listArquivos.removeAll();
						indice.clear();
						
						for(Arquivo arquivo : repositorio.listaArquivos()) {
							Importar.importar(t, arquivo);
						}

						indice.putAll(t.getIndice());
						atualizaLista(listArquivos);
						salvar(t, blackList, repositorio);
					}
				});
				frameIndexacao.getContentPane().add(btnAtualizar);
				
				JButton btnImprimir = new JButton("Índice");
				btnImprimir.setBounds(276, 143, 129, 23);
				frameIndexacao.getContentPane().add(btnImprimir);
				btnImprimir.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						JPanel painel = new JPanel();
						painel.setBorder(new TitledBorder(new EtchedBorder(), "Índice Remissivo"));
						
						JTextArea area = new JTextArea(16, 58);
					    area.setEditable(false);
					    String palavra = "";
					    for (Map.Entry<String, Value> entry : indice.entrySet()) {
							if(!palavra.equals(entry.getKey())) {
								palavra = entry.getKey();
								area.append(palavra + "\n");
							}
							area.append("  " + entry.getValue() + "\n");
					    }
					    
					    JScrollPane scroll = new JScrollPane(area);
					    scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
						
						JButton button = new JButton();
						button.setBounds(0, 0, 30, 30);
						try {
							button.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("resources/refresh.png"))
									.getScaledInstance(16, 16, java.awt.Image.SCALE_SMOOTH)));
						} catch (Exception ex) {
							System.out.println(ex);
						}
						button.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								area.setText(t.toString());
							}
						});
					    
						painel.add(button);
					    painel.add(scroll);
					    
					    JFrame frame = new JFrame();
					    frame.add(painel);
					    frame.pack();
					    frame.setLocationRelativeTo(null);
					    frame.setVisible(true);
					}
				});
			}
		});
		button.setBounds(10, 10, 70, 22);
		frame.getContentPane().add(button);
		
		rdbtnAnd.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				if(rdbtnOr.isSelected() )
					rdbtnOr.setSelected(false);
				
				else
					rdbtnAnd.setSelected(true);
				
				btnBusca.setEnabled(true);
					
			}
			
		});
		
		rdbtnOr.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				if(rdbtnAnd.isSelected())
					rdbtnAnd.setSelected(false);
				
				else
					rdbtnOr.setSelected(true);
				
				btnBusca.setEnabled(true);
			}
			
		});
		
	}
	
	/**
    * Salvar
    */
	public void salvar(Trie arvore, Trie blackList, RepositorioArquivos repositorio) {
		try {
			FileOutputStream arquivo = new FileOutputStream("salva.dat");
	   		ObjectOutputStream objeto = new ObjectOutputStream(arquivo);
	   		objeto.writeObject(arvore);
	   		objeto.writeObject(blackList);
	   		objeto.writeObject(repositorio);
	   		objeto.flush();
	   		objeto.close();
	   		System.out.println("Dados salvos com sucesso!");
		} catch(Exception e){
			System.out.println("Problemas ao salvar os dados!");
			e.printStackTrace(System.out);
		}
   }
   
   /**
    * Recarregar
    */
   public void recarrega() {
	   try {
		   FileInputStream arquivo = new FileInputStream("salva.dat");
		   ObjectInputStream objeto = new ObjectInputStream(arquivo);
		   t = (Trie) objeto.readObject();
		   blackList = (Trie) objeto.readObject();
		   repositorio = (RepositorioArquivos) objeto.readObject();
		   indice = t.getIndice();
		   System.out.println(indice.comparator());
		   objeto.close();
		   arquivo.close();
		   System.out.println("Dados recuperados com sucesso!");
		} catch(Exception e) {
			System.out.println("Problemas ao recarregar os dados!");
		}
	   return;
   }
   
   public void atualizaLista(List lista) {
	   ArrayList<String> ordenados = new ArrayList<String>();
		for(Arquivo arquivo : repositorio.listaArquivos()) {
			ordenados.add(arquivo.getNome() + " - " + arquivo.getQtdePalavras());
		}
		java.util.Collections.sort(ordenados);
		lista.removeAll();
		for(String arquivo: ordenados) {
			lista.add(arquivo);
		}
   }
}
