
public class BookLoan extends Loan {

    public BookLoan(String id, String borrowerName,
                    int daysLate, boolean firstTimeBorrower) {
        super(id, borrowerName, daysLate, firstTimeBorrower);
    }

    @Override
    public double calculateUndiscountedCharge() {
        return getDaysLate() * 500.0;
    }

    @Override
    public double calculateCharge() {
        double fine = calculateUndiscountedCharge();

        if (isFirstTimeBorrower()) {
            fine = fine * 0.92;
        }

        return fine;
    }
}
