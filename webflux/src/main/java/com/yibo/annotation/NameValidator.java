package com.yibo.annotation;

import com.yibo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: huangyibo
 * @Date: 2019/10/29 22:55
 * @Description:
 */
public class NameValidator implements ConstraintValidator<NameVerification,String> {

    @Autowired
    private UserRepository userRepository;

    private int value;

    private static final Pattern CHINESE_XINJIANG_PATTERN = Pattern.compile("^[\u4e00-\u9fa5.·\u36c3\u4DAE]{0,}$");

    @Override
    public void initialize(NameVerification constraintAnnotation) {
        //传入value 值，可以在校验中使用
        //理论上这里可以注入bean进行业务逻辑的校验
        this.value = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext constraintValidatorContext) {
        Matcher matcher = CHINESE_XINJIANG_PATTERN.matcher(name);
        return matcher.matches();
    }
}
