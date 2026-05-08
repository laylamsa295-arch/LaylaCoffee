
<% if(u != null && !u.msg.equals("")) { %>
    <b style="color: red"><%= u.msg %></b>
    <br><br>
    <% u.msg = ""; %>
<% } %>

<%if(!u.msg.equals("")){%>
<b style="color: red"><%=u.msg%></b>
<br><br>
<%u.msg="";%>
<%}%>

<%
if(u == null || u.status == 0 || u.status == 3 || u.status == 7 || u.status == 9) {
%>
    <%@include file="user/NotSignedIn.jsp"%>
<%
} else if(u.status == 1) {
%>
    <%@include file="user/PreSignIn.jsp"%>
<%
} else if(u.status == 2 || u.status == 5) {
%>
    <%@include file="user/SignedIn.jsp"%>
<%
} else if(u.status == 4) {
%>
    <%@include file="user/PreChangePassword.jsp"%>
<%
} else if(u.status == 6) {
%>
    <%@include file="user/PreSignUp.jsp"%>
<%
} else if(u.status == 8) {
%>
    <%@include file="user/PreForgotPassword.jsp"%>
<%
}
%>

