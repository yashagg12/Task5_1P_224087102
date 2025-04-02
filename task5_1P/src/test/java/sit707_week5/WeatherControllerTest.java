package sit707_week5;

import org.junit.*; // # unchanged – for JUnit annotations

public class WeatherControllerTest {

    private static WeatherController wController;           // # NEW: shared controller instance
    private static double[] temperatures;                   // # NEW: shared array to store hourly temps

    @BeforeClass
    public static void setUpBeforeClass() {                 // # NEW: Setup runs once before all tests
        System.out.println(">>> SETUP: Initialising WeatherController and reading temperature values...");
        wController = WeatherController.getInstance();      // # Moved here: init only once to avoid randomness
        int nHours = wController.getTotalHours();
        temperatures = new double[nHours];                  // # NEW: populate shared temp array
        for (int i = 0; i < nHours; i++) {
            temperatures[i] = wController.getTemperatureForHour(i + 1); // # Avoids repeating this in each test
        }
    }

    @AfterClass
    public static void tearDownAfterClass() {               // # NEW: Cleanup once after all tests
        System.out.println("<<< CLEANUP: Closing WeatherController...");
        if (wController != null) wController.close();       // # Moved here from each test
    }

    @Test
    public void testStudentIdentity() {
        String studentId = "224087102";                     // # FIXED: replaced null with actual ID
        Assert.assertEquals("224087102", studentId);        // # FIXED: validates ID
    }

    @Test
    public void testStudentName() {
        String studentName = "Yash";                        // # FIXED: replaced null with actual name
        Assert.assertEquals("Yash", studentName);           // # FIXED: validates name
    }

    @Test
    public void testTemperatureMin() {
        System.out.println("+++ testTemperatureMin +++");

        // Arrange
        double min = Double.MAX_VALUE;                      // # REFACTORED: calculate from cached array
        for (double temp : temperatures) {
            if (temp < min) min = temp;
        }

        // Act
        double cachedMin = wController.getTemperatureMinFromCache();

        // Assert
        Assert.assertEquals(min, cachedMin, 0.001);         // # REFACTORED: use assertEquals with delta
    }

    @Test
    public void testTemperatureMax() {
        System.out.println("+++ testTemperatureMax +++");

        // Arrange
        double max = Double.MIN_VALUE;                      // # REFACTORED: calculate from cached array
        for (double temp : temperatures) {
            if (temp > max) max = temp;
        }

        // Act
        double cachedMax = wController.getTemperatureMaxFromCache();

        // Assert
        Assert.assertEquals(max, cachedMax, 0.001);         // # REFACTORED: use assertEquals with delta
    }

    @Test
    public void testTemperatureAverage() {
        System.out.println("+++ testTemperatureAverage +++");

        // Arrange
        double sum = 0;
        for (double temp : temperatures) {
            sum += temp;
        }
        double average = sum / temperatures.length;         // # REFACTORED: calculate avg from shared array

        // Act
        double cachedAvg = wController.getTemperatureAverageFromCache();

        // Assert
        Assert.assertEquals(average, cachedAvg, 0.001);     // # REFACTORED: use assertEquals with delta
    }
}
