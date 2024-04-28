package com.proxyseller.twitter.model.user

import com.proxyseller.twitter.model.enumeration.Role
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Document(collection = "users")
class User implements UserDetails, Serializable {
    @Id
    private String id
    private String firstName
    private String lastName
    @Indexed(unique = true)
    private String username
    @Indexed(unique = true)
    private String email
    private String password
    private Integer age
    private Set<String> following = new HashSet<>()
    private Set<String> followers = new HashSet<>()
    private Role role = Role.ROLE_USER
    private boolean isDeleted = false

    @Override
    Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()))
    }

    @Override
    String getPassword() {
        return password
    }

    @Override
    String getUsername() {
        return username
    }

    @Override
    boolean isAccountNonExpired() {
        return true
    }

    @Override
    boolean isAccountNonLocked() {
        return true
    }

    @Override
    boolean isCredentialsNonExpired() {
        return true
    }

    @Override
    boolean isEnabled() {
        return !isDeleted
    }

    User() {
    }

    User(String id, String firstName, String lastName, String username, String email, String password, Integer age, Set<String> following, Set<String> followers, Role role, boolean isDeleted) {
        this.id = id
        this.firstName = firstName
        this.lastName = lastName
        this.username = username
        this.email = email
        this.password = password
        this.age = age
        this.following = following
        this.followers = followers
        this.role = role
        this.isDeleted = isDeleted
    }

    String getId() {
        return id
    }

    void setId(String id) {
        this.id = id
    }

    String getFirstName() {
        return firstName
    }

    void setFirstName(String firstName) {
        this.firstName = firstName
    }

    String getLastName() {
        return lastName
    }

    void setLastName(String lastName) {
        this.lastName = lastName
    }

    void setUsername(String username) {
        this.username = username
    }

    String getEmail() {
        return email
    }

    void setEmail(String email) {
        this.email = email
    }

    void setPassword(String password) {
        this.password = password
    }

    Integer getAge() {
        return age
    }

    void setAge(Integer age) {
        this.age = age
    }

    Set<String> getFollowing() {
        return following
    }

    void setFollowing(Set<String> following) {
        this.following = following
    }

    Set<String> getFollowers() {
        return followers
    }

    void setFollowers(Set<String> followers) {
        this.followers = followers
    }

    Role getRole() {
        return role
    }

    void setRole(Role role) {
        this.role = role
    }

    boolean getIsDeleted() {
        return isDeleted
    }

    void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted
    }

    // Method to follow a user
    void followUser(String id) {
        following.add(id)
    }

    // Method to unfollow a user
    void unfollowUser(String id) {
        following.remove(id)
    }

    // Method to add a follower
    void addFollower(String id) {
        followers.add(id)
    }

    // Method to remove a follower
    void removeFollower(String id) {
        followers.remove(id)
    }

    @Override
    String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", age=" + age +
                ", following=" + following +
                ", followers=" + followers +
                ", role=" + role +
                ", isDeleted=" + isDeleted +
                '}'
    }
}
