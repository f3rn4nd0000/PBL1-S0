package package1;

public class TextFile {
    	private int lastmodified; //esta vari�vel servir� de controle para saber se foi o �ltimo arquivo a ser modificado
    	private String content; //conte�do que est� inserido no arquivo, aqui trabalharemos com arquivo de texto.
 
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
		
		//por aqui poderemos modificar o conte�do ao se tentar por exemplo passar par�metro?!
		public void setContent(String content) { 
			this.content = content;
		}
		
    }
