import java.util.Scanner;

public class Menu {
    private static final Scanner sc = new Scanner(System.in);

    // Application start menu
    public static void start(AuthService auth) {
        while (true) {
            System.out.println("\n=== Car Rental Application ===");
            System.out.println("1) Register");
            System.out.println("2) Login");
            System.out.println("0) Exit");
            System.out.print("Choice: ");
            String ch = sc.nextLine().trim();

            switch (ch) {
                case "1" -> doRegister(auth);
                case "2" -> doLogin(auth);
                case "0" -> { System.out.println("Goodbye!"); return; }
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    // Registration flow
    private static void doRegister(AuthService auth) {
        System.out.print("Full Name: ");
        String name = sc.nextLine();

        System.out.print("Age: ");
        int age = Integer.parseInt(sc.nextLine().trim());

        System.out.print("Email: ");
        String email = sc.nextLine().trim();

        System.out.print("Password: ");
        String pass = sc.nextLine();

        System.out.print("Role (ADMIN / INDIVIDUAL / CORPORATE): ");
        String role = sc.nextLine().trim().toUpperCase();

        CustomerType ct = ("CORPORATE".equals(role)) ? CustomerType.CORPORATE : CustomerType.INDIVIDUAL;

        auth.register(name, age, email, pass, role, ct);
    }

    // Login flow
    private static void doLogin(AuthService auth) {
        System.out.print("Email: ");
        String email = sc.nextLine().trim();

        System.out.print("Password: ");
        String pass = sc.nextLine();

        auth.login(email, pass);
        if (auth.getCurrentUser() != null) {
            // For now, just confirmation; main menus are handled in Main.java
            System.out.println("Press Enter to return to the menu...");
            sc.nextLine();
        }
    }

    // Old helper methods (if still needed elsewhere)
    public static String getEmail() {
        System.out.println("Welcome to the application");
        while (true) {
            System.out.print("Enter your email: ");
            String mail = sc.nextLine();
            if (mail.contains("@")) {
                System.out.println("Valid email");
                return mail;
            } else {
                System.out.println("Please include '@' in your email");
            }
        }
    }

    public static String getPassword() {
        System.out.print("Enter your password: ");
        return sc.nextLine();
    }
}
