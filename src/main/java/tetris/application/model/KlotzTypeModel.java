package application.model;

import java.util.Random;

public enum KlotzTypeModel {
	LKLOTZ, IKLOTZ, ZKLOTZ, OKLOTZ, TKLOTZ, JKLOTZ, SKLOTZ, NOLOTZ;
	
	private static final KlotzTypeModel[] VALUES = {LKLOTZ, IKLOTZ, ZKLOTZ, OKLOTZ, TKLOTZ, JKLOTZ, SKLOTZ};
	private static final int SIZE = VALUES.length;
	private static final Random RANDOM = new Random();
	
	public static KlotzTypeModel randomKlotzType() {
		return VALUES[RANDOM.nextInt(SIZE)];
	}
}
