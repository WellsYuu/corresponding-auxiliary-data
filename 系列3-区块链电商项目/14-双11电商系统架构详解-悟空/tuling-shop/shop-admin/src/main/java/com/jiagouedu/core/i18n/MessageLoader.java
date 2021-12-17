package com.jiagouedu.core.i18n;

import java.util.ResourceBundle;

/**
 * Created by dylan on 15-1-26.
 */
public class MessageLoader {
    private MessageLoader(){
        init();
    }
    private static MessageLoader instance = null;
    public static MessageLoader instance(){
        if(instance == null){
            instance = new MessageLoader();
        }
        return instance;
    }

    public String getMessage(String key){
        if(resourceBundle == null){
            return key;
        }
        return resourceBundle.getString(key);
    }

    private ResourceBundle resourceBundle;


    public synchronized void init(){
        if(resourceBundle == null){
            resourceBundle = ResourceBundle.getBundle("i18n/tlshop");
        }
    }
}
