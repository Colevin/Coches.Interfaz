package exception;

public class statusVehicleNullException extends myException{
	public statusVehicleNullException() {
		super();
	}

	public statusVehicleNullException(String message) {
		super(message);
	}

	public statusVehicleNullException(String message, Throwable cause) {
		super(message, cause);
	}
}
