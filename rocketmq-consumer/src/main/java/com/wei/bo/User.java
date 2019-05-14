package com.wei.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * 发送用户消息
 */
@Data
public class User implements Serializable {
    private String loginName;
    private String pwd;
}