package backend.dto;
import java.util.List;

public class CompanyDto {
    public int companyId;
    public String companyTitle;
    public String companyName;
    // public List<ItemDto> items; // Removed: ItemDto no longer exists. Add new fields if needed for serviceType/servicePricing.
    public List<UserDto> users;
}