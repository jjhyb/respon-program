package com.yibo.domain;

import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

/**
 * @author: huangyibo
 * @Date: 2019/10/29 16:26
 * @Description:
 */

@Document(collection = "user")
@Data
@Validated
public class User {

    @Id
    private String id;

    @NotBlank(message = "姓名不能为空")
    private String name;

    @Range(min=10,max=100,message = "年龄在10-100之间")
    private int age;
}
