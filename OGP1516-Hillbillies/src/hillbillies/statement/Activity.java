package hillbillies.statement;

public abstract class Activity {
	
	// FIELDS
	
	private boolean started = false;
	private boolean finished = false;
	
	
	// ABSTRACT METHODS
	
	public abstract void perform();
	
	
	// METHODS
	
	public void revertPerform() {
		started = false;
		finished = false;
	}
	
	public void start() {
		this.started = true;
	}
	
	public void revertStart() {
		this.started = false;
	}
	
	public boolean isStarted() {
		return this.started;
	}
	
	public void finish() {
		this.finished = true;
	}
	
	public boolean isFinished() {
		return this.finished;
	}

}
