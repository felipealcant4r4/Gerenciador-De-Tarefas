package exception;

public class ErroAoApagarException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public ErroAoApagarException() {
		super("Ocorreu um erro ao apagar");
	};
}
