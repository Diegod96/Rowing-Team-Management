# Rowing-Team-Management
* Created a CRM application utilizing Java Spring Boot, Maven, and Vaddin framework.
* The list view lists out the rowers with information like name, 2k time, and what boat they are in.
* There is a dashboard view that produces a piechart of the distribution of rowers per boat.
* A User can add a rower by clicking the "Add Rower" button and filling out the information.

## Code & Resources Used
* **Java Version:** 12
* **Maven Verison:** 1.8
* **Vaadin Version:** 14.2.0
* **Spring Boot Version:** 2.2.0
* **Databases**: MySQL for production & H2 for development/testing

## Backend Portion
The backend end of this application contains the entities, repositories, security, and services

## Entities & Repositories

### Boat Entity
* The Boat entity contains relevant attributes for a boat like its name (Varsity 8, Lightweight 4+, etc.).
* The Boat's relationship to a rower is also defined here as you can have one boat to many rowers: 
```
@OneToMany(mappedBy = "boat", fetch = FetchType.EAGER)
    private List<Rower> rowers = new LinkedList<>();
```
* The ability to also fetch rowers from a boat is also defined here:
```
public List<Rower> getRowers() {
        return rowers;
    }
```

### Rower Entity
* The Rower entity has relevant information about a rower like their name, email, etc.
* The Rower entity also contains a getter to get the boat that a rower was assigned to 
```
    public Boat getBoat() {
        return boat;
    }
```

### Rower Repository
* The Rower Respository queries for a rower based on their first or last name:
```
    @Query("select r from Rower r " +
            "where lower(r.firstName) like lower(concat('%', :searchTerm, '%')) " +
            "or lower(r.lastName) like lower(concat('%', :searchTerm, '%'))") //
    List<Rower> search(@Param("searchTerm") String searchTerm); //
```

## Security
Spring Security is utilized here.

### UI Service Listener
* Implements VaadinServiceInitListener.
* Handles re-routing if a user tries to login with incorrect credentials:
```
private void authenticateNavigation(BeforeEnterEvent event) {
        if (!LoginView.class.equals(event.getNavigationTarget())
                && !SecurityUtils.isUserLoggedIn()) {
            event.rerouteTo(LoginView.class);
        }
    }
```

### Security Configuration
* Handles the login functionality for the application:
```
@Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .requestCache().requestCache(new CustomRequestCache())
                .and().authorizeRequests()
                .requestMatchers(SecurityUtils::isFrameworkInternalRequest).permitAll()

                .anyRequest().authenticated()

                .and().formLogin()
                .loginPage(LOGIN_URL).permitAll()
                .loginProcessingUrl(LOGIN_PROCESSING_URL)
                .failureUrl(LOGIN_FAILURE_URL)
                .and().logout().logoutSuccessUrl(LOGOUT_SUCCESS_URL);
    }
```
* Also allows access to static resources, bypassing Spring security.


### Security Utils
* SecurityUtils handles all static operations that have to do with security and querying rights from different beans of the UI.
* Tests if the request is an internal framework request:
```
    static boolean isFrameworkInternalRequest(HttpServletRequest request) {
        final String parameterValue = request.getParameter(ApplicationConstants.REQUEST_TYPE_PARAMETER);
        return parameterValue != null
                && Stream.of(ServletHelper.RequestType.values())
                .anyMatch(r -> r.getIdentifier().equals(parameterValue));
    }
```
* Tests if some user is authenticated:
```
    static boolean isUserLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null
                && !(authentication instanceof AnonymousAuthenticationToken)
                && authentication.isAuthenticated();
    }
```

## Services

### Boat Service
* Ability to find all boats
* get method to get stats about a boat like the name and rowers:
```
public Map<String, Integer> getStats() {
        HashMap<String, Integer> stats = new HashMap<>();
        findAll().forEach(boat ->
                stats.put(boat.getName(), boat.getRowers().size()));

        return stats;
    }
 ```
 ### Rower Service
 * Actions such as deleting and saving rowers to the database.:
 ```
    public void save(Rower rower) {
        if (rower == null) {
            LOGGER.log(Level.SEVERE,
                    "Rower is null. Are you sure you have connected your form to the application?");
            return;
        }
        rowerRepository.save(rower);
    }
 ```
 
 * Populates boat repository with appropiate boats.
 
 ## UI/Frontend
 
 ### Views
 * Contains views for the dashboard, list, rower form, and login page.
 * Dashboard view has component to create piechart:
 ```
     private Component getBoatsChart() {
        Chart chart = new Chart(ChartType.PIE);

        DataSeries dataSeries = new DataSeries();
        Map<String, Integer> stats = boatService.getStats();
        stats.forEach((name, number) ->
                dataSeries.add(new DataSeriesItem(name, number)));

        chart.getConfiguration().setSeries(dataSeries);

        return chart;
    }
 ```
 * List view has functionality like configuring the grid, updating the list, setting buttons & layout, etc.:
 ```
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
 ```
 * Rower form handles layout and actions for the rower form:
 ```
 // Events
    public static abstract class RowerFormEvent extends ComponentEvent<RowerForm> {
        private Rower rower;

        protected RowerFormEvent(RowerForm source, Rower rower) {
            super(source, false);
            this.rower = rower;
        }

        public Rower getRower() {
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
```

* Login view handles the login page such as layout and adding actions to the login buttons.
* The Main Layout imports CSS styling and created PWA:
```
@PWA(
        name = "Team Management App",
        shortName = "TMA",
        offlineResources = {
                "./styles/offline.css",
                "./images/offline.png"
        },
        enableInstallPrompt = false
)
@CssImport("./styles/shared-styles.css")
```

## Testing
* Unit test created with JUnit were developed to test the functionality of the Login Page, List View, and Rower Form.
* Here is an exmaple of a Unit test for the Rower Form:
```
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
 ```
 
 # TODO:
 * Work with Spring Security and the database and allow new users to create an account and edit.
 * Seeing that is more of an Admin kind of application, only a few pre determine users will have edit access while other will have just view access.
