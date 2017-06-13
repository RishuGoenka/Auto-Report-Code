
public class Coordinate {
	
	private int xaxis;
	private int yaxis;
	
	public Coordinate() {
		super();
	}

	public Coordinate(int xaxis, int yaxis) {
		super();
		this.xaxis = xaxis;
		this.yaxis = yaxis;
	}
	
	public int getXaxis() {
		return xaxis;
	}

	public void setXaxis(int xaxis) {
		this.xaxis = xaxis;
	}

	public int getYaxis() {
		return yaxis;
	}

	public void setYaxis(int yaxis) {
		this.yaxis = yaxis;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + xaxis;
		result = prime * result + yaxis;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Coordinate other = (Coordinate) obj;
		if (xaxis != other.xaxis)
			return false;
		if (yaxis != other.yaxis)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Coordinate [xaxis=" + xaxis + ", yaxis=" + yaxis + "]";
	}

	
}
