import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JPanel;

public class IOArquivos extends JPanel {
	
	private static final long serialVersionUID = 1L;
	JFileChooser fc;
	private Arquivo arquivo;
	
	public IOArquivos() {
		fc = new JFileChooser();
		arquivo = null;
	}
	
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
