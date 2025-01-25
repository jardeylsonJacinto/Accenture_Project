package acc.br.email_service.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import acc.br.email_service.dtos.EmailPayload;
import acc.br.email_service.model.Email;
import acc.br.email_service.service.EmailService;

@Component
public class EmailListener {
	
	@Autowired
	private EmailService emailService;

	    public void onMessageReceived(String message) throws JsonProcessingException {
	        ObjectMapper objectMapper = new ObjectMapper();

	        TypeReference<EmailPayload> mapType = new TypeReference<>() {};
	        EmailPayload payload = objectMapper.readValue(message, mapType);
	        
	        Email email = new Email();
	        email.setPara(payload.para());
	        email.setTitulo(payload.titulo());
	        email.setConteudo(payload.conteudo());
	        
	        emailService.sendSimpleEmail(email);

	        // TODO send an email using the data
	    }
	
}
