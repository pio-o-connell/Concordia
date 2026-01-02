package concordia.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.Timestamp;

public class ItemTest {
    @Test
    public void testItemFields() {
        Company company = new Company();
        Timestamp date = Timestamp.valueOf("2026-01-01 00:00:00");
        Item item = new Item(1, company, 10, "ItemA", "LocationA", "Notes", date);
        assertEquals(1, item.getItemId());
        assertEquals(company, item.getCompany());
        assertEquals(10, item.getQuantity());
        assertEquals("ItemA", item.getItemName());
        assertEquals("LocationA", item.getLocation());
        assertEquals("Notes", item.getNotes());
        assertEquals(date, item.getDate());
    }
}
