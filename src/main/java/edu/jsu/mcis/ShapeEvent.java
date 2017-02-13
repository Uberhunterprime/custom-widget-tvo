package edu.jsu.mcis;

public class ShapeEvent {
    private boolean octselected;
	private boolean hexselected;
	
    public ShapeEvent() {
        this(false);
    }
    public ShapeEvent(boolean octselected) {
        this.octselected = octselected;
    }
	public ShapeEvent(boolean hexselected){
		this.hexselected = hexselected;
	}
    public boolean octisSelected() { return octselected; }
    public boolean hexisSelected() { return hexselected; }
}