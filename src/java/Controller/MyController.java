
package Controller;
import Model.Cart;
import Model.Catalog;
import Model.Command;
import java.sql.*;
import Model.DBCon;
import Model.Product;
import Model.User;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
//import static jdk.nashorn.internal.runtime.Debug.id;
//import Model.;

public class MyController extends HttpServlet {

public HashMap<String,Command> allCommands;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try{
      HttpSession sess = request.getSession();
      User u = (User) sess.getAttribute("u");
     Catalog cat = (Catalog) sess.getAttribute("cat");
      Cart cart = (Cart) sess.getAttribute("cart");

    if(u == null){
    u = new User();
   cat = new Catalog();
    cart = new Cart();
    
    sess.setAttribute("u", u);
    sess.setAttribute("cat", cat);
    sess.setAttribute("cart", cart);
} 
    if(allCommands == null){
        allCommands = new HashMap<String,Command>();
        allCommands.putAll(u.getCommands());
        allCommands.putAll(cat.getCommands());
        allCommands.putAll(cart.getCommands());
    }
    HashMap<String, String>params = new HashMap<String,String>();
    Enumeration<String> pNames = request.getParameterNames();
    
    while(pNames.hasMoreElements()){
      String pName = pNames.nextElement();
      String pValue = request.getParameter(pName);
      params.put(pName, pValue);
    }
    String cmd = request.getParameter("cmd");
    Command c = allCommands.get(cmd);
    
    if(c!= null){
    c.run(params);
    }else{
        throw new Exception("Command " +cmd+" not found!");
    }
   
    String url = "/index.jsp";

if(cmd.equals("Buy")){
    url = "/invoice.jsp";
}
    RequestDispatcher rd = this.getServletContext().getRequestDispatcher(url);
    rd.forward(request, response);
    
      }catch(Exception e){
    e.printStackTrace();
    response.setContentType("text/html;charset=UTF-8");
    response.getWriter().println("<h3>Error:</h3>");
    response.getWriter().println("<pre>");
    e.printStackTrace(response.getWriter());
    response.getWriter().println("</pre>");
}
}

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response); 
     
    }

    @Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    processRequest(request, response);
}
}
