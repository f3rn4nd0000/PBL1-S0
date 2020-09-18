package package1;

public class TextFile {
    	private int lastmodified; //esta variável servirá de controle para saber se foi o último arquivo a ser modificado
    	private String content; //conteúdo que está inserido no arquivo, aqui trabalharemos com arquivo de texto.
 
    	public TextFile() {
    		this.lastmodified = lastmodified;
    		this.content = content;
    	}

		public int getLastmodified() {
			return lastmodified;
		}

		public void setLastmodified(int lastmodified) {
			this.lastmodified = lastmodified;
		}

		public String getContent() {
			return content;
		}
		
		//por aqui poderemos modificar o conteúdo ao se tentar por exemplo passar parâmetro?!
		public void setContent(String content) { 
			this.content = content;
		}
		
    }
