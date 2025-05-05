<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Add Instructor</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body class="container mt-4">
<h2>Add Instructor</h2>
<form action="AddInstructorServlet" method="post" class="mt-3">
    <div class="mb-3">
        <label>Name:</label>
        <input type="text" name="name" class="form-control" required />
    </div>
    <div class="mb-3">
        <label>Experience (years):</label>
        <input type="number" name="experience" class="form-control" required />
    </div>
    <div class="mb-3">
        <label>Email:</label>
        <input type="email" name="email" class="form-control" required />
    </div>
    <div class="mb-3">
        <label>Phone:</label>
        <input type="text" name="phone" class="form-control" required />
    </div>
    <button type="submit" class="btn btn-primary">Save Instructor</button>
</form>
</body>
</html>
