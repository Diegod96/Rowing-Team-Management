package com.diego.ui.views.list;

import com.diego.backend.entity.Boat;
import com.diego.backend.entity.Rower;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;

import java.util.List;

public class RowerForm extends FormLayout {


    TextField firstName = new TextField("First name");
    TextField lastName = new TextField("Last name");
    EmailField email = new EmailField("Email");
    ComboBox<Rower.Year> year = new ComboBox<>("Year");
    TextField test = new TextField("2K Time");
    ComboBox<Boat> boat = new ComboBox<>("Boat");

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");

    Binder<Rower> binder = new BeanValidationBinder<>(Rower.class);

    public RowerForm(List<Boat> boats) {
        addClassName("contact-form");

        binder.bindInstanceFields(this);
        year.setItems(Rower.Year.values());
        boat.setItems(boats);
        boat.setItemLabelGenerator(Boat::getName);


        add(firstName,
                lastName,
                email,
                year,
                test,
                boat,
                createButtonsLayout());
    }

    public void setRower(Rower rower) {
        binder.setBean(rower);
    }

    private Component createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(click -> validateAndSave());
        delete.addClickListener(click -> fireEvent(new DeleteEvent(this, binder.getBean())));
        close.addClickListener(click -> fireEvent(new CloseEvent(this)));

        binder.addStatusChangeListener(evt -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save, delete, close);
    }

    private void validateAndSave() {
        if (binder.isValid()) {
            fireEvent(new SaveEvent(this, binder.getBean()));
        }
    }

    // Events
    public static abstract class RowerFormEvent extends ComponentEvent<RowerForm> {
        private Rower rower;

        protected RowerFormEvent(RowerForm source, Rower rower) {
            super(source, false);
            this.rower = rower;
        }

        public Rower getContact() {
            return rower;
        }
    }

    public static class SaveEvent extends RowerFormEvent {
        SaveEvent(RowerForm source, Rower rower) {
            super(source, rower);
        }
    }

    public static class DeleteEvent extends RowerFormEvent {
        DeleteEvent(RowerForm source, Rower rower) {
            super(source, rower);
        }

    }

    public static class CloseEvent extends RowerFormEvent {
        CloseEvent(RowerForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}