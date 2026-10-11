public class LaptopLoan extends Loan {
    public static final double DAILY_RATE = 5000.0;

    public LaptopLoan(String id, String borrowerName, int daysLate, boolean firstTimeBorrower) {
        super(id, borrowerName, daysLate, firstTimeBorrower);
    }

    @Override
    public String getLoanType() {
        return "Laptop";
    }

    @Override
    public double getDailyRate() {
        return DAILY_RATE;
    }
}

    

