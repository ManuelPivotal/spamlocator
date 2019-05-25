package org.telaside.spamlocator.flow;

import static org.telaside.spamlocator.domain.ChannelNames.INPUT_MIME_MESSAGES_CHANNEL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.telaside.spamlocator.domain.SpamLocatorMessage;
import org.telaside.spamlocator.filter.SpamLocatorDuplicateFilter;
import org.telaside.spamlocator.filter.SpamLocatorValidFilter;
import org.telaside.spamlocator.handler.NetworkInformationHandler;
import org.telaside.spamlocator.handler.SpamLocatorHandler;

@Configuration("IncomingMailMainFlowConf")
public class IncomingMailMainFlow {
	
	private static final Logger LOG = LoggerFactory.getLogger(IncomingMailMainFlow.class);
	
	@Autowired
	private NetworkInformationHandler networkInformationHandler;
	
	@Autowired
	private SpamLocatorHandler spamLocatorHandler;
	
	@Autowired
	private SpamLocatorValidFilter spamLocatorValidFilter;
	
	@Autowired
	private SpamLocatorDuplicateFilter spamLocatorDuplicateFilter;
	
	@Bean
	public IntegrationFlow mainMailIntegrationFlow() {
		LOG.info("Creating mainMailIntegrationFlow");
		return IntegrationFlows.from(INPUT_MIME_MESSAGES_CHANNEL)
				.<SpamLocatorMessage>filter(spamLocatorValidFilter)
				.<SpamLocatorMessage>filter(spamLocatorDuplicateFilter)
				.<SpamLocatorMessage>handle((m, h) -> networkInformationHandler.hostHops(m))
				.<SpamLocatorMessage>handle((m, h) -> spamLocatorHandler.save(m))
				.nullChannel();
	}
}
