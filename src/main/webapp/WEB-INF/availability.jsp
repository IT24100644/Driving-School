<%@ page import="model.Instructor" %>
<%@ page import="java.time.DayOfWeek" %>
<%@ page import="model.AvailabilitySlot" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Update Availability - Driving School</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .availability-row { margin-bottom: 15px; }
        .remove-btn { margin-top: 32px; }
    </style>
</head>
<body>
<%@ include file="/header.jsp" %>
<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-10">
            <div class="card">
                <div class="card-header">
                    <h4 class="text-center">Update Availability for <%= ((Instructor)request.getAttribute("instructor")).getName() %></h4>
                </div>
                <div class="card-body">
                    <% if (request.getParameter("error") != null) { %>
                    <div class="alert alert-danger">${param.error}</div>
                    <% } %>

                    <form action="${pageContext.request.contextPath}/instructor/availability" method="post">
                        <input type="hidden" name="id" value="<%= ((Instructor)request.getAttribute("instructor")).getId() %>">

                        <div id="availability-container">
                            <!-- Existing availability slots -->
                            <% for (AvailabilitySlot slot : ((Instructor)request.getAttribute("instructor")).getAvailability()) { %>
                            <div class="row availability-row">
                                <div class="col-md-3">
                                    <label class="form-label">Day</label>
                                    <select class="form-select" name="day" required>
                                        <% for (DayOfWeek day : DayOfWeek.values()) { %>
                                        <option value="<%= day %>" <%= day == slot.getDayOfWeek() ? "selected" : "" %>>
                                            <%= day %>
                                        </option>
                                        <% } %>
                                    </select>
                                </div>
                                <div class="col-md-3">
                                    <label class="form-label">Start Time</label>
                                    <input type="time" class="form-control" name="startTime"
                                           value="<%= slot.getStartTime() %>" required>
                                </div>
                                <div class="col-md-3">
                                    <label class="form-label">End Time</label>
                                    <input type="time" class="form-control" name="endTime"
                                           value="<%= slot.getEndTime() %>" required>
                                </div>
                                <div class="col-md-3 remove-btn">
                                    <button type="button" class="btn btn-danger remove-availability">Remove</button>
                                </div>
                            </div>
                            <% } %>

                            <!-- Empty row for new entries -->
                            <div class="row availability-row">
                                <div class="col-md-3">
                                    <label class="form-label">Day</label>
                                    <select class="form-select" name="day">
                                        <option value="">Select Day</option>
                                        <% for (DayOfWeek day : DayOfWeek.values()) { %>
                                        <option value="<%= day %>"><%= day %></option>
                                        <% } %>
                                    </select>
                                </div>
                                <div class="col-md-3">
                                    <label class="form-label">Start Time</label>
                                    <input type="time" class="form-control" name="startTime">
                                </div>
                                <div class="col-md-3">
                                    <label class="form-label">End Time</label>
                                    <input type="time" class="form-control" name="endTime">
                                </div>
                                <div class="col-md-3 remove-btn">
                                    <button type="button" class="btn btn-danger remove-availability">Remove</button>
                                </div>
                            </div>
                        </div>

                        <div class="mt-3">
                            <button type="button" id="add-availability" class="btn btn-secondary">Add More</button>
                        </div>

                        <div class="d-grid mt-4">
                            <button type="submit" class="btn btn-primary">Save Availability</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    document.addEventListener('DOMContentLoaded', function() {
        // Add new availability row
        document.getElementById('add-availability').addEventListener('click', function() {
            const container = document.getElementById('availability-container');
            const newRow = document.createElement('div');
            newRow.className = 'row availability-row';
            newRow.innerHTML = `
                <div class="col-md-3">
                    <label class="form-label">Day</label>
                    <select class="form-select" name="day" required>
                        <option value="">Select Day</option>
                        <% for (DayOfWeek day : DayOfWeek.values()) { %>
                        <option value="<%= day %>"><%= day %></option>
                        <% } %>
                    </select>
                </div>
                <div class="col-md-3">
                    <label class="form-label">Start Time</label>
                    <input type="time" class="form-control" name="startTime" required>
                </div>
                <div class="col-md-3">
                    <label class="form-label">End Time</label>
                    <input type="time" class="form-control" name="endTime" required>
                </div>
                <div class="col-md-3 remove-btn">
                    <button type="button" class="btn btn-danger remove-availability">Remove</button>
                </div>
            `;
            container.appendChild(newRow);
        });

        // Remove availability row
        document.addEventListener('click', function(e) {
            if (e.target && e.target.classList.contains('remove-availability')) {
                const row = e.target.closest('.availability-row');
                if (document.querySelectorAll('.availability-row').length > 1) {
                    row.remove();
                } else {
                    // Reset the only row instead of removing it
                    const selects = row.querySelectorAll('select');
                    const inputs = row.querySelectorAll('input');
                    selects.forEach(select => select.value = '');
                    inputs.forEach(input => input.value = '');
                }
            }
        });
    });
</script>
<%@ include file="/footer.jsp" %>
</body>
</html>