import org.example.dao.UserDao;
import org.example.models.User;
import org.example.utils.ConfigurationFactoryUtil;
import org.hibernate.cfg.Configuration;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.MockedStatic;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.List;

import static org.mockito.Mockito.*;

public class UserDaoIntegrationTest {
    @Test
    public void testUserDao() {
        try (PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")) {
            postgres.withInitScript("init-script.sql");
            postgres.start();

            Configuration cfg = new Configuration().configure();
            cfg.setProperty("hibernate.connection.driver_class", postgres.getDriverClassName());
            cfg.setProperty("hibernate.connection.url", postgres.getJdbcUrl());
            cfg.setProperty("hibernate.connection.username", postgres.getUsername());
            cfg.setProperty("hibernate.connection.password", postgres.getPassword());

            try(MockedStatic<ConfigurationFactoryUtil> cfgUtil = mockStatic(ConfigurationFactoryUtil.class)){
                cfgUtil.when(ConfigurationFactoryUtil::getConfiguration).thenReturn(cfg);

                UserDao userDao = new UserDao();
                List<User> users = userDao.find();

                Assertions.assertEquals(1, users.size());
                User user = users.get(0);
                Assertions.assertEquals("testUser", user.getName());
                Assertions.assertEquals("test@mail.com", user.getEmail());
                Assertions.assertEquals(30, user.getAge());
            }

            postgres.stop();
        }
    }
}
