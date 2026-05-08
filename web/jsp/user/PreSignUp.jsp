<% //to keep information in fields if error msg found
String un = request.getParameter("username"); if(un==null) un="";
String p = request.getParameter("password"); if(p==null) p="";
String c = request.getParameter("confirm"); if(c==null) c="";
String n = request.getParameter("name"); if(n==null) n="";
String e = request.getParameter("email"); if(e==null) e="";
String ph = request.getParameter("phone"); if(ph==null) ph="";
String a = request.getParameter("address"); if(a==null) a="";
%>


<b>Sign Up</b>
<form action="MyController" method="post">
    <table border="0"> 
    <tr>
        <td>UserName:</td>
        <td><input type="text" name="username" value="<%=un%>" required></td>
    </tr>
    <tr> 
        <td>Password:</td>
        <td><input type="password" name="password" value="<%=p%>" requird></td>
    <tr> 
        <td>Confirm:</td>
        <td><input type="password" name="confirm" value="<%=c%>" requird></td>
    <tr> 
        <td>Name:</td>
        <td><input type="text" name="name" value="<%=n%>" requird></td>
    </tr>
    <tr> 
        <td>Email:</td>
        <td><input type="text" name="email" value="<%=e%>" requird></td>
    </tr>
    <tr> 
        <td>Phone:</td>
        <td><input type="text" name="phone" value="<%=ph%>" requird></td>
    </tr>
    <tr> 
        <td>address:</td>
        <td><input type="text" name="address" value="<%=a%>" requird></td>
    </tr>
        <td><a href="MyController?cmd=NotSignedIn">Cancel</a></td>
        <td><input type="submit" value="Sign Up"></td>
    <input type="hidden" name="cmd" value="SignedUp">
    </tr>
    </table>    
    
</form>