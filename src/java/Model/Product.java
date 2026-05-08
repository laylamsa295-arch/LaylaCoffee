package Model; 
public class Product {
    public int pid;
    public String name;
    public String company;
    public String details;
    public double price;
    public int quantity;
    public String image;
    int id;
    
    public Product(){
            pid=0;
            name="";
            company="";
            details="";
            price=0;
            quantity=0;
            image="";            
}
     public Product(int i, String n, String c, String d, double p, 
                    int q, String img){
            pid=i;
            name=n;
            company=c;
            details=d;
            price=p;
            quantity=q;
            image=img;           
}
    public boolean equals(Object o){
        if(o==null) return false;
        if(o==this) return true;
        if(!(o instanceof Product)) return false;
        Product p = (Product) o;
        return this.pid==p.pid;
    }
    public int hashCode(){
        return ((Integer)pid).hashCode();
    }
}
