import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class Main {

    void run() throws Exception {
        try {
            File file = new File("3course.xml");
            JAXBContext context = JAXBContext.newInstance(Timetable.class);

            Unmarshaller unmarshaller = context.createUnmarshaller();
            Timetable table = (Timetable) unmarshaller.unmarshal(file);
            table.show();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        new Main().run();
    }
}
