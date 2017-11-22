package com.deltacom.dto;

public class CredentialsDTO {
    private String email;
    private String password;

    public CredentialsDTO() {
    }

    public CredentialsDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CredentialsDTO that = (CredentialsDTO) o;

        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        return password != null ? password.equals(that.password) : that.password == null;
    }

    @Override
    public int hashCode() {
        int result = email != null ? email.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CredentialsDTO{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
