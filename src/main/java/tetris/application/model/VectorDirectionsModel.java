package application.model;

public enum VectorDirectionsModel {
	NORTH, NORTHWEST, WEST, SOUTHWEST, SOUTH, SOUTHEAST, EAST, NORTHEAST;
	
	private static final VectorDirectionsModel[] VALUES = {NORTH, NORTHWEST, WEST, SOUTHWEST, SOUTH, SOUTHEAST, EAST, NORTHEAST};

	
	public static VectorDirectionsModel rotateRight(final VectorDirectionsModel direction) {
		int counter = 0;
		int index = 0;
		for(VectorDirectionsModel value : VALUES) {
			if (value.equals(direction)) {
				index = (counter +2) % VALUES.length;
			}
			counter ++;
		}
		return VALUES[index];
	}
	
	public static VectorDirectionsModel rotateLeft(final VectorDirectionsModel direction) {
		int counter = 0;
		int index = 0;
		for(VectorDirectionsModel value : VALUES) {
			if (value.equals(direction)) {
				index = ((counter + 800) - 2) % VALUES.length;
			}
			counter ++;
		}
		return VALUES[index];
	}
}
