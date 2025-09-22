


 public class User {

    private String name;
    private Integer id;
    private String email;
    private String passwordHash;
    private String role;


     public User(String name, Integer id, String email, String passwordHash, String role) {
         this.name = name;
         this.id = id;
         this.email = email;
         this.passwordHash = passwordHash;
         this.role = role;
     }

     public String getName() {
         return name;
     }

     public void setName(String name) {
         this.name = name;
     }

     public Integer getId() {
         return id;
     }

     public void setId(Integer id) {
         this.id = id;
     }

     public String getEmail() {
         return email;
     }

     public void setEmail(String email) {
         this.email = email;
     }

     public String getPasswordHash() {
         return passwordHash;
     }

     public void setPasswordHash(String passwordHash) {
         this.passwordHash = passwordHash;
     }

     public String getRole() {
         return role;
     }

     public void setRole(String role) {
         this.role = role;
     }
 }


