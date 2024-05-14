package com.aliatic.core.trm.config;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.security.Key;

public class Constants {

    private Constants(){

    }
	
	 // Spring Security
    public static final String LOGIN_URL = "/login";
    public static final String HEADER_AUTHORIZACION_KEY = "Authorization";
    public static final String TOKEN_BEARER_PREFIX = "Aliatic ";

    // JWT
    public static final String ISSUER_INFO = "https://www.aliatic.com.co";
    public static final String SUPER_SECRET_KEY = "6jE((_#OSTIX}y5$3WNry!laABO2+%eoi-Ejg.xaQ8XTb&KrFESB9][BJvkIr@#O9x3^tHg#dRqT^3T6UO&06kHTKB.Ay0N,@Hv";
    public static final long TOKEN_EXPIRATION_TIME = 864_000_000; // 10 day

    //MISC Seguridad
    public static final String AUTHORITIES = "authorities";
    public static final String ID_JWT = "aliaticJWT";

    public static final String PATH_CONTROLLERS = "com.aliatic.core.trm.controllers";
    public static final String FORMAT_YYYY_MM_DD = "yyyy-MM-dd";

    public static final int MAX_REGS_POR_PAGINA = 20;

    public static final String VACIO = "";
    public static final String PAGINA_CERO= "0";

    public static Key getSigningKeyB64(String secret) {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public static Key getSigningKey(String secret) {
		byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
		return Keys.hmacShaKeyFor(keyBytes);
	}


}
