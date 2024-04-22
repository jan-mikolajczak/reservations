package com.jmiko.reservations.constant;

public class JWTUtil {

    public static final long EXPIRE_ACCESS_TOKEN = 60L*24*60*60000;
    public static final long EXPIRE_REFRESH_TOKEN = 90L*24*60*60000;
    public static final String BEARER_PREFIX= "Bearer ";
    public static final String ISSUER = "noReservationsApp";
    public static final String SECRET ="vhtFbgYRva";
    public static final String AUTH_HEADER = "Authorization";
}
