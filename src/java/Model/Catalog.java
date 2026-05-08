package Model;

import java.util.*;
import java.sql.*;

public class Catalog {
    public int status; //0=main,  1=details
    public ArrayList<Product> products;
    public int sort; // 0=pid, 1=name, 2=company, 3=price
    public boolean sortDesc; //false=ascending , true=descending
    public String keyword;
    public int currentPage;
    public int prodsPage;
    public int prodsLine;
    public String priceFormat;
    public Product currentProduct;
    
    public Catalog()throws Exception{
        status = 0;
        products = new ArrayList<Product>();
        sort=0;
        sortDesc = false;
        keyword = "";
        currentPage = 1;
        prodsPage = 6;
        prodsLine = 3;
        priceFormat = "KD %.3f";
        currentProduct = null;
       listAllProducts(keyword); 
    }
    
    public HashMap<String, Command> getCommands(){
        HashMap<String, Command> hm = new HashMap<String, Command>();
        
        hm.put("ListAllProducts", new ListAllProducts());
        hm.put("GetDetailedProduct", new GetDetailedProduct());
        hm.put("GetPage", new GetPage());
        hm.put("ChangeStatusToMain", new ChangeStatusToMain());
        hm.put("Sort", new Sort());
        hm.put("Search", new Search());
        
        for(Command c : hm.values()){
            c.register(this);
        }
        return hm;
    }
    
    public void listAllProducts(String k) throws Exception{
        Connection con = DBCon.getConnection();
        PreparedStatement pst1 = con.prepareStatement(
"select * from products where name like ? or company like ? or details like ?");
           
            pst1.setString(1,"%" +k+"%");
            pst1.setString(2,"%" +k+"%");
            pst1.setString(3,"%" +k+"%");

            ResultSet rs = pst1.executeQuery();
            products.clear();
            while(rs.next()){
                products.add(new Product(rs.getInt("pid"), 
                                         rs.getString("name"),
                                         rs.getString("company"),
                                         rs.getString("details"),
                                         rs.getDouble("price"),
                                         rs.getInt("quantity"),
                                         rs.getString("image")));
                                         
            }
            rs.close();           
            pst1.close();
            con.close();
    }
    public void getDetailedProduct(int pid)throws Exception{
        Connection con = DBCon.getConnection();
        PreparedStatement pst1 = con.prepareStatement(
                    "select * from products where pid=?");
           
            pst1.setInt(1, pid);
            ResultSet rs = pst1.executeQuery();
            if(rs.next()){
                currentProduct = (new Product(rs.getInt("pid"), 
                                              rs.getString("name"),
                                              rs.getString("company"),
                                              rs.getString("details"),
                                              rs.getDouble("price"),
                                              rs.getInt("quantity"),
                                              rs.getString("image")));
                                              
            int index = products.indexOf(currentProduct);
            products.set(index, currentProduct);
            status = 1;
            }else{
                currentProduct = null;
            }
            rs.close();           
            pst1.close();
            con.close();
    }
    public void getPage(int p){
        currentPage = p;
    }
    public void changeStatusToMain(){
        status = 0;
    }
    public void sort(String col, String desc){
        if(col!= null){
            sort = Integer.parseInt(col);
            sortDesc = desc!=null;      
        }
        currentPage = 1;
        
        switch(sort){
            case 0: if(!sortDesc) { Collections.sort(products, new IDAsc());
                         }else{ Collections.sort(products, new IDDesc());}
                break;
            case 1: if(!sortDesc){Collections.sort(products, new NameAsc());
                         }else{ Collections.sort(products, new NameDesc());}
                break;
            case 2: if(!sortDesc){Collections.sort(products, new CompanyAsc());
                         }else{ Collections.sort(products, new CompanyDesc());}
                break;
            case 3: if(!sortDesc){Collections.sort(products, new PriceAsc());
                         }else{ Collections.sort(products, new PriceDesc());}
                break;
            default: if(!sortDesc) { Collections.sort(products, new IDAsc());
                         }else{ Collections.sort(products, new IDDesc());}
        }
    }
    public void search(String k)throws Exception{
        keyword = k;
        currentPage = 1;
        listAllProducts(keyword);
        sort(null, null);
        
    }
} //end of catalog class

class IDAsc implements Comparator<Product>{
    public int compare(Product a, Product b){
        return a.pid - b.pid;
    }
}

class IDDesc implements Comparator<Product>{
    public int compare(Product a, Product b){
        return b.pid - a.pid;
    }
}

class NameAsc implements Comparator<Product>{
    public int compare(Product a, Product b){
        return a.name.compareTo(b.name);
    }
}
class NameDesc implements Comparator<Product>{
    public int compare(Product a, Product b){
        return b.name.compareTo(a.name);
    }
}

class CompanyAsc implements Comparator<Product>{
    public int compare(Product a, Product b){
        return a.company.compareTo(b.company);
    }
}

class CompanyDesc implements Comparator<Product>{
    public int compare(Product a, Product b){
        return b.company.compareTo(a.company);
    }
}

class PriceAsc implements Comparator<Product>{
    public int compare(Product a, Product b){
        return Double.compare(a.price, b.price);
    }
}

class PriceDesc implements Comparator<Product>{
    public int compare(Product a, Product b){
        return Double.compare(b.price, a.price);
    }
}
class ListAllProducts implements Command{
    Catalog catObj;
    public void register(Object o){
        catObj =(Catalog) o;
    }
    public void run(HashMap<String, String> hm) throws Exception{
        catObj.listAllProducts(hm.get("keyword"));
    }
}
    
 class GetDetailedProduct implements Command{
    Catalog catObj;
    public void register(Object o){
        catObj =(Catalog) o;
    }
    public void run(HashMap<String, String> hm) throws Exception{
        catObj.getDetailedProduct(Integer.parseInt(hm.get("pid")));
    }
  }

class GetPage implements Command{
    Catalog catObj;
    public void register(Object o){
        catObj =(Catalog) o;
    }
    public void run(HashMap<String, String> hm) throws Exception{
        catObj.getPage(Integer.parseInt(hm.get("page")));
    }
}

   class ChangeStatusToMain implements Command{
    Catalog catObj;
    public void register(Object o){
        catObj =(Catalog) o;
    }
    public void run(HashMap<String, String> hm) throws Exception{
        catObj.changeStatusToMain();
    } 
   }

    class Sort implements Command{
    Catalog catObj;
    public void register(Object o){
        catObj =(Catalog) o;
    }
    public void run(HashMap<String, String> hm) throws Exception{
        catObj.sort(hm.get("column"), hm.get("desc"));
    }
    }

    class Search implements Command{
    Catalog catObj;
    public void register(Object o){
        catObj =(Catalog) o;
    }
    public void run(HashMap<String, String> hm) throws Exception{
        catObj.search(hm.get("keyword"));
    }
}
 
