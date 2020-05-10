package exception;

public class speedVehicleNegException extends myException{
	public speedVehicleNegException() {
		super();
	}

	public speedVehicleNegException(String message) {
		super(message);
	}

	public speedVehicleNegException(String message, Throwable cause) {
		super(message, cause);
	}
}