package org.telaside.spamlocator.flow;

import static org.telaside.spamlocator.domain.ChannelNames.INPUT_MIME_MESSAGES_CHANNEL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.handler.LoggingHandler.Level;
import org.telaside.spamlocator.handler.NetworkInformationHandler;

@Configuration("IncomingMailMainFlowConf")
public class IncomingMailMainFlow {
	
	private static final Logger LOG = LoggerFactory.getLogger(IncomingMailMainFlow.class);
	
	@Autowired
	private NetworkInformationHandler networkInformationHandler;
	
	@Bean
	public IntegrationFlow mainMailIntegrationFlow() {
		LOG.info("Creating mainMailIntegrationFlow");
		return IntegrationFlows.from(INPUT_MIME_MESSAGES_CHANNEL)
				.handle(networkInformationHandler)
				//.log(Level.INFO, "IncomingSpamLocatorMessage")
				.nullChannel();
	}
}
