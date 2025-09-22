import java.util.Scanner;

public class Menu {
 static Scanner scanner = new Scanner(System.in);

    public static String getEmail() {
        System.out.println("Welcome to the application");
        while (true) {
            System.out.print("Mailinizi yaziniz: ");
            String mail = scanner.nextLine();

            if (mail.contains("@")) {
                System.out.println("Mailiniz dogru");
                return mail;
            } else {
                System.out.println("Lutfen @ isareti ekleyiniz");
            }
        }
    }

    public static String getPassword() {
        System.out.print("Sifrenizi yaziniz: ");
        return scanner.nextLine();
    }
}
