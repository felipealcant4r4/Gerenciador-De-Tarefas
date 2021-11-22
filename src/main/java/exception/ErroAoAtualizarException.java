package exception;

public class ErroAoAtualizarException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public ErroAoAtualizarException() {
		super("Ocorreu um erro ao atualizar");
	};
}
