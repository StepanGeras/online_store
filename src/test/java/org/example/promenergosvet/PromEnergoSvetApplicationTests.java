package org.example.promenergosvet;

import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.TestPropertySource;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
@TestPropertySource(locations="classpath:application.properties")
class PromEnergoSvetApplicationTests {

	@Autowired
	private JavaMailSender javaMailSender;

	@Test
	void testSendEmail() {
		assertDoesNotThrow(() -> {
			MimeMessage message = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);

			helper.setFrom("m.disai@mail.ru");
			helper.setTo("testpes95@mail.ru");
			helper.setSubject("Тестовая тема");
			helper.setText("Тестовое сообщение");

			javaMailSender.send(message);
		});
	}

}
