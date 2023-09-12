package com.javainuse;

import io.jsonwebtoken.impl.Base64Codec;

public class Encoding {

	public static void main(String[] args) {
		javax.xml.bind.DatatypeConverter.printBase64Binary("javainuse".getBytes());
		System.out.println(new Base64Codec().encode("javainuse".getBytes()));

	}

}
