package witaxi;
import io.micronaut.runtime.Micronaut;
import blnk.witaxi.Library;
public class Application {

    public static void main(String[] args) {
        Library.someLibraryMethod();
        Micronaut.run(Application.class, args);
    }
}