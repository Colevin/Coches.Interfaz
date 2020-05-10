package exception;

public class weatherRoadNullException extends myException{
	public weatherRoadNullException() {
		super();
	}

	public weatherRoadNullException(String message) {
		super(message);
	}

	public weatherRoadNullException(String message, Throwable cause) {
		super(message, cause);
	}
}
