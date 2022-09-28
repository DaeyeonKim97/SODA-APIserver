package com.soda.apiserver.user.model.entity;

import javax.persistence.*;

@Entity
@Table(name="TBL_ROLE")
public class Role {
    @Id
    @GeneratedValue
    @Column(name = "ROLE_ID")
    private int id; // 인덱스
    @Column(name = "ROLE_NAME", unique = true, nullable = false)
    private String roleName; //권한명
    @Column(name = "ROLE_DESC", nullable = true)
    private String roleDesc; //권한설명

    public Role() {
    }

    public Role(int id, String roleName, String roleDesc) {
        this.id = id;
        this.roleName = roleName;
        this.roleDesc = roleDesc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", roleName='" + roleName + '\'' +
                ", roleDesc='" + roleDesc + '\'' +
                '}';
    }
}
