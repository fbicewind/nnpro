package cn.nnnight.security;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

public class MyPasswordEncode implements PasswordEncoder {

	private static final String SITE_WIDE_SECRET = "private-secret-key";
	private static final PasswordEncoder encoder = new StandardPasswordEncoder(SITE_WIDE_SECRET);

	public String encode(CharSequence arg0) {
		return encoder.encode(arg0);
	}

	public boolean matches(CharSequence arg0, String arg1) {
		return encoder.matches(arg0, arg1);
	}

	public static void main(String[] args) {
		MyPasswordEncode aa = new MyPasswordEncode();
		System.out.println(encoder.encode("test"));
		System.out.println(aa.matches("test", encoder.encode("test")));
	}

}
