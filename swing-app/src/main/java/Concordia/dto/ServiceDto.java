/*
 * Obsolete DTO, file removed
 */

package concordia.dto;

import java.util.List;
import concordia.dto.HistoryDto;

public class ServiceDto {
    public int serviceId;
    public int companyId;
    public String serviceName;
    public String notes;
    public List<HistoryDto> transactionHistory;
}
