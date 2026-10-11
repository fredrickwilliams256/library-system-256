import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final InputHelper input = new InputHelper(SCANNER);
    private static final LoanManager manager = new LoanManager();

    public static void main(String[] args) {
        System.out.println("==========================================");
        System.out.println("     LIBRARY LOAN CHARGING SYSTEM");
        System.out.println("     Student: FREDRICK WILLIAMS");
        System.out.println("     Registration: VU-DIT-2511-1434-EVE");
        System.out.println("==========================================");

        boolean running = true;
        while (running) {
            displayMenu();
            int choice = input.readInt("Choose an option: ", 1, 7);

            switch (choice) {
                case 1:
                    addLoan("Book");
                    break;
                case 2:
                    addLoan("Laptop");
                    break;
                case 3:
                    manager.printLoans(manager.getAllLoans());
                    break;
                case 4:
                    findLoan();
                    break;
                case 5:
                    removeLoan();
                    break;
                case 6:
                    showReports();
                    break;
                case 7:
                    running = false;
                    System.out.println("Thank you for using the Library Loan Charging System.");
                    break;
                default:
                    // readInt already restricts the menu choice to 1–7.
                    System.out.println("Invalid option.");
            }
        }
        SCANNER.close();
    }

    private static void displayMenu() {
        System.out.println();
        System.out.println("--------------- MAIN MENU ---------------");
        System.out.println("1. Add a book loan");
        System.out.println("2. Add a laptop loan");
        System.out.println("3. List all loans");
        System.out.println("4. Find a loan");
        System.out.println("5. Remove a loan");
        System.out.println("6. Reports (sorted charges and summary)");
        System.out.println("7. Exit");
        System.out.println("-----------------------------------------");
    }

    private static void addLoan(String type) {
        System.out.println();
        System.out.println("Add " + type.toLowerCase() + " loan");
        String borrowerName = input.readNonEmptyString("Borrower's full name: ");
        int daysLate = input.readInt("Number of days late (0 or more): ", 0, 100000);
        boolean firstTime = input.readYesNo("Is this a first-time borrower?");

        String id = manager.generateLoanId();
        Loan loan;

        if (type.equalsIgnoreCase("Book")) {
            loan = new BookLoan(id, borrowerName, daysLate, firstTime);
        } else {
            loan = new LaptopLoan(id, borrowerName, daysLate, firstTime);
        }

        manager.addLoan(loan);
        System.out.println("Loan saved successfully.");
        System.out.println("Generated loan ID: " + id);
        System.out.printf("Charge due: UGX %,.2f%n", loan.calculateCharge());
    }

    private static void findLoan() {
        String id = input.readNonEmptyString("Enter loan ID (e.g. FW434-001): ");
        try {
            Loan loan = manager.findLoan(id);
            manager.printLoans(java.util.Collections.singletonList(loan));
        } catch (LoanNotFoundException exception) {
            System.out.println(exception.getMessage());
        }
    }

    private static void removeLoan() {
        String id = input.readNonEmptyString("Enter the loan ID to remove: ");
        try {
            Loan loan = manager.findLoan(id);
            System.out.println("Loan selected:");
            manager.printLoans(java.util.Collections.singletonList(loan));

            boolean confirm = input.readYesNo("Are you sure you want to remove this loan?");
            if (confirm) {
                manager.removeLoan(id);
                System.out.println("Loan removed successfully.");
            } else {
                System.out.println("Removal cancelled.");
            }
        } catch (LoanNotFoundException exception) {
            System.out.println(exception.getMessage());
        }
    }

    private static void showReports() {
        System.out.println();
        System.out.println("=========== CHARGES: HIGHEST FIRST ===========");
        manager.printLoans(manager.getLoansSortedByChargeDescending());

        System.out.println();
        System.out.println("================ SUMMARY REPORT ================");
        System.out.println("Total loans: " + manager.getLoanCount());
        System.out.printf("Total charges: UGX %,.2f%n", manager.getTotalCharges());
        System.out.printf("Average charge per loan: UGX %,.2f%n", manager.getAverageCharge());
    }
}

