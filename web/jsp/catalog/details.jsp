
    
<%if(cat.currentProduct != null){%>
<table cellpadding="10" cellspacing="0" border="0" width="80%" align="center">
    <tr>
        <td valign="top"><img width="400" height="300" src="images/products/<%=cat.currentProduct.image%>"></td>
        <td valign=top" width="100%">
            <b>ID:</b> <%=cat.currentProduct.pid%><br>
            <b>Name:</b> <%=cat.currentProduct.name%><br>
            <b>Company:</b> <%=cat.currentProduct.company%><br>
            <b>Details:</b><pre id="details"> <%=cat.currentProduct.details%></pre><br><br>
            <b>Price:</b><%=String.format(cat.priceFormat, cat.currentProduct.price)%><br>
            <b>Available Quantity:</b> <%=cat.currentProduct.quantity%><br>
           

        <% if(cat.currentProduct.quantity > 0){%>
        <form action="MyController" method="post">
            Quantity: <input type="text" name="quantity" size="5" value="1" />
            <input type="submit" value="Add to Cart" <%if(u.name.equals("")){%>disabled<%}%>/>
            <input type="hidden" name="pid" value="<%=cat.currentProduct.pid%>">
            <input type="hidden" name="cmd" value="AddToCart">
            </form>
            <%}else{%>
            <b style="color: red">Sorry, item out of stock!</b>
            <%}%>
        </td>
    </tr>
</table>
        <%}else{%>
        <b style="color: red">Product Not Found!</b><br>
        <%}%>
