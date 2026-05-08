
package Model;

import java.util.*;
import java.sql.*;

//@author Hajed
 
public class Cart {
    
    public ArrayList<Product> products;
    public double totalPrice;
    public int uid;
    public String msg;
    
    public Cart(){
        products = new ArrayList<Product>();
        totalPrice = 0;
        uid = 0;
        msg = "";
    }
   
    public HashMap<String, Command> getCommands(){
        HashMap<String, Command> hm = new HashMap<String, Command>();
        
        hm.put("AddToCart", new AddToCart());
        hm.put("IncreaseQuantity", new IncreaseQuantity());
        hm.put("DecreaseQuantity", new DecreaseQuantity());
        hm.put("DeleteProduct", new DeleteProduct());
        hm.put("Buy", new Buy());

        for(Command c: hm.values()){
            c.register(this);
        }
        return hm;
    }
    
    public void listCartProducts()throws Exception{
        Connection con = DBCon.getConnection();
        PreparedStatement pst1 = con.prepareStatement(
            "update cart, products set cart.quantity=products.quantity "+
  " where products.pid=cart.pid and uid=? and cart.quantity > products.quantity");
               
        PreparedStatement pst2 = con.prepareStatement(
                "delete from cart where uid=? and quantity=0");
        
        PreparedStatement pst3 = con.prepareStatement(
           "select * from cart, products where products.pid=cart.pid and uid=?");
        

        pst1.setInt(1, uid);
        int rows = pst1.executeUpdate();
        if(rows>0){
            msg = "your products quantity were updated because "
                    + "of availability in the store!";
        }
        pst2.setInt(1, uid);
        rows = pst2.executeUpdate();
        if(rows>0){
          msg = msg + 
            " some products were removed because of availability in the store!";
            
        }
        
        pst3.setInt(1, uid);
        ResultSet rs = pst3.executeQuery();
        products.clear();
        totalPrice = 0;
        while(rs.next()){
           products.add(new Product(rs.getInt("cart.pid"), 
                                         rs.getString("name"),
                                         rs.getString("company"),
                                         rs.getString("details"),
                                         rs.getDouble("price"),
                                         rs.getInt("cart.quantity"),
                                         rs.getString("image")));
                                         //rs.getDouble("vram"),
                                         //rs.getDouble("speed")));
           totalPrice = totalPrice + 
                   rs.getDouble("price") * rs.getInt("cart.quantity");
            } 
        rs.close();
        pst1.close();
        pst2.close();
        pst3.close();
        con.close();

    }
    
    public void addToCart(int pid, int q)throws Exception{
        Connection con = DBCon.getConnection();
        PreparedStatement pst1 = con.prepareStatement(
                "select * from cart where uid=? and pid=?");
        PreparedStatement pst2 = con.prepareStatement(
                "update cart set quantity=? where uid=? and pid=?");
        PreparedStatement pst3 = con.prepareStatement(
                "insert into cart(uid,pid,quantity)values(?,?,?)");

        pst1.setInt(1, uid);
        pst1.setInt(2, pid);
        ResultSet rs = pst1.executeQuery();
        if(rs.next()){
            int cartQ = rs.getInt("quantity");
            pst2.setInt(1, cartQ+q);
            pst2.setInt(2, uid);
            pst2.setInt(3, pid);
            int rows = pst2.executeUpdate();
            if(rows==0){
                msg = "Please try again!";
            }
            
        }else{
            pst3.setInt(1, uid);
            pst3.setInt(2, pid);
            pst3.setInt(3, q);
            int rows = pst3.executeUpdate();
            if(rows==0){
                msg = "Please try again!";
            }
        }
              rs.close();
              pst1.close();
              pst2.close();
              pst3.close();
              con.close();
    }
    
    public void increaseQuantity(int pid) throws Exception{
                Connection con = DBCon.getConnection();
                Statement st = con.createStatement();
                
                int rows = st.executeUpdate(
          "update cart set quantity=quantity+1 where uid="+uid+" and pid="+pid);
                if(rows==0){
                    msg = "Please try again!";
                }
                st.close();
                con.close();
    }
    
