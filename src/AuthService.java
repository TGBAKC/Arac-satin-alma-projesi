
import java.util.ArrayList;
import java.util.List;

public class AuthService {
    private final Service service = new Service();
    private User currentUser;

 private List<User> users =  new ArrayList<>() ;




public void register (String name, String email, String password, String role){
    if (email == null || email.trim().isEmpty()){
    System.out.println("gecersiz email");
    return;

}
    if(!email.contains("@")){
        System.out.println("email formati gecersiz");
        return;
    }
   for (User user : users){
       if (user.getEmail().equals(email)){
           System.out.println("bu email zaten kayitli");
           return;
       }
   }
    if (password == null || password.trim().isEmpty()) {
        System.out.println("geçersiz şifre" );
        return;
    }
    String hashedPassword = service.hashPassword(password);
    User newUser = new User(name, users.size() + 1, email, hashedPassword, role);
    users.add(newUser);
    System.out.println("Kayıt başarılı ✅");




}

public void login(String email, String password){
    if (email == null || email.trim().isEmpty()){
        System.out.println("gecersiz email");
        return;

    }
    if(!email.contains("@")){
        System.out.println("email formati gecersiz");
        return;
    }
    User foundUser = null;

    for (User user : users){

        if (user.getEmail().equals(email)){

            foundUser = user;
            break;

        }

    }

    if (foundUser == null){
        System.out.println("email bulunamamdi");
        return;
    }

    if(password == null || password.trim().isEmpty()){
        System.out.println("geceersiz password");
        return;
    }


   String hashedPassword =service.hashPassword(password);

   if(foundUser.getPasswordHash().equals(hashedPassword)) {
    currentUser = foundUser;
    System.out.println("giris basarili");
   }
   else{
    System.out.println("sifre hatali");
   }

   }









    public User getCurrentUser() {
        return currentUser;
    }



}