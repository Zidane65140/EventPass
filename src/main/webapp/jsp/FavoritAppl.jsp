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
    scope="session" />

<jsp:useBean id="myLogin"
    class="de.hwg_lu.bwi420.beans.LoginBean"
    scope="session" />

<%
String action = request.getParameter("action");
String eventIdString = request.getParameter("eventId");

if (!myLogin.isLoggedIn()) {
    response.sendRedirect("LoginView.jsp");
    return;
}

if (eventIdString != null) {

    int eventId = Integer.parseInt(eventIdString);

    if ("add".equals(action)) {

        if (!MyEvent.isFavorit(myLogin.getEmail(), eventId)) {
            MyEvent.insertFavorit(myLogin.getEmail(), eventId);
        }

    } else if ("delete".equals(action)) {

        MyEvent.deleteFavorit(myLogin.getEmail(), eventId);
    }
} 

response.sendRedirect("Favoriten.jsp");
%>
</body>
</html>