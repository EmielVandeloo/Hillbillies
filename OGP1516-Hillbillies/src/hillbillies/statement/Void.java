package hillbillies.statement;

import hillbillies.part3.programs.SourceLocation;
import hillbillies.program.Program;

public class Void extends Statement {

	public Void(SourceLocation sl) {
		super(sl);
	}

	@Override
	public void perform(Program program) {
		// Do nothing.
	}

}
