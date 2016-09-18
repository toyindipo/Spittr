package spittr.config;


import java.util.Arrays;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;

@Configuration
@EnableJms
public class JmsConfig {
	
	@Bean
	public ActiveMQConnectionFactory createJmsConnectionFactory() {
		String brokerUrl = "tcp://localhost:61616";
		ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(brokerUrl);
		factory.setTrustedPackages(Arrays.asList("spittr.misc,java.util,java.lang,org.springframework.security.core.authority".split(",")));
		return factory;
	}
	
	@Bean
	//@Primary
	public Destination createActiveMQQueue() {
		return new ActiveMQQueue("spitter.alert.queue");
	}
	
	@Bean
	@Primary
	public Destination createActiveMQTopic() {
		return new ActiveMQTopic("spitter.alert.topic");
	}
	
	@Bean
	public MessageConverter createMessageConverter() {
		return new MappingJackson2MessageConverter();
	}
	
	@Bean 
	public JmsTemplate createJmsTemplate(ConnectionFactory connectionFactory, 
			Destination destination) {		
		JmsTemplate template = new JmsTemplate(connectionFactory);
		template.setDefaultDestination(destination);
		return template;
	}
	
	@Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(
    		ConnectionFactory connectionFactory, Destination destination) {
        DefaultJmsListenerContainerFactory factory =
                new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);        
        factory.setConcurrency("3-10");
        return factory;
    }

}
