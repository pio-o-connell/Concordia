package concordia.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;

public class CompanyTest {
    @Test
    public void testGetItemsAsList() {
        Company company = new Company();
        Item item1 = new Item();
        Item item2 = new Item();
        company.addItem(item1);
        company.addItem(item2);
        ArrayList<Item> itemsList = company.getItemsAsList();
        assertEquals(2, itemsList.size());
        assertTrue(itemsList.contains(item1));
        assertTrue(itemsList.contains(item2));
    }

    @Test
    public void testGetUsersAsList() {
        Company company = new Company();
        User user1 = new User();
        User user2 = new User();
        company.getUsersAsList().add(user1);
        company.getUsersAsList().add(user2);
        ArrayList<User> usersList = company.getUsersAsList();
        // Since getUsersAsList returns a copy, adding to it doesn't affect company.users
        assertEquals(0, company.getUsersAsList().size());
    }
}
