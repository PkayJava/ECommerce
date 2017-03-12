package com.angkorteam.ecommerce.page.discount;

import com.angkorteam.framework.wicket.markup.html.form.Button;
import com.angkorteam.framework.wicket.markup.html.form.Form;
import com.angkorteam.framework.wicket.markup.html.panel.TextFeedbackPanel;
import com.angkorteam.framework.jdbc.InsertQuery;
import com.angkorteam.framework.spring.NamedParameterJdbcTemplate;
import com.angkorteam.platform.Platform;
import com.angkorteam.platform.page.MBaaSPage;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.wicket.markup.html.border.Border;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.validation.validator.RangeValidator;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by socheatkhauv on 7/2/17.
 */
public class DiscountCouponGeneratePage extends MBaaSPage {

    private static final String ALPHABET = "ZAQWSXCDERFVBGTYHNMJUIKLOP";

    private static final String NUMBER = "1234567890";

    private String ecommerceDiscountId;

    private String type;
    private DropDownChoice<String> typeField;
    private TextFeedbackPanel typeFeedback;

    private Long quantity = 100l;
    private TextField<Long> quantityField;
    private TextFeedbackPanel quantityFeedback;

    private String prefix;
    private TextField<Long> prefixField;
    private TextFeedbackPanel prefixFeedback;

    private String suffix;
    private TextField<Long> suffixField;
    private TextFeedbackPanel suffixFeedback;

    private Integer length = 8;
    private TextField<Long> lengthField;
    private TextFeedbackPanel lengthFeedback;

    private Form<Void> form;
    private Button generateButton;
    private BookmarkablePageLink<Void> closeButton;

    @Override
    protected void doInitialize(Border layout) {
        add(layout);

        this.ecommerceDiscountId = getPageParameters().get("ecommerceDiscountId").toString("");

        this.form = new Form<>("form");
        layout.add(this.form);

        List<String> types = Arrays.asList("Number", "Alphabet", "Number & Alphabet");
        this.typeField = new DropDownChoice<>("typeField", new PropertyModel<>(this, "type"), types);
        this.typeField.setRequired(true);
        this.form.add(this.typeField);
        this.typeFeedback = new TextFeedbackPanel("typeFeedback", this.typeField);
        this.form.add(this.typeFeedback);

        this.quantityField = new TextField<>("quantityField", new PropertyModel<>(this, "quantity"));
        this.quantityField.setRequired(true);
        this.quantityField.add(RangeValidator.minimum(1L));
        this.form.add(this.quantityField);
        this.quantityFeedback = new TextFeedbackPanel("quantityFeedback", this.quantityField);
        this.form.add(this.quantityFeedback);

        this.lengthField = new TextField<>("lengthField", new PropertyModel<>(this, "length"));
        this.lengthField.setRequired(true);
        this.lengthField.add(RangeValidator.minimum(4));
        this.form.add(this.lengthField);
        this.lengthFeedback = new TextFeedbackPanel("lengthFeedback", this.lengthField);
        this.form.add(this.lengthFeedback);

        this.prefixField = new TextField<>("prefixField", new PropertyModel<>(this, "prefix"));
        this.form.add(this.prefixField);
        this.prefixFeedback = new TextFeedbackPanel("prefixFeedback", this.prefixField);
        this.form.add(this.prefixFeedback);

        this.suffixField = new TextField<>("suffixField", new PropertyModel<>(this, "suffix"));
        this.form.add(this.suffixField);
        this.suffixFeedback = new TextFeedbackPanel("suffixFeedback", this.suffixField);
        this.form.add(this.suffixFeedback);

        this.generateButton = new Button("generateButton");
        this.generateButton.setOnSubmit(this::generateButtonOnSubmit);
        this.form.add(this.generateButton);

        PageParameters parameters = new PageParameters();
        parameters.add("ecommerceDiscountId", ecommerceDiscountId);
        this.closeButton = new BookmarkablePageLink<>("closeButton", DiscountBrowsePage.class, parameters);
        this.form.add(this.closeButton);
    }

    private void generateButtonOnSubmit(Button button) {
        NamedParameterJdbcTemplate named = Platform.getBean(NamedParameterJdbcTemplate.class);
        InsertQuery insertQuery = new InsertQuery("ecommerce_discount_coupon");
        insertQuery.addValue("ecommerce_discount_coupon_id = :ecommerce_discount_coupon_id", "");
        insertQuery.addValue("ecommerce_discount_id = :ecommerce_discount_id", this.ecommerceDiscountId);
        insertQuery.addValue("code = :code", "");
        insertQuery.addValue("used = :used", false);
        if (this.prefix == null) {
            this.prefix = "";
        }
        if (this.suffix == null) {
            this.suffix = "";
        }
        long i = 1;
        while (i <= this.quantity) {
            try {
                Map<String, Object> param = insertQuery.getParam();
                param.put("ecommerce_discount_coupon_id", Platform.randomUUIDLong("ecommerce_discount_coupon"));
                String code;
                if (this.type.equals("Number")) {
                    code = RandomStringUtils.random(this.length, NUMBER);
                } else if (this.type.equals("Alphabet")) {
                    code = RandomStringUtils.random(this.length, ALPHABET);
                } else {
                    code = RandomStringUtils.random(this.length, ALPHABET + NUMBER);
                }
                param.put("code", this.prefix + code + this.suffix);
                named.update(insertQuery.toSQL(), param);
                i = i + 1;
            } catch (Throwable e) {
            }
        }
        PageParameters parameters = new PageParameters();
        parameters.add("ecommerceDiscountId", ecommerceDiscountId);
        setResponsePage(DiscountCouponBrowsePage.class, parameters);
    }

}
