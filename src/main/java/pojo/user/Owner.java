package pojo.user;

public class Owner extends User {
    private String createdAt;
    private String updatedAt;

    public Owner() {
    }

    public Owner(String name, String email, String createdAt, String updatedAt) {
        super();
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}