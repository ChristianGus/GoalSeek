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
	
	public GoalSeeker (double barVal, double dyn, double perc, int yrs, int mth, int sMnth, int sYrs, Binary prePost, double end, double r){
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
	
	public GoalSeeker (double barVal, double dyn, double perc, int yrs, int mth, int sMnth, int sYrs, Binary prePost, double end, double r, double tol){
		this(barVal, dyn, perc, yrs, mth, sMnth, sYrs, prePost, end, r);
		this.tolerance = tol;
	}

	public String formula = "=(bar*"
			+ "(1+percent*(month+sperrMonth)/1200)*(1+percent/100)^years"
			+ "+r*(1+dynamic/100)^(years-1)"
			+ "*(12+(66+12*schuss)*percent/1200)"
			+ "*(((1+percent/100)/(1+dynamic/100))^years-1)/(((1+percent/100)/(1+dynamic/100))-1)"
			+ "*(1+percent*(month+sperrMonth)/1200)"
			+ "+r*(1+dynamic/100)^years*month*(1+percent*(2*sperrMonth+month-1+2*schuss)/2400))*(1+percent/100)^sperrYears+endval";
	
	public double calculateEndval(){
		double result = bar
				* (1+percent*(month+sperrMonth)/1200)
				* Math.pow((1+percent/100),years)
				+ Math.pow(rente*(1+dynamic/100),(years-1))
				* (12+(66+12*schuss)*percent/1200)
				* (Math.pow(((1+percent/100)/(1+dynamic/100)),(years-1)))
				/ (((1+percent/100)/(1+dynamic/100))-1)
				* (1+percent*(month+sperrMonth)/1200)
				+ Math.pow(rente*(1+dynamic/100),years)
				* month
				* (1+percent*(2*sperrMonth+month-1+2*schuss)/2400)
				* Math.pow((1+percent/100),sperrYears)
				+ endval;
		if (this.tolerance < result) {
			return result;
		} else return 0;
	}
	public static void main(String[] args) {
		Integer e = new Integer(Binary.nachschüssig.getValue());
		Double f = new Double(Math.pow(2, 4));
		GoalSeeker goal = new GoalSeeker(1000, 0, 1, 10, 0, 0, 0, Binary.nachschüssig, 61956.3639661922, -500);
		goal.setTolerance(0.0000000001);
		Double y = goal.calculateEndval();
		System.out.println(y);
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
