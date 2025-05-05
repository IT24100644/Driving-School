<%@ page import="model.User" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>User Management - Driving School</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<%@ include file="../fragments/header.jsp" %>
<div class="container mt-5">
  <div class="d-flex justify-content-between align-items-center mb-4">
    <h2>User Management</h2>
    <a href="${pageContext.request.contextPath}/user/register" class="btn btn-success">Add New User</a>
  </div>

  <% if (request.getParameter("success") != null) { %>
  <div class="alert alert-success">${param.success}</div>
  <% } %>
  <% if (request.getParameter("error") != null) { %>
  <div class="alert alert-danger">${param.error}</div>
  <% } %>

  <div class="card">
    <div class="card-body">
      <table class="table table-striped">
        <thead>
        <tr>
          <th>ID</th>
          <th>Name</th>
          <th>Email</th>
          <th>Role</th>
          <th>Phone</th>
          <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <% List<User> users = (List<User>) request.getAttribute("users"); %>
        <% for (User user : users) { %>
        <tr>
          <td><%= user.getId() %></td>
          <td><%= user.getName() %></td>
          <td><%= user.getEmail() %></td>
          <td><%= user.getRole() %></td>
          <td><%= user.getPhone() != null ? user.getPhone() : "N/A" %></td>
          <td>
            <a href="${pageContext.request.contextPath}/user/edit?id=<%= user.getId() %>"
               class="btn btn-sm btn-warning">Edit</a>
            <form action="${pageContext.request.contextPath}/user/delete" method="post"
                  style="display: inline;">
              <input type="hidden" name="id" value="<%= user.getId() %>">
              <button type="submit" class="btn btn-sm btn-danger"
                      onclick="return confirm('Are you sure?')">Delete</button>
            </form>
          </td>
        </tr>
        <% } %>
        </tbody>
      </table>
    </div>
  </div>
</div>
<%@ include file="../fragments/footer.jsp" %>
</body>
</html>