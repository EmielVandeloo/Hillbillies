package hillbillies.model.character;

import java.math.BigDecimal;
import be.kuleuven.cs.som.annotate.Value;

@Value
public class JobStat {
	public static final float ATTACK = 1;
	public static final float REST = (float) 0.2;
	public static final float SPRINT = (float) 0.1;
	public static final BigDecimal THREEMINUTEREST = new BigDecimal(180.0);
	public static BigDecimal BigDec1 = new BigDecimal(0.0);
	public static BigDecimal BigDec2 = new BigDecimal(0.0);
}