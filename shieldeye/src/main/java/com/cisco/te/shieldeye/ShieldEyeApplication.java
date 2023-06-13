package com.cisco.te.shieldeye;

import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ShieldEyeApplication {

	public static void main(String[] args) {
		System.out.println("spring boot arguments: " + Arrays.asList(args));
		if (args.length > 0) {
			SdavcSecurityService.SDAVC_SEVRVER_IP = args[0];
		}
		SpringApplication.run(ShieldEyeApplication.class, args);
	}

}
