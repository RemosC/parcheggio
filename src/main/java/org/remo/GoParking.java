package org.remo;

import org.remo.dtos.CustomerDTO;
import org.remo.dtos.VehicleDTO;
import org.remo.exceptions.CantWayInException;
import org.remo.exceptions.CantWayOutException;
import org.remo.exceptions.TooManyVeichlesInException;
import org.remo.models.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class GoParking {

    public static void main(String[] args) {

        Map<Class<? extends Vehicle>, Integer> maxAvailableSeats = new HashMap<>();
        maxAvailableSeats.put(Car.class, 55);
        maxAvailableSeats.put(Roulotte.class, 10);

        // collego il parcheggio all'area del parcheggio
        ParkingAble parking = new Parking(new ParkingMap(maxAvailableSeats));

        setPriceListsProducts();

        trials(parking);
    }

    private static void setPriceListsProducts() {
        EntityManagerFactory emf = JPAUtil.getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        Product parcheggioBase = new Product("PBASE", "Parcheggio base", null, new BigDecimal(5));
        em.persist(parcheggioBase);
        Product parcheggioAvanzato = new Product("PADVANCED", "Parcheggio avanzato", null, new BigDecimal(7));
        em.persist(parcheggioAvanzato);

        Product lavaggioAutoEsterno = new Product("EXTERNAL_WASH", "Lavaggio Auto Esterno", null, new BigDecimal(10));
        Product lavaggioAutoInterno = new Product("INTERNAL_WASH", "Lavaggio Auto Interno", null, new BigDecimal(20));
        Product servizioNavetta = new Product("SHUTTLE_SERVICE", "Servizio Navetta", null, new BigDecimal(30));
        em.persist(lavaggioAutoEsterno);
        em.persist(lavaggioAutoInterno);
        em.persist(servizioNavetta);

        ProductGroup pg1 = new ProductGroup("LUX", "Lusso", null);
        ProductGroup pg2 = new ProductGroup("ELUX", "Extra Lusso", null);
        em.persist(pg1);
        em.persist(pg2);


        ProductProductGroup ppga1 = new ProductProductGroup(parcheggioBase, pg1, new BigDecimal(7));
        ProductProductGroup ppga2 = new ProductProductGroup(parcheggioAvanzato, pg1, new BigDecimal(9));
        ProductProductGroup ppgb1 = new ProductProductGroup(parcheggioBase, pg2, new BigDecimal(7));
        ProductProductGroup ppgb2 = new ProductProductGroup(parcheggioAvanzato, pg2, new BigDecimal(9));
        em.persist(ppga1);
        em.persist(ppga2);
        em.persist(ppgb1);
        em.persist(ppgb1);

        ProductProductGroup ppg1 = new ProductProductGroup(lavaggioAutoEsterno, pg1, new BigDecimal(18));
        ProductProductGroup ppg2 = new ProductProductGroup(lavaggioAutoEsterno, pg2, new BigDecimal(22));
        ProductProductGroup ppg3 = new ProductProductGroup(lavaggioAutoInterno, pg1, new BigDecimal(32));
        ProductProductGroup ppg4 = new ProductProductGroup(lavaggioAutoInterno, pg2, new BigDecimal(39));
        em.persist(ppg1);
        em.persist(ppg2);
        em.persist(ppg3);
        em.persist(ppg4);

        em.getTransaction().commit();
    }


    /**
     * Esegue i test
     *
     * @param parking
     */
    public static void trials(ParkingAble parking)  {
        VehicleDTO macchina1 = new VehicleDTO(null, "RM 88E6833", "ferrari", "C");
        VehicleDTO macchina2 = new VehicleDTO(null, "VE 1988493", "Mazda", "C");
        VehicleDTO macchina3 = new VehicleDTO(null, "SA F488493", "Maggiolino", "C");

        VehicleDTO roulotte1 = new VehicleDTO(null, "MI F488J44", "Roulotte Lux", "R");
        VehicleDTO roulotte2 = new VehicleDTO(null, "PA 5Jd8J44", "Roulotte Poor", "R");

        CustomerDTO customer1 = new CustomerDTO(null, "Paolo", "rossi", "CNDKSSJDJJFFJ",
                "paolo@rossi.it", false);
        CustomerDTO customer2 = new CustomerDTO(null, "Gianni", "Bianchi", "GNFJJFJEMEE",
                "gianni@bianchi.it", false);
        CustomerDTO customer3 = new CustomerDTO(null, "Marco", "Verdi", "CNDKJEJEJNNW",
                "marco@verdi.it", false);
        CustomerDTO customer4 = new CustomerDTO(null, "Franco", "Gialli", "MNNDOORDEE",
                "franco@gialli.it", false);

        try {

            /* entra la 1° macchina */
            parking.checkIn(macchina1, customer1);
            /* entra la 2° macchina */
            parking.checkIn(macchina2, customer2);
            /* entra la 3° macchina */
            parking.checkIn(macchina3, customer3);

            /* entra la 1° roulotte */
            parking.checkIn(roulotte1, customer1);

            /* esce la 2° macchina */
            parking.checkOut(macchina2, LocalDateTime.now().plusHours(8L));
            /* esce la 3° macchina */
            parking.checkOut(macchina3, LocalDateTime.now().plusHours(25L));


        } catch (CantWayInException | CantWayOutException | TooManyVeichlesInException e) {
            System.out.println("(ERRORE) " + e.getMessage());
        }
    }

}
