import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class Main {

    void run() throws Exception {
        try {
            File file = new File("3course.xml");
            JAXBContext timetableContext = JAXBContext.newInstance(Timetable.class);

            Unmarshaller timetableUnmarshaller = timetableContext.createUnmarshaller();
            Timetable table = (Timetable) timetableUnmarshaller.unmarshal(file);

            JAXBContext courseListContext = JAXBContext.newInstance(CourseInfoList.class);
            Unmarshaller courseListUnmarshaller = courseListContext.createUnmarshaller();

            CourseMap courseMap = new CourseMap();

            file = new File("sm_spec.xml");
            CourseInfoList smList = (CourseInfoList) courseListUnmarshaller.unmarshal(file);
            courseMap.addCourseInfoList(smList);

            file = new File("mm_spec.xml");
            CourseInfoList mmList = (CourseInfoList) courseListUnmarshaller.unmarshal(file);
            courseMap.addCourseInfoList(mmList);

            file = new File("sa_spec.xml");
            CourseInfoList saList = (CourseInfoList) courseListUnmarshaller.unmarshal(file);
            courseMap.addCourseInfoList(saList);

            for (Day day : table.getDays()) {
                for (TwoHourClass thclass : day.getTwoHourClasses()) {
                    for (Lesson lesson : thclass.getLessons()) {
                        lesson.fetchAdditionalCourseInfo(courseMap);
                    }
                }
            }

            table.show();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        new Main().run();
    }
}
