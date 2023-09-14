package com.samuel.spring.jwt;

import javax.xml.bind.DatatypeConverter;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import io.jsonwebtoken.impl.Base64Codec;

public class Encoding {

	public static void main(String[] args) {
		System.out.println(DatatypeConverter.printBase64Binary("issuer".getBytes()));
		System.out.println(new Base64Codec().encode("username".getBytes()));
		System.out.println(new BCryptPasswordEncoder().encode("password"));


	}

}
