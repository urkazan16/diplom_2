package site.nomoreparties.stellarburgers.user;

public class UserAuthorizationFields {
    private String email;
    private String password;

    public UserAuthorizationFields(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public static UserAuthorizationFields from(UserRegistrationFields userRegistrationFields) {
        return new UserAuthorizationFields(userRegistrationFields.getEmail(), userRegistrationFields.getPassword());
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return " User { email: '" + email + "' Password: '" + password + "'}";
    }
}
