package com.auth.springauthservice.dto;

public class LoginUserDto {
    public static final String HttpStatus = null;
	private String id;
    private String passwd;
    private String jwtToken;

    public LoginUserDto() {
    }

    public LoginUserDto(String id, String passwd) {
        this.id = id;
        this.passwd = passwd;
    }


    public LoginUserDto(String id, String passwd, String jwtToken) {
        this.id = id;
        this.passwd = passwd;
        this.jwtToken = jwtToken;
    }


    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPasswd() {
        return this.passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getJwtToken() {
        return this.jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }


}