package datingAppDemo;

import lombok.Data;

import java.util.Date;


@Data
public class User implements Comparable<User> {

    private long id;
    private String name;
    private Date registrationDate;
    private String sex;


    public User(long id, String name, Date registrationDate, String sex) {
        this.id = id;
        this.name = name;
        this.registrationDate = registrationDate;
        this.sex = sex;
    }


    @Override
    public int compareTo(User o) {
        return this.getRegistrationDate().compareTo(o.registrationDate);
    }
}
