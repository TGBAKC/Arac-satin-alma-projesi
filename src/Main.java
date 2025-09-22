public class Main {
    public static void main(String[] args) {

        Service service = new Service();

        String email = Menu.getEmail();
        String password = Menu.getPassword();

        // Email doğrulama
        boolean isValid = service.validateEmail(email);
        if (!isValid) {
            System.out.println("Geçersiz email ❌");
            return; // programı bitir
        }

        // Parola hashleme
        String passwordHash = service.hashPassword(password);
        System.out.println("Email: " + email);
        System.out.println("SHA-256 ile hashlenmiş şifre: " + passwordHash);
    }
}
