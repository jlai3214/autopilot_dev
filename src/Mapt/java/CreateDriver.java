package Mapt.java;

/**
 * Selenium Singleton Class
 *
 * @author CarlCocchiaro
 *
 */
@SuppressWarnings("varargs")
public class CreateDriver {

    // constructor
    private CreateDriver() {
    }

    /**
     * getInstance method to retrieve active driver instance
     *
     * @return CreateDriver
     */
    public static CreateDriver getInstance() {
        if ( instance == null ) {
            instance = new CreateDriver();
        }

        return instance;
    }
}