import org.example.dao.UserDao;
import org.example.models.User;
import org.example.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedConstruction;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceUnitTest {
    private List<User> users;

    @Before
    public void init() {
        Random rand = new Random();
        this.users = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            this.users.add(new User("name" + rand.nextInt(),"email" + rand.nextInt(), rand.nextInt()));
        }
    }

    @Test
    public void test()  {
        try(MockedConstruction<UserDao> mocked = mockConstruction(UserDao.class, (mock, ctx) ->
            when(mock.find()).thenReturn(users)
        ))
        {
            UserService userService = new UserService();
            Assertions.assertEquals(users, userService.find());
        }
    }
}
