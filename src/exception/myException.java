package exception;

public class myException extends RuntimeException {
	public myException() {
		super();
	}

	public myException(String message) {
		super(message);
	}

	public myException(String message, Throwable cause) {
		super(message, cause);
	}
}
