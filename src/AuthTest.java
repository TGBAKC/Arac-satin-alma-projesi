public class AuthTest {
    public static void main(String[] args) {
        AuthService auth = new AuthService();

        // 1) Try registration (change email each run to avoid duplicate)
        auth.register("Test User", 29, "test1@demo.com",
                "123456", "INDIVIDUAL", CustomerType.INDIVIDUAL);

        // 2) Try login
        auth.login("test1@demo.com", "123456");
    }
}
