import java.text.DecimalFormat;

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
	private Double minimum = -1000000000000000000.0;
	private Double maximum = -minimum;
	private Integer maxIterations = 1000;
	
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
		if (result-tolerance < 0 && result + tolerance > 0) {
			return 0;
		}
		return result;
	}
	
	public boolean validateEndval() {
		double myEndval = this.getEndval();
		setEndval(minimum);
		double minVal = calculate();
		setEndval(maximum);
		double maxVal = calculate();
		setEndval(myEndval);
		return (minVal * maxVal < 0);
	}
	
	public double seekEndval() {
		double x1 = minimum;
		double x2 = maximum;
		int iterNum = 1;    /* how many times bisection has been performed */
		double f1, f2, fmid;  /* function values */
		double mid = 0;          /* new point for function evaluation */
		/* to print numbers with 7 digits behind the decimal point */
		DecimalFormat df = new DecimalFormat("0.0000000");

		System.out.println("Iteration #\tX1\t\tX2\t\tX3\t\tF(X3)"); // \t is a tab

		do {
			setEndval(x1);
			f1 = calculate(); // evaluate function at endpoints
			setEndval(x2);
			f2 = calculate();
			if (f1 * f2 > 0) {   // can't do bisection
				System.out.println("Values do not bracket a root");
				break;            // give up
			}
			mid = (x1 + x2) / 2;  // bisection gives us the "average" of the point values
			setEndval(mid);
			fmid = calculate(); // evaluate function at midpoint
			System.out.println(iterNum + "\t\t" + df.format(x1) + "\t" + df.format(x2) + "\t" +
					df.format(mid) + "\t" + df.format(fmid));
			if (fmid * f1 < 0)    // determine next interval bound
				x2 = mid;
			else
				x1 = mid;
			iterNum++;          // current iteration has been completed
		} while (Math.abs(x1 - x2) / 2 >= tolerance && Math.abs(fmid) > tolerance && iterNum <= maxIterations);
		// interval size minimum not reached yet and we haven't found a root and it's not time
		// to give up yet
		return mid;
	}


	public static void main(String[] args) {
		GoalSeeker goal = new GoalSeeker(
				1000.0,
				-500.0,
				51956.3639661922,
				1,
				0,
				10,
				0,
				0,
				0,
				Binary.nachschüssig);
		
		goal.setTolerance(0.0000000001);
		System.out.println(goal.seekEndval());

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

	public double getBar() {
		return bar;
	}

	public void setBar(double bar) {
		this.bar = bar;
	}

	public double getRente() {
		return rente;
	}

	public void setRente(double rente) {
		this.rente = rente;
	}

	public Double getMinimum() {
		return minimum;
	}

	public void setMinimum(Double minimum) {
		this.minimum = minimum;
	}

	public Double getMaximum() {
		return maximum;
	}

	public void setMaximum(Double maximum) {
		this.maximum = maximum;
	}

	public Integer getMaxIterations() {
		return maxIterations;
	}

	public void setMaxIterations(Integer maxIterations) {
		this.maxIterations = maxIterations;
	}

	public void setSchuss(int schuss) {
		this.schuss = schuss;
	}
	
	
}
