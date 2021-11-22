package exception;

public class ErroAoSalvarException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public ErroAoSalvarException() {
		super("Ocorreu um erro ao salvar");
	};
}
