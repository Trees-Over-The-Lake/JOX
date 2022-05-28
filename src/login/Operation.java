package login;

public enum Operation {

	LOGIN(0),
	CREATE_SERVER(1);
	
	public int operation_value;
	Operation(int operation_value) {
		this.operation_value = operation_value;
	}
}
