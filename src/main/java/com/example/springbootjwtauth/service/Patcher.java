package com.example.springbootjwtauth.service;

import com.example.springbootjwtauth.payload.request.UserRequest;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Component
public class Patcher {
    public void userPatcher(UserRequest existingIntern, UserRequest incompleteIntern) throws IllegalAccessException {

        //GET THE COMPILED VERSION OF THE CLASS
        Class<?> userClass= UserRequest.class;
        Field[] userFields=userClass.getDeclaredFields();
        System.out.println(userFields.length);
        for(Field field : userFields){
            System.out.println(field.getName());
            //CANT ACCESS IF THE FIELD IS PRIVATE
            field.setAccessible(true);

            //CHECK IF THE VALUE OF THE FIELD IS NOT NULL, IF NOT UPDATE EXISTING INTERN
            Object value=field.get(incompleteIntern);
            if(value!=null){
                field.set(existingIntern,value);
            }
            //MAKE THE FIELD PRIVATE AGAIN
            field.setAccessible(false);
        }

    }

}
