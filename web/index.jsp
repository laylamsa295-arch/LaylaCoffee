<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@page import="Model.*"%> 
<%
        HttpSession sess =request.getSession();
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
%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="style.css" rel="stylesheet" type="text/css"/>
        <title>Layla Coffee</title>
        <style>
          body{
    background-image: url('images/COFFEE11.jpg');
    background-repeat: no-repeat;
    background-attachment: fixed;
    background-size: 100% 100%;
}
        </style>           
    </head>
    <body>        
 
        <div id="container">           
            <div id="header">
               <%@include file="jsp/header.jsp"%>
            </div>
            <div id="user">
               <%@include file="jsp/user.jsp"%>
            </div>
            <div id="catalog" align="center">
               <%@include file="jsp/catalog.jsp"%>
            </div>
            <div id="cart">
               <%@include file="jsp/cart.jsp"%>
            </div>
            <div id="footer" align="center">
               <%@include file="jsp/footer.jsp"%>
            </div>            
        </div>
    </body>
</html>
