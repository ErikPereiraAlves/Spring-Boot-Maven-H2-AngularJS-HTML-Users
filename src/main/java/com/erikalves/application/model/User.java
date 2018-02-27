package com.erikalves.application.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name="USER")
public class User implements Serializable {


    private static final long serialVersionUID = 1L;

    @Id
    @Column (name = "USER_ID")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long userId;

    @NotNull
    @Column(name = "USER_NAME", unique = true)
    private String userName;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + userId + '\'' +
                "name='" + userName + '\'' +

                '}';
    }
}
