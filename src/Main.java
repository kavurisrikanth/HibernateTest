import home.Accident;
import home.InsurancePayout;
import home.Person;
import home.Road;
import org.hibernate.HibernateException;
import org.hibernate.Metamodel;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.metamodel.EntityType;

import java.util.*;

public class Main {
    private static final SessionFactory ourSessionFactory;

    static {
        try {
            Configuration configuration = new Configuration();
            configuration.configure();

            ourSessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Session getSession() throws HibernateException {
        return ourSessionFactory.openSession();
    }

    public static void main(final String[] args) throws Exception {
        final Session session = getSession();
        try {
            System.out.println("querying all the managed entities...");
            final Metamodel metamodel = session.getSessionFactory().getMetamodel();
            for (EntityType<?> entityType : metamodel.getEntities()) {
                final String entityName = entityType.getName();
                final Query query = session.createQuery("from " + entityName);
                System.out.println("executing: " + query.getQueryString());
                for (Object o : query.list()) {
                    System.out.println("  " + o);
                }
            }

            writeData(session);
//            readData();
        } finally {
            session.close();
        }
    }

    private static void readData() {
        System.out.println(getAllPersons());
        System.out.println("*****");
        System.out.println(getPersonsByName("Bravo"));
        System.out.println("*****");
        System.out.println(getPersonsYoungerThan(40));
        System.out.println("*****");
        System.out.println(getPersonById(61));
        System.out.println(getPersonById(62));
        System.out.println(getPersonById(63));
        System.out.println(getPersonById(61));
    }

    public static void writeData(Session session) {
//        writePeople(session);
//        writeRoads(session);
//        writeAccidents(session);
//        writeInsurance(session);
    }

    /****
        Reading
     */
    public static List<Person> getAllPersons() {
        Query allPersonsQuery = getSession().getNamedQuery("Person_getAll");
        return allPersonsQuery.list();
    }

    public static List<Person> getPersonsByName(String name) {
        Query namedPersonQuery = getSession().getNamedQuery("Person_getByName");
        namedPersonQuery.setParameter("name", name);
        return namedPersonQuery.list();
    }

    public static List<Person> getPersonsYoungerThan(int age) {
        Query youngerThanPersonQuery = getSession().getNamedQuery("Person_ageLessThan");
        youngerThanPersonQuery.setParameter("age", age);
        return youngerThanPersonQuery.list();
    }

    public static Person getPersonById(int id) {
        return  getPersonsById(id).get(0);
    }

    public static List<Person> getPersonsById(int id) {
        Query personByIdQuery = getSession().getNamedQuery("Person_getById");
        personByIdQuery.setParameter("id", id);
        return personByIdQuery.list();
    }

    public static Road getRoadById(int id) {
        return  getRoadsById(id).get(0);
    }

    public static List<Road> getRoadsById(int id) {
        Query roadByIdQuery = getSession().getNamedQuery("Road_getById");
        roadByIdQuery.setParameter("id", id);
        return roadByIdQuery.list();
    }

    public static Accident getAccidentById(int id) {
        return getAccidentsById(id).get(0);
    }

    public static List<Accident> getAccidentsById(int id) {
        Query accidentByIdQuery = getSession().getNamedQuery("Accident_getById");
        accidentByIdQuery.setParameter("id", id);
        return accidentByIdQuery.list();
    }



    /****
     * Writing
     */

    private static void writeInsurance(Session session) {
        for (int i = 0; i < 30; i++) {
            Random rand = new Random();

            int accId = rand.nextInt(50) + 211;
            Accident accident = getAccidentById(accId);

            session.beginTransaction();
            InsurancePayout ip = new InsurancePayout();
            ip.setAccident(accident);
            ip.setAmount((rand.nextDouble() + 0.1) * 150000);
            session.save(ip);
            session.getTransaction().commit();
        }
    }

    private static void writeAccidents(Session session) {
        Random rand = new Random();
        for (int i = 0; i < 50; i++) {
            int personId = rand.nextInt(5) + 61;
            Person person = getPersonById(personId);

            int roadId = rand.nextInt(15) + 66;
            Road road = getRoadById(roadId);

            long ms = -946771200000L + (Math.abs(rand.nextLong()) % (70L * 365 * 24 * 60 * 60 * 1000));
            // Construct a date
            Date dt = new Date(ms);

            session.beginTransaction();
            Accident accident = new Accident();
            accident.setVictim(person);
            accident.setOn(road);
            accident.setDate(dt);
            session.save(accident);
            session.getTransaction().commit();
        }
    }

    private static void writeRoads(Session session) {
        String address = "Road number";
        session.beginTransaction();
        for (int i = 0; i < 15; i++) {
            Road road = new Road();
            road.setAddress(address + " " + i);
            session.save(road);
        }
        session.getTransaction().commit();
    }

    private static void writePeople(Session session) {
        String[] names = {"Alto", "Bravo", "Charlie", "Drax", "Elon"};
        int[] ages = {21, 56, 53, 21, 37};

        for (int i = 0; i < names.length; i++) {
            session.beginTransaction();
            Person person = new Person();
            person.setName(names[i]);
            person.setAge(ages[i]);
            session.save(person);
            session.getTransaction().commit();
        }
    }
}