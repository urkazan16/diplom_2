package site.nomoreparties.stellarburgers.constants;

import com.github.javafaker.Faker;
import site.nomoreparties.stellarburgers.user.UserRegistrationFields;

public class RandomTestUser {

    public static UserRegistrationFields getRandomRegistration() {
        Faker faker = new Faker();
        String email = faker.internet().emailAddress();
        String password = faker.internet().password(8, 16);
        String name = faker.name().username();
        return new UserRegistrationFields(email, password, name);
    }

}
