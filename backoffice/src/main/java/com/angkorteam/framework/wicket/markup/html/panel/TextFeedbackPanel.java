package com.angkorteam.framework.wicket.markup.html.panel;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.ValidationErrorFeedback;
import org.apache.wicket.model.PropertyModel;

public class TextFeedbackPanel extends Label {

    private String message = "";

    private FormComponent<?> formComponent;

    public TextFeedbackPanel(String id, FormComponent<?> formComponent) {
        super(id);
        setOutputMarkupId(true);
        this.formComponent = formComponent;
        setDefaultModel(new PropertyModel<>(this, "message"));
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
        this.formComponent.add(new Behavior() {
            @Override
            public void onEvent(Component component, IEvent<?> event) {
                onEventFeedbackMessage(component, event);
            }

            @Override
            public void onComponentTag(Component component, ComponentTag tag) {
                onEventComponentTag(component);
            }
        });
    }

    protected void onEventComponentTag(Component component) {
        if (component.hasFeedbackMessage()) {
            FeedbackMessage feedbackMessage = component.getFeedbackMessages().first();
            feedbackMessage.markRendered();
            if (feedbackMessage.getMessage() instanceof ValidationErrorFeedback) {
                this.message = (String) ((ValidationErrorFeedback) feedbackMessage.getMessage()).getMessage();
            } else if (feedbackMessage.getMessage() instanceof String) {
                this.message = (String) feedbackMessage.getMessage();
            }
        } else {
            this.message = "";
        }
    }

    protected void onEventFeedbackMessage(Component component, IEvent<?> event) {
        if (component.hasFeedbackMessage()) {
            FeedbackMessage feedbackMessage = component.getFeedbackMessages().first();
            feedbackMessage.markRendered();
            if (feedbackMessage.getMessage() instanceof ValidationErrorFeedback) {
                this.message = (String) ((ValidationErrorFeedback) feedbackMessage.getMessage()).getMessage();
            } else if (feedbackMessage.getMessage() instanceof String) {
                this.message = (String) feedbackMessage.getMessage();
            }
        } else {
            this.message = "";
        }
    }

}