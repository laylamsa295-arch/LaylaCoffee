<form action="MyController" method="get">
    <select name="column">
        <option <%if(cat.sort==0) {%> selected<%}%> value="0">ID</option>
        <option <%if(cat.sort==1) {%> selected<%}%> value="1">Name</option>
        <option <%if(cat.sort==2) {%> selected<%}%> value="2">Company</option>
        <option <%if(cat.sort==3) {%> selected<%}%> value="3">Price</option>
    </select> 
    <input type="hidden" name="cmd" value="Sort">
    <input type="checkbox" name="desc" value="checked"
           <%if(cat.sortDesc==true){%>checked<%}%>/> 
    <input type="submit" value="Sort">
</form>
