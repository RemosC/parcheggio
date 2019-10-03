package org.remo.daos;

import org.remo.JPAUtil;

import org.remo.dtos.CustomerDTO;
import org.remo.dtos.RegisterOutDTO;
import org.remo.dtos.VehicleDTO;
import org.remo.models.Customer;
import org.remo.models.Product;
import org.remo.models.Register;
import org.remo.models.Vehicle;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;

public class RegisterDBDAO implements RegisterDAO {

    private EntityManagerFactory emf =  JPAUtil.getEntityManagerFactory();

    private EntityManager em;

    private PriceListResolverService priceResolver;

    public RegisterDBDAO() {
        em = emf.createEntityManager();
        priceResolver = new PriceListResolverService();
    }

    @Override
    public void registerIn(Customer customer, Vehicle vehicle) {
        em.getTransaction().begin();

        Register register = new Register();

        register.setCheckInDateTime(LocalDateTime.now());
        register.setCheckOutDateTime(null);
        register.setCustomer(customer);
        register.setAmount(null);
        register.setVehicle(vehicle);
        em.persist(register);

        em.getTransaction().commit();
    }

    @Override
    public RegisterOutDTO registerOut(Vehicle vehicle, LocalDateTime whenGoOut) {
        em.getTransaction().begin();
        if (whenGoOut==null)
            whenGoOut = LocalDateTime.now();

        Register register = getRegisterByVehicle(vehicle);
        if (register!=null) {
            register.setCheckOutDateTime(whenGoOut);

            register.setAmount(resolvePrice(register.getCheckInDateTime(), whenGoOut));
        }

        RegisterOutDTO registerOut = new RegisterOutDTO();
        registerOut.setCheckInDateTime(register.getCheckInDateTime());
        registerOut.setCheckOutDateTime(register.getCheckOutDateTime());
        registerOut.setAmount(register.getAmount());
        CustomerDTO customerDTO = new CustomerDTO(register.getCustomer().getId(),
                                                  register.getCustomer().getFirstname(),
                                                  register.getCustomer().getLastname(),
                                                  register.getCustomer().getSsn(),
                                                  register.getCustomer().getEmail(),
                                                  false
                                                  );
        VehicleDTO vehicleDTO = new VehicleDTO(vehicle.getId(), vehicle.getLicensePlate(), vehicle.getModel(), vehicle.getType());

        registerOut.setCustomer(customerDTO);
        registerOut.setVehicle(vehicleDTO);

        em.getTransaction().commit();

        return registerOut;
    }


    private BigDecimal resolvePrice(LocalDateTime in, LocalDateTime out) {
        BigDecimal result;
        long diff = ChronoUnit.HOURS.between(in, out);
        if (diff<24)
            result = priceResolver.getProductPrice("PBASE", "LUX");
        else
            result = priceResolver.getProductPrice("PADVANCED", "ELUX");
        return result;
    }


    /**
     * query JPQL
     *
     * @param vehicle
     * @return
     */
    public Register getRegisterByVehicle(Vehicle vehicle) {
        TypedQuery<Register> query =
                em.createQuery("Select r from Register r where r.vehicle=:vehicle and r.checkOutDateTime is null",
                        Register.class);
        query.setParameter("vehicle", vehicle);
        return query.getSingleResult();
    }

}
