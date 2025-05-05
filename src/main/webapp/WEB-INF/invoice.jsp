<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Invoice ${invoiceNumber} - Driving School</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f8f9fa;
        }
        .invoice-box {
            max-width: 800px;
            margin: auto;
            padding: 30px;
            border: 1px solid #eee;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.15);
            font-size: 16px;
            line-height: 24px;
            font-family: 'Helvetica Neue', 'Helvetica', Arial, sans-serif;
            color: #555;
            background-color: white;
        }
        .invoice-box table {
            width: 100%;
            line-height: inherit;
            text-align: left;
        }
        .invoice-box table td {
            padding: 5px;
            vertical-align: top;
        }
        .invoice-box table tr.top table td {
            padding-bottom: 20px;
        }
        .invoice-box table tr.heading td {
            background: #eee;
            border-bottom: 1px solid #ddd;
            font-weight: bold;
        }
        .invoice-box table tr.details td {
            padding-bottom: 20px;
        }
        .invoice-box table tr.item td {
            border-bottom: 1px solid #eee;
        }
        .invoice-box table tr.total td {
            border-top: 2px solid #eee;
            font-weight: bold;
        }
    </style>
</head>
<body>
<div class="container mt-5 mb-5">
    <div class="invoice-box">
        <pre>${invoice}</pre>
    </div>

    <div class="text-center mt-4">
        <button onclick="window.print()" class="btn btn-primary me-2">
            <i class="bi bi-printer-fill"></i> Print Invoice
        </button>
        <a href="${pageContext.request.contextPath}/payment/invoice?id=${param.id}&type=corporate"
           class="btn btn-secondary me-2">Corporate Invoice</a>
        <a href="${pageContext.request.contextPath}/payment/history" class="btn btn-outline-secondary">Back to History</a>
    </div>
</div>
<%@ include file="/footer.jsp" %>
</body>
</html>