/**
 * Description: This program manages a secret draw.
 * It allows the user to register the draw, register participants,
 * validate repeated names, generate the secret friend and show a summary.
 *
 * Inputs: current date, menu option, draw name, description, budget,
 * draw date, number of participants and participant names.
 *
 * Outputs: registration messages, participant list,
 * secret friend assignment and draw summary.
 */
import java.time.LocalDate;
import java.util.Random;
import java.util.Scanner;

public class SorteoSecreto {

    private static Scanner sc = new Scanner(System.in);
    static String name;
    static String description;
    static double budget;
    static LocalDate date;
    static boolean drawGenerated = false;
    static String[] secretFriends;

    public static void main(String[] args) {

        LocalDate now = LocalDate.now();
        System.out.println("System date: " + now);

        String[] participants = new String[0];
        int option;

        do {
            System.out.println("\nChoose an option:");
            System.out.println("1. Register draw");
            System.out.println("2. Register participants");
            System.out.println("3. Validate information");
            System.out.println("4. Consult participants");
            System.out.println("5. Generate draw");
            System.out.println("6. Draw summary");
            System.out.println("0. Exit");

            option = sc.nextInt();
            sc.nextLine();

            switch (option) {

                case 1:
                    drawRegister();
                    break;

                case 2:
                    participants = participantRegistration();
                    break;

                case 3:
                    if (participants.length == 0) {
                        System.out.println("Error: there are no registered participants.");
                    } else {
                        System.out.println("Participants were validated successfully.");
                    }
                    break;

                case 4:
                    consultParticipants(participants);
                    break;

                case 5:
                    generateDraw(participants);
                    break;

                case 6:
                    drawSummary(participants, name, description, budget, date, drawGenerated);
                    break;

                case 0:
                    System.out.println("Exiting program...");
                    break;

                default:
                    System.out.println("Invalid option.");
            }

        } while (option != 0);
    }

    /**
     * Description: This method registers the draw information.
     */
    public static void drawRegister() {

        System.out.println("Enter the name of the draw:");
        name = sc.nextLine();

        System.out.println("Enter the description of the draw:");
        description = sc.nextLine();

        System.out.println("Enter the suggested budget:");
        budget = sc.nextDouble();

        System.out.println("Enter the year of the draw:");
        int year = sc.nextInt();

        System.out.println("Enter the month of the draw:");
        int month = sc.nextInt();

        System.out.println("Enter the day of the draw:");
        int dayOfMonth = sc.nextInt();

        sc.nextLine();
        date = LocalDate.of(year, month, dayOfMonth);

        System.out.println("Draw registered successfully.");
    }

    /**
     * Description: This method registers the participants
     * and avoids repeated names.
     *
     * @return participants array with the registered names
     */
    public static String[] participantRegistration() {

        System.out.println("How many participants are there?");
        int numberPeople = sc.nextInt();
        sc.nextLine();

        String[] participants = new String[numberPeople];

        for (int i = 0; i < numberPeople; i++) {

            String person;
            boolean valid;

            do {
                System.out.println("Enter participant " + (i + 1) + ":");
                person = sc.nextLine();

                valid = validateInformation(participants, person, i);

                if (!valid) {
                    System.out.println("This name already exists. Enter another.");
                }

            } while (!valid);

            participants[i] = person;
        }

        return participants;
    }

    /**
     * Description: Checks if a participant name is repeated.
     *
     * @param participants registered participants
     * @param person participant name
     * @param limit number of participants already stored
     * @return true if the name is not repeated, false otherwise
     */
    public static boolean validateInformation(String[] participants, String person, int limit) {

        for (int i = 0; i < limit; i++) {
            if (participants[i].equalsIgnoreCase(person)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Description: Shows the registered participants.
     *
     * @param participants registered participants
     */
    public static void consultParticipants(String[] participants) {

        if (participants.length == 0) {
            System.out.println("There are no registered participants.");
            return;
        }

        System.out.println("List of registered participants:");
        for (int i = 0; i < participants.length; i++) {
            System.out.println((i + 1) + ". " + participants[i]);
        }
    }

    /**
     * Description: Generates the secret friend for each participant
     * without assigning the same person to themselves.
     *
     * @param participants registered participants
     */
    public static void generateDraw(String[] participants) {

        if (participants.length < 2) {
            System.out.println("Error: at least 2 participants are needed.");
            return;
        }

        Random random = new Random();
        int[] positions = new int[participants.length];

        for (int i = 0; i < participants.length; i++) {
            positions[i] = i;
        }

        boolean validDraw = false;

        while (!validDraw) {
            for (int i = 0; i < positions.length; i++) {
                int randomIndex = random.nextInt(positions.length);

                int temp = positions[i];
                positions[i] = positions[randomIndex];
                positions[randomIndex] = temp;
            }

            validDraw = true;

            for (int i = 0; i < positions.length; i++) {
                if (positions[i] == i) {
                    validDraw = false;
                }
            }
        }

        secretFriends = new String[participants.length];

        for (int i = 0; i < participants.length; i++) {
            secretFriends[i] = participants[positions[i]];
        }

        drawGenerated = true;

        for (int i = 0; i < participants.length; i++) {
            System.out.println("Press enter when " + participants[i] + " is ready to see the secret friend.");
            sc.nextLine();
            System.out.println(participants[i] + ": " + secretFriends[i]);
        }
    }

    /**
     * Description: Shows the summary of the draw with its data
     * and the secret friends if the draw was already generated.
     *
     * @param participants registered participants
     * @param name draw name
     * @param description draw description
     * @param budget suggested budget
     * @param date draw date
     * @param drawGenerated indicates if the draw was generated
     */
    public static void drawSummary(String[] participants, String name, String description, double budget, LocalDate date, boolean drawGenerated) {

        System.out.println("Do you want to see the summary? (1 = yes, 2 = no)");
        int see = sc.nextInt();
        sc.nextLine();

        if (see == 1) {
            System.out.println("\nDraw summary:");
            System.out.println("Draw name: " + name);
            System.out.println("Description: " + description);
            System.out.println("Budget: " + budget);
            System.out.println("Date: " + date);
        } else if (see == 2) {
            System.out.println("Continue...");
        } else {
            System.out.println("Invalid option.");
        }

        if (drawGenerated) {
            System.out.println("\nSecret friends:");
            for (int i = 0; i < participants.length; i++) {
                System.out.println(participants[i] + ": " + secretFriends[i]);
            }
        } else {
            System.out.println("The draw has not been generated yet.");
        }
    }
}