package example.haim.shoppingappfirebase.models;

/**
 * Shopping List
 */

//POJO
public class ShoppingList {
    private String title;
    private String owner; // Display Name - UID
    private String updateTime;
    private String profileImage;


    //Full Constructor


    public ShoppingList(String title, String owner, String updateTime, String profileImage) {
        this.title = title;
        this.owner = owner;
        this.updateTime = updateTime;
        this.profileImage = profileImage;
    }

    //Empty (Default POJO) Constructor
    public ShoppingList() {
    }

    //Getter and Setters
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getOwner() {
        return owner;
    }
    public void setOwner(String owner) {
        this.owner = owner;
    }
    public String getUpdateTime() {
        return updateTime;
    }
    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
    public String getProfileImage() {
        return profileImage;
    }
    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    //To String
    @Override
    public String toString() {
        return "ShoppingList{" +
                "title='" + title + '\'' +
                ", owner='" + owner + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", profileImage='" + profileImage + '\'' +
                '}';
    }
}
