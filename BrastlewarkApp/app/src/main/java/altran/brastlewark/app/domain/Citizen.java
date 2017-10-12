package altran.brastlewark.app.domain;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

/**
 * Created by nicolas on 10/5/17.
 * "id": 0,
 * "name": "Tobus Quickwhistle",
 * "thumbnail": "http://www.publicdomainpictures.net/pictures/10000/nahled/thinking-monkey-11282237747K8xB.jpg",
 * "age": 306,
 * "weight": 39.065952,
 * "height": 107.75835,
 * "hair_color": "Pink",
 * "professions": [
 * "Metalworker",
 * "Woodcarver",
 * "Stonecarver",
 * " Tinker",
 * "Tailor",
 * "Potter"
 * ],
 * "friends": [
 * "Cogwitz Chillwidget",
 * "Tinadette Chillbuster"
 * ]
 */

@Parcel
public class Citizen {

    private long id;

    private String name;

    private String thumbnail;

    private int age;

    private double weight;

    private double height;

    @SerializedName("professions")
    private List<String> professionList;

    @SerializedName("friends")
    private List<String> friendList;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public List<String> getProfessionList() {
        return professionList;
    }

    public void setProfessionList(List<String> professionList) {
        this.professionList = professionList;
    }

    public List<String> getFriendList() {
        return friendList;
    }

    public void setFriendList(List<String> friendList) {
        this.friendList = friendList;
    }
}
