package site.nomoreparties.stellarburgers.user;

public class UserTokenFields {
    private String token;

    public UserTokenFields(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "UserTokenFields{" +
                "token='" + token + '\'' +
                '}';
    }
}
