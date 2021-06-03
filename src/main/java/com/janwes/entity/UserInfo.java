package com.janwes.entity;

import lombok.Data;

/**
 * @author Janwes
 * @version 1.0
 * @package com.janwes.entity
 * @date 2021/6/2 18:00
 * @description
 */
@Data
public class UserInfo {

    private String id;

    private String nickName;

    private String password;

    public UserInfo(String id, String nickName) {
        this.id = id;
        this.nickName = nickName;
    }
}
