package com.chathall.springchatserver.enums;

public enum ChatroomUserRole {
    OWNER("OWNER"),
    MODERATOR("MODERATOR"),
    USER("USER");
    private final String role;

    ChatroomUserRole(String role) {
        this.role = role;
    }


    @Override
    public String toString() {
        return this.role;
    }
}
