package fk.page.finekorea.service;



import fk.page.finekorea.domain.entity.QuoteEntity;
import fk.page.finekorea.domain.repository.QuoteRepository;
import fk.page.finekorea.dto.QuoteDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class QuoteService {

    private QuoteRepository quoteRepository;
    private final JavaMailSender javaMailSender;
    private static final String senderEmail= "32144733@dankook.ac.kr";
    private static final String email= "hjungcwoo@naver.com";


    public Long savePost(QuoteDto quoteDto) {
        return quoteRepository.save(quoteDto.toEntity()).getId();
    }

    public Page<QuoteEntity> getQuotelist(Pageable pageable) {
        return quoteRepository.findAll(pageable);
    }

    public MimeMessage createMail(QuoteDto quoteDto) {
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            message.setFrom(senderEmail);
            message.setRecipients(MimeMessage.RecipientType.TO, email);
            message.setSubject("[홈페이지 문의]" + quoteDto.getName());  // 제목 설정
            String body = "";
            body += "<h3>" + "이름 : " + quoteDto.getName() + "</h3>";
            body += "<h3>" + "휴대폰 번호 : " + quoteDto.getPhone() + "</h3>";
            body += "<h3>" + "이메일 : " + quoteDto.getEmail() + "</h3>";
            if(!quoteDto.getContent().isEmpty()){
                body += "<h3>" + "내용 : " + quoteDto.getContent() + "</h3>";
            }
            if(!quoteDto.getAddress().isEmpty()){
                body += "<h3>" + "지역 : " + quoteDto.getAddress() + "</h3>";
            }
            if(!quoteDto.getBusinessName().isEmpty()){
                body += "<h3>" + "상호명 : " + quoteDto.getBusinessName() + "</h3>";
            }
            if(!quoteDto.getBusinessDay().isEmpty()){
                body += "<h3>" + "요청납품일 : " + quoteDto.getBusinessDay() + "</h3>";
            }
            message.setText(body, "UTF-8", "html");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return message;
    }

    public void sendEmail(QuoteDto quoteDto){
        MimeMessage message = createMail(quoteDto);
        javaMailSender.send(message);
    }
}
