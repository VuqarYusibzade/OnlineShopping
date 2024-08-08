package com.example.onlineshopping.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data

public class Confirmation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "token_id")
    private Long tokenId;


    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    private String confirmationToken;

}
