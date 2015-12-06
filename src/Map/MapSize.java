package Map;
/*
 * Small (36x36), Medium (72x72), Large (108x108)
 */
public enum MapSize {
	SMALL(36),
	MEDIUM(72),
	LARGE(108);
	
	private final int value;
	
	private MapSize(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
}
