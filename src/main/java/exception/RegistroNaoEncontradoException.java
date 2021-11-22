package exception;

public class RegistroNaoEncontradoException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public RegistroNaoEncontradoException() {
		super("O registro não existe no banco");
	};
}
