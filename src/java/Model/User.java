
package Model;

import java.util.*;
import java.sql.*;

// @author Hajed
 
public class User {
    
    public int uid;
    public String username;
    public String name;
    public String email;
    public String phone; 
    public String address;
    public int status; // 0=NotSignedIn, 1=SignIn, 2=SignedIn, 3=SignedOut,
                       // 4=PreChangePassword, 5=PasswordChanged, 6=PreSignUp, 
                       // 7=SignUp, 8=PreForgotPassword, 9=ForgotPassword
    public String msg;
    
    public User(){
        uid=0;
        username="";
        name="";
        email="";
        phone="";
        address="";
        status=0;
        msg="";
    }
    public User(int i, String u, String n, String e, String ph, String a){
        uid=i;
        username=u;
        name=n;
        email=e;
        phone=ph;
        address=a;
        status=0;
        msg="";
    }
    public HashMap<String, Command> getCommands(){
        HashMap<String, Command> hm = new HashMap<String, Command>();
        
        hm.put("NotSignedIn", new NotSignedIn());
        hm.put("PreSignIn", new PreSignIn());
        hm.put("SignedIn", new SignedIn());
        hm.put("SignedOut", new SignedOut());
        hm.put("PreChangePassword", new PreChangePassword());
        hm.put("PasswordChanged", new PasswordChanged());
        hm.put("PreSignUp", new PreSignUp());
        hm.put("SignedUp", new SignedUp());
        hm.put("PreForgotPassword", new PreForgotPassword());
        hm.put("ForgotPassword", new ForgotPassword());

        for(Command c: hm.values()){
            c.register(this);
        }
        
        return hm;
    }
    
    public void notSignedIn(){
        status=0;
    }
    public void preSignIn(){
        status=1;
    }
    public void signedIn(String u, String p)throws Exception{
        if(u==null || p==null){
          if(!username.equals("")){
              status = 2;
          }
         }else if(u.equals("") || p.equals("")){
            msg = "Please fill in all fields.";
        }else{
            Connection con = DBCon.getConnection();
            PreparedStatement pst1 = con.prepareStatement(
                "select * from user where username=? and password=sha(?)");
           
            pst1.setString(1, u);
            pst1.setString(2, p);
            ResultSet rs = pst1.executeQuery();
            if(rs.next()){
                uid=rs.getInt("uid");
                username=rs.getString("username");
                name=rs.getString("name");
                email=rs.getString("email");
                phone=rs.getString("phone");
                address=rs.getString("address");
                status=2;
                msg="Welcome "+name; 
            }else{
                msg = "Wrong username or password! please try again.";
            }
            rs.close();
            pst1.close();
            con.close();
        }       
    }
    public void signedOut(){
        msg=" see you soon " + name;
        uid=0;
        username="";
        name="";
        email="";
        phone="";
        address="";
        status=3;
    }
    public void preChangePassword(){
        status=4;
    }
   public void passwordChanged(String cp, String np, String c) throws Exception{
        if(cp.equals("") || np.equals("") || c.equals("")){
            msg = "Please fill all fields! ";
        }else if(!np.equals(c)){
            msg = "Please check your new password and confirm it..";
        }else{
             Connection con = DBCon.getConnection();
             PreparedStatement pst1 = con.prepareStatement(
       "update user set password=sha(?) where username=? and password=sha(?)");
           
            pst1.setString(1, np);
            pst1.setString(2, username);
            pst1.setString(3, cp);
            int rows = pst1.executeUpdate();
            if(rows == 0){
                msg = "Please check your current password.. ";
            }else{
               msg = "Your password  is changed successfully. "
                       + "please use it to sign in next time.";
               status = 5;           
                        
            }
            pst1.close();
            con.close();
        }
    }
    public void preSignUp(){
        status=6;
    }
    public void signedUp(String u, String p, String c, String n,
                         String e, String ph, String a) throws Exception{
        if(u.equals("") ||
                p.equals("") ||
                c.equals("") ||
                n.equals("") ||
                e.equals("") ||
                ph.equals("") ||
                a.equals("")){
            msg = "Please fill in all fields";
        }else if(!p.equals(c)){
            msg = "Passwords are Not the same!!";
        }else{
            Connection con = DBCon.getConnection();
            PreparedStatement pst1 = con.prepareStatement(
                    "select * from user where username=?");
            PreparedStatement pst2 = con.prepareStatement(
    "insert into user (uid, username, password, name, email, phone, address) values ((select ifnull(max(u.uid),0)+1 from user u), ?, sha(?), ?, ?, ?, ?)"
);
            pst1.setString(1, u);
            ResultSet rs = pst1.executeQuery();
            if(rs.next()){
             msg = "Username exists! please sign in or choose another userid!";
            }else{
                pst2.setString(1, u);
                pst2.setString(2, p);
                pst2.setString(3, n);
                pst2.setString(4, e);
                pst2.setString(5, ph);
                pst2.setString(6, a);
                int rows = pst2.executeUpdate();
                if(rows==0){
                    msg = "Try again!";
                }else{
                    msg="Sign up successful. please sign in.";
                    status = 7;
                }

            }

            rs.close();
            pst1.close();
            pst2.close();
            con.close();
        }
                
        
    }
    public void preForgotPassword(){
        status=8;
    }
    public void forgotPassword(String n, String e)throws Exception{
        if(n.equals("") || e.equals("")){
            msg = "please fill in all fields..";
        }else{
            Connection con = DBCon.getConnection();
            PreparedStatement pst1 = con.prepareStatement(
               "update user set password=sha('000') where name=? and email=?");
           
            pst1.setString(1, n);
            pst1.setString(2, e);
            int rows = pst1.executeUpdate();
            if(rows == 0){
                msg = "Please check your name and email.. ";
            }else{
                msg = "Your temporary password is '000'. "
                     + "please use it to sign in then change youe password..";
               status = 9;           
                        
            }
            pst1.close();
            con.close();
        }
    }
} //end of class User

