package hhplus.hhplusconcertreservation.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import hhplus.hhplusconcertreservation.common.filter.AuthFilter;
import hhplus.hhplusconcertreservation.common.filter.WaitingQueueFilter;
import jakarta.servlet.Filter;

@Configuration
public class WebConfig {
	@Value("${variables.authTokenSecretKey}")
	private String authTokenSecretKey;

	@Value("${variables.waitingTokenSecretKey}")
	private String waitingTokenSecretKey;

	@Bean
	public FilterRegistrationBean authFilter() {
		FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
		filterRegistrationBean.setFilter(new AuthFilter(authTokenSecretKey));
		filterRegistrationBean.setOrder(1);
		filterRegistrationBean.addUrlPatterns("/users/queue/*");
		filterRegistrationBean.addUrlPatterns("/concerts/*");
		filterRegistrationBean.addUrlPatterns("/points/*");

		return filterRegistrationBean;
	}

	@Bean
	public FilterRegistrationBean waitingQueueFilter() {
		FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
		filterRegistrationBean.setFilter(new WaitingQueueFilter(waitingTokenSecretKey));
		filterRegistrationBean.setOrder(2);
		filterRegistrationBean.addUrlPatterns("/concerts/waiting/*");

		return filterRegistrationBean;
	}
}
