package com.michael.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD})
@Documented
public @interface PetField {

	String name() default "";
	String[] value() default {};
	String version() default "1.0";
	boolean serialize() default true;
    boolean deserialize() default true;
}
