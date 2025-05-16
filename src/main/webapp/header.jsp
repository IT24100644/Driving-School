<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>--%>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
  <div class="container">
    <a class="navbar-brand" href="${pageContext.request.contextPath}">SafeWay Driving School</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNav">
      <ul class="navbar-nav me-auto">
        <li class="nav-item">
          <a class="nav-link" href="userList.jsp">Users</a>
        </li>
      </ul>
      <ul class="navbar-nav">
        <% if (session.getAttribute("user") != null) { %>
        <li class="nav-item">
          <a class="nav-link" href="${pageContext.request.contextPath}/user/profile">Profile</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="${pageContext.request.contextPath}/logout">Logout</a>
        </li>
        <% } else { %>
        <li class="nav-item">
          <a class="nav-link" href="login.jsp">Login</a>
        </li>

        <% } %>
      </ul>
    </div>
  </div>
</nav>