package org.lacassandra.smooshyfaces.entity;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class SmooshyFacesSession extends UserSession {
    public final static String COOKIE_NAME = "SMOOSHYID";
}
