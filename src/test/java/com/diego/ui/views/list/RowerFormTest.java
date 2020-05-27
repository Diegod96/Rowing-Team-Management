package com.diego.ui.views.list;

import com.diego.backend.entity.Boat;
import com.diego.backend.entity.Rower;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class RowerFormTest {
    private List<Boat> boats;
    private Rower diegoDelgado;
    private Boat boat1;
    private Boat boat2;

    @Before
    public void setupData() {
        boats = new ArrayList<>();
        boat1 = new Boat("Varsity 8");
        boat2 = new Boat("Varsity 4+");
        boats.add(boat1);
        boats.add(boat1);

        diegoDelgado = new Rower();
        diegoDelgado.setFirstName("Diego");
        diegoDelgado.setLastName("Delgado");
        diegoDelgado.setEmail("test@test.com");
        diegoDelgado.setYear(Rower.Year.Senior);
        diegoDelgado.setBoat(boat2);
    }

    @Test
    public void formFieldsPopulated() {
        RowerForm form = new RowerForm(boats);
        form.setRower(diegoDelgado);
        Assert.assertEquals("Diego", form.firstName.getValue());
        Assert.assertEquals("Delgado", form.lastName.getValue());
        Assert.assertEquals("test@test.com", form.email.getValue());
        Assert.assertEquals(boat2, form.boat.getValue());
        Assert.assertEquals(Rower.Year.Senior, form.year.getValue());
    }

//    @Test
//    public void saveEventHasCorrectValues() {
//        RowerForm form = new RowerForm(boats);
//        Rower rower = new Rower();
//        form.setRower(rower);
//
//        form.firstName.setValue("John");
//        form.lastName.setValue("Doe");
//        form.boat.setValue(boat1);
//        form.email.setValue("john@doe.com");
//        form.year.setValue(Rower.Year.Junior);
//
//        AtomicReference<Rower> savedContactRef = new AtomicReference<>(null);
//        form.addListener(RowerForm.SaveEvent.class, e -> {
//            savedContactRef.set(e.getRower());
//        });
//        form.save.click();
//        Rower savedRower = savedContactRef.get();
//
//        Assert.assertEquals("John", savedRower.getFirstName());
//        Assert.assertEquals("Doe", savedRower.getLastName());
//        Assert.assertEquals("john@doe.com", savedRower.getEmail());
//        Assert.assertEquals(boat1, savedRower.getBoat());
//        Assert.assertEquals(Rower.Year.Junior, savedRower.getYear());
//    }
//}
}