package com.angkorteam.platform.page;

import com.angkorteam.framework.extension.wicket.AdminLTEPage;
import com.angkorteam.framework.extension.wicket.markup.html.form.Button;
import com.angkorteam.framework.extension.wicket.markup.html.form.Form;
import com.angkorteam.framework.extension.wicket.markup.html.panel.TextFeedbackPanel;
import com.angkorteam.platform.Platform;
import com.angkorteam.platform.Session;
import com.google.common.base.Strings;
import org.apache.wicket.NonResettingRestartException;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.Url;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.validation.ValidationError;

/**
 * Created by socheat on 10/23/16.
 */
public class LoginPage extends AdminLTEPage {

    private String login;
    private TextField<String> loginField;
    private TextFeedbackPanel loginFeedback;

    private String password;
    private PasswordTextField passwordField;
    private TextFeedbackPanel passwordFeedback;

    private Url originalUrl;

    private Form<Void> form;
    private Button loginButton;

    @Override
    protected void onInitialize() {
        super.onInitialize();

        this.originalUrl = RestartResponseAtInterceptPageException.getOriginalUrl();

        Label descriptionText = new Label("descriptionText", Platform.getSetting(SettingPage.DESCRIPTION_TEXT));
        add(descriptionText);

        Label environmentText = new Label("environmentText", Platform.getSetting(SettingPage.ENVIRONMENT_TEXT));
        add(environmentText);

        this.form = new Form<>("form");
        this.add(this.form);
        this.loginField = new TextField<>("loginField", new PropertyModel<>(this, "login"));
        this.loginField.setRequired(true);
        this.form.add(this.loginField);
        this.loginFeedback = new TextFeedbackPanel("loginFeedback", this.loginField);
        this.form.add(this.loginFeedback);

        this.passwordField = new PasswordTextField("passwordField", new PropertyModel<>(this, "password"));
        this.passwordField.setRequired(true);
        this.form.add(this.passwordField);
        this.passwordFeedback = new TextFeedbackPanel("passwordFeedback", this.passwordField);
        this.form.add(this.passwordFeedback);

        this.loginButton = new Button("loginButton");
        this.loginButton.setOnSubmit(this::loginButtonOnSubmit);
        this.form.add(this.loginButton);

        String link = Platform.getConfiguration("vendor_manual", "");
        ExternalLink vendorManual = new ExternalLink("vendorManual", link);
        vendorManual.setVisible(!Strings.isNullOrEmpty(link));
        add(vendorManual);

        if (AbstractAuthenticatedWebSession.get().isSignedIn()) {
            setResponsePage(getApplication().getHomePage());
        }
    }

    private void loginButtonOnSubmit(Button button) {
        boolean valid = Session.get().signIn(this.login, this.password);
        if (valid) {
            if (this.originalUrl != null) {
                String url = RequestCycle.get().getUrlRenderer().renderUrl(this.originalUrl);
                throw new NonResettingRestartException(url);
            } else {
                setResponsePage(getApplication().getHomePage());
            }
        } else {
            this.loginField.error(new ValidationError().addKey("incorrect"));
            this.passwordField.error(new ValidationError().addKey("incorrect"));
        }
    }

}
