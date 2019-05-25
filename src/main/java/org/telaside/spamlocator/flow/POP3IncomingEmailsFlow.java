package org.telaside.spamlocator.flow;

import static org.telaside.spamlocator.domain.ChannelNames.INPUT_MIME_MESSAGES_CHANNEL;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.mail.MailReceiver;
import org.springframework.integration.mail.MailReceivingMessageSource;
import org.springframework.integration.mail.Pop3MailReceiver;
import org.telaside.spamlocator.transform.IntegrationMimeMessageToSpamLocator;


@Profile("pop3")
@Configuration("POP3IncomingEmailsFlowCOnf")
public class POP3IncomingEmailsFlow {
	
	private static final Logger LOG = LoggerFactory.getLogger(POP3IncomingEmailsFlow.class);
	
	@Value("${spamlocator.input.pop3.max-messages-per-poll:500}")
	private long maxMessagePerPop3Polling;
	
	@Bean
	public IntegrationMimeMessageToSpamLocator integrationMimeMessageToSpamLocator() {
		return new IntegrationMimeMessageToSpamLocator();
	}
	
	@Bean
	public IntegrationFlow pop3SourceIntegrationFlow() throws Exception {
		LOG.info("Starting an incoming pop3 email reader");
		
		MailReceiver receiver = new Pop3MailReceiver("mail.nfrance.com", "m.meyer@telaside.com", "ju4m86p2"); //"pop3://m.meyer@telaside.com:ju4m86p2@mail.nfrance.com/INBOX");
		MailReceivingMessageSource source = new MailReceivingMessageSource(receiver);
		
		return IntegrationFlows.from(source, e -> e.poller(Pollers.fixedDelay(5, TimeUnit.MINUTES).maxMessagesPerPoll(maxMessagePerPop3Polling)))
				.transform(integrationMimeMessageToSpamLocator())
				.channel(INPUT_MIME_MESSAGES_CHANNEL)
				.get();
	}
}
