
public enum Binary {
	nachschüssig(0), vorschüssig(1);
	
	private final int value;
	
	Binary(int state) {
		this.value = state;
	}
	
	public int getValue() {
		return value;
	}
}
