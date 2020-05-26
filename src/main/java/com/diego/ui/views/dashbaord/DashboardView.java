package com.diego.ui.views.dashbaord;

import com.diego.service.BoatService;
import com.diego.service.RowerService;
import com.diego.ui.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.ChartType;
import com.vaadin.flow.component.charts.model.DataSeries;
import com.vaadin.flow.component.charts.model.DataSeriesItem;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.Map;


@PageTitle("Dashboard | Team Management App")
@Route(value = "dashboard", layout = MainLayout.class)
public class DashboardView extends VerticalLayout {

    private final RowerService rowerService;
    private final BoatService boatService;


    public DashboardView(RowerService rowerService, BoatService boatService) {

        this.rowerService = rowerService;
        this.boatService = boatService;

        addClassName("dashboard-view");
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        add(
                getRowerStats(),
                getBoatsChart()
        );
    }

    private Component getBoatsChart() {
        Chart chart = new Chart(ChartType.PIE);

        DataSeries dataSeries = new DataSeries();
        Map<String, Integer> stats = boatService.getStats();
        stats.forEach((name, number) ->
                dataSeries.add(new DataSeriesItem(name, number)));

        chart.getConfiguration().setSeries(dataSeries);

        return chart;
    }

    private Span getRowerStats() {
        Span stats = new Span(rowerService.count() + " rowers");
        stats.addClassName("rower-stats");

        return stats;
    }
}
