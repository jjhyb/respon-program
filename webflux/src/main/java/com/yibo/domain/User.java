package com.yibo.domain;

import com.yibo.annotation.NameVerification;
import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;

/**
 * @author: huangyibo
 * @Date: 2019/10/29 16:26
 * @Description:
 */

@Document(collection = "user")
@Data
public class User {

    @Id
    private String id;

    @NotBlank(message = "姓名不能为空")
    @NameVerification(message = "姓名格式不合法")
    private String name;

    @Range(min=10,max=100,message = "年龄在10-100之间")
    private int age;
}
