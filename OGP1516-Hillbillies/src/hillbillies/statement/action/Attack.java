package hillbillies.statement.action;

import hillbillies.model.Unit;
import hillbillies.program.Program;

public class Attack extends Action {

	@Override
	public void perform(Program program) {
		// TODO Auto-generated method stub
		Unit.attack();
	}

}
