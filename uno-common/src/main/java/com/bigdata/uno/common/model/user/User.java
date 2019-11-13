package com.bigdata.uno.common.model.user;

import com.bigdata.uno.common.model.base.BaseEntity;
import com.bigdata.uno.common.model.base.Column;
import lombok.*;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity<User, Long> {
    @Column(name = "name")
    private String name;

    @Column(name = "chineseName")
    private String chineseName;

    @Column(name = "email")
    private String email;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "password")
    private String password;

    @Column(name = "avatar")
    private String avatar;
}
