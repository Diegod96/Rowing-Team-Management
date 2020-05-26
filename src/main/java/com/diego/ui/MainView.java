package com.diego.ui;

import com.diego.backend.entity.Boat;
import com.diego.backend.entity.Rower;
import com.diego.service.BoatService;
import com.diego.service.RowerService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

@Route("")
@CssImport("./styles/shared-styles.css")
public class MainView extends VerticalLayout {

    private final RowerForm form;
    Grid<Rower> grid = new Grid<>(Rower.class);
    TextField filterText = new TextField();
    private RowerService rowerService;


    public MainView(RowerService rowerService, BoatService boatService) {
        this.rowerService = rowerService;
        addClassName("list-view");
        setSizeFull();
        configureGrid();
        getToolBar();

        form = new RowerForm(boatService.findAll());
        form.addListener(RowerForm.SaveEvent.class, this::saveRower);
        form.addListener(RowerForm.DeleteEvent.class, this::deleteRower);
        form.addListener(RowerForm.CloseEvent.class, e -> closeEditor());

        Div content = new Div(grid, form);
        content.addClassName("content");
        content.setSizeFull();

        add(getToolBar(), content);
        updateList();
        closeEditor();
    }

    private void deleteRower(RowerForm.DeleteEvent evt) {
        rowerService.delete(evt.getContact());
        updateList();
        closeEditor();
    }

    private void saveRower(RowerForm.SaveEvent evt) {
        rowerService.save(evt.getContact());
        updateList();
        closeEditor();
    }


    private HorizontalLayout getToolBar() {
        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addRowerButton = new Button("Add Rower", click -> addRower());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addRowerButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void addRower() {
        grid.asSingleSelect().clear();
        editRower(new Rower());
    }

    private void updateList() {
        grid.setItems(rowerService.findAll(filterText.getValue()));
    }

    private void configureGrid() {
        grid.addClassName("rower-grid");
        grid.setSizeFull();
        grid.removeColumnByKey("boat");
        grid.setColumns("firstName", "lastName", "email", "year", "test");
        grid.addColumn(rower -> {
           Boat boat = rower.getBoat();
           return boat == null ? "-": boat.getName();
        }).setHeader("Boat");

        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(evt -> editRower(evt.getValue()));
    }

    private void editRower(Rower rower) {
        if (rower == null) {
            closeEditor();
        } else {
            form.setRower(rower);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        form.setRower(null);
        form.setVisible(false);
        removeClassName("editing");
    }
}


