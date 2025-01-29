package acc.br.email_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import acc.br.email_service.model.Email;
import acc.br.email_service.service.EmailService;
import io.swagger.v3.oas.annotations.Operation;

@RestController
public class EmailController {

    @Autowired
    private EmailService emailService;

    @Operation(summary = "Metodo manual de envio de demail (TESTE)")
    @PostMapping("/send-email")
    public String sendEmail(@RequestBody Email email) {
        try {
            emailService.sendSimpleEmail(email);
            return "E-mail enviado com sucesso!";
        } catch (Exception e) {
            return "Erro ao enviar e-mail: " + e.getMessage();
        }
    }
}