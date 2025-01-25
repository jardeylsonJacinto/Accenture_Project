package acc.br.email_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import acc.br.email_service.model.Email;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendSimpleEmail(Email email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email.getPara());
        message.setSubject(email.getTitulo());
        message.setText(email.getConteudo());
        message.setFrom("projeto.accenture.contato@gmail.com");
        // Email remetente
        mailSender.send(message);
    }
}
