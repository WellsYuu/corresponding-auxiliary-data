package com.bit.api.core;

/**
 * Created by Tommy on 2017/10/26.
 */
public interface TokenService {

    public Token createToken();

    public Token getToken(String token);
}
