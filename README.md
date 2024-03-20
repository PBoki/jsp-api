# Info
Provides easy access to bus information from [JSP](http://www.jsp.com.mk/PlanskiVozenRed.aspx). <br>
There is no site scraping. The data is fetched directly from [skopska trans info](http://info.skopska.mk/). <br>

# Example Usage

```java
import com.github.pboki.jspapi.JSP;

public class Main {

    public static void main(String[] args) {
        JSP jsp = new JSP().init();

        jsp.fetchInfo() // Fetch bus info.
                .join() // Blocking call
                .stream() // Stream the collection
                .filter(info -> info.stopId() == jsp.getStopByNumber(310).orElseThrow().id()) // Filter for specific bus stop
                .filter(info -> info.lineId() == jsp.getLineByNumber("5А").orElseThrow().id()) // Filter for specific bus number. Note that letters must be macedonian!
                .findAny() // Match any
                .ifPresentOrElse(info -> {
                    System.out.println("Bus Times: ");
                    for (int seconds : info.remainingTime()) {
                        System.out.println(String.format("%02d:%02d:%02d", seconds / 3600, (seconds % 3600) / 60, seconds % 60)); // Prints bus times in HH:mm:ss
                    }
                }, () -> System.out.println("Something went wrong!"));
    }

}
```