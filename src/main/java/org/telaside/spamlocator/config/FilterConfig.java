package org.telaside.spamlocator.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telaside.spamlocator.filter.SpamLocatorDuplicateFilter;
import org.telaside.spamlocator.filter.SpamLocatorValidFilter;

@Configuration
public class FilterConfig {
	
	@Bean
	public SpamLocatorValidFilter spamLocatorValidFilter() {
		return new SpamLocatorValidFilter();
	}
	
	@Bean
	public SpamLocatorDuplicateFilter spamLocatorDuplicateFilter() {
		return new SpamLocatorDuplicateFilter();
	}
}
