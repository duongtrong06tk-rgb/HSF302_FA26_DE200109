package fu.de200109.util;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtil {
    private static final EntityManagerFactory EMF =
            Persistence.createEntityManagerFactory("hsf302FU");

    public JPAUtil() {
    }

    public static EntityManagerFactory getEmf() {
        return EMF;
    }

    public static void close() {
        if(EMF.isOpen()) {
            EMF.close();
        }
    }
}