class NotSignedIn implements Command{
    User userObj;
    public void register(Object o){
        userObj = (User) o;
    }
    public void run(HashMap<String, String> hm) throws Exception{
        userObj.notSignedIn();
    }
}

class PreSignIn implements Command{
    User userObj;
    public void register(Object o){
        userObj = (User) o;
    }
    public void run(HashMap<String, String> hm) throws Exception{
        userObj.preSignIn();
    }
}
class SignedIn implements Command{
    User userObj;
    public void register(Object o){
        userObj = (User) o;
    }
    public void run(HashMap<String, String> hm) throws Exception{
        userObj.signedIn(hm.get("username"), hm.get("password"));
    }
}
class SignedOut implements Command{
    User userObj;
    public void register(Object o){
        userObj = (User) o;
    }
    public void run(HashMap<String, String> hm) throws Exception{
        userObj.signedOut();
    }
}
class PreChangePassword implements Command{
    User userObj;
    public void register(Object o){
        userObj = (User) o;
    }
    public void run(HashMap<String, String> hm) throws Exception{
        userObj.preChangePassword();
    }
}
class PasswordChanged implements Command{
    User userObj;
    public void register(Object o){
        userObj = (User) o;
    }
    public void run(HashMap<String, String> hm) throws Exception{
        userObj.passwordChanged(hm.get("currentpassword"),
                                hm.get("newpassword"), 
                                hm.get("confirmpassword") );
    }
}
class PreSignUp implements Command{
    User userObj;
    public void register(Object o){
        userObj = (User) o;
    }
    public void run(HashMap<String, String> hm) throws Exception{
        userObj.preSignUp();
    }
}
class SignedUp implements Command{
    User userObj;
    public void register(Object o){
        userObj = (User) o;
    }
    public void run(HashMap<String, String> hm) throws Exception{
      userObj.signedUp(hm.get("username"),hm.get("password"), hm.get("confirm"),
           hm.get("name"), hm.get("email"), hm.get("phone"), hm.get("address"));
    }
}
class PreForgotPassword implements Command{
    User userObj;
    public void register(Object o){
        userObj = (User) o;
    }
    public void run(HashMap<String, String> hm) throws Exception{
        userObj.preForgotPassword();
    }
}
class ForgotPassword implements Command{
    User userObj;
    public void register(Object o){
        userObj = (User) o;
    }
    public void run(HashMap<String, String> hm) throws Exception{
        userObj.forgotPassword(hm.get("name"), hm.get("email"));
    }
}
