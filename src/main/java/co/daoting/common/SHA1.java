package co.daoting.common;

import java.security.MessageDigest;

/**
 * @author wangyf
 * @date 2019年05月17日 13:29
 **/
public class SHA1 {

    public static byte[] encode(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
            messageDigest.update(bytes);
            byte[] digest = messageDigest.digest();
            return digest;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
