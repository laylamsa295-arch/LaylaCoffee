<b>Sign In:</b>
<form action="MyController" method="post">
    <table border="0"> 
    <tr>
        <td>UserName:</td>
        <td><input type="text" name="username" required></td>
    </tr>
    <tr> 
        <td>Password:</td>
        <td><input type="password" name="password" required></td>
    <tr> 
    <tr>
        <td><a href="MyController?cmd=NotSignedIn">Cancel</a></td>
        <td><input type="submit" value="Sign In"></td>
        <input type="hidden" name="cmd" value="SignedIn">
    </tr>
    </table>       
</form>