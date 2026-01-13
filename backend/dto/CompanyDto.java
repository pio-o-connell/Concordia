package backend.dto;
import java.util.List;

public class CompanyDto {
    public int companyId;
    public String companyTitle;
    public String companyName;
    public java.util.List<ServiceDto> services;
    public List<UserDto> users;
}