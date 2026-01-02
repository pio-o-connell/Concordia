package concordia.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HistoryTest {
    @Test
    public void testHistoryFields() {
        history history = new history(1, 100, "Montreal", "ProviderA", "2026-01-01", "Notes");
        assertEquals(1, history.getHistoryId());
        assertEquals(100, history.getAmount());
        assertEquals("Montreal", history.getLocation());
        assertEquals("ProviderA", history.getProvider());
        assertEquals("2026-01-01", history.getDeliveryDate());
        assertEquals("Notes", history.getNotes());
    }
}
