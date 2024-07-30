package custom_exceptions;

public class InvalidAccountNumber extends Exception {
	public InvalidAccountNumber(String str) {
		super(str);
	}
}
