package backend.controller;

import backend.dto.CompanyDto;
import backend.dto.TransactionHistoryDto;
import backend.dto.ServiceTypeDto;
import backend.dto.ServicePricingDto;
import backend.dto.UserDto;
import backend.infrastructure.JpaContext;
import backend.infrastructure.PersistenceFactory;
import backend.repository.UserRepository;
import backend.service.InventoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import concordia.domain.Company;
// import concordia.domain.Item; // Removed: Item is obsolete
import concordia.domain.User;
import concordia.domain.ServiceType;
import concordia.domain.ServicePricing;
import concordia.domain.TransactionHistory;
import jakarta.persistence.EntityManagerFactory;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@WebServlet("/api/concordia")
public class ConcordiaServlet extends HttpServlet {
    private transient EntityManagerFactory entityManagerFactory;
    private ObjectMapper mapper;

    @Override
    public void init() {
        mapper = new ObjectMapper();
        entityManagerFactory = PersistenceFactory.create();
    }

    @Override
    public void destroy() {
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String type = normalizeType(req.getParameter("type"));
        if (type == null) {
            respondBadRequest(resp, "Missing type parameter");
            return;
        }
        resp.setContentType("application/json");
        try (JpaContext context = JpaContext.open(entityManagerFactory)) {
            InventoryService inventory = context.inventoryService();
            UserRepository users = context.userRepository();
            switch (type) {
                case "serviceType":
                    writeJson(resp, mapServiceTypes(inventory.getAllServiceTypes()));
                    break;
                case "servicePricing":
                    writeJson(resp, mapServicePricings(inventory.getAllServicePricings()));
                    break;
                case "transactionHistory":
                    writeJson(resp, mapTransactionHistories(inventory.getAllTransactionHistories()));
                    break;
                case "company":
                    writeJson(resp, mapCompanies(inventory.getAllCompanies()));
                    break;
                case "user":
                    writeJson(resp, mapUsers(users.getAllUsers()));
                    break;
                default:
                    respondBadRequest(resp, "GET not supported for type: " + type);
            }
        } catch (Exception ex) {
            respondServerError(resp, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String type = normalizeType(req.getParameter("type"));
        if (type == null) {
            respondBadRequest(resp, "Missing type parameter");
            return;
        }
        try (JpaContext context = JpaContext.open(entityManagerFactory)) {
            InventoryService inventory = context.inventoryService();
            UserRepository users = context.userRepository();
            switch (type) {
                case "serviceType": {
                    ServiceTypeDto dto = mapper.readValue(req.getInputStream(), ServiceTypeDto.class);
                    ServiceType serviceType = new ServiceType();
                    serviceType.setTypeName(dto.typeName);
                    inventory.addServiceType(serviceType);
                    resp.setStatus(HttpServletResponse.SC_CREATED);
                    resp.getWriter().write("ServiceType created");
                    break;
                }
                case "servicePricing": {
                    ServicePricingDto dto = mapper.readValue(req.getInputStream(), ServicePricingDto.class);
                    ServicePricing servicePricing = new ServicePricing();
                    servicePricing.setServiceTypeId(dto.serviceTypeId);
                    servicePricing.setPrice(dto.price);
                    servicePricing.setCurrency(dto.currency);
                    inventory.addServicePricing(servicePricing);
                    resp.setStatus(HttpServletResponse.SC_CREATED);
                    resp.getWriter().write("ServicePricing created");
                    break;
                }
                case "transactionHistory": {
                    TransactionHistoryDto dto = mapper.readValue(req.getInputStream(), TransactionHistoryDto.class);
                    TransactionHistory th = new TransactionHistory();
                    th.setServiceTypeId(dto.serviceTypeId);
                    th.setAmount(dto.amount);
                    th.setLocation(dto.location);
                    th.setProvider(dto.provider);
                    th.setDeliveryDate(dto.deliveryDate);
                    th.setNotes(dto.notes);
                    inventory.addTransactionHistory(th);
                    resp.setStatus(HttpServletResponse.SC_CREATED);
                    resp.getWriter().write("TransactionHistory created");
                    break;
                }
                case "company": {
                    CompanyDto dto = mapper.readValue(req.getInputStream(), CompanyDto.class);
                    context.companyRepository().insertCompany(dto.companyId, dto.companyName);
                    resp.setStatus(HttpServletResponse.SC_CREATED);
                    resp.getWriter().write("Company created");
                    break;
                }
                case "user": {
                    UserDto dto = mapper.readValue(req.getInputStream(), UserDto.class);
                    users.insertUser(dto.userId, dto.companyId, dto.username, "");
                    resp.setStatus(HttpServletResponse.SC_CREATED);
                    resp.getWriter().write("User created");
                    break;
                }
                default:
                    respondBadRequest(resp, "POST not supported for type: " + type);
            }
        } catch (Exception ex) {
            respondServerError(resp, ex);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String type = normalizeType(req.getParameter("type"));
        if (type == null) {
            respondBadRequest(resp, "Missing type parameter");
            return;
        }
        try (JpaContext context = JpaContext.open(entityManagerFactory)) {
            InventoryService inventory = context.inventoryService();
            UserRepository users = context.userRepository();
            switch (type) {
                // Removed obsolete item/history update cases. Add serviceType/servicePricing/transactionHistory update logic as needed.
                case "company": {
                    CompanyDto dto = mapper.readValue(req.getInputStream(), CompanyDto.class);
                    Company company = new Company(dto.companyId, "", dto.companyName, new HashSet<>(), new HashSet<>());
                    context.companyRepository().updateCompany(company);
                    resp.getWriter().write("Company updated");
                    break;
                }
                case "user": {
                    UserDto dto = mapper.readValue(req.getInputStream(), UserDto.class);
                    User user = new User(dto.userId, dto.companyId, dto.username, "");
                    users.updateUser(user);
                    resp.getWriter().write("User updated");
                    break;
                }
                default:
                    respondBadRequest(resp, "PUT not supported for type: " + type);
                    return;
            }
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception ex) {
            respondServerError(resp, ex);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String type = normalizeType(req.getParameter("type"));
        if (type == null) {
            respondBadRequest(resp, "Missing type parameter");
            return;
        }
        String idParam = req.getParameter("id");
        if (idParam == null) {
            respondBadRequest(resp, "Missing id parameter");
            return;
        }
        int id = Integer.parseInt(idParam);
        try (JpaContext context = JpaContext.open(entityManagerFactory)) {
            InventoryService inventory = context.inventoryService();
            UserRepository users = context.userRepository();
            switch (type) {
                // Removed obsolete item/history delete cases. Add serviceType/servicePricing/transactionHistory delete logic as needed.
                case "company":
                    context.companyRepository().deleteCompany(id);
                    resp.getWriter().write("Company deleted");
                    break;
                case "user":
                    users.deleteUser(id);
                    resp.getWriter().write("User deleted");
                    break;
                default:
                    respondBadRequest(resp, "DELETE not supported for type: " + type);
                    return;
            }
            resp.setStatus(HttpServletResponse.SC_OK);
        } catch (Exception ex) {
            respondServerError(resp, ex);
        }
    }

    private List<CompanyDto> mapCompanies(Collection<Company> companies) {
        List<CompanyDto> result = new ArrayList<>();
        if (companies == null) {
            return result;
        }
        for (Company company : companies) {
            CompanyDto dto = new CompanyDto();
            dto.companyId = company.getCompanyId();
            dto.companyName = company.getCompanyName();
            // Add mapping for serviceTypes/servicePricings if needed
            dto.users = mapUsers(company.getUsers());
            result.add(dto);
        }
        return result;
    }

    private List<ServiceTypeDto> mapServiceTypes(Collection<ServiceType> serviceTypes) {
        List<ServiceTypeDto> result = new ArrayList<>();
        if (serviceTypes == null) {
            return result;
        }
        for (ServiceType service : serviceTypes) {
            ServiceTypeDto dto = new ServiceTypeDto();
            dto.serviceTypeId = service.getServiceTypeId();
            dto.typeName = service.getTypeName();
            result.add(dto);
        }
        return result;
    }

    private List<ServicePricingDto> mapServicePricings(Collection<ServicePricing> pricings) {
        List<ServicePricingDto> result = new ArrayList<>();
        if (pricings == null) {
            return result;
        }
        for (ServicePricing pricing : pricings) {
            ServicePricingDto dto = new ServicePricingDto();
            dto.servicePricingId = pricing.getServicePricingId();
            dto.serviceTypeId = pricing.getServiceTypeId();
            dto.price = pricing.getPrice();
            dto.currency = pricing.getCurrency();
            result.add(dto);
        }
        return result;
    }

    private List<TransactionHistoryDto> mapTransactionHistories(Collection<TransactionHistory> histories) {
        List<TransactionHistoryDto> result = new ArrayList<>();
        if (histories == null) {
            return result;
        }
        for (TransactionHistory record : histories) {
            TransactionHistoryDto dto = new TransactionHistoryDto();
            dto.transactionId = record.getTransactionId();
            dto.serviceTypeId = record.getServiceTypeId();
            dto.amount = record.getAmount();
            dto.location = record.getLocation();
            dto.provider = record.getProvider();
            dto.deliveryDate = record.getDeliveryDate();
            dto.notes = record.getNotes();
            result.add(dto);
        }
        return result;
    }

    private List<UserDto> mapUsers(Collection<User> users) {
        List<UserDto> result = new ArrayList<>();
        if (users == null) {
            return result;
        }
        for (User user : users) {
            UserDto dto = new UserDto();
            dto.userId = user.getUserId();
            dto.companyId = user.getCompanyId();
            dto.username = user.getUsername();
            result.add(dto);
        }
        return result;
    }

    private void writeJson(HttpServletResponse resp, Object payload) throws IOException {
        resp.getWriter().write(mapper.writeValueAsString(payload));
    }

    private void respondBadRequest(HttpServletResponse resp, String message) throws IOException {
        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        resp.getWriter().write(message);
    }

    private void respondServerError(HttpServletResponse resp, Exception ex) throws IOException {
        resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        resp.getWriter().write("Error: " + ex.getMessage());
    }

    private String normalizeType(String type) {
        return type == null ? null : type.trim().toLowerCase();
    }
}
