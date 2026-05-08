
<div id="pages">
<% if(cat.currentPage==1){%>
    Previous
    <%}else{%>
    <a href="MyController?cmd=GetPage&page=<%=cat.currentPage-1%>"> Previous</a>
    <% } %>
   
    <% int pages = (int) Math.ceil(cat.products.size() / (cat.prodsPage * 1.0));%>
        
    <% for(int i=1; i <= pages; i++){%>
        <% if(i==cat.currentPage){%>
        <%=i%>
        <%} else {%>
        <a href="MyController?cmd=GetPage&page=<%=i%>"> <%=i%></a>
        <%}%>
    <%}%>
        <% if(cat.currentPage==pages){%>
        Next
      <%}else{%>
            <a href="MyController?cmd=GetPage&page=<%=cat.currentPage+1%>"> Next</a>
        <%}%>
        
</div>