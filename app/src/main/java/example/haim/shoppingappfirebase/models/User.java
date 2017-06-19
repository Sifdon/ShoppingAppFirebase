package example.haim.shoppingappfirebase.models;

import android.net.Uri;

import com.google.firebase.auth.FirebaseUser;

/**
 * Creates a User Object
 */

// Firebase Objects - POJO
//1) must have an empty constructor to any Firebase Object.
//2) Getters and Setters for all the properties!

public class User {
    private String displayName;
    private String email;
    private String photoUrl;


    //Full Constructor
    public User(FirebaseUser user) {
        this.displayName = user.getDisplayName();
        this.email = user.getEmail();

        //verify that user has Profile photo - if null set default user photo
        Uri firePhoto = user.getPhotoUrl();
        if (firePhoto == null){
            photoUrl = "https://firebasestorage.googleapis.com/v0/b/fir-database-3e08b.appspot.com/o/user-default.png?alt=media&token=aff7e73f-2034-4191-8d86-0b357d68d62f";
        }else{
            photoUrl = firePhoto.toString();
        }

    }



    //Empty Constructors:
    public User(){}

    //Getters and Setters:


    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    @Override
    public String toString() {
        return "User{" +
                "displayName='" + displayName + '\'' +
                ", email='" + email + '\'' +
                ", photoUrl='" + photoUrl + '\'' +
                '}';
    }
}
