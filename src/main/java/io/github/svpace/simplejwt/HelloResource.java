package io.github.svpace.simplejwt;

import static java.util.stream.Collectors.joining;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
class HelloResource {

    @GetMapping("/hello")
    public String getHello(@AuthenticationPrincipal Authentication auth) {
	return String
	    .format(
		"Hello %s %s",
		auth.getName(),
		auth.getAuthorities().stream().map(it -> it.getAuthority()).collect(joining(" "))
	    );
    }

    @GetMapping("/hello-mighty")
    public String getHelloMighty(@AuthenticationPrincipal Authentication auth) {
	return String.format("Hello Mighty %s", auth.getName());
    }
}