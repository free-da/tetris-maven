package application.model;

public class SingleKlotzModel {
	private VectorDirectionsModel direction;
	int norm;
	
	public SingleKlotzModel(final VectorDirectionsModel direction, final int norm) {
		this.setDirection(direction);
		this.norm = norm;
	}

	public VectorDirectionsModel getDirection() {
		return direction;
	}

	public void setDirection(final VectorDirectionsModel direction) {
		this.direction = direction;
	}

}
