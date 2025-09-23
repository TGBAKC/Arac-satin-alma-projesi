


 public class User {

    private String name;
    private Integer age;
    private Integer id;
    private String email;
    private String passwordHash;
    private String role;
    private CustomerType customerType;

     public User(String name, Integer age,Integer id, String email, String passwordHash, String role , CustomerType customerType) {
         this.name = name;
         this.age =age;
         this.id = id;
         this.email = email;
         this.passwordHash = passwordHash;
         this.role = role;
         this.customerType = customerType;

     }

     public String getName() {
         return name;
     }

     public void setName(String name) {
         this.name = name;
     }
     public Integer getAge(){
         return age;
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

     public CustomerType getCustomerType() {
         return customerType;
     }

     public void setCustomerType(CustomerType customerType) {
         this.customerType = customerType;
     }

     public void setAge(Integer age) {
         this.age = age;
     }

     public CustomerType customerType(){
         return customerType;
     }
 }


