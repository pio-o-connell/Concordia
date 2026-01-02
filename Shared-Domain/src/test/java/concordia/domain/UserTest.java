package concordia.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    @Test
    public void testUserFields() {
        Company company = new Company();
        User user = new User(1, 1, "username", "password");
        user.setCompany(company);
        assertEquals(1, user.getUserId());
        assertEquals("username", user.getUsername());
        assertEquals("password", user.getPassword());
        assertEquals(company, user.getCompany());
    }
}
