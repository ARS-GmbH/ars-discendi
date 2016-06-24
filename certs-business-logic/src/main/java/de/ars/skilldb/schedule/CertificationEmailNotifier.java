/**
 *
 */
package de.ars.skilldb.schedule;

import java.io.IOException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.io.FileUtils;
import org.springframework.core.io.Resource;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import de.ars.skilldb.domain.AccomplishedCertification;
import de.ars.skilldb.domain.Certification;
import de.ars.skilldb.returnobjects.ExpirationNotification;
import de.ars.skilldb.returnobjects.UpdateResponse;
import de.ars.skilldb.util.Ensure;

/**
 * Sendet das Ergebnis des Zertifizierungs-Updates per Email an die Administratoren/Interessenten.
 */
public class CertificationEmailNotifier implements UpdateSubscriber, ExpirationSubscriber {

    private static final String SENDER_ADDRESS = "Zertifizierungsdatenbank <maxi-goetz@gmx.de>";
    private final MailSender mailSender;
    private final List<String> mailSubscribers;

    private Resource emailTemplate;

    /**
     * Erzeugt einen neuen {@code CertificationUpdateEmailNotifier} mit dem zu nutzenden
     * {@code MailSender}.
     *
     * @param mailSender
     *            der zu nutzende {@code MailSender}.
     */
    public CertificationEmailNotifier(final MailSender mailSender) {
        this.mailSender = mailSender;
        mailSubscribers = new ArrayList<>();
    }

    public void setEmailTemplate(final Resource emailTemplate) {
        this.emailTemplate = emailTemplate;
    }

    @Override
    public void recieveResponse(final UpdateResponse response) {
        if (response.getUpdated().isEmpty()) {
            StringBuffer messageBody = new StringBuffer(128);
            messageBody.append("Folgende Zertifizierungen wurden aktualisiert: \n\n");
            for (Certification certification : response.getUpdated()) {
                messageBody.append(certification.getName()).append(", Status: ")
                        .append(certification.getStatus().getName()).append(System.lineSeparator());
            }
            String message = finalizeMessage(messageBody);
            sendEmail("Neue und aktualisierte Zertifizierungen", message,
                    mailSubscribers.toArray(new String[mailSubscribers.size()]));
        }
    }

    @Override
    public void receiveNotification(final ExpirationNotification... notifications) {
        for (ExpirationNotification notification : notifications) {
            if (!notification.getAccomplished().isEmpty()) {
                StringBuffer messageBody = new StringBuffer(128);
                messageBody.append("Folgende Zertifizierungen laufen in Kürze aus: \n\n");
                for (AccomplishedCertification accomplished : notification.getAccomplished()) {
                    Ensure.that(accomplished.getCertification()).isNotNull();
                    Ensure.that(accomplished.getCertification().getExpirationDate());
                    messageBody
                            .append(accomplished.getCertification().getName())
                            .append(", läuft ab am: ")
                            .append(new SimpleDateFormat("dd.MM.yyyy", Locale.GERMAN).format(accomplished
                                    .getCertification().getExpirationDate())).append(System.lineSeparator());
                }
                String message = finalizeMessage(messageBody);
                sendEmail("Ablaufende Zertifizierungen", message, notification.getUser().getEmail());
            }
        }
    }

    private void sendEmail(final String subject, final String message, final String... recipients) {
        SimpleMailMessage template = new SimpleMailMessage();
        template.setSubject(subject);
        template.setText(message);
        template.setFrom(SENDER_ADDRESS);
        template.setReplyTo(SENDER_ADDRESS);

        for (String recipient : recipients) {
            SimpleMailMessage mail = new SimpleMailMessage(template);
            mail.setTo(recipient);
            mailSender.send(mail);
        }
    }

    private String finalizeMessage(final StringBuffer messageBody) {
        Ensure.that(messageBody).isNotNull();
        try {
            return String.format(FileUtils.readFileToString(emailTemplate.getFile(), Charset.forName("UTF-8")),
                    messageBody.toString());
        }
        catch (IOException e) {
            return messageBody.toString();
        }
    }

}
