package com.example.demo.validator.annotation;

import com.example.demo.constant.ValidatorEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.example.demo.constant.DemoResponseConst.ReturnCode;
import static com.example.demo.constant.DemoResponseConst.ReturnCode.E9998;
import static com.example.demo.constant.ValidatorEnum.NONE;
//自訂的annotation
//SOURCE：注解将被编译器丢弃
//CLASS：注解在class文件中可用，但会被VM丢弃
//RUNTIME：VM将在运行期间保留注解，因此可以通过反射机制读取注解的信息
@Retention(RetentionPolicy.RUNTIME)
//該註解可以註解的元素範圍   
//FIELD：域声明（包括enum实例）
//LOCAL_VARIABLE：局部变量声明
//METHOD：方法声明
//PACKAGE：包声明
//PARAMETER：参数声明
//TYPE：类、接口（包括注解类型）或enum声明
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE})
public @interface Validator {

    /**
     * 定義正則表示式來檢核資料格式
     */
//	ElementType.FIELD
    String pattern() default "";
    /**
     * 定義檢核錯誤時回傳的錯誤代碼
     */
//	ElementType.FIELD
    ReturnCode returnCode() default E9998;
    /**
     * 是否允許null
     */
    boolean notNull() default true;
    /**
     * 定義複雜的檢核邏輯
     */
    ValidatorEnum validator() default NONE;
    /**
     * 若欄位 為集合，或多層物件，則設true
     */
    boolean recursive() default false;
}
