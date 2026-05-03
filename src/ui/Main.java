package ui;

import java.time.LocalDate;
import java.util.Scanner;
import model.Raffle;
import model.RaffleManager;

/**
 * Console-based user interface for the SorteoSecreto system.
 */
public class Main {

    private RaffleManager manager;
    private Scanner reader;

    public Main() {
        this.manager = new RaffleManager();
        this.reader = new Scanner(System.in);
    }

    public static void main(String[] args) {
        Main app = new Main();
        app.showMenu();
    }

    public void showMenu() {
        int option = 0;
        do {
            System.out.println("\n===== SORTEO SECRETO =====");
            System.out.println("1. Register raffle");
            System.out.println("2. Modify raffle");
            System.out.println("3. Clone raffle");
            System.out.println("4. Register participant");
            System.out.println("5. Modify participant");
            System.out.println("6. Remove participant");
            System.out.println("7. Register restriction");
            System.out.println("8. Execute draw");
            System.out.println("9. Annul raffle");
            System.out.println("10. View raffle status");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");

            option = Integer.parseInt(reader.nextLine());

            switch (option) {
                case 1: registerRaffle(); break;
                case 2: modifyRaffle(); break;
                case 3: cloneRaffle(); break;
                case 4: registerParticipant(); break;
                case 5: modifyParticipant(); break;
                case 6: removeParticipant(); break;
                case 7: registerRestriction(); break;
                case 8: executeDraw(); break;
                case 9: annulRaffle(); break;
                case 10: viewRaffleStatus(); break;
                case 0: System.out.println("Goodbye!"); break;
                default: System.out.println("Invalid option."); break;
            }
        } while (option != 0);
    }

    private void registerRaffle() {
        System.out.print("Raffle name: ");
        String name = reader.nextLine();
        System.out.print("Description: ");
        String description = reader.nextLine();
        System.out.print("Suggested budget: ");
        double budget = Double.parseDouble(reader.nextLine());
        System.out.print("Event date (YYYY-MM-DD): ");
        LocalDate date = LocalDate.parse(reader.nextLine());

        boolean success = manager.registerRaffle(name, description, budget, date);
        if (success) {
            System.out.println("Raffle registered successfully.");
        } else {
            System.out.println("A raffle with that name already exists.");
        }
    }

    private void modifyRaffle() {
        System.out.print("Current raffle name: ");
        String currentName = reader.nextLine();
        System.out.print("New name: ");
        String newName = reader.nextLine();
        System.out.print("New description: ");
        String newDescription = reader.nextLine();
        System.out.print("New suggested budget: ");
        double newBudget = Double.parseDouble(reader.nextLine());
        System.out.print("New event date (YYYY-MM-DD): ");
        LocalDate newDate = LocalDate.parse(reader.nextLine());

        boolean success = manager.modifyRaffle(currentName, newName, newDescription, newBudget, newDate);
        if (success) {
            System.out.println("Raffle modified successfully.");
        } else {
            System.out.println("Could not modify the raffle (not found or name conflict).");
        }
    }

    private void cloneRaffle() {
        System.out.print("Original raffle name: ");
        String originalName = reader.nextLine();
        System.out.print("New raffle name: ");
        String newName = reader.nextLine();

        boolean success = manager.cloneRaffle(originalName, newName);
        if (success) {
            System.out.println("Raffle cloned successfully.");
        } else {
            System.out.println("Could not clone (original not found or new name already exists).");
        }
    }

    private void registerParticipant() {
        System.out.print("Raffle name: ");
        String raffleName = reader.nextLine();
        System.out.print("Participant email: ");
        String email = reader.nextLine();
        System.out.print("Participant name: ");
        String name = reader.nextLine();

        boolean success = manager.addParticipantToRaffle(raffleName, email, name);
        if (success) {
            System.out.println("Participant registered.");
        } else {
            System.out.println("Could not register (raffle not found or email duplicated).");
        }
    }

    private void modifyParticipant() {
        System.out.print("Raffle name: ");
        String raffleName = reader.nextLine();
        System.out.print("Current email: ");
        String currentEmail = reader.nextLine();
        System.out.print("New email: ");
        String newEmail = reader.nextLine();
        System.out.print("New name: ");
        String newName = reader.nextLine();

        boolean success = manager.modifyParticipantInRaffle(raffleName, currentEmail, newEmail, newName);
        if (success) {
            System.out.println("Participant modified.");
        } else {
            System.out.println("Could not modify (raffle/participant not found or email conflict).");
        }
    }

    private void removeParticipant() {
        System.out.print("Raffle name: ");
        String raffleName = reader.nextLine();
        System.out.print("Participant email: ");
        String email = reader.nextLine();

        boolean success = manager.removeParticipantFromRaffle(raffleName, email);
        if (success) {
            System.out.println("Participant removed.");
        } else {
            System.out.println("Could not remove (not found).");
        }
    }

    private void registerRestriction() {
        System.out.print("Raffle name: ");
        String raffleName = reader.nextLine();
        System.out.print("Email of participant who CANNOT give: ");
        String emailFrom = reader.nextLine();
        System.out.print("Email of participant who CANNOT receive: ");
        String emailTo = reader.nextLine();

        boolean success = manager.addRestrictionToRaffle(raffleName, emailFrom, emailTo);
        if (success) {
            System.out.println("Restriction registered.");
        } else {
            System.out.println("Could not register restriction (raffle/participants not found or same email).");
        }
    }

    private void executeDraw() {
        System.out.print("Raffle name: ");
        String raffleName = reader.nextLine();

        boolean success = manager.executeDraw(raffleName);
        if (success) {
            System.out.println("Draw executed successfully.");
        } else {
            System.out.println("Could not execute draw (not found, already drawn, too few participants, or restrictions make it infeasible).");
        }
    }

    private void annulRaffle() {
        System.out.print("Raffle name: ");
        String raffleName = reader.nextLine();

        boolean success = manager.annulRaffle(raffleName);
        if (success) {
            System.out.println("Raffle annulled.");
        } else {
            System.out.println("Raffle not found.");
        }
    }

    private void viewRaffleStatus() {
        System.out.print("Raffle name: ");
        String raffleName = reader.nextLine();

        String report = manager.getRaffleStatusReport(raffleName);
        System.out.println("\n--- RAFFLE STATUS ---");
        System.out.println(report);
    }
}