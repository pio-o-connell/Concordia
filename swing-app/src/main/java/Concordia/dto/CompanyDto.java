package concordia.dto;

import java.util.List;
// import backend.dto.ServiceTypeDto;
// import backend.dto.ServicePricingDto;
import concordia.dto.UserDto;

public class CompanyDto {
    public int companyId;
    public String companyName;
    // public List<ServiceTypeDto> serviceTypes;
    // public List<ServicePricingDto> servicePricings;
    public List<UserDto> users;
}
