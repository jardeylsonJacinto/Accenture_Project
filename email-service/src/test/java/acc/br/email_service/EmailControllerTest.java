package acc.br.email_service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import acc.br.email_service.controller.EmailController;
import acc.br.email_service.model.Email;
import acc.br.email_service.service.EmailService;

@ExtendWith(MockitoExtension.class)
public class EmailControllerTest {

    @Mock
    private EmailService emailService;

    @InjectMocks
    private EmailController emailController;

    private Email email;

    @BeforeEach
    public void setup() {
        email = new Email();
        email.setPara("exemplo@dominio.com");
        email.setTitulo("Assunto de Teste");
        email.setConteudo("Conteúdo do e-mail de teste");
        
    }

    @Test
    public void testSendEmail_Success() {
        doNothing().when(emailService).sendSimpleEmail(email);
        
        String response = emailController.sendEmail(email);
        
        assertEquals("E-mail enviado com sucesso!", response);
        verify(emailService, times(1)).sendSimpleEmail(email);
    }

    @Test
    public void testSendEmail_Failure() {
        doThrow(new RuntimeException("Erro ao enviar e-mail"))
                .when(emailService).sendSimpleEmail(email);
        
        String response = emailController.sendEmail(email);
        
        assertTrue(response.contains("Erro ao enviar e-mail"));
        verify(emailService, times(1)).sendSimpleEmail(email);
    }
}
