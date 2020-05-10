package exception;

public class maxVehicleSpeedNegativeException extends myException{
	public maxVehicleSpeedNegativeException() {
		super();
	}

	public maxVehicleSpeedNegativeException(String message) {
		super(message);
	}

	public maxVehicleSpeedNegativeException(String message, Throwable cause) {
		super(message, cause);
	}
}
