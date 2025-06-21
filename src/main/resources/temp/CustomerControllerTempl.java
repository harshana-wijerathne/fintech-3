package site.wijerathne.harshana.fintech.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.zaxxer.hikari.HikariDataSource;
import site.wijerathne.harshana.fintech.dto.customer.CustomerDTO;
import site.wijerathne.harshana.fintech.exception.DataAccessException;
import site.wijerathne.harshana.fintech.model.User;
import site.wijerathne.harshana.fintech.repo.AuditLogRepo;
import site.wijerathne.harshana.fintech.repo.customer.CustomerRepo;
import site.wijerathne.harshana.fintech.service.customer.CustomerService;
import site.wijerathne.harshana.fintech.service.customer.CustomerServiceImpl;
import site.wijerathne.harshana.fintech.util.DtoValidation;
import site.wijerathne.harshana.fintech.util.SqlDateTypeAdapter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/admin/customers/*")
public class CustomerControllerTempl extends HttpServlet {
    private static final Logger logger = Logger.getLogger(CustomerController.class.getName());
    private CustomerService customerService;
    private Gson gson;

    @Override
    public void init() throws ServletException {
        try {
            this.gson = new GsonBuilder()
                    .registerTypeAdapter(Date.class, new SqlDateTypeAdapter())
                    .create();
            HikariDataSource dataSource = (HikariDataSource) getServletContext().getAttribute("DATA_SOURCE");
            CustomerRepo customerRepo = new CustomerRepo(dataSource);
            AuditLogRepo auditLogRepo = new AuditLogRepo(dataSource);
            this.customerService = new CustomerServiceImpl(customerRepo,auditLogRepo);
            logger.log(Level.INFO, "CustomerController initialized successfully");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Controller initialization failed", e);
            throw new ServletException("Controller initialization failed", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            String pathInfo = req.getPathInfo();
            String id = req.getParameter("id");
            String search = req.getParameter("search");

            if (pathInfo != null && pathInfo.length() > 1) {
                handleGetByPath(pathInfo, resp);
            } else if (id != null) {
                handleGetById(id, resp);
            } else if (search != null && !search.trim().isEmpty()) {
                handleSearch(search, resp);
            } else {
                handleGetAll(req, resp);
            }
        } catch (NumberFormatException e) {
            logger.log(Level.WARNING, "Invalid ID format", e);
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, "Invalid ID format");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Unexpected error", e);
            sendError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unexpected error");
        }
    }
    private void handleGetAll(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int page = 1;
        int pageSize = 8;

        try {
            if (req.getParameter("page") != null) {
                page = Integer.parseInt(req.getParameter("page"));
            }
            if (req.getParameter("pageSize") != null) {
                pageSize = Integer.parseInt(req.getParameter("pageSize"));
            }
        } catch (NumberFormatException e) {
            logger.log(Level.WARNING, "Invalid pagination parameters", e);
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, "Invalid pagination parameters");
            return;
        }

        try{
            List<CustomerDTO> customers = customerService.getAllCustomers(page, pageSize);
            resp.getWriter().write(gson.toJson(customers));
        }catch (Exception e){
            logger.log(Level.WARNING, "Error while getting all customers", e);
            throw new RuntimeException(e);
        }

    }
    private void handleGetByPath(String pathInfo, HttpServletResponse resp) throws IOException {
        try {
            String id = pathInfo.substring(1);
            CustomerDTO customer = customerService.getCustomerById(id);
            if (customer == null) {
                sendError(resp, HttpServletResponse.SC_NOT_FOUND, "Customer not found");
            } else {
                resp.getWriter().write(gson.toJson(customer));
            }
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Invalid path parameter format");
        }
    }
    private void handleGetById(String id, HttpServletResponse resp){
        CustomerDTO customer;
        try{
            customer = customerService.getCustomerById(id);
            if (customer == null) {
                sendError(resp, HttpServletResponse.SC_NOT_FOUND, "Customer not found");
            } else {
                resp.getWriter().write(gson.toJson(customer));
            }
        }catch (Exception e){
            logger.log(Level.WARNING, "Error while getting customer by id", e);
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }


    }
    private void handleSearch(String searchTerm, HttpServletResponse resp) throws IOException {
        try{
            if (searchTerm == null || searchTerm.trim().isEmpty()) {
                logger.log(Level.WARNING, "Empty search term provided");
                sendError(resp, HttpServletResponse.SC_BAD_REQUEST, "Search term cannot be empty");
                return;
            }

            List<CustomerDTO> customers = customerService.searchCustomers(searchTerm);
            if (customers.isEmpty()) {
                logger.log(Level.INFO, "No customers found for search term: " + searchTerm);
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().write(gson.toJson(Collections.emptyList()));
            } else {
                logger.log(Level.INFO, "Found " + customers.size() + " customers for search term: " + searchTerm);
                resp.getWriter().write(gson.toJson(customers));
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error during search for: " + searchTerm, e);
            sendError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error during search");
        }
    }



    private void sendError(HttpServletResponse resp, int statusCode, String message) throws IOException {
        resp.setStatus(statusCode);
        resp.getWriter().write(gson.toJson(Map.of("error", true, "message", message)));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        if (!"application/json".equalsIgnoreCase(req.getContentType())) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Content-Type must be application/json");
            return;
        }

        try{
            CustomerDTO customerDTO = gson.fromJson(req.getReader(), CustomerDTO.class);
            if (customerDTO.getFullName() == null || customerDTO.getFullName().trim().isEmpty() ||
                    customerDTO.getNicPassport() == null || customerDTO.getNicPassport().trim().isEmpty()) {
                sendError(resp, HttpServletResponse.SC_BAD_REQUEST,
                        "Full name and NIC/Passport are required");
                return;
            }

            DtoValidation.validate(customerDTO,resp);

            User user = (User) req.getSession().getAttribute("username");
            String actorUserId = user.getUserId();
            String ipAddress = req.getRemoteAddr();

            CustomerDTO savedCustomer = customerService.createCustomer(customerDTO, actorUserId, ipAddress);

            // Return the saved customer with generated ID
            resp.setStatus(HttpServletResponse.SC_CREATED);
            resp.getWriter().write(gson.toJson(savedCustomer));
            logger.log(Level.INFO, "Created new customer with ID: " + savedCustomer.getCustomerId());

        } catch (JsonSyntaxException e) {
            logger.log(Level.WARNING, "Invalid JSON format", e);
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, "Invalid customer data format");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error while saving customer", e);
            sendError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to save customer");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        try{
            String pathInfo = req.getPathInfo();
            if (pathInfo == null || pathInfo.length() <= 1) {
                sendError(resp, HttpServletResponse.SC_BAD_REQUEST, "Customer ID must be provided in the path");
                return;
            }

            String customerId = pathInfo.substring(1);

            User user = (User) req.getSession().getAttribute("username");
            String actorUserId = user.getUserId();
            String ipAddress = req.getRemoteAddr();

            // Delete the customer
            customerService.deleteCustomer(customerId, actorUserId, ipAddress);

            // Return success response
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write(gson.toJson(Map.of(
                    "success", true,
                    "message", "Customer deleted successfully"
            )));
            logger.log(Level.INFO, "Deleted customer with ID: " + customerId);

        } catch (IllegalArgumentException e) {
            logger.log(Level.WARNING, e.getMessage());
            sendError(resp, HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error deleting customer", e);
            sendError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to delete customer");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        if (!"application/json".equalsIgnoreCase(req.getContentType())) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Content-Type must be application/json");
            return;
        }
        try{
            CustomerDTO customerDTO = gson.fromJson(req.getReader(), CustomerDTO.class);

            if (customerDTO.getFullName() == null || customerDTO.getFullName().trim().isEmpty() ||
                    customerDTO.getNicPassport() == null || customerDTO.getNicPassport().trim().isEmpty()) {
                sendError(resp, HttpServletResponse.SC_BAD_REQUEST,
                        "Full name and NIC/Passport are required");
                return;
            }

            DtoValidation.validate(customerDTO,resp);


            User user = (User) req.getSession().getAttribute("username");
            String actorUserId = user.getUserId();
            String ipAddress = req.getRemoteAddr();


            CustomerDTO updatedCustomer = customerService.updateCustomer(customerDTO, actorUserId, ipAddress);

            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write(gson.toJson(updatedCustomer));
            logger.log(Level.INFO, "Updated customer with ID: " + updatedCustomer.getCustomerId());

        } catch (JsonSyntaxException e) {
            logger.log(Level.WARNING, "Invalid JSON format", e);
            sendError(resp, HttpServletResponse.SC_BAD_REQUEST, "Invalid customer data format");
        } catch (IllegalArgumentException e) {
            logger.log(Level.WARNING, e.getMessage());
            sendError(resp, HttpServletResponse.SC_NOT_FOUND, e.getMessage());
        }catch ( DataAccessException e){
            logger.log(Level.SEVERE, "Error updating customer", e);
            sendError(resp, HttpServletResponse.SC_NOT_FOUND, "ID Not Found");
        }catch (IOException e) {
            logger.log(Level.SEVERE, "Validation Error", e);
            sendError(resp, HttpServletResponse.SC_NOT_ACCEPTABLE, "ID Not Found");
        }
        catch (Exception e) {
            logger.log(Level.SEVERE, "Error updating customer", e);
            sendError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to update customer");
        }
    }

}