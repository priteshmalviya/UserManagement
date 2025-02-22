package com.pritesh.usermanagement.model;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.json.JSONArray;
import com.pritesh.usermanagement.converter.JpaConverterForJSONArray;
import com.pritesh.usermanagement.utils.Constants;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = Constants.USER_STRING)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = Constants.ID_STRING)
    private Long id;

    @Column(name = Constants.NAME_STRING)
    private String name;

    @Column(name = Constants.USER_NAME_STRING, unique = true)
    private String userName;

    @Column(name = Constants.EMAIL_STRING, unique = true)
    private String email;

    @Column(name = Constants.PASSWORD_STRING)
    private String password;

    @Column(name = Constants.ROLES_STRING)
    @Convert(converter = JpaConverterForJSONArray.class)
    private JSONArray roles;

    @Column(name = Constants.PERMISSIONS_STRING)
    @Convert(converter = JpaConverterForJSONArray.class)
    private JSONArray permissions = new JSONArray();
}
