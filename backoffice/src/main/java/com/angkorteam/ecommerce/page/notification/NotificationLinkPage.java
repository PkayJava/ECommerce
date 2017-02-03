package com.angkorteam.ecommerce.page.notification;

import com.angkorteam.framework.extension.wicket.markup.html.form.Button;
import com.angkorteam.framework.extension.wicket.markup.html.form.Form;
import com.angkorteam.framework.extension.wicket.markup.html.panel.TextFeedbackPanel;
import com.angkorteam.platform.Platform;
import com.angkorteam.platform.page.MBaaSPage;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequestWithBody;
import org.apache.wicket.markup.html.border.Border;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.PropertyModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.Map;

/**
 * Created by socheatkhauv on 26/1/17.
 */
public class NotificationLinkPage extends MBaaSPage {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationLinkPage.class);

    private String title;
    private TextField<String> titleField;
    private TextFeedbackPanel titleFeedback;

    private String message;
    private TextField<String> messageField;
    private TextFeedbackPanel messageFeedback;

    private String imageUrl;
    private TextField<String> imageUrlField;
    private TextFeedbackPanel imageUrlFeedback;

    private String link;
    private TextField<String> linkField;
    private TextFeedbackPanel linkFeedback;

    private Button sendButton;
    private Form<Void> form;

    @Override
    protected void doInitialize(Border layout) {
        add(layout);

        this.form = new Form<>("form");
        layout.add(this.form);

        this.titleField = new TextField<>("titleField", new PropertyModel<>(this, "title"));
        this.form.add(this.titleField);
        this.titleFeedback = new TextFeedbackPanel("titleFeedback", this.titleField);
        this.form.add(this.titleFeedback);

        this.messageField = new TextField<>("messageField", new PropertyModel<>(this, "message"));
        this.form.add(this.messageField);
        this.messageFeedback = new TextFeedbackPanel("messageFeedback", this.messageField);
        this.form.add(this.messageFeedback);

        this.imageUrlField = new TextField<>("imageUrlField", new PropertyModel<>(this, "imageUrl"));
        this.form.add(this.imageUrlField);
        this.imageUrlFeedback = new TextFeedbackPanel("imageUrlFeedback", this.imageUrlField);
        this.form.add(this.imageUrlFeedback);

        this.linkField = new TextField<>("linkField", new PropertyModel<>(this, "link"));
        this.form.add(this.linkField);
        this.linkFeedback = new TextFeedbackPanel("linkFeedback", this.linkField);
        this.form.add(this.linkFeedback);

        this.sendButton = new Button("sendButton");
        this.sendButton.setOnSubmit(this::sendButtonOnSubmit);
        this.form.add(this.sendButton);
    }

    private void sendButtonOnSubmit(Button button) {
        ThreadPoolTaskScheduler scheduler = Platform.getBean(ThreadPoolTaskScheduler.class);

        scheduler.execute(() -> {
            JdbcTemplate jdbcTemplate = Platform.getBean(JdbcTemplate.class);
            Gson gson = Platform.getBean("gson", Gson.class);

            Map<String, Object> googleCloudMessage = Maps.newHashMap();
            Map<String, Object> data = Maps.newHashMap();
            googleCloudMessage.put("data", data);
            if (!Strings.isNullOrEmpty(this.title)) {
                data.put("title", this.title);
            }
            if (!Strings.isNullOrEmpty(this.message)) {
                data.put("message", this.message);
            }
            if (!Strings.isNullOrEmpty(this.link)) {
                data.put("link", this.link);
            }
            if (!Strings.isNullOrEmpty(this.imageUrl)) {
                data.put("image_url", this.imageUrl);
            }
            googleCloudMessage.put("to", "/topics/global");

            String googleApiKey = jdbcTemplate.queryForObject("SELECT value from setting where name = ?", String.class, "google_api_key");
            try {
                HttpRequestWithBody request = Unirest.post("https://gcm-http.googleapis.com/gcm/send");
                String body = request.header("Authorization", "key=" + googleApiKey).header("Content-Type", "application/json").body(gson.toJson(googleCloudMessage)).asString().getBody();
                LOGGER.info("gcm result {}", body);
            } catch (UnirestException e) {
                System.out.println(e.getMessage());
                LOGGER.info("gcm error {}", e.getMessage());
            }
        });

        setResponsePage(NotificationLinkPage.class);
    }

}