package servlet;

import model.*;
import service.PaymentService;
import service.UserService;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet(name = "PaymentServlet", value = "/payment/*")
public class PaymentServlet extends HttpServlet {
    private PaymentService paymentService;
    private UserService userService;

    @Override
    public void init() throws ServletException {
        super.init();
        this.userService = new UserService();
        this.paymentService = new PaymentService(userService);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getPathInfo();

        switch (action) {
            case "/process":
                showPaymentForm(request, response);
                break;
            case "/history":
                showPaymentHistory(request, response);
                break;
            case "/invoice":
                generateInvoice(request, response);
                break;
            case "/admin":
                showAdminDashboard(request, response);
                break;
            case "/edit":
                showEditForm(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getPathInfo();

        switch (action) {
            case "/process":
                processPayment(request, response);
                break;
            case "/update":
                updatePayment(request, response);
                break;
            case "/delete":
                deletePayment(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void showPaymentForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user instanceof Student) {
            double dueAmount = paymentService.getTotalDueByStudent(user.getId());
            request.setAttribute("dueAmount", dueAmount);
            request.getRequestDispatcher("/views/payment/payment-form.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/error/403.jsp");
        }
    }

    private void showPaymentHistory(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user instanceof Student) {
            List<Payment> payments = paymentService.getPaymentsByStudent(user.getId());
            double totalPaid = paymentService.getTotalPaidByStudent(user.getId());
            double totalDue = paymentService.getTotalDueByStudent(user.getId());

            request.setAttribute("payments", payments);
            request.setAttribute("totalPaid", totalPaid);
            request.setAttribute("totalDue", totalDue);
            request.getRequestDispatcher("/views/payment/history.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/error/403.jsp");
        }
    }

    private void generateInvoice(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String paymentId = request.getParameter("id");
        Payment payment = paymentService.getPaymentById(paymentId);

        if (payment != null) {
            String invoice;
            String invoiceType = request.getParameter("type");

            if ("corporate".equals(invoiceType)) {
                invoice = paymentService.generateCorporateInvoice(payment);
            } else {
                invoice = paymentService.generateInvoice(payment);
            }

            request.setAttribute("invoice", invoice);
            request.setAttribute("invoiceNumber", payment.getInvoiceNumber());
            request.getRequestDispatcher("/views/payment/invoice.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/payment/history?error=Payment not found");
        }
    }

    private void showAdminDashboard(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Payment> pendingPayments = paymentService.getPendingPayments();
        List<Payment> allPayments = paymentService.getAllPayments();

        double totalRevenue = allPayments.stream()
                .filter(p -> p.getStatus().equals("Completed"))
                .mapToDouble(Payment::getAmount)
                .sum();

        request.setAttribute("pendingPayments", pendingPayments);
        request.setAttribute("allPayments", allPayments);
        request.setAttribute("totalRevenue", totalRevenue);
        request.getRequestDispatcher("/views/payment/admin-dashboard.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String paymentId = request.getParameter("id");
        Payment payment = paymentService.getPaymentById(paymentId);

        if (payment != null) {
            request.setAttribute("payment", payment);
            request.getRequestDispatcher("/views/payment/edit-payment.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/payment/admin?error=Payment not found");
        }
    }

    private void processPayment(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Student student = (Student) session.getAttribute("user");

        String paymentMethod = request.getParameter("paymentMethod");
        double amount = Double.parseDouble(request.getParameter("amount"));
        String description = request.getParameter("description");

        Payment payment;
        switch (paymentMethod) {
            case "card":
                payment = new CardPayment();
                ((CardPayment)payment).setCardNumber(request.getParameter("cardNumber"));
                ((CardPayment)payment).setCardHolder(request.getParameter("cardHolder"));
                ((CardPayment)payment).setExpiryDate(request.getParameter("expiryDate"));
                ((CardPayment)payment).setCvv(request.getParameter("cvv"));
                break;
            case "bank":
                payment = new BankTransferPayment();
                ((BankTransferPayment)payment).setAccountNumber(request.getParameter("accountNumber"));
                ((BankTransferPayment)payment).setBankName(request.getParameter("bankName"));
                ((BankTransferPayment)payment).setReference(request.getParameter("reference"));
                break;
            default:
                payment = new CashPayment();
                break;
        }

        payment.setPaymentId(generatePaymentId(paymentMethod));
        payment.setStudentId(student.getId());
        payment.setAmount(amount);
        payment.setDescription(description);
        payment.setStatus("Completed");

        if (paymentService.processPayment(payment)) {
            response.sendRedirect(request.getContextPath() + "/payment/history?success=Payment processed");
        } else {
            response.sendRedirect(request.getContextPath() + "/payment/process?error=Payment failed");
        }
    }

    private void updatePayment(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String paymentId = request.getParameter("id");
        String status = request.getParameter("status");

        if (paymentService.updatePaymentStatus(paymentId, status)) {
            response.sendRedirect(request.getContextPath() + "/payment/admin?success=Payment updated");
        } else {
            response.sendRedirect(request.getContextPath() + "/payment/edit?id=" + paymentId + "&error=Update failed");
        }
    }

    private void deletePayment(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String paymentId = request.getParameter("id");

        if (paymentService.deletePayment(paymentId)) {
            response.sendRedirect(request.getContextPath() + "/payment/admin?success=Payment deleted");
        } else {
            response.sendRedirect(request.getContextPath() + "/payment/admin?error=Deletion failed");
        }
    }

    private String generatePaymentId(String paymentMethod) {
        return "PAY-" + paymentMethod.toUpperCase().charAt(0) + "-" + System.currentTimeMillis();
    }
}