    public void decreaseQuantity(int pid)throws Exception{
                Connection con = DBCon.getConnection();
                Statement st = con.createStatement();
                
                int rows = st.executeUpdate(
                   "update cart set quantity=greatest(quantity-1,1) "
                           + "where uid="+uid+" and pid="+pid);
                if(rows==0){
                    msg = "Please try again!";
                }
                st.close();
                con.close();
    }
    
    public void deleteProduct(int pid)throws Exception{
                Connection con = DBCon.getConnection();
                Statement st = con.createStatement();
                
                int rows = st.executeUpdate(
                     "delete from cart where uid="+uid+" and pid="+pid);
                if(rows==0){
                    msg = "Please try again!";
                }
                st.close();
                con.close();
    }
    
    public void buy()throws Exception{
        Connection con = DBCon.getConnection();
        PreparedStatement pst1 = con.prepareStatement(
                "insert into invoice(uid,date)values(?,now())");
        PreparedStatement pst2 = con.prepareStatement(
          "select invid from invoice where uid=? order by invid desc limit 1");
        PreparedStatement pst3 = con.prepareStatement(
                "insert into invproduct(invid,pid,quantity,price) "+
                "select ?, cart.pid,cart.quantity, price from cart, "
                        + "products where products.pid=cart.pid and uid=?");
        PreparedStatement pst4 = con.prepareStatement(
         "update products, cart set "
                 + "products.quantity=products.quantity-cart.quantity where "+
                        "uid=? and products.pid=cart.pid");
        PreparedStatement pst5 = con.prepareStatement(
                "delete from cart where uid=?");
        
        pst1.setInt(1, uid);
        int rows = pst1.executeUpdate();
        if(rows>0){
            pst2.setInt(1, uid);
            ResultSet rs = pst2.executeQuery();
            if(rs.next()){
                int invid = rs.getInt("invid");
                pst3.setInt(1, invid);
                pst3.setInt(2, uid);
                rows = pst3.executeUpdate();
                if(rows>0){
                    pst4.setInt(1, uid);
                    rows = pst4.executeUpdate();
                    if(rows>0){
                        pst5.setInt(1, uid);
                        rows = pst5.executeUpdate();
                    }
                }
                
            }
            rs.close();
        }else{
            msg = "Please try again!";
        }
              pst1.close();
              pst2.close();
              pst3.close();
              pst4.close();
              pst5.close();
              con.close();
    }
}//end of class Cart

class AddToCart implements Command{
    Cart cartObj;
    public void register(Object o){
        cartObj = (Cart) o;
    }
    public void run(HashMap<String, String> hm)  throws Exception{
        cartObj.addToCart(Integer.parseInt(hm.get("pid")), 
                Integer.parseInt(hm.get("quantity")));
        }
}
class IncreaseQuantity implements Command{
        Cart cartObj;
    public void register(Object o){
        cartObj = (Cart) o;
    }
    public void run(HashMap<String, String> hm)  throws Exception{
        cartObj.increaseQuantity(Integer.parseInt(hm.get("pid")));
        }
}
class DecreaseQuantity implements Command{
    Cart cartObj;
    public void register(Object o){
        cartObj = (Cart) o;
    }
    public void run(HashMap<String, String> hm)  throws Exception{
        cartObj.decreaseQuantity(Integer.parseInt(hm.get("pid")));
        }
}
class DeleteProduct implements Command{
    Cart cartObj;
    public void register(Object o){
        cartObj = (Cart) o;
    }
    public void run(HashMap<String, String> hm)  throws Exception{
        cartObj.deleteProduct(Integer.parseInt(hm.get("pid")));
        }   
}
class Buy implements Command{
    Cart cartObj;
    public void register(Object o){
        cartObj = (Cart) o;
    }
    public void run(HashMap<String, String> hm)  throws Exception{
        cartObj.buy();
        }
}
