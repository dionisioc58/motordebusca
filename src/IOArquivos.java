import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JPanel;
/**
 * The Class IOArquivos.
 * Para escolha de arquivos no computador
 */
public class IOArquivos extends JPanel {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The fc. */
	JFileChooser fc;
	
	/** The arquivo. */
	private Arquivo arquivo;
	
	/**
	 * Instantiates a new IO arquivos.
	 */
	public IOArquivos() {
		fc = new JFileChooser();
		arquivo = null;
	}
	
	/**
	 * Adds the arquivo.
	 *
	 * @return the arquivo
	 */
	public Arquivo addArquivo() {
		int returnVal = fc.showOpenDialog(IOArquivos.this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            arquivo = new Arquivo(file.getPath(), file.getName());
		} else
			System.out.println("Cancelado pelo usuário!");
		return this.arquivo;
	}
}
