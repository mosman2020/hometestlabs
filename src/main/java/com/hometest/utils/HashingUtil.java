package com.hometest.utils;

import java.security.MessageDigest;

import org.springframework.stereotype.Service;

@Service
public class HashingUtil {

	//HASHING PASSWORD TO SHA-256
		public static String sha256(String password) {
			try {
				MessageDigest digest = MessageDigest.getInstance("SHA-256");
				byte[] hash = digest.digest(password.getBytes("UTF-8"));
				StringBuffer hexString = new StringBuffer();

				for (int i = 0; i < hash.length; i++) {
					String hex = Integer.toHexString(0xff & hash[i]);
					if (hex.length() == 1)
						hexString.append('0');
					hexString.append(hex);
				}

				return hexString.toString();
			} catch (Exception ex) {
				throw new RuntimeException(ex);
			}
		}
}
