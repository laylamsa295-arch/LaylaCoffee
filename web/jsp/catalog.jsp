<%if(cat.status==0) { %>
<table border="0">
    <tr>
        <td><%@include file="catalog/sort.jsp" %></td>
        <td width="50px" align="center">|</td>
        <td><%@include file="catalog/search.jsp" %></td>
    </tr>
</table>
    <br><br>
    Number of Products:<%=cat.products.size()%>
    <br><br>
    <%if(cat.products.size()==0) {%>
        <div align="center"><b style="color: red">No Products Found!</b></div>
    <%}else{%>
    <div style="width: 80%">
        <%@include file="catalog/products.jsp" %>
        <br><br>
        <%@include file="catalog/pages.jsp" %>
    </div>
      <%}%>    
    <% } else { %>
    <a href="MyController?cmd=ChangeStatusToMain">Back</a>
    <br><br>
    <%@include file="catalog/details.jsp"%>
    <% } %>

        
