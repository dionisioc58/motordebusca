import java.awt.Button;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
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

public class MotorBusca {

	private JFrame frame,frameIndexacao, frameBusca;
	private JTextField textField;
	Trie t = new Trie();
	RepositorioArquivos repositorio = new RepositorioArquivos();
	private HashMap<String, Value> indice;

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
		indice = new HashMap<String, Value>();
		frame = new JFrame();
		frame.setBounds(150, 100, 450, 362);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		frameIndexacao = new JFrame();
		frameIndexacao.setBounds(300, 150, 450, 362);
		frameIndexacao.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frameIndexacao.getContentPane().setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(73, 122, 259, 29);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JRadioButton rdbtnNormal = new JRadioButton("NORMAL");
		rdbtnNormal.setBounds(77, 158, 63, 23);
		rdbtnNormal.setSelected(true);
		frame.getContentPane().add(rdbtnNormal);
		
		JRadioButton rdbtnAnd = new JRadioButton("AND");
		rdbtnAnd.setBounds(142, 158, 63, 23);
		frame.getContentPane().add(rdbtnAnd);
		
		JRadioButton rdbtnOr = new JRadioButton("OR");
		rdbtnOr.setBounds(207, 158, 63, 23);
		frame.getContentPane().add(rdbtnOr);
		
		JButton btnBusca = new JButton("Busca");
		btnBusca.setEnabled(true);
		btnBusca.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(textField.getText().isEmpty() == false)
				{
					frameBusca = new JFrame();
					frameBusca.setBounds(150, 200, 450, 362);
					frameBusca.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
					frameBusca.getContentPane().setLayout(null);
					
					frameBusca.setVisible(true);
					
					List listBusca = new List();
					listBusca.setBounds(0, 0, 434, 323);
					frameBusca.getContentPane().add(listBusca);
				
				
					String busca = textField.getText();
					String palavrasDigitadas[];
					ArrayList<TrieNode> palavrasEncontradas = new ArrayList<TrieNode>();
					
					palavrasDigitadas = busca.split(" ");
					
					
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
				listArquivos.setBounds(37, 41, 233, 272);
				frameIndexacao.getContentPane().add(listArquivos);
				
				JButton btnAdicionar = new JButton("Adicionar");
				btnAdicionar.setBounds(276, 41, 89, 23);
				frameIndexacao.getContentPane().add(btnAdicionar);
				btnAdicionar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {						
						IOArquivos selecionar = new IOArquivos();
						Arquivo novo = selecionar.addArquivo();
						if(novo != null) {
							if(repositorio.addArquivo(novo))
							{
								Importar.importar(t, novo);
								listArquivos.add(novo.getNome());
							}
						}
					}
				});
				
				JButton btnRemover = new JButton("Remover");
				btnRemover.setBounds(276, 75, 89, 23);
				frameIndexacao.getContentPane().add(btnRemover);
				btnRemover.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						if(listArquivos.getSelectedIndex() > -1) {
							if(t.removeBook(listArquivos.getSelectedItem())) {
								repositorio.delArquivo(listArquivos.getSelectedItem());
								listArquivos.remove(listArquivos.getSelectedIndex());
							}
						} else
							JOptionPane.showMessageDialog(btnRemover, "Selecione um arquivo para remover.", "ATENÇÃO", 0);
					}
				});
				
				JButton btnAtualizar = new JButton("Atualizar");
				btnAtualizar.setBounds(276, 109, 89, 23);
				btnAtualizar.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						indice.clear();
						indice = new HashMap<>();
						indice.putAll(t.getIndice());
						for (String palavra: indice.keySet()){
				            System.out.println(palavra);
				            System.out.println("  " + indice.get(palavra));
						}
					}
				});
				frameIndexacao.getContentPane().add(btnAtualizar);
				
				JButton btnImprimir = new JButton("Índice");
				btnImprimir.setBounds(276, 143, 89, 23);
				frameIndexacao.getContentPane().add(btnImprimir);
				btnImprimir.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						JPanel painel = new JPanel();
						painel.setBorder(new TitledBorder(new EtchedBorder(), "Índice Remissivo"));
						
						JTextArea area = new JTextArea(16, 58);
					    area.setEditable(false);
					    area.setText(t.toString());
					    
					    JScrollPane scroll = new JScrollPane(area);
					    scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
					    area.setFont(new Font("Courier New", Font.PLAIN, 16));
						
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
				if(rdbtnOr.isSelected())
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
}
