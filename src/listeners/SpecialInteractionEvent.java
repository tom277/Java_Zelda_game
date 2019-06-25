package listeners;

import java.util.EventObject;

public class SpecialInteractionEvent extends EventObject {
	/**
	 * This class hold the relevant information for an interaction event 
	 */
	private static final long serialVersionUID = -9072489346141494034L;
	private String interactionName;

	public SpecialInteractionEvent(Object source, String interactionName) {
		super(source);// this calls the constructor of the parent class in this case EventObject 
		this.interactionName = interactionName;
	}

	public String getInteractionName() {
		return interactionName;
	}
}
