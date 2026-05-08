
<b>Shopping Cart:</b><br><br>
<%
    cart.uid = u.uid;
    cart.listCartProducts();
%>

<% if(u.name.equals("")) { %>
    Sign in to use the shopping cart...
<% } else { %>

    <% if(!cart.msg.equals("")) { %>
        <b style="color: red"><%=cart.msg%></b>
        <% cart.msg = ""; %>
        <br><br>
    <% } %>

    <br> Number of products: <%= cart.products != null ? cart.products.size() : 0 %><br><br>

    <% if (cart.products != null && !cart.products.isEmpty()) { %>
        <% for(int i = 0; i < cart.products.size(); i++) { %>
            <img width="80" height="70" src="images/products/<%= cart.products.get(i).image %>"><br>
            ID: <%= cart.products.get(i).pid %><br>
            Name: 
            <a href="MyControllar?cmd=GetDetailedProduct&pid=<%= cart.products.get(i).pid %>">
                <%= cart.products.get(i).name %>
            </a><br>
            Price: <%= String.format("%.3f KD", cart.products.get(i).price) %><br>
            Quantity: <%= cart.products.get(i).quantity %>
            <a href="MyController?cmd=IncreaseQuantity&pid=<%= cart.products.get(i).pid %>">+</a>
            <a href="MyController?cmd=DecreaseQuantity&pid=<%= cart.products.get(i).pid %>">-</a>
            <a href="MyController?cmd=DeleteProduct&pid=<%= cart.products.get(i).pid %>">clear</a>
            <br><br><br>
        <% } %>
    <% } else { %>
        <i>Your cart is empty.</i><br><br>
    <% } %>

    Total = <%= String.format("%.3f KD", cart.totalPrice) %><br><br>

    <form action="MyController" method="post">
        <input type="submit" value="Buy" <% if(cart.products == null || cart.products.size() == 0) { %>disabled<% } %>>
        <input type="hidden" name="cmd" value="Buy">
        <a href="MyController?cmd=ChangeStatusToMain">Continue Shopping</a>
    </form>
<% } %>