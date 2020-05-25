package com.diego.ui;

import com.diego.backend.entity.Boat;
import com.diego.backend.entity.Rower;
import com.diego.service.RowerService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

@Route("")
public class MainView extends VerticalLayout {

    Grid<Rower> grid = new Grid<>(Rower.class);
    TextField filterText = new TextField();
    private RowerService rowerService;

    public MainView(RowerService rowerService) {
        this.rowerService = rowerService;
        addClassName("list-view");
        setSizeFull();
        configureGrid();
        configureFilter();


        add(filterText, grid);
        updateList();
    }

    private void configureFilter() {
        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());
    }

    private void updateList() {
        grid.setItems(rowerService.findAll(filterText.getValue()));
    }

    private void configureGrid() {
        grid.addClassName("rower-grid");
        grid.setSizeFull();
        grid.removeColumnByKey("boat");
        grid.setColumns("firstName", "lastName", "email", "year", "test", "boat");
        grid.addColumn(rower -> {
           Boat boat = rower.getBoat();
           return boat == null ? "-": boat.getName();
        }).setHeader("Boat");

        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }
}


