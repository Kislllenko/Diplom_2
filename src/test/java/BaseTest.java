import pojo.CreateUserJson;
import requests.UserRequests;
import requests.UserResponse;
import org.junit.After;
import org.junit.Before;

public class BaseTest {

    final static String[] INGREDIENTS = {"61c0c5a71d1f82001bdaaa6d","61c0c5a71d1f82001bdaaa6f"};
    final static String BURGER_NAME = "Бессмертный флюоресцентный бургер";
    CreateUserJson createUser = new CreateUserJson("kislenko-s@yandex.ru", "1Qwe%", "Kislenko");
    CreateUserJson updateUser = new CreateUserJson("kislenkoSY@yandex.ru", "Qe%13", "KislenkoS");
    CreateUserJson wrongUser = new CreateUserJson("kislenko-s@yandex.ru", "1Qwe%22", "KislenkoS");

    @Before
    public void setUp() { UserRequests.create(createUser); }

    @After
    public void tearDown() {

        String userAccessToken = UserRequests.login(createUser).then().extract().response().as(UserResponse.class).getToken();

        UserRequests.delete(userAccessToken);
    }
}
