package id.lariss.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.lariss.service.ChatService;
import id.lariss.service.WhatsAppService;
import id.lariss.service.dto.whatsapp.webhook.Message;
import id.lariss.service.dto.whatsapp.webhook.Request;
import jakarta.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class WhatsAppServiceImpl implements WhatsAppService {

    private static final Logger LOG = LoggerFactory.getLogger(WhatsAppServiceImpl.class);

    @Autowired
    private CamelContext camelContext;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProducerTemplate producerTemplate;

    @Autowired
    private ChatService chatService;

    @Value("${whatsapp.verify_token}")
    private String verifyToken;

    @Value("${whatsapp.api_url}")
    private String apiUrl;

    @Value("${whatsapp.access_token}")
    private String apiToken;

    @PostConstruct
    public void init() throws Exception {
        camelContext.addRoutes(
            new RouteBuilder() {
                @Override
                public void configure() {
                    JacksonDataFormat jacksonDataFormat = new JacksonDataFormat();
                    jacksonDataFormat.setPrettyPrint(true);

                    from("direct:sendWhatsAppMessage")
                        .setHeader("Authorization", constant("Bearer " + apiToken))
                        .setHeader("Content-Type", constant("application/json"))
                        .marshal(jacksonDataFormat)
                        .process(exchange -> {
                            LOG.debug("Sending JSON: {}", exchange.getIn().getBody(String.class));
                        })
                        .to(apiUrl)
                        .process(exchange -> {
                            LOG.debug("Response: {}", exchange.getIn().getBody(String.class));
                        });
                }
            }
        );
    }

    @Override
    public Mono<String> verifyWebhook(String mode, String token, String challenge) {
        return Mono.defer(() ->
            "subscribe".equals(mode) && verifyToken.equals(token) ? Mono.just(challenge) : Mono.just("Verification failed")
        );
    }

    @Override
    public Mono<Void> processIncomingMessage(String payload) {
        return Mono.fromRunnable(() -> {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                Request request = objectMapper.readValue(payload, Request.class);
                List<Message> messages = request.getEntries().get(0).getChanges().get(0).getValue().getMessages();
                if (!messages.isEmpty()) {
                    String receivedText = messages.get(0).getText().getBody();
                    String fromNumber = messages.get(0).getFrom();
                    chatService
                        .chat(fromNumber, receivedText)
                        .collectList()
                        .subscribe(strings -> sendWhatsAppMessage(fromNumber, String.join("", strings)).subscribe());
                }
            } catch (Exception e) {
                LOG.error("Error processing incoming payload: {} ", payload, e);
            }
        });
    }

    @Override
    public Mono<Void> sendWhatsAppMessage(String toNumber, String message) {
        return Mono.fromRunnable(() -> {
            Map<String, Object> body = new HashMap<>();
            body.put("messaging_product", "whatsapp");
            body.put("to", toNumber);
            body.put("type", "text");

            Map<String, String> text = new HashMap<>();
            text.put("body", message);
            body.put("text", text);

            producerTemplate.sendBody("direct:sendWhatsAppMessage", body);
        });
    }
}