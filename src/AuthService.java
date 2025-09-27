public class AuthService {
    private final Service service = new Service();           // for hashPassword
    private final UserRepository userRepo = new JdbcUserRepository();
    private User currentUser;

    public void register(String name, Integer age, String email, String password,
                         String role, CustomerType customerType) {

        if (email == null || email.isBlank() || !email.contains("@")) {
            System.out.println("Invalid email");
            return;
        }
        if (userRepo.existsByEmail(email)) {
            System.out.println("This email is already registered");
            return;
        }
        if (password == null || password.isBlank()) {
            System.out.println("Invalid password");
            return;
        }

        String hashed = service.hashPassword(password);
        User u = new User(name, age, null, email, hashed, role, customerType);
        userRepo.create(u); // save to DB
        System.out.println("Registration successful ✅ (id=" + u.getId() + ")");
    }

    public void login(String email, String password){
        if (email == null || email.isBlank() || !email.contains("@")) {
            System.out.println("Invalid email");
            return;
        }
        User found = userRepo.findByEmail(email);
        if (found == null) {
            System.out.println("Email not found");
            return;
        }
        if (password == null || password.isBlank()) {
            System.out.println("Invalid password");
            return;
        }
        String hashed = service.hashPassword(password);
        if (found.getPasswordHash().equals(hashed)) {
            currentUser = found;
            System.out.println("Login successful ✅ Welcome " + found.getName());
        } else {
            System.out.println("Incorrect password");
        }
    }

    public User getCurrentUser() { return currentUser; }
}
