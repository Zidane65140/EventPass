<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<jsp:useBean id="MyEvent"
    class="de.hwg_lu.bwi420.beans.EventPassBean"
    scope="session"/>

<%
String action = request.getParameter("action");
String indexString = request.getParameter("index");

if ("entfernen".equals(action) && indexString != null) {

    int index = Integer.parseInt(indexString);

    MyEvent.warenkorbArtikelEntfernen(index);
}

response.sendRedirect("Warenkorb.jsp");


%>

</body>
</html>