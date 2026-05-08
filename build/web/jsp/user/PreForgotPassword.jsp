<b>Forgot Password:</b>
<form action="MyController" method="post">
    <table border="0"> 
    <tr>
        <td>Name:</td>
        <td><input type="text" name="name" required></td>
    </tr>
    <tr> 
        <td>Email:</td>
        <td><input type="email" name="email" requird></td>
    <tr> 
        <td><a href="MyController?cmd=NotSignedIn">Cancel</a></td>
        <td><input type="submit" value="Submit"></td>
    <input type="hidden" name="cmd" value="ForgotPassword">
    </tr>
    </table>      
</form>
