
public enum Binary {
	vorschüssig(0), nachschüssig(1);
	
	private final int value;
	
	Binary(int state) {
		this.value = state;
	}
	
	public int getValue() {
		return value;
	}
}
