package io.oilfox.backend.api.shared.email;

import com.google.inject.Inject;
import com.microtripit.mandrillapp.lutung.MandrillApi;
import com.microtripit.mandrillapp.lutung.model.MandrillApiError;
import com.microtripit.mandrillapp.lutung.view.MandrillMessage;
import com.microtripit.mandrillapp.lutung.view.MandrillMessageStatus;
import com.microtripit.mandrillapp.lutung.view.MandrillUserInfo;
import io.oilfox.backend.api.shared.properties.CommonProperties;
import io.oilfox.backend.api.shared.properties.EmailProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by ipusic on 1/26/16.
 */
public class EmailManager {
    private EmailProperties emailProperties;
    private CommonProperties commonProperties;
    private static Logger logger = LoggerFactory.getLogger(EmailManager.class);
    private MandrillApi mandrillApi;

    @Inject
    public EmailManager(EmailProperties emailProperties, CommonProperties commonProperties) {
        this.emailProperties = emailProperties;
        this.commonProperties = commonProperties;
        mandrillApi = new MandrillApi(emailProperties.getAccessToken());
    }

    public String whoAmI() throws IOException, MandrillApiError {
        MandrillUserInfo user = mandrillApi.users().info();

        return user.getUsername();
    }

    /**
     * Will send email using mandrill api
     * @param subject Subject to put, or NULL or defined by template
     * @param from From address, or NULL if defined by template
     * @param to To address
     * @param template Template to use, should be already registered on mandrill account
     * @param vars Key/Value pairs if any
     * @return True -> Email sent, False -> Error
     * @throws IOException
     * @throws MandrillApiError
     */
    public boolean sendEmail(String subject, String from, String to, String template, Map<String, String> vars) throws IOException, MandrillApiError {
        MandrillMessage message = new MandrillMessage();
        message.setSubject(subject);
        message.setAutoText(true);
        message.setFromEmail(from);
        message.setMergeLanguage("handlebars");

        logger.debug("Setting up email recipients...");
        ArrayList<MandrillMessage.Recipient> recipients = new ArrayList<>();
        MandrillMessage.Recipient recipient = new MandrillMessage.Recipient();
        recipient.setEmail(to);
        recipients.add(recipient);

        message.setTo(recipients);
        message.setPreserveRecipients(true);

        logger.debug("setting up email merge values...");
        // provide key/value variables
        List<MandrillMessage.MergeVar> mergeValues = new ArrayList<>();

        if (vars != null) {
            // common variables
            vars.put("serverurl", commonProperties.getServerUrl());

            for (Map.Entry<String, String> entry: vars.entrySet()) {
                mergeValues.add(new MandrillMessage.MergeVar(entry.getKey(), entry.getValue()));
            }
        }

        message.setMerge(true);
        message.setGlobalMergeVars(mergeValues);

        try {
            if (emailProperties.getSendEmail()) {
                logger.debug("Will send email...");
                MandrillMessageStatus[] messageStatusReports = mandrillApi.messages().sendTemplate(template, null, message, false);
                boolean allOk = true;

                for (int i = 0; i < messageStatusReports.length; i++) {
                    logger.debug("email status: " + messageStatusReports[i].getStatus());
                    logger.debug("email status: " + messageStatusReports[i].getRejectReason());
                    logger.debug("email status: " + messageStatusReports[i].getEmail());
                    allOk = allOk && Objects.equals(messageStatusReports[i].getStatus(), "sent");
                }

                return allOk;
            } else {
                logger.info("Skipping email sending...");
                return true;
            }
        } catch (MandrillApiError e) {
            logger.error("Error while sending email. ", e);
            e.printStackTrace();
            return false;
        }
    }
}
