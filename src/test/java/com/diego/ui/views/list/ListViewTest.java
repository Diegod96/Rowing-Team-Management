package com.diego.ui.views.list;

import com.diego.backend.entity.Rower;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.provider.ListDataProvider;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ListViewTest {

    @Autowired
    private ListView listView;

    @Test
    public void formShownWhenContactSelected() {
        Grid<Rower> grid = listView.grid;
        Rower firstRower = getFirstItem(grid);

        RowerForm form = listView.form;

        Assert.assertFalse(form.isVisible());
        grid.asSingleSelect().setValue(firstRower);
        Assert.assertTrue(form.isVisible());
        Assert.assertEquals(firstRower, form.binder.getBean());
    }
    private Rower getFirstItem(Grid<Rower> grid) {
        return( (ListDataProvider<Rower>) grid.getDataProvider()).getItems().iterator().next();
    }
}