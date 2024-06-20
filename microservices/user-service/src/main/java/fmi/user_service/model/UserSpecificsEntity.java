package fmi.user_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity(name="UserSpecifics")
@Getter
@Setter
public class UserSpecificsEntity {
    @Id
    @GeneratedValue
    private Long userSpecificsId;
    private Date birthday;
    private String bio;
    private String preferences;
    private Date registrationDate;
    @OneToOne(mappedBy = "userSpecifics")
    private UserEntity user;

    public UserSpecificsEntity(Date birthday, String bio, String preferences) {
        this.birthday = birthday;
        this.bio = bio;
        this.preferences = preferences;
    }

    public UserSpecificsEntity() {

    }
}
