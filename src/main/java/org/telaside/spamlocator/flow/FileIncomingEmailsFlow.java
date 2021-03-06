package org.telaside.spamlocator.flow;

import static org.telaside.spamlocator.domain.ChannelNames.INPUT_MIME_MESSAGES_CHANNEL;

import java.io.File;
import java.util.HashMap;
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
import org.springframework.integration.file.dsl.Files;
import org.springframework.integration.json.JsonToObjectTransformer;
import org.telaside.spamlocator.transform.MapToSpamLocator;

@Profile("file")
@Configuration("FileIncomingEmailsFlowConf")
public class FileIncomingEmailsFlow {
	
	private static final Logger LOG = LoggerFactory.getLogger(FileIncomingEmailsFlow.class);

	@Value("${spamlocator.input.files.inputdirectory:/tmp/spamlocator/}")
	private String emailFileDirectory;
	
	@Value("${spamlocator.input.files.maxmessagesperpoll:500}")
	private long maxFileIncomingMessages;
	
	@Bean
	public MapToSpamLocator mapToSpamLocator() {
		return new MapToSpamLocator();
	}
	
	@Bean
	public JsonToObjectTransformer jsonToMapTransformer() {
		return new JsonToObjectTransformer(HashMap.class);
	}
	
	@Bean
	public IntegrationFlow mainFileIntegrationFlow() {		
		File emailFileDirectoryFile = new File(emailFileDirectory);		
		LOG.info("Creating mainFileIntegrationFlow with directory {}", emailFileDirectory);		
		return IntegrationFlows.from(Files
					.inboundAdapter(emailFileDirectoryFile)
					.patternFilter("*.log")
					.preventDuplicates(true), e -> e.poller(Pollers.fixedDelay(500, TimeUnit.MILLISECONDS)
							.maxMessagesPerPoll(maxFileIncomingMessages))
				)
				.handle(Files.splitter(false, false))
				.transform(jsonToMapTransformer())
				.transform(mapToSpamLocator())
				.channel(INPUT_MIME_MESSAGES_CHANNEL)
				.get();
	}
}
