
public abstract class Loan implements Chargeable {
    private String id;
    private String borrowerName;
    private int daysLate;
    private boolean firstTimeBorrower;

    public Loan(String id, String borrowerName,
                int daysLate, boolean firstTimeBorrower) {
        setId(id);
        setBorrowerName(borrowerName);
        setDaysLate(daysLate);
        this.firstTimeBorrower = firstTimeBorrower;
    }

    public String getId() {
        return id;
    }

    public String getBorrowerName() {
        return borrowerName;
    }

    public int getDaysLate() {
        return daysLate;
    }

    public boolean isFirstTimeBorrower() {
        return firstTimeBorrower;
    }

    public void setId(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID cannot be blank.");
        }
        this.id = id.trim();
    }

    public void setBorrowerName(String borrowerName) {
        if (borrowerName == null || borrowerName.trim().isEmpty()) {
            throw new IllegalArgumentException("Borrower name cannot be blank.");
        }
        this.borrowerName = borrowerName.trim();
    }

    public void setDaysLate(int daysLate) {
        if (daysLate < 0) {
            throw new IllegalArgumentException(
                "Days late cannot be negative."
            );
        }
        this.daysLate = daysLate;
    }

    public void setFirstTimeBorrower(boolean firstTimeBorrower) {
        this.firstTimeBorrower = firstTimeBorrower;
    }

    public abstract double calculateUndiscountedCharge();

    @Override
    public abstract double calculateCharge();

    public double calculateDiscount() {
        return calculateUndiscountedCharge() - calculateCharge();
    }
}
