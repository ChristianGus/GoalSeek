public class GoalSeeker {

	private double bar;
	private double dynamic;
	private double percent;
	private int years;
	private int month;
	private int sperrMonth;
	private int sperrYears;
	private int schuss;
	private double endval;
	private double tolerance;
	private double rente;
	
	public GoalSeeker (
			double barVal, 
			double r, 
			double end, 
			double perc, 
			double dyn, 
			int yrs,
			int mth,
			int sMnth, 
			int sYrs, 
			Binary prePost) {
		this.bar = barVal;
		this.dynamic = dyn;
		this.percent = perc;
		this.years = yrs;
		this.month = mth;
		this.sperrMonth = sMnth;
		this.sperrYears = sYrs;
		this.schuss = prePost.getValue();
		this.endval = end;
		this.rente = r;
	}
	
	public GoalSeeker (double barVal, 
			double r, 
			double end, 
			double perc, 
			double dyn, 
			int yrs,
			int mth,
			int sMnth, 
			int sYrs, 
			Binary prePost,
			double tol){
		this(barVal, r, end, perc, dyn, yrs, mth, sMnth, sYrs, prePost);
		this.tolerance = tol;
	}

	public String formula = "=(bar*"
			+ "(1+percent*(month+sperrMonth)/1200)*(1+percent/100)^years"
			+ "+r*(1+dynamic/100)^(years-1)"
			+ "*(12+(66+12*schuss)*percent/1200)"
			+ "*(((1+percent/100)/(1+dynamic/100))^years-1)/(((1+percent/100)/(1+dynamic/100))-1)"
			+ "*(1+percent*(month+sperrMonth)/1200)"
			+ "+r*(1+dynamic/100)^years*month*(1+percent*(2*sperrMonth+month-1+2*schuss)/2400))*(1+percent/100)^sperrYears+endval";
	
	public double calculate() {
		
		double q = 1 + percent/100;
		double qd = 1 + dynamic/100;
		double qq = q/qd;
		
		/** Folgende Excel Formel soll nachgebildet werden:
		 * =(B*(1+p*(m+sp)/1200)*q^n+r*qd^(n-1)*(12+(66+12*t)*p/1200)*(qq^n-1)/(qq-1)*(1+p*(m+sp)/1200)+r*qd^n*m*(1+p*(2*sp+m-1+2*t)/2400))*q^zp+E
		 * wobei gilt: 
		 * B = bar
		 * r = rente
		 * E = endval
		 * p = percent
		 * d = dynamic
		 * n = years
		 * m = month
		 * sp = sperrMonth
		 * zp = sperrYears
		 * t = schuss (0 oder 1 aus Enum)
		 * q,qd,qq wie oben
		 */
		
		double result = (bar * (1+percent*(month+sperrMonth)/1200)
				* Math.pow(q, years)+rente
				* Math.pow(qd,years-1)
				* (12+(66+12*schuss)*percent/1200)
				* (Math.pow(qq,years)-1)
				/ (qq-1)
				* (1+percent*(month+sperrMonth)/1200)
				+ rente*Math.pow(qd,years)
				* month*(1+percent*(2*sperrMonth+month-1+2*schuss)/2400))
				* Math.pow(q,sperrYears)+endval;
		
		return result;
	}
	
	public double seekEndval() {
		double tryVal = this.endval;
		setEndval(61956);
		System.out.println(calculate());
		Double minimum = Double.MIN_VALUE;
		Double maximum = Double.MAX_VALUE;
		setEndval(minimum);
		Double minVal = calculate();
		System.out.println(minVal);
		setEndval(maximum);
		Double maxVal = calculate();
		System.out.println(maxVal);
		return tryVal;
	}
	
	
	public static void main(String[] args) {
		GoalSeeker goal = new GoalSeeker(
				1000.0,
				-500.0,
				61956.3639661922,
				1,
				0,
				10,
				0,
				0,
				0,
				Binary.nachschüssig);
		
		goal.setTolerance(0.0000000001);
		System.out.println(goal.calculate());

	}

	public double getDynamic() {
		return dynamic;
	}

	public void setDynamic(double dynamic) {
		this.dynamic = dynamic;
	}

	public double getPercent() {
		return percent;
	}

	public void setPercent(double percent) {
		this.percent = percent;
	}

	public int getYears() {
		return years;
	}

	public void setYears(int years) {
		this.years = years;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getSperrMonth() {
		return sperrMonth;
	}

	public void setSperrMonth(int sperrMonth) {
		this.sperrMonth = sperrMonth;
	}

	public int getSperrYears() {
		return sperrYears;
	}

	public void setSperrYears(int sperrYears) {
		this.sperrYears = sperrYears;
	}

	public int getSchuss() {
		return schuss;
	}

	public void setSchuss(Binary schuss) {
		this.schuss = schuss.getValue();
	}

	public double getEndval() {
		return endval;
	}

	public void setEndval(double endval) {
		this.endval = endval;
	}

	public double getTolerance() {
		return tolerance;
	}

	public void setTolerance(double tolerance) {
		this.tolerance = tolerance;
	}
	
	
}
