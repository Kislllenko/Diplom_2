package url;

public class Uri {

    public static final String BASE = "https://stellarburgers.nomoreparties.site/api";
    public static final String CREATE = BASE + "/auth/register"; // создание пользователя
    public static final String LOGIN = BASE + "/auth/login"; // авторизация пользователя
    public static final String DELETE = BASE + "/auth/user"; // удаление пользователя
    public static final String PATCH = BASE + "/auth/user"; // обновление данных о пользователи
    public static final String ORDER_PATH = BASE + "/orders/"; // создание заказа

}
