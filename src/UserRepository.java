public interface UserRepository {
    User findByEmail(String email);
    boolean existsByEmail(String email);
    User create(User u);
}
