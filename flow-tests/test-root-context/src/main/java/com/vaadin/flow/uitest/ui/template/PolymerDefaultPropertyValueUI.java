/*
 * Copyright 2000-2017 Vaadin Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.vaadin.flow.uitest.ui.template;

import com.vaadin.annotations.HtmlImport;
import com.vaadin.annotations.Synchronize;
import com.vaadin.annotations.Tag;
import com.vaadin.flow.html.Button;
import com.vaadin.flow.html.Div;
import com.vaadin.flow.template.PolymerTemplate;
import com.vaadin.flow.template.model.TemplateModel;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.PropertyDescriptor;
import com.vaadin.ui.PropertyDescriptors;
import com.vaadin.ui.UI;

public class PolymerDefaultPropertyValueUI extends UI {

    private static final PropertyDescriptor<String, String> msgDescriptor = PropertyDescriptors
            .propertyWithDefault("message", "");

    public interface MyModel extends TemplateModel {
        void setText(String text);

        void setName(String name);
    }

    @Tag("default-property")
    @HtmlImport("/com/vaadin/flow/uitest/ui/template/PolymerDefaultPropertyValue.html")
    public static class MyTemplate extends PolymerTemplate<MyModel> {

        public MyTemplate() {
            getModel().setText("foo");
            setMessage("updated-message");
        }

        @Synchronize("email-changed")
        public String getEmail() {
            return getElement().getProperty("email");
        }

        @Synchronize("message-changed")
        public String getMessage() {
            return get(msgDescriptor);
        }

        public void setMessage(String value) {
            set(msgDescriptor, value);
        }

    }

    @Override
    protected void init(VaadinRequest request) {
        MyTemplate template = new MyTemplate();
        template.setId("template");
        add(template);

        Button button = new Button("Show email value",
                event -> createEmailValue(template));
        button.setId("show-email");
        add(button);
    }

    private void createEmailValue(MyTemplate template) {
        Div div = new Div();
        div.setText(template.getEmail());
        div.setId("email-value");
        add(div);
    }

}
