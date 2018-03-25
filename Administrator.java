package Project1.FixtureGenerator;

public class Administrator {
    private String username;
    private String password;
    private int adminID;

    public int getAdminID() {
        return adminID;
    }

    public void setAdminID(int adminID) {
        this.adminID = adminID;
    }

    public Administrator(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setName(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean check(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    public String getCSV() {
        return username + "," + password;
    }
}