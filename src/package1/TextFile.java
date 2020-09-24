package package1;
import java.io.File;
/**
 * Criei essa classe pra representar os arquivos de texto e facilitar as manipulações do filePath para os métodos de escrita e cópia.
 * @author ffern
 * 
 */
public class TextFile extends Operations {
	
	String filePath;
	String content;
	
	public TextFile() {
		this.filePath = filePath;
		this.content = content;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
