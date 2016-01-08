
public enum Binary {
	vorschüssig(1), nachschüssig(0);
	
	private final int value;
	
	Binary(int state) {
		this.value = state;
	}
	
	public int getValue() {
		return value;
	}
}
