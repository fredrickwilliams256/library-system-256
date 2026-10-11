import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class LoanManager {
    private final List<Loan> loans = new ArrayList<Loan>();
    private int nextNumber = 1;
    private static final String ID_PREFIX = "FW434-";

    public String generateLoanId() {
        return String.format("%s%03d", ID_PREFIX, nextNumber++);
    }

    public void addLoan(Loan loan) {
        if (loan == null) {
            throw new IllegalArgumentException("Loan cannot be null.");
        }
        loans.add(loan);
    }

    public List<Loan> getAllLoans() {
        return new ArrayList<Loan>(loans);
    }

    public Loan findLoan(String id) throws LoanNotFoundException {
        for (Loan loan : loans) {
            if (loan.getId().equalsIgnoreCase(id.trim())) {
                return loan;
            }
        }
        throw new LoanNotFoundException("No loan was found with ID " + id + ".");
    }

    public void removeLoan(String id) throws LoanNotFoundException {
        Loan loan = findLoan(id);
        loans.remove(loan);
    }

    public List<Loan> getLoansSortedByChargeDescending() {
        List<Loan> sorted = new ArrayList<Loan>(loans);
        Collections.sort(sorted, new Comparator<Loan>() {
            @Override
            public int compare(Loan first, Loan second) {
                return Double.compare(second.calculateCharge(), first.calculateCharge());
            }
        });
        return sorted;
    }

    public double getTotalCharges() {
        double total = 0.0;
        for (Loan loan : loans) {
            total += loan.calculateCharge();
        }
        return total;
    }

    public double getAverageCharge() {
        if (loans.isEmpty()) {
            return 0.0;
        }
        return getTotalCharges() / loans.size();
    }

    public int getLoanCount() {
        return loans.size();
    }

    public void printLoans(List<Loan> list) {
        if (list.isEmpty()) {
            System.out.println("No loans to display.");
            return;
        }

        System.out.printf("%-12s %-10s %-22s %5s %-8s %s%n",
                "Loan ID", "Type", "Borrower", "Late", "First-time", "Charge");
        System.out.println("-------------------------------------------------------------------------------");
        for (Loan loan : list) {
            System.out.println(loan);
        }
    }
}

