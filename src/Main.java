public class Main {
    public static void main(String[] args) {
        AuthService auth = new AuthService();
        Menu.start(auth);

        User current = auth.getCurrentUser();
        if (current != null && "ADMIN".equalsIgnoreCase(current.getRole())) {
            new AdminMenu().start();
        } else if (current != null) {
            new CustomerMenu(current).start();
        } else {
            System.out.println("No user logged in. Exiting...");
        }
    }
}
