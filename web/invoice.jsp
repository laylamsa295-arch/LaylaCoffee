<%@page import="java.lang.String"%>
<%@ page import="com.itextpdf.text.*" %>
<%@ page import="com.itextpdf.text.pdf.*" %>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%@ page import="Model.*" %>

<%
User u = (User) session.getAttribute("u");
Cart cart = (Cart) session.getAttribute("cart");

int invid = 1;
double grandTotal = 0;

for(Product p : cart.products){
    grandTotal += p.price * p.quantity;
}
%>

<%
    // ???? ??? ??? PDF
String folderPath = application.getRealPath("/") + "invoices/";
java.io.File folder = new java.io.File(folderPath);
if (!folder.exists()) {
    folder.mkdirs();
}

String pdfPath = folderPath + "invoice_" + invid + ".pdf";
%>

    <%
    Document document = new Document();
    PdfWriter.getInstance(document, new FileOutputStream(pdfPath));
    document.open();

    document.add(new Paragraph("Invoice ID: " + invid));
    document.add(new Paragraph("Customer: " + u.name + " (" + u.email + ")"));
    document.add(new Paragraph("Date: " + new java.util.Date()));
    document.add(new Paragraph(" "));

    PdfPTable tablePDF = new PdfPTable(5);
    tablePDF.addCell("Name");
    tablePDF.addCell("Company");
    tablePDF.addCell("Qty");
    tablePDF.addCell("Price");
    tablePDF.addCell("Subtotal");

    for(Product p : cart.products){
        double sub = p.price * p.quantity;
        tablePDF.addCell(p.name);
        tablePDF.addCell(p.company);
        tablePDF.addCell(String.valueOf(p.quantity));
        tablePDF.addCell("KD " + String.valueOf(p.price));
        tablePDF.addCell("KD " + String.valueOf(sub));
    }

    document.add(tablePDF);
    document.add(new Paragraph("Total: KD " + String.format("%.3f", grandTotal)));

    document.close();
%>

<!DOCTYPE html>
<html>
<head>
    <title>Invoice</title>
    <style>
        body {
            font-family: 'Segoe UI', sans-serif;
            margin: 40px;
            background-color: #f9f9f9;
        }
        .invoice {
            background: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 4px 10px rgba(0,0,0,0.1);
        }
        .invoice h2 {
            color: #2575fc;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 25px;
        }
        th, td {
            padding: 12px;
            border-bottom: 1px solid #ddd;
        }
        th {
            background-color: #f0f8ff;
            color: #2575fc;
        }
        .btn {
            display: inline-block;
            margin-top: 20px;
            padding: 10px 20px;
            background-color: #2575fc;
            color: white;
            text-decoration: none;
            border-radius: 4px;
        }
        .btn:hover {
            background-color: #1a5edb;
        }
    </style>
</head>
<body>
    <div class="invoice">
        <h2>Invoice</h2>
        <p><strong>Customer:</strong> <%= u.name %> (<%= u.email %>)</p>
        <p><strong>Date:</strong> <%= new java.util.Date() %></p>
        <p><strong>Invoice ID:</strong> <%= invid %></p>

        <table>
            <tr>
                <th>Image</th>
                <th>Name</th>
                <th>Company</th>
                <th>Quantity</th>
                <th>Price</th>
                <th>Subtotal</th>
            </tr>

            <%
                for (Product p : cart.products) {
                    double sub = p.price * p.quantity;
            %>

            <tr>
                <td><img src="images/products/<%= p.image %>" width="70" height="60"/></td>
                <td><%= p.name %></td>
                <td><%= p.company %></td>
                <td><%= p.quantity %></td>
                <td>KD <%= p.price %></td>
                <td>KD <%= sub %></td>
            </tr>

            <% } %>

            <tr>
                <th colspan="5" style="text-align:right;">Total</th>
                <th>KD <%= grandTotal %></th>
            </tr>
        </table>

        <center>
            <a class="btn" href="invoices/invoice_<%=invid%>.pdf" target="_blank">Download PDF</a>
            <a class="btn" href="jsp/catalog.jsp">Return to Catalog</a>
            <a class="btn" href="index.jsp">Go to Home</a>
        </center>
    </div>
</body>
</html>